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
			new Color(230,230,50), new Color(255,255,255)};
	private Color[] colourTest1 =  {new Color(25, 56, 89), new Color(159,42,150), new Color(38, 159, 120),
			new Color(80, 7, 80), new Color(8, 50, 180), 
			new Color(0, 50, 50), new Color(0, 7, 160), 
			new Color(4, 160, 160), new Color(0, 4, 255)};
	Color black = new Color(0, 0 ,0);
	private int[] testSutoffs0 = {2,3,4,5,7,9,11,14, 17, 25, 50, 58}; 
	private int[] testSutoffs1 = {2,4,6,8,12,15,25,30, 35, 40, 51}; 
/*
 * The Maple worksheet called "equivalence" was used to catalogue a large quantity of randomly generated 
 * complex numbers into lists according to how many iterations of z = z^2 + care required to diverge
 * them to a distance >2 from the origin. This enabled compete coverage for the correct selection of the 
 * colour returned. All cases are tested up to iterations = 16, after which values on the cut-offs and on 
 * either side ate tested. The value of C = -0.0722 - 0.6675i is used. 
 */
	@Test
	public void testA() {
		double reC = -0.0722; 
		double imC = -0.6675;
		Iterator myIterator = new Iterator(150, colourTest, testSutoffs1);
	    assertEquals(myIterator.colourPicker(reC,imC, 1.423, 1.438), colourTest[0]); // zero iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.467, 1.454), colourTest[0]); // zero iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.899, 1.475), colourTest[0]); // one iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.219, 1.445), colourTest[0]); // one iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.309, 0.121), colourTest[1]); // two iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.155, 0.073), colourTest[1]); // two iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.243, 1.187), colourTest[1]); // three iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.123, 0.890), colourTest[1]); // three iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.995, 0.3), colourTest[2]); // four iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.14,  0.397), colourTest[2]); // four iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.274, 0.935), colourTest[2]); // five iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.707, 0.126), colourTest[3]); // six iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.495, 0.072), colourTest[3]); // seven iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.309, 0.866), colourTest[4]); // eight iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.799, 0.31), colourTest[4]); // nine iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.063, 0.481), colourTest[4]); // ten iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.296, 0.834), colourTest[4]); // eleven iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.348, 0.728), colourTest[5]); // twelve iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.762, 0.78), colourTest[5]); // thirteen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.846, 0.494), colourTest[5]); // fourteen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.322, 0.14), colourTest[6]); // fifteen iteration
	    assertEquals(myIterator.colourPicker(reC,imC, 0.322, 0.178), colourTest[6]); // sixteen iteration
	    assertEquals(myIterator.colourPicker(reC,imC, 0.413, 0.563), colourTest[6]); // 24 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.182, 0.170), colourTest[7]); // 25 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.101, 0.601), colourTest[7]); // 26 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.459, 0.515), colourTest[7]); // 29 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.1, 0.197), colourTest[8]); // 30 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.026, 0.936), colourTest[8]); // 31 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.677, 0.311), colourTest[8]); // 34 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.098, 0.304), colourTest[9]); // 35 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.4668, 0.411), colourTest[9]); // 36 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.903, 0.581), colourTest[9]); // 39 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.048, 0.407), colourTest[10]); // 40 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.251, 0.615), colourTest[10]); // 40 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.113, 0.553), colourTest[10]); // 50 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.920, 0.681), colourTest[11]); // 51 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.834, 0.596), colourTest[11]); // 52 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.251, 0.444), colourTest[11]); // 148 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.017, 0.744), Color.BLACK); // 151 iterations
	      
	}
	/*
	 * TestB is similar to testA, but with a different value of C and a different list of cutoffs
	 * The Maple worksheet called "equivalence2" was used to catalogue a large quantity of randomly generated 
	 * complex numbers into lists according to how many iterations of z = z^2 + care required to diverge
	 * them to a distance >2 from the origin. This enabled compete coverage for the correct selection of the 
	 * colour returned. All cases are tested up to iterations = 18, after which values on the cut-offs and on 
	 * either side ate tested. The value C = -0.7061 - 0.2737i is used.
	 */
	@Test
	public void testB() {
		double reC = -0.7061; 
		double imC = -0.2737;
		Iterator myIterator = new Iterator(150, colourTest, testSutoffs0);
	    assertEquals(myIterator.colourPicker(reC,imC, 1.437, 1.479), colourTest[0]); // zero iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.42, 1.043), colourTest[0]); // one iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.461, 0.421), colourTest[1]); // two iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.049, 0.602), colourTest[2]); // three iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.063, 0.583), colourTest[3]); // four iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.325, 0.006), colourTest[4]); // five iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.067, 0.889), colourTest[4]); // six iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.172, 0.78), colourTest[5]); // seven iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.846, 0.494), colourTest[5]); // eight iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.308, 0.09), colourTest[6]); // nine iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.199, 0.008), colourTest[6]); // ten iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.653, 0.399), colourTest[7]); // eleven iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.799, 0.31), colourTest[7]); // twelve iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.017, 0.744), colourTest[7]); // thirteen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.064, 0.017), colourTest[8]); // fourteen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.877, 0.335), colourTest[8]); // fifteen iteration
	    assertEquals(myIterator.colourPicker(reC,imC, 1.074, 0.379), colourTest[8]); // sixteen iteration
	    assertEquals(myIterator.colourPicker(reC,imC, 1.043, 0.04), colourTest[9]); // seventeen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.268, 0.473), colourTest[9]); // eighteen iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.228, 0.518), colourTest[9]); // 24 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.067, 0.167), colourTest[10]); // 25 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.016, 0.452), colourTest[10]); // 26 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.111, 0.172), colourTest[10]); // 49 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.774, 0.26), colourTest[11]); // 50 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.136, 0.272), colourTest[11]); // 51 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.468, 0.552), colourTest[11]); // 57 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 1.309, 0.121), colourTest[12]); // 59 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.045, 0.316), colourTest[12]); // 150 iterations
	    assertEquals(myIterator.colourPicker(reC,imC, 0.955, 0.3), Color.BLACK); // 151 iterations
	   
	      
	}
