package Project.JuliaMandelbrot;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Random;

import org.jcheck.annotations.Configuration;
import org.jcheck.annotations.Generator;
import org.jcheck.generator.Gen;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
/* 
 * This tests the Iterator class, whose constructor requires the input of 
 * three valid parameters: (i) a valid integer ( > 25) for the maximum number 
 * of iterations, after which it will be assumed that a point does not diverge.
 * (ii) an array of base type Color which has at least one element, and (iii) 
 * an array of strictly increasing positive integers. If any of these 
 * pre-conditions are not met, the constructor should raise an exception.
 * If these conditions are met, it should return a valid instance of Iterator.
 * The first four tests verify that an exception is raised (or not) according 
 * to whether the pre-conditions are met. One problem I met is that where 
 * strict conditions have to be met, bombarding with a range of possibilities will tend 
 * not to provide good coverage for valid cases, so the fourth test (testIteratorReturned)
 * was written to ensure the valid case was covered, but with a wide range of 
 * possibilities.
 * The other issue I encountered was that when I came to test that the correct 
 * colour was always returned I found I was inevitably using similar logic in the 
 * test as in the iterator class itself. I was at pains to minimise this by
 * using a different approach in comparing the colour returned and the iterations,
 * but here is inevitable some circularity in automatic testing. 
 */

