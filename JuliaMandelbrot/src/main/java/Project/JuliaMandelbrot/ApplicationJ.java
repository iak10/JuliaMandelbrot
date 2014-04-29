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
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class ApplicationJ extends JFrame {
	ApplicationM mandelbrotCopy; // Global variable used to reference the JFrame holding the Mandelbrot image
	double[] xArray; // Array holding x (real) components of animation values of c
	double[] yArray; // Array holding y (imaginary) components of animation values of c
	int topIndex; // top index of arrays xArray and yArray
	private static final int H = 301; // Height of window
	private static final int W = 401; // Width of window
	private Color previousColour = new Color(00, 00, 00);
	private Color currentColour = new Color(00, 00, 00);
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
	int maxIterations = 100; // maximum iterations for each complex number, if it hasn't diverged it is assumed it won't
	private int xMargin = 150; // gap between the left side of JFrame and the plotted image
	private int yMargin = 40; // gap between lower edge of plotted image and botto of the JFrame
	private int topOffset = 93
			; // gap between top of JFrame and the plotted image
	private int rightMargin = 30; // gap between right edge of plotted image and the edge of the JFrame
	private int yAxisGap = 8; // gap between y-axis and plotted image
	private int xAxisGap = 8; // gap between x-axis and plotted image
	private double imC = 0; // imaginary component of the current value of complex number c
	private double reC = 0; // real component of the current value of complex number c  
	private int index = 0;
	private Transaction theAL = new Transaction();
	boolean animationStart = false;
	boolean firstDisplay = true;
	int delay = 120;
	boolean forward = true;
	private BufferedImage theAI;
	private Graphics2D theAG;
	private BufferedImage theAIToSave;
	private Graphics2D theAGToSave;
	private Color[] juliaColours = {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50)};
	private int[] juliaCutOffs = {2,3,4,5,7,9,11,14, 17, 25, 50, 58};  

	private Iterator iterator;
	private AxisWiz axisWiz;

	private int mouseX;
	private int mouseY;

	private JMenuBar menubar = new JMenuBar();
	private JMenu file;
	private JMenu zoom;
	private JMenu axes;
	private JMenuItem save;
	private JMenuItem exit;
	private JMenuItem setRanges;
	private JMenuItem normal;
	private JMenuItem show;
	private JMenuItem hide;
	private boolean showAxis = false;
	private JLabel[] axisLabel = {new JLabel(String.valueOf(maxY)), new JLabel(String.valueOf((maxY + minY)/2)), 
			new JLabel(String.valueOf(minY)), new JLabel(String.valueOf(minX)),
			new JLabel(String.valueOf((maxX + minX)/2)), new JLabel(String.valueOf(maxX))};  
	String outputC;
	private boolean zoomSelect = false;
	JOptionPane frame = new JOptionPane();
	JButton c = new JButton("Forward");
	JButton d = new JButton("Back");
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	MyJCanvas canvas = new MyJCanvas();


	public ApplicationJ(ApplicationM mandelbrot) {

		mandelbrotCopy = mandelbrot;		
		setLocation(930, 580);
		setSize(W + xMargin + rightMargin, H + yMargin + topOffset ); // Size of drawing area
		setTitle("Julia set animation");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Animate animation = new Animate();
		Container cp = getContentPane();
		cp.setLayout(null);
		
		canvas.setBounds(xMargin, topOffset - 48, W, H );
		cp.add(canvas);
		
		JButton a = new JButton("Start");
		a.setBounds(10, 1 * 40 + 25, 95, 30);
		a.addActionListener(theAL);
		cp.add(a);

		JButton b = new JButton("Stop");
		b.setBounds(10, 2 * 40 + 25, 95, 30);
		b.addActionListener(theAL);
		cp.add(b);


		c.setBounds(10, 3 * 40 + 25, 95, 30);
		c.addActionListener(theAL);
		c.setEnabled(false);
		cp.add(c);


		d.setBounds(10, 4 * 40 + 55, 95, 30);
		d.addActionListener(theAL);
		d.setEnabled(false);
		cp.add(d);

		JButton e = new JButton("Faster");
		e.setBounds(10, 5 * 40 + 55, 95, 30);
		e.addActionListener(theAL);
		cp.add(e);

		JButton f = new JButton("Slower");
		f.setBounds(10, 6 * 40 + 55, 95, 30);
		f.addActionListener(theAL);
		cp.add(f);

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

		menubar.add(file);
		menubar.add(zoom);
		menubar.add(axes);

		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		setRanges = new JMenuItem("Select Area With Mouse");
		normal = new JMenuItem("Normal");
		show = new JMenuItem("Show");
		hide = new JMenuItem("Hide");

		file.add(save);
		file.add(exit);
		zoom.add(setRanges);
		zoom.add(normal);
		axes.add(show);
		axes.add(hide);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("save invoked");
				theAIToSave = (BufferedImage) createImage(W + 130, H + 100);
				theAGToSave  = theAIToSave.createGraphics();
				theAGToSave.drawImage(theAI, 80, 50, null);
//				outputC = "C = " + String.valueOf(reC) + " + " + String.valueOf(imC) + "i";	
//				theAGToSave.drawString(outputC, 10,  20); 		
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

				try {
					ImageIO.write(theAIToSave, "png", new File("JuliaImage " + processDate(new Date(), dateFormat) + ".png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				repaint();
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

	public void pathSet(double[] xArrayIn, double[] yArrayIn, int arrayMax )
	{
		xArray = xArrayIn;
		yArray = yArrayIn;
		topIndex = arrayMax;
		index = 0;
	}

	class Transaction implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			String actionIs = ae.getActionCommand();

			if (actionIs.equals("Start"))
			{ 
				if(topIndex < 5)
				{
					JOptionPane.showMessageDialog(frame, "No Path Set, Please set path by dragging on the Mandelbrot set");
				}
				else
				{
					animationStart = true;	
					mandelbrotCopy.applicationRunningTrue();
					c.setEnabled(true);
					d.setEnabled(true);

				}    	  		    	    
			} 
			if (actionIs.equals("Stop"))
			{
				animationStart = false;
				mandelbrotCopy.applicationRunningFalse();
				c.setEnabled(false);
				d.setEnabled(false);
			}
			if (actionIs.equals("Forward"))
			{
				forward = true;
				mandelbrotCopy.applicationRunningTrue();
			}
			if (actionIs.equals("Back"))
			{
				forward = false;
				mandelbrotCopy.applicationRunningTrue();
			}
			if (actionIs.equals("Faster"))
			{
				if (delay > 30)
					delay -= 10;
			}
			if (actionIs.equals("Slower"))  
			{
				delay += 10;
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

		public synchronized void setC()
		{	
			reC = xArray[index];
			imC = yArray[index];
			if(forward)
			{
				index++;
			}else
			{
				index--;
			}
			if(index > topIndex) index = 0;

			if(index < 0) index = topIndex;
		}
   
		public void run() {
			while( true )
			{
				try
				{

					if (animationStart)
					{
						setC();
						background();
						Thread.sleep(10);
						// repaint();
						canvas.redisplay();
						mandelbrotCopy.traceC(index);
						firstDisplay = false;
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
	{ 							  // shown or damaged
		super.paint(g);
	//	System.out.println("Paint invoked");
		/*if(firstDisplay)
		{
			((Graphics2D) g).setPaint(Color.BLACK);
			((Graphics2D) g).fill( new Rectangle2D.Double( xMargin + 5, topOffset + 5, W - 10, H - 10) );	
		}*/
		if (showAxis)
		{
			axisWiz.drawAxis((Graphics2D) g, xMargin, topOffset, xAxisGap, yAxisGap);
		}	
	}
	
	public void reDisplayFrame()
	{
		repaint();
	}

	public void reDraw( Graphics2D g )
	{
		System.out.println("Redraw invoked");
	}
	
	public void background()
	{
		theAI = (BufferedImage) createImage(W, H);
		theAG = theAI.createGraphics();	
		reDrawJulia(theAG);
		outputC = "C = " + ((double) Math.round( reC*1000000))/1000000 + " + " + ((double) Math.round( imC*1000000))/1000000 + "i";	
		theAG.setPaint(new Color(255, 255, 255));
		theAG.drawString(outputC, 10,  20); 		
	}
	
	public String processDate(Date inputDate, DateFormat format) 
	{ // formats date and time for output in text file
		String dateString = format.format(inputDate);
		dateString = dateString.replace(':', ' ');
		dateString = dateString.replace('/', ' ');
		return dateString;
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

	public double getRe()
	{
		return reC;
	}
	public double getIm() 
	{
		return imC;
	}

	public class MyJCanvas extends JPanel   {
		private int mouseXZoom;
		private int mouseYZoom;
		private double xZoom;
		private double yZoom;
		private int xDrag;
		private int yDrag;
		private boolean dragging = false;
		private double xPressed;
		private double yPressed; 
		String outputC;

		public MyJCanvas()
		{
			addMouseListener(new MouseAdapter() 		
			{
				public void mousePressed (MouseEvent e)
				{	    

					mouseX = e.getX();
					mouseY = e.getY();
					if (mouseInRange(mouseX, mouseY))
					{
						xPressed = ((minX)+((mouseX)*((maxX - minX)/W)));
						yPressed = ((maxY)-((mouseY)*((maxY - minY)/H)));
					}
					dragging = false;
				}
				public void mouseReleased (MouseEvent e)
				{
					if(y - yZoom != 0 && x - xZoom != 0 && zoomSelect && mouseInRange(e.getX(), e.getY()))
					{
						mouseXZoom = e.getX();
						mouseYZoom = e.getY();
						xZoom = (minX)+((mouseXZoom)*((maxX - minX)/W));
						yZoom = (maxY)-((mouseYZoom)*((maxY - minY)/H));

						minX = Math.min(xPressed, xZoom);
						maxX = Math.max(xPressed, xZoom);
						minY = Math.min(yPressed, yZoom);
						maxY = Math.max(yPressed  , yZoom);

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
						if (zoomSelect  && !animationStart) repaint();
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
            super.paintComponent(g);
            g.drawImage( theAI, 0,  0,  this);
      //      System.out.println("Paint component invoked");
            if(firstDisplay)
    		{
    			((Graphics2D) g).setPaint(Color.BLACK);
    			((Graphics2D) g).fill( new Rectangle2D.Double( 5, 5, W - 10, H - 10) );	
    		}
            if (zoomSelect && dragging)
    		{
    			((Graphics2D) g).setPaint(Color.WHITE);
    			g.drawRect(Math.min(mouseX, xDrag), Math.min(mouseY, yDrag), Math.abs(mouseX - xDrag), Math.abs(mouseY - yDrag)); 
    		}
        }
		
		public boolean mouseInRange(int mouseXp, int mouseYp)
		{
			return (mouseXp <= W && mouseYp <= H && mouseXp >= 0 && mouseYp >= 0);
		}
		public void redisplay()
		{
			repaint();
		}
	}
	
}