/*
 *  The data in the Maple 12 work sheet data 2 were used to generate the test data below. For C = 0.2 + 0.1i
 *  the number of iterations rC was computed for a large number of 
 *  randomly generated values of the real and imaginary components of Z_0. The corresponding iterations, and
 *  real and imaginary part of Z_0 were pasted into arrays to facilitate the tests below. The number of 
 *  iterations was checked manually against the cut-offs in the arrays, and the tests below were used to
 *  check that the Iterator instance was returning the correct colours. 
 */
		
	 
	private double[] reZ = { 0.3800000000,   -1.950000000, -1.010000000, -0.04000000000, 1.510000000, 
		                    0.3100000000, 1.450000000, -0.3900000000, -0.2100000000, -1.180000000, 
		                	-1.110000000, 1.150000000, 1.780000000, -1.930000000, 1.920000000,      
		                	-0.5700000000, -0.9100000000, -1.090000000, 0.05000000000, 1.660000000, 
	                		-1.480000000, 1.450000000, 1.040000000, -0.06000000000, -0.4600000000,  
                			-0.8500000000, -0.7100000000, 1.400000000, 0.3000000000, -0.2900000000,  
	                		-0.5400000000, -0.5800000000, -1.920000000, 0.6700000000, 1.070000000,    
	                		0.4600000000, -1.110000000, -0.3000000000, -0.9200000000, -1.840000000,   
	                		0.1200000000, 1.600000000, 1.380000000, 1.550000000, -0.3700000000,      
	                		0.9500000000, -0.5700000000, -1.540000000, -1.980000000, -0.9400000000 }; 

	
	private double[] imZ = { 1.5, 1.490000000, -1.130000000, 1.320000000, -1.340000000, 
	                		0.780000000, 1.000000000, 1.230000000, 0.200000000, -1.260000000, 
	                	    -0.5800000000, -1.010000000, 0.240000000, 0.230000000, 0.360000000, 
			                -0.7400000000, -1.120000000, -0.8000000000, 0.880000000, -1.000000000, 
			                -1.320000000, -0.200000000, 0.260000000, 0.510000000, -0.7900000000, 
			                 0.880000000, -0.7800000000, 1.460000000, 1.090000000, 1.180000000, 
			                 1.150000000, 0.410000000, 0.990000000, 1.020000000, 1.050000000, 
			                -0.380000000, 1.230000000, 0.320000000, -1.110000000, 0.640000000, 
			                -0.140000000, 0.450000000, -0.260000000, 1.410000000, 1.150000000, 
			                -0.6400000000, -0.9000000000, 1.330000000, 0.800000000, 1.160000000 }; 
	
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
							            colourTest1[1], colourTest1[0], colourTest1[0], colourTest1[0], colourTest1[1], 
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
		for(int i = 0; i < 20; i++)
		{
			System.out.println("i = " + i + " z = " + reZ1[i] + " + " + imZ1[i] + " iterations = " + iterations1[i]);
			assertEquals(theIterator.colourPicker(0.2, 0.1, reZ1[i], imZ1[i]), expectedColours1[i]);
		}
	}

}
