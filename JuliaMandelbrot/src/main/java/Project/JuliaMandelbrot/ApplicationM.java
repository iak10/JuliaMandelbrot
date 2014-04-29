package Project.JuliaMandelbrot;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.File;
import java.io.IOException;

public class ApplicationM extends JFrame {
	private static final int H = 720; // Height of window
	private static final int W = 810; // Width of window
	private Color previousColour = new Color(00, 00, 00);   // detects change of colour, so same colour is drawn as line
	private Color currentColour = new Color(00, 00, 00);    // colour returned by iterator for plot
	private Color backgroundColour = new Color(240, 240, 240); // background colour of frame
	private double firstChangex = 0; // used in plotting, holds x coordinate where colour starts
	private double x, y; // mathematical values of x and y positions represented by a pixel
	private double minXDefault = -2.25; // minimum value of x in normal zoom state
	private double maxXDefault =  0.9; // maximum value of x in normal zoom state
	private double minYDefault = -1.35; // minimum value of y in normal zoom state
	private double maxYDefault = 1.35;  // maximum value of y in normal zoom state			
	private double minX = minXDefault; // initialise minimum x to default value
	private double maxX = maxXDefault; // initialise maximum x to default value
	private double minY = minYDefault; // initialise minimum y to default value
	private double maxY = maxYDefault; // initialise maximum x to default value
	private int maxIterations = 150; // maximum iterations for each complex number, if it hasn't diverged it is assumed it won't
	private int xMargin = 70; // gap between the left side of JFrame and the plotted image
	private int yMargin = 60; // gap between lower edge of plotted image and botto of the JFrame
	private int topOffset = 150; // gap between top of JFrame and the plotted image
	private int rightMargin = 30; // gap between right edge of plotted image and the edge of the JFrame
  	private final JTextField theRealOutput = new JTextField(); // text field for real component of C clocked on
  	private final JTextField theImOutput = new JTextField();  // text field for imaginary component of C clocked on
  	public double[] xArray, yArray; // hold x and y numerical values in animation path of values for C
	public int[] xPixelArray, yPixelArray; // hold x and y coordinates of pixels in animation path of values for C
	public int topArrayIndex = 0;
	private Color[] mandelbrotColours = {new Color(36, 59, 120), new Color(52, 90, 180), new Color(158, 27, 224), 
			                             new Color(45, 27, 156), new Color(0, 180, 180),new Color(146, 255, 100), new Color(255,255,255)};
    private int[] mandelbrotCutOffs = {1,7,13,19,30,70};  
    private BufferedImage theAI, theAIToSave;
	private Graphics2D theAG, theAGToSave;
	private boolean animationRunning = false;
	private int animationIndex, animationX, animationY;
	private boolean showAxis = false;
	private String outputC = "";
	private Iterator iterator;
	private JMenuBar menubar = new JMenuBar();
	private JMenu file, zoom, axes;
	private JMenuItem save, exit, setRanges, normal, show, hide;
	private AxisWiz axisWiz;
	private JLabel[] axisLabel = {new JLabel(String.valueOf(maxY)), new JLabel(String.valueOf((maxY + minY)/2)), 
            new JLabel(String.valueOf(minY)), new JLabel(String.valueOf(minX)),
            new JLabel(String.valueOf((maxX + minX)/2)), new JLabel(String.valueOf(maxX))};  
	private int xAxisGap = 8, yAxisGap = 8; // space between the axes (when visible) and the plotted image
	boolean zoomSelect = false;
	private ApplicationJ copyOfJulia;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	MyCanvas canvas = new MyCanvas();

