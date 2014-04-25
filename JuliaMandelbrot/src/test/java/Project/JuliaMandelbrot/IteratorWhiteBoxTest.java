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
	 * Tests the return of the correct colour, tests first condition in while loop 
	 * and else clause in colourPicker if-else statement
	 */
	@Test
	public void colourTest()
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
	 * This tests the non entry of the while loop in the juliaIteration 
	 * method due to the first loop control condition bring already false
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
	 * Tests that the boundary values for non divergent values of Z and C return correct colour
	 * tests second loop condition in juliaIteration and if clause of the if-else statement in colourPicker
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
	 * Tests the second loop control condition and also tests the 
	 * ability to have cut off array and colour array at different lengths
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
		}
		
	}
	
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
