package Project.JuliaMandelbrot;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;


public class IteratorWhiteBoxTest {
	Iterator theIterator;
	private Color[] colourTest =  {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50)};
	private int[] testCutOffs0 = {2,3,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs1 = {1,3,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs2 = {4,10};  
	

	/**
	 * Tests the return of the correct colour, (other 
	 * than black), which tests the 1st condition
	 * in while loop in the JuliaIteration method, and
	 * the code within this while-loop, and the else clause 
	 * in the colourPicker method's if-else statement. Most of the 
	 * test values for Z_0 do not result in the number of iterations
	 * being exactly equal to a cut--ff value.
	 */
	@Test
	public void cutoffTest1()
	{
		theIterator = new Iterator(150, colourTest, testCutOffs0);
		assertEquals(new Color(230, 230, 50), theIterator.colourPicker(-0.07, 0.67, 0.184539, -0.383721));
		assertEquals(new Color(190, 220, 100), theIterator.colourPicker(-0.07, 0.67, 0.288195, -0.327397));	
		assertEquals(new Color(125, 220, 140), theIterator.colourPicker(-0.07, 0.67, 0.41588, -0.58746));	
		assertEquals(new Color(0, 255, 255), theIterator.colourPicker(-0.07, 0.67, 0.411552, -0.651201));
		assertEquals(new Color(190, 160, 160), theIterator.colourPicker(-0.07, 0.67, 0.394239, -0.694545));
		assertEquals(new Color(0, 160, 160), theIterator.colourPicker(-0.07, 0.67, 0.381254, -0.73024));
		assertEquals(new Color(130, 50, 50), theIterator.colourPicker(-0.07, 0.67, 0.396403, -0.771035));
		assertEquals(new Color(140, 50, 180), theIterator.colourPicker(-0.07, 0.67, 0.394239, -0.837325));
		assertEquals(new Color(80, 180, 80), theIterator.colourPicker(-0.07, 0.67, 0.532745, -0.931662));
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, 0.711973, -1.026396));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 0.926897, -1.044575));		
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, 0.993165, -1.301095));

	}
	
	/**
	 * This tests that values of Z_0 that require a number of iterations to diverge
	 * that is exactly equal to a cut-off will result in the correct colour being returned.
	 * This tests the correct termination of the loop within the else-clause of the if-else 
	 * statement in the colourPicker method
	 */
	@Test
	public void cutOffTest2()
	{
		theIterator = new Iterator(150, colourTest, testCutOffs1);
		assertEquals(colourTest[1], theIterator.colourPicker(0.08,-0.67, 1.0245, 1.0245));
		assertEquals(colourTest[2], theIterator.colourPicker(0.08,-0.67, 0.9745, 0.9745));
		assertEquals(colourTest[3], theIterator.colourPicker(0.08,-0.67, 0.9565, 0.9565));
		assertEquals(colourTest[4], theIterator.colourPicker(0.08,-0.67, 0.9490, 0.9490));
		assertEquals(colourTest[5], theIterator.colourPicker(0.08,-0.67, 0.9255, 0.9255));
		assertEquals(colourTest[6], theIterator.colourPicker(0.08,-0.67, 0.9155, 0.9155));
		assertEquals(colourTest[7], theIterator.colourPicker(0.08,-0.67, 0.0005, 0.0005));
	  	assertEquals(colourTest[8], theIterator.colourPicker(0.08,-0.67, 0.1525, 0.1525));
		assertEquals(colourTest[9], theIterator.colourPicker(0.08,-0.67, 0.1890, 0.1890));
		assertEquals(colourTest[10], theIterator.colourPicker(0.08,-0.67, 0.3150, 0.3150));
		assertEquals(colourTest[11], theIterator.colourPicker(0.08,-0.67, 0.6630, 0.6630));
	}


	/**
	 * This tests values close to the boundary for returning the first and second
	 * colours in the colourTest array. In the first two statements the first colour is
	 * returned because the value of Z_0 is already at a distance of 2 or more 
	 * from the origin.
	 * With the first cut-off set to 1 iteration, the values of 0 + 1.99i, 0 - 1.99i,
	 * 1.99 + 0i and -1.99+0i cause the second colour to be returned.
	 */
	@Test
	public void loopNonEntry()
	{
		theIterator = new Iterator(150, colourTest, testCutOffs1);
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, 2, 0));
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, -2, 0));

		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 1.99, 0));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, -1.99, 0));

		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 0, 1.99));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 0, -1.99));

	}
	
	/**
	 * Tests that non divergent values of Z and C return correct colour (black)
	 * This tests second loop condition in juliaIteration and if clause of the 
	 * if-else statement in colourPicker
	 */
	@Test
	public void nonDivergenceTest()
	{
		theIterator = new Iterator(150, colourTest, testCutOffs0);
		assertEquals(new Color(0, 0, 0), theIterator.colourPicker(0, 0, 0, 0));
		assertEquals(new Color(0, 0, 0), theIterator.colourPicker(-0.07, 0.67, 0, 0));
		assertEquals(new Color(0, 0, 0), theIterator.colourPicker(-0.493333, 0.5925, 0.004988, 0.084718));
		assertEquals(new Color(0, 0, 0), theIterator.colourPicker(-0.493333, 0.5925, -0.187101, -0.22359));
		assertEquals(new Color(0, 0, 0), theIterator.colourPicker(-0.493333, 0.5925, -0.242976, -0.235891));
	}
	
	/**
	 * Tests the ability to have cut off array and colour array at different lengths 
	 * When only two colours are given in the array of colours when the constructor is 
	 * invoked then any values of z and c should return one of these two colours or black
	 */
	@Test
	public void arrayLengthTest()
	{
		theIterator = new Iterator(150, colourTest, testCutOffs2);
		for(double i = -2; i <= 2; i += 0.1)
		{
			assertTrue(theIterator.colourPicker(-0.07, 0.67, i, i).equals(colourTest[0]) || 
					theIterator.colourPicker(-0.07, 0.67, i, i).equals(colourTest[1]) || 
					theIterator.colourPicker(-0.07, 0.67, i, i).equals(new Color(0, 0, 0)));
			assertTrue(theIterator.colourPicker(-i, -i, i, i).equals(colourTest[0]) || 
					theIterator.colourPicker(-i, -i, i, i).equals(colourTest[1]) || 
					theIterator.colourPicker(-i, -i, i, i).equals(new Color(0, 0, 0)));
		}	
	}
	/**
	 * Tests that the constructor raises a run-time exception in all four situations
	 * that it should: 9i0 if it is initialised with an empty colour array, initialised 
	 * with an empty cut-offs array, or with unordered cut-offs or with the first cut-off
	 * less than zero.
	 */
	
	@Test(expected=RuntimeException.class)
	public void emptyColoursArrayTest()
	{
		Color[] emptyColours = {};
		theIterator = new Iterator(150, emptyColours, testCutOffs0);
	}
	
	@Test(expected=RuntimeException.class)
	public void emptyCutoffArrayTest()
	{
		int[] emptyCutOffs = {};
		theIterator = new Iterator(150, colourTest, emptyCutOffs);
	}
	
	@Test(expected=RuntimeException.class)
	public void negativeCutoffTest()
	{
		int[] invalidCutoffs = {-1, 2, 3, 4}; 
		theIterator = new Iterator(150, colourTest, invalidCutoffs);
	}
	
	@Test(expected=RuntimeException.class)
	public void unorderedCutoffTest()
	{
		int[] invalidCutoffs = {1, 2, 4, 3}; 
		theIterator = new Iterator(150, colourTest, invalidCutoffs);
	}

}
