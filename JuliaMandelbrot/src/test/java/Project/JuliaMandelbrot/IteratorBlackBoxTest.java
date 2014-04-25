package Project.JuliaMandelbrot;
import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Test;

public class IteratorBlackBoxTest {
	Iterator theIterator;
	private Color[] colourTest =  {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50)};
	private Color[] colourTest1 =  {new Color(25, 56, 89), new Color(159,42,150), new Color(38, 159, 120),
			new Color(80, 7, 80), new Color(8, 50, 180), 
			new Color(0, 50, 50), new Color(0, 7, 160), 
			new Color(4, 160, 160), new Color(0, 4, 255)};
	Color black = new Color(0, 0 ,0);
	private int[] testSutoffs0 = {2,3,4,5,7,9,11,14, 17, 25, 50, 58}; 
	private int[] testSutoffs1 = {2,3,4,5,8,12,15,25,40}; 
	
	 
	private double[] reZ = { 0.3800000000,   -1.950000000, -1.010000000, -0.04000000000, 1.510000000, //4
		                    0.3100000000, 1.450000000, -0.3900000000, -0.2100000000, -1.180000000, //9
		                	-1.110000000, 1.150000000, 1.780000000, -1.930000000, 1.920000000,      //14
		                	-0.5700000000, -0.9100000000, -1.090000000, 0.05000000000, 1.660000000, //19
	                		-1.480000000, 1.450000000, 1.040000000, -0.06000000000, -0.4600000000,  //24
                			-0.8500000000, -0.7100000000, 1.400000000, 0.3000000000, -0.2900000000,  //29
	                		-0.5400000000, -0.5800000000, -1.920000000, 0.6700000000, 1.070000000,    //34
	                		0.4600000000, -1.110000000, -0.3000000000, -0.9200000000, -1.840000000,   //39
	                		0.1200000000, 1.600000000, 1.380000000, 1.550000000, -0.3700000000,      //44
	                		0.9500000000, -0.5700000000, -1.540000000, -1.980000000, -0.9400000000 }; //49

	
	private double[] imZ = { 1.5, 1.490000000, -1.130000000, 1.320000000, -1.340000000, //4
	                		0.780000000, 1.000000000, 1.230000000, 0.200000000, -1.260000000, //9
	                	    -0.5800000000, -1.010000000, 0.240000000, 0.230000000, 0.360000000, //14
			                -0.7400000000, -1.120000000, -0.8000000000, 0.880000000, -1.000000000, //19
			                -1.320000000, -0.200000000, 0.260000000, 0.510000000, -0.7900000000, //24
			                 0.880000000, -0.7800000000, 1.460000000, 1.090000000, 1.180000000, //29
			                 1.150000000, 0.410000000, 0.990000000, 1.020000000, 1.050000000, //34
			                -0.380000000, 1.230000000, 0.320000000, -1.110000000, 0.640000000, //39
			                -0.140000000, 0.450000000, -0.260000000, 1.410000000, 1.150000000, //44
			                -0.6400000000, -0.9000000000, 1.330000000, 0.800000000, 1.160000000 }; //49
	
	private double[] iterations = {  1, 0, 1, 2, 0, //4
			                       151, 1, 2, 151, 1,  //9
			                         2, 1, 1, 1, 1,   //14
			                       151, 1, 2, 151, 1,   //19
			                         1, 1, 2, 151, 151, //24
			                         3, 4, 0, 4, 3,   //29
			                         2, 151, 0, 2, 1,  //34
			                       151, 1, 151, 1, 1,  //39
			                       151, 1, 1, 0, 3,    //44
			                         3, 4, 0, 0, 1,  };  //49
	
	private double[] reZ1 = { -0.3600000000, 1.300000000, 0.1800000000,
			-1.610000000, 1.620000000, -1.710000000, 0.8700000000, 1.710000000,
			1.510000000, -1.780000000, -0.7300000000, -1.340000000,
			1.760000000, -1.650000000, -1.170000000, 0.08000000000,
			1.090000000, 0.4000000000, 1.210000000, -0.2400000000,
			-1.660000000, -1.010000000, -1.280000000, -0.4100000000,
			0.6400000000, 1.530000000, 1.800000000, -1.280000000,
			-0.3600000000, 1.490000000, -1.500000000, 1.260000000,
			-0.04000000000, -1.680000000, 1.840000000, -1.370000000,
			-0.1600000000, -0.2700000000, -0.9200000000, 1.430000000,
			1.600000000, 0.03000000000, -1.090000000, 0.1900000000,
			0.6400000000, -0.5900000000, 1.280000000, 1.330000000,
			-0.8500000000, -1.450000000
			};
	private double[] imZ1 = { -0.390000000, 1.090000000, -0.010000000,
			0.750000000, -1.400000000, -0.170000000, 1.450000000,
			-0.7100000000, -1.400000000, 0.180000000, -0.9300000000,
			0.910000000, -0.210000000, 0.380000000, 0.730000000, 1.360000000,
			-0.8400000000, 0.800000000, -0.110000000, -0.6100000000,
			1.200000000, 0, -0.010000000, 0.750000000, -1.480000000,
			-0.8200000000, -0.200000000, 0.650000000, -0.410000000,
			-1.350000000, 0.820000000, -1.020000000, 0.980000000, 0.070000000,
			1.000000000, -1.080000000, 0.390000000, -0.240000000, -1.420000000,
			0.900000000, 1.420000000, -0.150000000, 1.020000000, 1.490000000,
			1.350000000, 0.900000000, 1.260000000, -1.020000000, -1.360000000,
			-0.8200000000};
	
	//{2,3,4,5,8,12,15,25,40};
	
	private double[] iterations1 = { 201, 1, 201, 1, 0,
									 1,   1, 1,   0, 1,
									 3,   1, 1,   1, 2,
									 2,   2, 201, 2, 201,
									 0,   3, 2,   201, 1,
									 1,   1, 1,   201, 0,
									 1,   1, 10,  1,   0,
									 1,   201, 201, 1, 1,
									 0,   201, 1,   1, 1,
									 201, 1,   1,   1, 1 };
	
	private Color[] expectedColours = {colourTest[0], colourTest[0], colourTest[0], colourTest[1], colourTest[0],   
			                           black        , colourTest[0], colourTest[1], black        , colourTest[0],
			                           colourTest[1], colourTest[0], colourTest[0], colourTest[0], colourTest[0], 
			                           black        , colourTest[0], colourTest[1], black        , colourTest[0], 
			                           colourTest[0], colourTest[0], colourTest[1], black        , black        , 
			                           colourTest[2], colourTest[3], colourTest[0], colourTest[3], colourTest[2],
			                           colourTest[1], black,         colourTest[0], colourTest[1], colourTest[0],
			                           black        , colourTest[0], black        , colourTest[0], colourTest[0],
			                           black        , colourTest[0], colourTest[0], colourTest[0], colourTest[2],
			                           colourTest[2], colourTest[3], colourTest[0], colourTest[0], colourTest[0]} ;
	
	private Color[] expectedColours1 = {black		 ,  colourTest1[0], black         , colourTest1[0], colourTest1[0],   
										colourTest1[0], colourTest1[0], colourTest1[0], colourTest1[0], colourTest1[0],
							            colourTest1[2], colourTest1[0], colourTest1[0], colourTest1[0], colourTest1[1], 
							            colourTest1[1], colourTest1[1], black         , colourTest1[1], black         , 
							            colourTest1[0], colourTest1[2], colourTest1[1], black         , colourTest1[0], 
							            colourTest1[0], colourTest1[0], colourTest1[0], black         , colourTest1[0],
							            colourTest1[0], colourTest1[0], colourTest1[5], colourTest1[0], colourTest1[0],
							            colourTest1[0], black         , black         , colourTest1[0], colourTest1[0],
							            colourTest1[0], black         , colourTest1[0], colourTest1[0], colourTest1[0],
							            black         , colourTest1[0], colourTest1[0], colourTest1[0], colourTest1[0]} ;
	@Test
	public void testOne() {
		theIterator = new Iterator(150, colourTest, testSutoffs0);
		for(int i = 0; i < 50; i++)
		{
			System.out.println("i = " + i + " z = " + reZ[i] + " + " + imZ[i] + " iterations = " + iterations[i]);
			assertEquals(theIterator.colourPicker(0.2, 0.1, reZ[i], imZ[i]), expectedColours[i]);
		}
	}
	
	@Test
	public void testTwo() {
		theIterator = new Iterator(200, colourTest1, testSutoffs1);
		for(int i = 0; i < 50; i++)
		{
			System.out.println("i = " + i + " z = " + reZ1[i] + " + " + imZ1[i] + " iterations = " + iterations1[i]);
			assertEquals(theIterator.colourPicker(0.2, 0.1, reZ1[i], imZ1[i]), expectedColours1[i]);
		}
	}

}