@RunWith(org.jcheck.runners.JCheckRunner.class)
public class AlgebraicTest { 
	Iterator theIterator;
	private Color[] colourTest =  {new Color(36, 159, 120), new Color(0,50,150), new Color(0, 150, 150),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50)};
	private int[] testCutOffs0 = {2,3,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs11 = {1,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs2 = {1, 3};  
	private int[] testCutOffs4 = {2, 4, 6, 12};  
	private Color black = new Color(0, 0, 0);

	/*
	 * Exceptiontest1 checks that an exception is raised when the sequence of cut-offs
	 * provided to the constructor of Iterator is not valid, either because the 
	 * first element is less than 1 or because the array elements are not  
	 * strictly increasing. The final else-clause checks that when the cut-offs are 
	 * valid an instance of Iterator is returned. A valid array of colours is provided
	 * to the constructor of Iterator, as well as a valid number (150) for the maximum
	 * iterations.
	 */	
	@Configuration(tests=200, size=100)
	@Test
	public void exceptionTest1(int a, int b, int c, int d)
	{
		boolean exceptionRaised = false;
		int[] cutoffs = {a, b, c, d};	
		if(a < 1)
		{
			try {
				theIterator = new Iterator(150, colourTest, cutoffs);
				fail( "My method didn't throw when I expected it to" );
			} 
			catch (RuntimeException expectedException) 
			{
				exceptionRaised = true;
			}
			assertTrue(exceptionRaised);

		}
		else if( b <= a || c <= b || d <= c)
		{
			try {
				theIterator = new Iterator(150, colourTest, cutoffs);
				fail( "My method didn't throw when I expected it to" );
			} 
			catch (RuntimeException expectedException) 
			{
				exceptionRaised = true;
			}
			assertTrue(exceptionRaised);

		}else
		{
			theIterator = new Iterator(150, colourTest, cutoffs);
			assertTrue(theIterator.getClass().getSimpleName().equals("Iterator"));	
		}

	}
	
	/*
	 * Exceptiontest2 ensures that the first instance of the array of cutoffs is >=1,
	 * and checks that an exception is raised when the next two cut offs are not 
	 * strictly increasing. The else-clause checks that when the cut-offs are 
	 * valid an instance of Iterator is returned.A valid array of colours is provided
	 * to the constructor of Iterator, as well as a valid number (150) for the maximum
	 * iterations.
	 */
	@Configuration(tests=200, size=100)
	@Test
	public void exceptionTest2(int a, int b, int c)
	{
		boolean exceptionRaised = false;
		int[] cutoffs ={a, b, c};	
		if(cutoffs[0] < 0) cutoffs[0] = - cutoffs[0]; cutoffs[0] = 1 + cutoffs[0]/2;
		if( b <= cutoffs[0] || c <= b)
		{
			try {
				theIterator = new Iterator(150, colourTest, cutoffs);
				fail( "My method didn't throw when I expected it to" );
			} 
			catch (RuntimeException expectedException) 
			{
				exceptionRaised = true;
			}
			assertTrue(exceptionRaised);

		}else
		{
			theIterator = new Iterator(150, colourTest, cutoffs);
			assertTrue(theIterator.getClass().getSimpleName().equals("Iterator"));	
		}		
	}
	
	/*
	 * Exceptiontest3 ensures that a valid array of colours and a valid array
	 * of cutoffs are provided to the constructor method of Iterator, which should
	 * raise an exception or not according to whether the values of a, which is given
	 * to the constructor as the maximum number of iterations is less than 25 or not.
	 */
	@Configuration(tests=200, size=1000)
	@Test
	public void exceptionTest3(int a)
	{
		boolean exceptionRaised = false;
		if (a < 25)
		{
			try {
				theIterator = new Iterator(a, colourTest, testCutOffs11);
				fail( "My method didn't throw when I expected it to" );
			} 
			catch (RuntimeException expectedException) 
			{
				exceptionRaised = true;
			}
			assertTrue(exceptionRaised);

		}else
		{
			//			System.out.println("simplename a = " + a);
			theIterator = new Iterator(a, colourTest, testCutOffs11);
			assertTrue(theIterator.getClass().getSimpleName().equals("Iterator"));	
		}		
	}

	/*
	 * Exceptiontest4 ensures that a valid number for maximum iterations and a valid
	 * array of of cutoffs are provided to the constructor method of Iterator, but 
	 * the length of the array of colours may be zero, and if so an exception should 
	 * be raised, and if not an instance of Iterator should be returned.
	 */
	@Configuration(tests=200, size=100)
	@Test
	public void exceptionTest4(int a)
	{
		boolean exceptionRaised = false;
		if (a < 0) a = 0;
		Color[] colours = colourArray(a);
		if (a < 1)
		{
			try {
				theIterator = new Iterator(a, colours, testCutOffs11);
				fail( "My method didn't throw when I expected it to" );
			} 
			catch (RuntimeException expectedException) 
			{
				exceptionRaised = true;
			}
			assertTrue(exceptionRaised);

		}else
		{	
			theIterator = new Iterator(150, colours, testCutOffs11);
			assertTrue(theIterator.getClass().getSimpleName().equals("Iterator"));	
		}		
	}
	
	/*
	 * This tests that when the pre-conditions of the constructor of class Iterator
	 * are fulfilled by providing an array of colours, and a value > 25 for the maximum
	 * number of iterations, and an ascending sequence of positive integers in the
	 * array of cut-offs, an instance of Iterator is returned by the constructor.
	 */
	@Configuration(tests=200, size=10)
	@Test
	public void testIteratorReturned(int a, int b, int c, int d, int e)
	{   
		if (a < 0) a = -a;
		if (b < 0) b = -b;
		if (c < 0) c = -c;
		if (d < 0) d = -d;
		int[] cutoffs ={a + 1, a + b + 2, a + b + c + 3, a + b + c + d + 4};	
		theIterator = new Iterator(35 + e, colourTest, cutoffs);
		assertTrue(theIterator.getClass().getSimpleName().equals("Iterator"));	

	}

	/*
	 * When the first cutoff in the array of cut-offs is 1, the first colour 
	 * in the colour array should be returned if and only if the initial value 
	 * of c is at a distance of 2 or more form the origin.
	 */
	@Configuration(tests=10000, size=2)
	@Test
	public void range2Test(double i, double j, double reC, double imC) {
		theIterator = new Iterator(150, colourTest, testCutOffs11);
		if((i*i) + (j*j) > 4)
		{
			assertEquals(theIterator.colourPicker(reC, imC, i, j), (colourTest[0]));
		}
		else
		{
			assertFalse(theIterator.colourPicker(reC, imC, i, j).equals(colourTest[0]));
		}			
	}
	
	/*
	 * The same as range2Test but with a wider range allowed 
	 * of initial values for C.  When the first cutoff in the 
	 * array of cut-offs is 1, the first colour in the 
	 * colour array should be returned if and only if the initial value 
	 * of c is at a distance of 2 or more form the origin.
	 */
	@Configuration(tests=10000, size=10)
	@Test
	public void range10Test(double i, double j, double reC, double imC) {
		theIterator = new Iterator(150, colourTest, testCutOffs11);
		if((i*i) + (j*j) > 4)
		{
			assertEquals(theIterator.colourPicker(reC, imC, i, j), (colourTest[0]));
		}
		else
		{
			assertFalse(theIterator.colourPicker(reC, imC, i, j).equals(colourTest[0]));
		}			
	}

	/* 
	 * This tests that the instance of Iterator gives the expected results for the 
	 * intersection of the Real line with the Mandelbrot set. This intersection is
	 * the closed interval [-2, 0.25]. With z initially 0 and Im(C) = 0, the Iterator
	 * instance should return black for values of Re(C) in the closed interval [-2, 0.25]
	 * and should not return black for values of RE(C) outside this interval.
	 */
	@Configuration(tests=10000, size= 3)
	@Test
	public void needleTest(double reC)
	{
		theIterator = new Iterator(150, colourTest, testCutOffs11);
		if(reC >= -2 && reC <= 0.25)
		{
			assertEquals(theIterator.colourPicker(reC, 0, 0, 0), (new Color(0, 0, 0)));
		}
		else
		{
			assertFalse(theIterator.colourPicker(reC, 0, 0, 0).equals(new Color(0, 0, 0)));
		}	
	}
	
	/* 
	 * This tests that when the value of zero is given to the instance of Iterator
	 * as the real and imaginary component of C, then it will return black (for non-
	 * divergence) if it is given a value of z that is a distance of 1 or less 
	 * from the origin, and will not return black if the initial value if z is
	 * a distance >1 from the origin. When C = 0, the Julia set is a circle of radius 1.
	 */
	@Configuration(tests=10000, size= 2)
	@Test
	public void radiusTest(double reZ, double imZ)
	{
		theIterator = new Iterator(500, colourTest, testCutOffs11);
		if((reZ*reZ) + (imZ * imZ) <= 1)
		{
			assertEquals(theIterator.colourPicker(0, 0, reZ, imZ), (new Color(0, 0, 0)));
		}
		else
		{
			assertFalse(theIterator.colourPicker(0, 0, reZ, imZ).equals(new Color(0, 0, 0)));
		}	
	}

	/* 
	 * This tests that only the expected colours are returned by the Iterator
	 * instance. It can return any of the four colours in the array of colours
	 * created
	 */
	@Configuration(tests=10000, size= 15000)
	@Test
	public void coloursTest(int a, int b, int c, int d)
	{
		Color[] colours = colourArray(4);
		theIterator = new Iterator(15000, colours, testCutOffs11);
		Color returnColour = theIterator.colourPicker(a*0.0001, b*0.0001, c*0.0001, d*0.0001);
		assertTrue(returnColour.equals(colours[0]) || returnColour.equals(colours[1]) 
				|| returnColour.equals(colours[2]) ||returnColour.equals(colours[3]) || returnColour.equals(black));
	}
	
	/* 
	 * This tests that only the expected colours are returned by the Iterator
	 * instance. When it is given two cut offs it can only return the first three 
	 * colours, even though there are six colours in the array
	 */
	@Configuration(tests=10000, size= 15000)
	@Test
	public void coloursTest2(int a, int b, int c, int d)
	{
		Color[] colours = colourArray(6);
		theIterator = new Iterator(150, colours, testCutOffs2);
		Color returnColour = theIterator.colourPicker(a*0.0001, b*0.0001, c*0.0001, d*0.0001);
		assertTrue(returnColour.equals(colours[0]) || returnColour.equals(colours[1]) 
				|| returnColour.equals(colours[2]) || returnColour.equals(black));
	}

	/* The purpose of this test is to check that the instance of Iterator
	 * is returning the correct colour for a range of numbers specifying
	 * the real and imaginary components of c and of the initial value of z.
	 * The initial values are between -1 and 1 to provide good coverage for
	 * all colours involved.
	 */
	@Configuration(tests=100, size= 1000)
	@Test
	public void testCorrectColour(int a, int b, int c, int d)
	{   
		double[] zee = new double[2];
		zee[0] = c*0.001;
		zee[1] = d*0.001;
		int iterations = 0;
		while (zee[0]*zee[0] + zee[1]*zee[1] <= 4 && iterations <=  150)
		{
			zee = nextPoint(a*0.001,b*0.001,zee[0],zee[1]);
			iterations++;
		}
		theIterator = new Iterator(150, colourTest, testCutOffs4);
		Color returnColour = theIterator.colourPicker(a*0.001, b*0.001, c*0.001, d*0.001);
		assertTrue(returnColour.equals(colourTest[0]) || returnColour.equals(colourTest[1]) 
				|| returnColour.equals(colourTest[2]) ||returnColour.equals(colourTest[3]) 
				|| returnColour.equals(colourTest[4]) || returnColour.equals(black));
		if (returnColour.equals(colourTest[0]))
		{
			assertTrue(iterations < testCutOffs4[0]);
		}
		if (returnColour.equals(colourTest[1]))
		{
			assertTrue(iterations >= testCutOffs4[0] && iterations <= testCutOffs4[1]);
		}
		if (returnColour.equals(colourTest[2]))
		{
			assertTrue(iterations >= testCutOffs4[1] && iterations <= testCutOffs4[2]);
		}
		if (returnColour.equals(colourTest[3]))
		{
			assertTrue(iterations >= testCutOffs4[2] && iterations <= testCutOffs4[3]);
		}
		if (returnColour.equals(colourTest[4]))
		{
			assertTrue(iterations >= testCutOffs4[3]);
		}

	}
	
	/* 
	 * The purpose of this method is to iterate a complex number z
	 * to the next point, which is z^2 + c
	 */
	public double[] nextPoint(double x, double y, double a, double b)
	{
		double[] array = new double[2];
		array[0] = (a*a) - (b*b) + x; // real part of next iterate
		array[1] =(2*a*b) + y;        // imaginary part of next iterate
		return array;	// return the new point	
	}
	
	/* 
	 * The purpose of this method is to generate an array of colours
	 * whose red, blue and green components are generated as random
	 * numbers between 0 and 255
	 */
	public Color[] colourArray(int len)
	{
		Random generator = new Random();
		Color[] myColourArray = new Color[len];
		for (int i=0; i < len; i++)
		{
			myColourArray[i] = new Color(generator.nextInt(256),generator.nextInt(256),generator.nextInt(256));
		}
		return myColourArray;
	}

}