	public ApplicationM() {
		
		setSize(W + xMargin + rightMargin, H + yMargin + topOffset ); // Size of drawing area plus margins
		getContentPane().setBackground( backgroundColour );
		setDefaultCloseOperation(EXIT_ON_CLOSE);    
		setTitle("Mandelbrot set");
		setResizable(false); // Do not allow user to re-size because pixels represent mathematical points
		
		//
		// Code to add widgets to the frame
		//
		 Container cp = getContentPane();
		 cp.setLayout(null);
		 theRealOutput.setBounds(200,60,80,30);
		 cp.add(theRealOutput);	 
		 theImOutput.setBounds(320,60,80,30);
		 cp.add(theImOutput);
		 setVisible(true);

		 JLabel label1 = new JLabel("Click to set C =");
		 JLabel label2 = new JLabel(" + ");
		 JLabel label3 = new JLabel("i");
		 JLabel label4 = new JLabel("Drag mouse with button down to set path for the Julia set animation");

		 label1.setBounds(75,60,150,30);
		 label1.setFont(new Font("ARIAL", Font.PLAIN, 16));
		 cp.add(label1);

		 label2.setBounds(290,60,50,30);
		 label2.setFont(new Font("Serif", Font.PLAIN, 20));
		 cp.add(label2);

		 label3.setBounds(410,60,100,30);
		 label3.setFont(new Font("Serif", Font.ITALIC, 20));
		 cp.add(label3);
		 
		 label4.setBounds(75,20,700,30);
		 label4.setFont(new Font("ARIAL", Font.PLAIN, 18));
		 cp.add(label4);
		 
		// canvas.setLocation(new Point(xMargin, topOffset));
		 canvas.setBounds(xMargin, topOffset - 48, W, H );
		 cp.add(canvas);
		 
		 // Initialise the instance of Iterator and draw the Mandelbrot set as the background image
		 iterator = new Iterator(maxIterations, mandelbrotColours, mandelbrotCutOffs);
		 background();		

		 axisWiz = new AxisWiz(W + 1, H + 1);
		 
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
   		 		theAIToSave = (BufferedImage) createImage(W + 130, H + 130);
   		 		theAGToSave  = theAIToSave.createGraphics();
   		 		theAGToSave.drawImage(theAI, 80, 50, null);
   		 	    theAGToSave.drawString(outputC, 10,  20);

			    plotPoint(theAGToSave, 80 + xPixelArray[animationIndex], 50 + yPixelArray[animationIndex]);

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
 					ImageIO.write(theAIToSave, "png", new File("Mandelbrotimage" + processDate(new Date(), dateFormat) +".png"));
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
   		 		
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
 		 		for(int i = 0; i < topArrayIndex; i++)
		 		{
		 			xPixelArray[i] = (int) Math.round((xArray[i] - minX)*(W/(maxX - minX)));
		 			yPixelArray[i] = (int) Math.round((maxY - yArray[i])*(H/(maxY - minY)));	
		 		}
 		 		
 		 		repaint();
 		 	} 	
    	 }); 
 	 
    	 setRanges.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		zoomSelect = true; 
		 	}
    	 }); 
     	 setVisible(true);
	     repaint();
		
	} // end of constructor method
	
	public void heresJulia(ApplicationJ julia)
	{
		copyOfJulia = julia;
	}

	public void paint(Graphics g) // When 'Window' is first
	{ 							  // shown or damaged
		super.paint(g);   
		System.out.println("Paint invoked");
	    if(showAxis)
		{
			axisWiz.drawAxis((Graphics2D) g, xMargin, topOffset, xAxisGap, yAxisGap);
			System.out.println("axes redrawn");
	 	}	
	}
	
	public void reDraw(Graphics2D g)
	{
		System.out.println("Re-draw invoked");
	}
	
	public void reDisplayFrame()
	{
		repaint();
	}
	
	public void plotPoint(Graphics2D g, int x, int y)
	{
		if(mouseInRange(x, y))
		{
			g.setPaint(new Color(255, 255, 255));
			g.drawLine(x-3, y, x + 3, y);
			g.drawLine(x, y - 3, x, y + 3);	
		}	
	}
	
	public void background()
	{
		theAI = (BufferedImage) createImage(W + 1, H + 1);
		theAG = theAI.createGraphics();		
		reDrawMandelbrot(theAG);
	}
	
	public void reDrawMandelbrot(Graphics2D g) // Re draw contents
	{	
		for(double yPixel = 0; yPixel < H; yPixel++)
		{
			for(double xPixel = 0; xPixel < W; xPixel++)
			{
				x = (minX)+(xPixel*((maxX - minX)/W));
				y = (maxY)-(yPixel*((maxY - minY)/H));		
				previousColour = currentColour;	
				currentColour = iterator.colourPicker(x, y, 0.0, 0.0);
				if(! previousColour.equals(currentColour) || xPixel == W - 1)
				{
					g.draw(new Line2D.Double(firstChangex , yPixel , xPixel , yPixel ));
					firstChangex = xPixel % (W - 1);
					g.setPaint(currentColour);
				}				
			}
		}
	}
	
	public boolean mouseInRange(int mouseXp, int mouseYp)
	{
		return (mouseXp <= W  && mouseYp <= H && mouseXp >= 0 && mouseYp >= 0);
	}
	
	public void traceC(int i)
	{	
		//displayC(xArray[i], yArray[i]);
		//repaint(animationX - 3, animationY - 3, 7, 7);;
//		System.out.println(i);
		animationIndex = i;
		int prevAnimationX, prevAnimationY;
		prevAnimationX = animationX;
		prevAnimationY = animationY;
		animationX = xPixelArray[animationIndex];
		animationY = yPixelArray[animationIndex];
	//	canvas.reDisplay();
		canvas.repaint(Math.min(animationX, prevAnimationX)-3,Math.min(animationY, prevAnimationY)-3,Math.abs(animationX-prevAnimationX)+7,Math.abs(animationY-prevAnimationY)+7);
	}
	
	
	public String processDate(Date inputDate, DateFormat format)
	{
		String dateString = format.format(inputDate);
		dateString = dateString.replace(':', ' ');
		dateString = dateString.replace('/', ' ');
		return dateString;
	}	
	
	public void applicationRunningTrue()
	{
		animationRunning = true;	
	}
	
	public void applicationRunningFalse()
	{
		animationRunning = false;	
	}
	
	public String getReCString()
	{
		return theRealOutput.getText();
	}
	
	public String getImCString()
	{
		return theImOutput.getText();  
	}
	
	public class MyCanvas extends JPanel   {
		private int mouseX, mouseY; 
		private int mouseXZoom,mouseYZoom;
		private double xZoom, yZoom;
		private int xDrag, yDrag;
		private int xDragPrev = 0;
		private int yDragPrev = 0;
		private boolean dragging = false;
		private boolean mouseDown =  false;
		private int dragCounter = 0;
		private double xPressed, yPressed;
		public ArrayList<Integer> xPixelArrayList, yPixelArrayList; 
		
		public MyCanvas()
		{
			addMouseListener(new MouseAdapter() 			
		    {
		        public void mousePressed (MouseEvent e)
		        {	        		           	
		           mouseDown = true;
		           mouseX = e.getX();
		           mouseY = e.getY();
		           if(!zoomSelect)
		           {
		        	   xPixelArrayList = new ArrayList<Integer>();
		        	   yPixelArrayList = new ArrayList<Integer>();
		        	   x = (minX)+((mouseX)*((maxX - minX)/W));
			           y = (maxY)-((mouseY)*((maxY - minY)/H));
				           
				        if(mouseInRange(mouseX, mouseY))
				        {	           
				        	double re = Math.round(x*1000000);
				   		    double im = Math.round(y*1000000);   
				   	        theRealOutput.setText(String.valueOf(re/1000000));
				   	        theImOutput.setText(String.valueOf(im/1000000)); 
			        	}			        
		           }
		           else
		           { 
		               if (mouseInRange(mouseX, mouseY))
		               {
		                   xPressed = ((minX)+((mouseX)*((maxX - minX)/W)));
		                   yPressed = ((maxY)-((mouseY)*((maxY - minY)/H)));
		               }
	                   dragging = false;
		               dragCounter = 0;
		             {
		        } }};
		           
		        
		        public void mouseReleased (MouseEvent e)
		        {
		        	if(!zoomSelect && xPixelArrayList.size() > 6)
		        	{
			        	xArray = new double[xPixelArrayList.size()];
		        		yArray = new double[yPixelArrayList.size()];
		        		xPixelArray = new int[xPixelArrayList.size()];
		        		yPixelArray = new int[yPixelArrayList.size()];
		
		        		
		        		for(int i = 0; i < xPixelArrayList.size(); i++)
		        		{
		        			xPixelArray[i] = xPixelArrayList.get(i);
		        			yPixelArray[i] = yPixelArrayList.get(i);
		        			xArray[i] = ((minX)+((xPixelArray[i])*((maxX - minX)/W)));
		        			yArray[i] = ((maxY)-((yPixelArray[i])*((maxY - minY)/H)));	
		        		}
		        		copyOfJulia.pathSet(xArray,yArray,xPixelArrayList.size() - 1);
		        	}
		        	else if(y - yZoom != 0 && x - xZoom != 0 && zoomSelect && mouseInRange(e.getX(), e.getY()))
		        	{
			            mouseXZoom = e.getX();
				        mouseYZoom = e.getY();
				        xZoom = (minX)+((mouseXZoom)*((maxX - minX)/W));
				        yZoom = (maxY)-((mouseYZoom)*((maxY - minY)/H));
				        
				        minX = Math.min(xPressed, xZoom);
				 		maxX = Math.max(xPressed, xZoom);
				 		minY = Math.min(yPressed, yZoom);
				 		maxY = Math.max(yPressed  , yZoom);
				 		zoomSelect = false;
				 		axisWiz.setAxisLabels(axisLabel, maxY, (maxY + minY)/2, 
	    		 				                 minY, minX, (maxX + minX)/2, maxX);
				 		background();
				 		//repaint();
				 		reDisplayFrame();
				 		for(int i = 0; i < topArrayIndex; i++)
				 		{
				 			xPixelArray[i] = (int) Math.round((xArray[i] - minX)*(W/(maxX - minX))); 
				 			yPixelArray[i] = (int) Math.round((maxY - yArray[i])*(H/(maxY - minY)));	
				 			
				 		}					
		        	}
//		        	else if(!zoomSelect)
//		        	{		    
//		        		index = 0;
		        		
//		        	}
				 	dragging = false;   
				 	mouseDown = false;
				 	dragCounter = 0;
			    } 		    	    	
		    });
		        
			addMouseMotionListener(new MouseMotionAdapter() 
		    {
		        public void mouseDragged (MouseEvent e)
		        {
		        	if (zoomSelect)
		        	{
		        		xDrag = e.getX();
		            	yDrag = e.getY();
		            	if (mouseInRange(xDrag, yDrag))
			        	{
		            	   dragCounter ++;
		                   dragging = true;
		                   
		                   if (dragCounter % 8 == 0) 
		                   {
	                	     int xRepaintRange = Math.max(Math.abs(xDrag - xDragPrev),Math.max(Math.abs(mouseX - xDrag), Math.abs(mouseX - xDragPrev)));
			                 int yRepaintRange = Math.max(Math.abs(yDrag - yDragPrev),Math.max(Math.abs(mouseY - yDrag), Math.abs(mouseY - yDragPrev)));
	                		 repaint(Math.min(Math.min(mouseX, xDrag),xDragPrev) - 2,  Math.min(Math.min(mouseY, yDrag),yDragPrev) - 2, xRepaintRange + 4, yRepaintRange + 4);
	                         xDragPrev = xDrag;
		        	         yDragPrev = yDrag;
			        	   }
		        	    }
		            	else
		            	{
		            		dragging = false;
		            		zoomSelect = false;
		            		repaint();
		            	} 
		        	}
		        	else if(animationRunning)
		        	{
		        		JOptionPane frame = new JOptionPane();
		        		JOptionPane.showMessageDialog(frame, "Animation already running, please stop the current animation before setting another path");
		        	}
		        	else if(!zoomSelect && mouseDown && mouseInRange(mouseX, mouseY))
		            {
		        		 int xIn = e.getX();
		        		 int yIn = e.getY();
	        	    	 xPixelArrayList.add(xIn);
	        	    	 yPixelArrayList.add(yIn);
				   	     double re  = (minX+(xIn*((maxX - minX)/W)));
	        			 double im  = (maxY-(yIn*((maxY - minY)/H)));	
	        			 re = Math.round(re*1000000);
				   	     im = Math.round(im*1000000);   
				   	     theRealOutput.setText(String.valueOf(re/1000000));
				   	     theImOutput.setText(String.valueOf(im/1000000)); 
			             topArrayIndex = xPixelArrayList.size();
		            }	           
		        }
		    });
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("Paint component invoked");
            g.drawImage( theAI,  0,  0,  this);      
            if (animationRunning)
    		{
    			plotPoint((Graphics2D) g, xPixelArray[animationIndex], yPixelArray[animationIndex]);
//    			displayC(xArray[animationIndex],yArray[animationIndex]);
    			g.drawString(outputC, 10, 20);
    		}
            if(zoomSelect && dragging)
    		{
    			((Graphics2D) g).setPaint(Color.WHITE);  
    			g.drawRect(Math.min(mouseX, xDrag), Math.min(mouseY, yDrag), Math.abs(mouseX - xDrag), Math.abs(mouseY - yDrag));
    		}
        }
		public void reDisplay()
		{
			repaint();		
		}
		
/*		public void displayC(double reC, double imC)
		{
			 double re = Math.round(reC*1000000);
			 double im = Math.round(imC*1000000);
			 outputC = "C = " + String.valueOf(re/1000000) + " + " + String.valueOf(im/1000000) + "i";	 
		}*/
    }
}