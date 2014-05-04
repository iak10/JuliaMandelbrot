package Project.JuliaMandelbrot;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



class ApplicationJOrbit extends JFrame {
	ApplicationM mandelbrotCopy; // Global variable used to reference the JFrame holding the Mandelbrot image
	ApplicationJ juliaCopy; // Global variable used to reference the JFrame holding the Julia animation
	private static final int H = 301; // Height of window
	private static final int W = 401; // Width of window
	private Color previousColour = new Color(00, 00, 00);
	private Color currentColour = new Color(00, 00, 00);
	private Color backgroundColour = new Color(240, 240, 240); // set background of JFrame
	private double firstChangex = 0; // Used when drawing the Julia image, holds the position on line where current colour starts
	private double x, y; // The mathematical x and y values corresponding to image pixels
	private final double minXDefault = -2; // default lower bound of x-range plotted
	private final double maxXDefault = 2;  // default upper bound of x-range plotted
	private final double minYDefault = -1.5; // default lower bound of y-range plotted
	private final double maxYDefault = 1.5; // default upper bound of y-range plotted
	private double minX = minXDefault; // lower bound of x-range plotted, may be changed by user zooming in
	private double maxX = maxXDefault; // upper bound of x-range plotted, may be changed by user zooming in
	private double minY = minYDefault; // lower bound of y-range plotted, may be changed by user zooming in
	private double maxY = maxYDefault; // upper bound of y-range plotted, may be changed by user zooming in
	private int maxIterations = 170; // maximum iterations for each complex number, if it hasn't diverged it is assumed it won't
	private int xMargin = 155; // gap between the left side of JFrame and the plotted image
	private int yMargin = 115;  // gap between lower edge of plotted image and bottom of the JFrame
	private int topOffset = 150; // gap between top of JFrame and the plotted image
	private int rightMargin = 25; // gap between right edge of plotted image and the edge of the JFrame
	private int yAxisGap = 8; // gap between y-axis and plotted image
	private int xAxisGap = 8; // gap between x-axis and plotted image
	private double imC = 0; // imaginary component of the current value of complex number c
	private double reC = 0; // real component of the current value of complex number c
	private Transaction theAL = new Transaction();
	boolean animationStart = false;
	private boolean traceRunning = false;
	int delay = 240;
	private BufferedImage theAI, theAI2, theAIToSave;
	private Graphics2D theAG, theAG2, theAGToSave;
	private Color[] juliaColours = {new Color(36, 159, 120), 
			new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50), new Color(255,255,255)};
	private int[] juliaCutOffs = {2,3,4,5,7,9,11,14, 17, 25, 50, 58};
	private final JTextField theRealInput = new JTextField(); 
	private final JTextField theIminput = new JTextField();  
	private final JTextField theRealintputaOrbit = new JTextField(); 
	private final JTextField theIminputOrbit = new JTextField();  
	private JLabel label1 = new JLabel(" x =");
	private JLabel label2 = new JLabel(" y =");
	private JLabel label3 = new JLabel(" c = x + iy");
	private JLabel label1Orbit = new JLabel(" x =");
	private JLabel label2Orbit = new JLabel(" y =");
	private JLabel label3Orbit = new JLabel(" z = x + iy");
	private double[] nextPoint = new double[2];
	private Iterator iterator;
	private AxisWiz axisWiz;
	private JMenuBar menubar = new JMenuBar();
	private JMenu file;
	private JMenu zoom, axes, trace;
	private JMenuItem save, exit, setRanges, normal, show, hide, showCurrent, showAll, saveTraceData;
	private boolean showAllPoints1 = false;
	private boolean showAxis = false;
	private boolean firstDisplay = true;
	private JLabel[] axisLabel = {new JLabel(String.valueOf(maxY)), new JLabel(String.valueOf((maxY + minY)/2)), 
			new JLabel(String.valueOf(minY)), new JLabel(String.valueOf(minX)),
			new JLabel(String.valueOf((maxX + minX)/2)), new JLabel(String.valueOf(maxX))};  
	String outputC;
	private boolean zoomSelect = false;
	private int oldXPixel = 0, oldYPixel = 0, newXPixel = 0, newYPixel = 0;
	JOptionPane frame = new JOptionPane();
	BufferedWriter textOut;
	JButton a;
	JButton h;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	boolean fromAnimation = false;
	ArrayList<double[]> traceData = new ArrayList<double[]>();
	MyJOCanvas canvas = new MyJOCanvas();

	public ApplicationJOrbit(ApplicationM mandelbrot, ApplicationJ julia) {
		
		mandelbrotCopy = mandelbrot;	
		juliaCopy = julia;
		setLocation(930, 0);
		setSize(W + xMargin + rightMargin, H + yMargin + topOffset ); // Size of drawing area
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setResizable(false);	
		setTitle("Julia set orbit plotter");
		Animate animation = new Animate();
		Container cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(backgroundColour);

		a = new JButton("Restart");
		a.setBounds(10, 1 * 50 + 90  , 95, 40);
		a.addActionListener(theAL);
		cp.add(a);
		a.setEnabled(false);

		JButton b = new JButton("Stop");
		b.setBounds(10, 2 * 50 + 90 , 95, 40);
		b.addActionListener(theAL);
		cp.add(b);

		JButton e = new JButton("Faster");
		e.setBounds(10, 3 * 50 + 120, 95, 40);
		e.addActionListener(theAL);
		cp.add(e);

		JButton f = new JButton("Slower");
		f.setBounds(10, 4 * 50 + 120, 95, 40);
		f.addActionListener(theAL);
		cp.add(f);

		JButton g = new JButton("Draw");
		g.setBounds(140, 50 , 95, 40);
		g.addActionListener(theAL);
		cp.add(g);

		h = new JButton("Trace Orbit");
		h.setBounds(140, topOffset + H + yMargin - 100 , 115, 40);
		h.addActionListener(theAL);
		cp.add(h);
		h.setEnabled(false);

		JButton i = new JButton("Get C");
		i.setBounds(365, 10 , 70, 30);
		i.addActionListener(theAL);
		cp.add(i);
		
		canvas.setBounds(xMargin, topOffset - 48, W, H );
		cp.add(canvas);

		theRealInput.setBounds(290,55,80,30);
		cp.add(theRealInput);	 

		theIminput.setBounds(440,55,80,30);
		cp.add(theIminput);

		label1.setBounds(250,50,55,30);
		label1.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label1);

		label2.setBounds(400,50,55,30);
		label2.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label2);

		label3.setBounds(250,2,100,30);
		label3.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label3);

		theRealintputaOrbit.setBounds(310,topOffset + H + yMargin - 90,80,30);
		cp.add(theRealintputaOrbit);	 

		theIminputOrbit.setBounds(460,topOffset + H + yMargin - 90,80,30);
		cp.add(theIminputOrbit);

		label1Orbit.setBounds(270,topOffset + H + yMargin - 93 ,50,30);
		label1Orbit.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label1Orbit);

		label2Orbit.setBounds(420,topOffset + H + yMargin - 93,50,30);
		label2Orbit.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label2Orbit);

		label3Orbit.setBounds(270,topOffset + H + yMargin - 133,100,30);
		label3Orbit.setFont(new Font("Serif", Font.PLAIN, 20));
		cp.add(label3Orbit);		 

		iterator = new Iterator(maxIterations, juliaColours, juliaCutOffs);
		axisWiz = new AxisWiz(W, H);

		axisWiz.placeAxisLables(this, axisLabel, xMargin, topOffset);

		axisWiz.setAxisLabels(axisLabel, maxY, (maxY + minY)/2, 
				minY, minX, (maxX + minX)/2, maxX);

		menubar = new JMenuBar();
		setJMenuBar(menubar);

		file = new JMenu("File");
		zoom = new JMenu("Zoom");
		axes = new JMenu("Axes");
		trace = new JMenu("Trace");

		menubar.add(file);
		menubar.add(zoom);
		menubar.add(axes);
		menubar.add(trace);

		save = new JMenuItem("Save Image");
		exit = new JMenuItem("Exit");
		setRanges = new JMenuItem("Select Area With Mouse");
		normal = new JMenuItem("Normal");
		show = new JMenuItem("Show");
		hide = new JMenuItem("Hide");
		showCurrent = new JMenuItem("Show Current Points");
		showAll = new JMenuItem("Show All Points");
		saveTraceData = new JMenuItem("Save Trace Data");

		file.add(save);
		file.add(saveTraceData);
		file.add(exit);
		zoom.add(setRanges);
		zoom.add(normal);
		axes.add(show);
		axes.add(hide);
		trace.add(showCurrent);
		trace.add(showAll);

		//Radio Buttons
		JRadioButton mandelbrotGet = new JRadioButton("from mandelbrot");
		mandelbrotGet.setSelected(true);

		JRadioButton animationGet = new JRadioButton("from animation");
		animationGet.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(mandelbrotGet);
		group.add(animationGet);

		mandelbrotGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fromAnimation = false;
			}
		});

		animationGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fromAnimation = true;
			}
		});

		mandelbrotGet.setBounds(450, 3, 130, 20);
		animationGet.setBounds(450, 23, 130, 20);

		cp.add(mandelbrotGet);
		cp.add(animationGet);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// copy visual information to the buffered image for saving

				theAIToSave = (BufferedImage) createImage(W + 130, H + 100);
				theAGToSave  = theAIToSave.createGraphics();
				theAGToSave.drawImage(theAI2, 80, 50, null);
				outputC = "C = " + String.valueOf(reC) + " + " + String.valueOf(imC) + "i";	
				theAGToSave.drawString(outputC, 10,  20);
				if(showAxis) 
				{
					axisWiz.drawAxis(theAGToSave, 80, 50, 8, 8);
					theAGToSave.drawString(axisWiz.axisLabelNumber(maxY), 0, 53);
					theAGToSave.drawString(axisWiz.axisLabelNumber((minY + maxY)/2),0, 53 + H/2);
					theAGToSave.drawString(axisWiz.axisLabelNumber(maxY),0, 53 + H);
					theAGToSave.drawString(axisWiz.axisLabelNumber(minX), 47, 50 + H + 32);
					theAGToSave.drawString(axisWiz.axisLabelNumber((minX + maxX)/2), 47 + W/2, 50 + H + 32);
					theAGToSave.drawString(axisWiz.axisLabelNumber(maxX), 47 + W, 50 + H + 32);
				}


				// save buffer showing trace points (if any)
				try {
					ImageIO.write(theAIToSave, "png", new File("orbit " + processDate(new Date(), dateFormat) + ".png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		saveTraceData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// open new file for subsequent trace data
				try {
					textOut = new BufferedWriter(new FileWriter("trace" + processDate(new Date(), dateFormat) + ".txt"));
					textOut.write("Draw: c  = " + reC + " + " + imC +  " i " + new Date());
					textOut.newLine();
					for(int i = 0; i < traceData.size(); i++)
					{
						textOut.write("Z = " + traceData.get(i)[0] + " " + traceData.get(i)[1] + " i " );
						textOut.newLine();
					}
					textOut.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		showAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAllPoints1 = true;
			}
		});

		showCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAllPoints1 = false;
			}
		});

		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAxis = true;
				axisWiz.setVisibility(axisLabel, true);
				repaint();
			}
		});

		hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAxis = false;
				axisWiz.setVisibility(axisLabel, false);
				repaint();
			}
		});  	  

		normal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				minX = minXDefault;
				maxX = maxXDefault;
				minY = minYDefault;
				maxY = maxYDefault;
				axisWiz.setAxisLabels(axisLabel, maxY, (maxY + minY)/2, 
						minY, minX, (maxX + minX)/2, maxX);
				background();
				animationStart = true;
				repaint();
			}
		}); 

		setRanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoomSelect = true;
			}
		});
		setVisible(true);	 		
	} // end of constructor


	class Transaction implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			String actionIs = ae.getActionCommand();
			if (actionIs.equals("Restart"))
			{ 
				traceRunning = true;
			} 
			if (actionIs.equals("Stop"))
			{  
				traceRunning = false;
			}
			if (actionIs.equals("Faster"))
			{
				if (delay > 40)
					delay -= 10;
				System.out.println(delay);
			}
			if (actionIs.equals("Slower"))
			{
				delay += 10;
			}	 
			if (actionIs.equals("Get C"))
			{
				if(!fromAnimation)
				{
					theRealInput.setText(mandelbrotCopy.getReCString());
					theIminput.setText(mandelbrotCopy.getImCString());	 
				}else
				{
					double re = Math.round(juliaCopy.getRe()*1000000);
					double im = Math.round(juliaCopy.getIm()*1000000);
					theRealInput.setText(Double.toString(re/1000000));
					theIminput.setText(Double.toString(im/1000000));	 
				}
			}	 
			if (actionIs.equals("Draw"))
			{
				firstDisplay = false;
				traceData.clear();
				boolean validInput = true;
				if (theRealInput.getText().length() ==  0 && theIminput.getText().length()  == 0)
				{
					theRealInput.setText(mandelbrotCopy.getReCString());
					theIminput.setText(mandelbrotCopy.getImCString());	    		
				}
				if (theRealInput.getText().length() ==  0) theRealInput.setText("0");
				if (theIminput.getText().length() ==  0) theIminput.setText("0");    		    	  

				try {
					reC = Double.parseDouble(theRealInput.getText());
					imC = Double.parseDouble(theIminput.getText());
					System.out.println("C = " + reC + "  " + imC + "i");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Please Enter A Valid Number");
					validInput = false;
				}
				if (validInput)
				{
					animationStart = true;
					background();
					h.setEnabled(true);
					repaint(xMargin, topOffset, W, H);	
				}
			}	
			if (actionIs.equals("Trace Orbit"))
			{
				traceData.clear();
				boolean validInput = true;
				try {
					nextPoint[0] = Double.parseDouble(theRealintputaOrbit.getText());
					nextPoint[1] = Double.parseDouble(theIminputOrbit.getText());
					traceData.add(nextPoint);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Please Enter A Valid Number");
					validInput = false;
				}
				if (validInput)
				{
					traceRunning = true;
					a.setEnabled(true);
					newXPixel = (int) Math.round(((nextPoint[0] - minX)*(W/(maxX - minX))));
					newYPixel = (int) Math.round(((maxY - nextPoint[1])*(H/(maxY - minY))));
					repaint();
				}
			}	
		}
	}

	class Animate implements Runnable
	{

		private Thread theAnimation;
		public Animate()
		{
			theAnimation = new Thread(this);
			theAnimation.start();
		}

		public void run() {
			while( true )
			{
				try
				{				
					if (animationStart || zoomSelect )
					{
						canvas.redisplay();		
						animationStart = false;
					}
					if (traceRunning)
					{
						oldXPixel = newXPixel;
						oldYPixel = newYPixel;
						nextPoint = nextPoint(reC,imC,nextPoint[0],nextPoint[1]);
						newXPixel = (int) Math.round(((nextPoint[0] - minX)*(W/(maxX - minX))));
						newYPixel = (int) Math.round(((maxY - nextPoint[1])*(H/(maxY - minY))));
						canvas.renewPoints(oldXPixel,oldYPixel,newXPixel,newYPixel);
					}

					Thread.sleep(delay);				
				}
				catch (InterruptedException e) {}
			}	
		}	
	}

	public void update(Graphics g)
	{
		paint((Graphics2D) g);
	}

	public void paint(Graphics g) // When 'Window' is first
	{ // shown or damaged
		super.paint(g);
		System.out.println("paint invoked");
		if (firstDisplay)
		{  
			((Graphics2D) g).setPaint(Color.BLACK);
			((Graphics2D) g).fill( new Rectangle2D.Double( xMargin, topOffset, W, H ) );	
		}
		if (showAxis)
		{
			axisWiz.drawAxis((Graphics2D) g, xMargin, topOffset, xAxisGap, yAxisGap);
		}	
	}
	
	public void reDisplayFrame()
	{
		repaint();
	}

	public void reDrawJulia(Graphics2D g) // Re draw contents  
	{
		for(double yPixel = 0; yPixel < H; yPixel++)
		{
			for(double xPixel = 0; xPixel < W; xPixel++)
			{
				x = (minX)+(xPixel*((maxX - minX)/W));
				y = (maxY)-(yPixel*((maxY - minY)/H));
				previousColour = currentColour;		
				currentColour = iterator.colourPicker(reC, imC, x, y);
				if(! previousColour.equals(currentColour) || xPixel == W - 1)
				{
					g.draw(new Line2D.Double(firstChangex, yPixel, xPixel, yPixel));
					firstChangex = xPixel % (W - 1);
					g.setPaint(currentColour);
				}				
			}
		}
	}

	public double[] nextPoint(double x, double y, double a, double b)
	{
		double[] array = new double[2];
		array[0] = (a*a) - (b*b) + x; // real part of next iterate
		array[1] =(2*a*b) + y;        // imaginary part of next iterate
		traceData.add(array);
		// stop the animation if the point goes off the full-sized Julia plot
		if (array[0] > maxXDefault || array[0] < minXDefault ||array[1] > maxYDefault || array[1] < minYDefault) traceRunning = false;
		return array;	// return the new point	
	}

	public void background() 
	{  // re-plots the Julia set on both alternate images
		theAI = (BufferedImage) createImage(W, H);
		theAI2 = (BufferedImage) createImage(W, H);
		theAG = theAI.createGraphics();	
		theAG2 = theAI2.createGraphics();	
		reDrawJulia(theAG);
		reDrawJulia(theAG2);
	}

	public String processDate(Date inputDate, DateFormat format) 
	{ // formats date and time for output in text file
		String dateString = format.format(inputDate);
		dateString = dateString.replace(':', ' ');
		dateString = dateString.replace('/', ' ');
		return dateString;
	}
	
	public class MyJOCanvas extends JPanel   {
		private int mouseX, mouseY;
		private int mouseXZoom, mouseYZoom;
		private double xZoom, yZoom;
		private int xDrag, yDrag;
		private boolean dragging = false;

		public MyJOCanvas()
		{
			addMouseListener(new MouseAdapter() 		
			{
				public void mousePressed (MouseEvent e)
				{	    

					mouseX = e.getX();
					mouseY = e.getY();
					if (mouseInRange(mouseX, mouseY))
					{
						x = (minX)+(mouseX*((maxX - minX)/W));
						y = (maxY)-(mouseY*((maxY - minY)/H));

						if(!zoomSelect)
						{	           
							double re = Math.round(x*1000000);
							double im = Math.round(y*1000000);   
							theRealintputaOrbit.setText(String.valueOf(re/1000000));
							theIminputOrbit.setText(String.valueOf(im/1000000)); 
						}
					}
					dragging = false;
				}
				public void mouseReleased (MouseEvent e)
				{
					if(y - yZoom != 0 && x - xZoom != 0 && zoomSelect && mouseInRange(e.getX(), e.getY()))
					{
						System.out.println("Mouse released");
						mouseXZoom = e.getX();
						mouseYZoom = e.getY();
						xZoom = (minX)+(mouseXZoom*((maxX - minX)/W));
						yZoom = (maxY)-(mouseYZoom*((maxY - minY)/H));

						minX = Math.min(x, xZoom);
						maxX = Math.max(x, xZoom);
						minY = Math.min(y, yZoom);
						maxY = Math.max(y, yZoom);

						axisWiz.setAxisLabels(axisLabel, maxY, (maxY + minY)/2, 
								minY, minX, (maxX + minX)/2, maxX);
						background();
						//repaint();
						reDisplayFrame();
					}
					zoomSelect = false;
					dragging = false;   	
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() 
			{
				public void mouseDragged (MouseEvent e)
				{
					xDrag = e.getX();
					yDrag = e.getY();
					if (mouseInRange(xDrag, yDrag))
					{
						dragging = true;
					}
					else
					{
						dragging = false;
						zoomSelect = false;
					} 
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
            super.paintComponents(g);
            if(showAllPoints1 == false)
    		{
    			g.drawImage( theAI,  0,  0,  this);
    		}else
    		{
    			g.drawImage( theAI2,  0,  0,  this);
    		}

    		if(traceRunning) 
    		{
    			plotPoint((Graphics2D) g, newXPixel, newYPixel);
    		}  
            System.out.println("Paint component invoked");
            if(zoomSelect && dragging)
    		{
    			((Graphics2D) g).setPaint(Color.WHITE);
    			g.drawRect(Math.min(mouseX, xDrag), Math.min(mouseY, yDrag), Math.abs(mouseX - xDrag), Math.abs(mouseY - yDrag));
    		}
        }
		
		public boolean mouseInRange(int mouseXp, int mouseYp)
		{
			return (mouseXp <= W && mouseYp <= H && mouseXp >= 0 && mouseYp >= 0);
		}
		
		public void renewPoints(int oldXPixel,int oldYPixel,int newXPixel,int newYPixel)
		{
			repaint(Math.min(oldXPixel, newXPixel)-3,Math.min(oldYPixel, newYPixel)-3,Math.abs(newXPixel-oldXPixel)+7,Math.abs(newYPixel-oldYPixel)+7);
		}
		
		public void plotPoint(Graphics2D g, int xPixel, int yPixel)
		{
			g.setPaint(new Color(255, 255, 255)); // plot current iterate small white cross
			g.drawLine(xPixel-3, yPixel, xPixel + 3, yPixel);
			g.drawLine(xPixel, yPixel - 3, xPixel, yPixel + 3);	
			theAG2.setPaint(new Color(255, 255, 255)); // record the point as a single pixel on the second alternate image
			theAG2.drawLine(xPixel, yPixel, xPixel, yPixel);
		}
		
		public void redisplay()
		{
			repaint();
		}
	}
	
}