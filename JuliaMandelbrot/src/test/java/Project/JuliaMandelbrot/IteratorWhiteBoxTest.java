package Project.JuliaMandelbrot;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
/*
 * The white box test of th eIterator class aims to test all lines of code in the class
 * and all combinations of tru and false for the boolean conditions and complex conditions.
 */
public class IteratorWhiteBoxTest {
	Iterator theIterator;
	private Color[] colourTest =  {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140), new Color(190,220,100),
			new Color(230,230,50), new Color(255,255,255)};
	private Color[] colourTest2 =  {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
			new Color(80, 180, 80), new Color(140, 50, 180), 
			new Color(130, 50, 50), new Color(0, 160, 160), 
			new Color(190, 160, 160), new Color(0, 255, 255), 
			new Color(125, 220, 140),new Color(190,220,100),
			new Color(230,230,50)};
	private int[] testCutOffs0 = {2,3,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs1 = {1,3,4,5,7,9,11,14, 17, 25, 50, 58};  
	private int[] testCutOffs2 = {4,10};  
	

	/**
	 * cutoffTest1 tests the return of the correct colour, (other than black).
	 * THis tests the 1st condition in the while loop in the juliaIteration
	 * method, both when it is true and when it becomes false.
	 * It also tests the execution of the else clause in the 
	 * colourPicker method's if-else statement. 
	 */
	@Test 
	public void cutoffTest1A() // TEST 1A
	{
		double reC = -0.07;
		double imC = 0.67;
		theIterator = new Iterator(150, colourTest, testCutOffs0);
		assertEquals(new Color(190, 220, 100), theIterator.colourPicker(reC,imC, 0.288195, -0.327397));	
		assertEquals(new Color(125, 220, 140), theIterator.colourPicker(reC,imC, 0.41588, -0.58746));	
		assertEquals(new Color(0, 255, 255), theIterator.colourPicker(reC,imC, 0.411552, -0.651201));
		assertEquals(new Color(190, 160, 160), theIterator.colourPicker(reC,imC, 0.394239, -0.694545));
		assertEquals(new Color(0, 160, 160), theIterator.colourPicker(reC,imC, 0.381254, -0.73024));
		assertEquals(new Color(130, 50, 50), theIterator.colourPicker(reC,imC, 0.396403, -0.771035));
		assertEquals(new Color(140, 50, 180), theIterator.colourPicker(reC,imC, 0.394239, -0.837325));
		assertEquals(new Color(80, 180, 80), theIterator.colourPicker(reC,imC, 0.532745, -0.931662));
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(reC,imC, 0.711973, -1.026396));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(reC,imC, 0.926897, -1.044575));		
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(reC,imC, 0.993165, -1.301095));

	}
	
	/**
	 * This tests that values of Z_0 that require a number of iterations to diverge
	 * that is equal to (58) or greater than (69 and 70) the last cut-off. The test
	 * checks Color.WHITE is returned. It also checks the termination of the while
	 * loop inside colourPicker's else-clause by the second loop control condition, 
	 * and the execution of the following if-statement with both conditions true.
	 * In all other cases in the test below the loop will be terminated when 
	 * the first loop control condition is false.
	 */
	@Test 
	public void cutoffTest1B() // TEST 1B
	{
		double reC = -0.0722;
		double imC = -0.6675 ;
		theIterator = new Iterator(150, colourTest, testCutOffs0);
		assertEquals(Color.white, theIterator.colourPicker(reC,imC, 0.73, 0.683));
		assertEquals(Color.white, theIterator.colourPicker(reC,imC, 0.716, 0.561));
		assertEquals(Color.white, theIterator.colourPicker(reC,imC, 0.094, 0.654));
	}
	
	
	/**
	 * This tests that values of Z_0 that require a number of iterations to diverge
	 * that is exactly equal to a cut-off will result in the correct colour being returned.
	 * This tests the correct termination of the loop within the else-clause of the if-else 
	 * statement in the colourPicker method.
	 */
	@Test
	public void cutOffTest2() // TEST 2
	{
		double reC = 0.08;
		double imC = -0.67;
		theIterator = new Iterator(150, colourTest2, testCutOffs1);
		assertEquals(colourTest2[1], theIterator.colourPicker(reC,imC, 1.0245, 1.0245));
		assertEquals(colourTest2[2], theIterator.colourPicker(reC,imC, 0.9745, 0.9745));
		assertEquals(colourTest2[3], theIterator.colourPicker(reC,imC, 0.9565, 0.9565));
		assertEquals(colourTest2[4], theIterator.colourPicker(reC,imC, 0.9490, 0.9490));
		assertEquals(colourTest2[5], theIterator.colourPicker(reC,imC, 0.9255, 0.9255));
		assertEquals(colourTest2[6], theIterator.colourPicker(reC,imC, 0.9155, 0.9155));
		assertEquals(colourTest2[7], theIterator.colourPicker(reC,imC, 0.0005, 0.0005));
	  	assertEquals(colourTest2[8], theIterator.colourPicker(reC,imC, 0.1525, 0.1525));
		assertEquals(colourTest2[9], theIterator.colourPicker(reC,imC, 0.1890, 0.1890));
		assertEquals(colourTest2[10], theIterator.colourPicker(reC,imC, 0.3150, 0.3150));
		assertEquals(colourTest2[11], theIterator.colourPicker(reC,imC, 0.6630, 0.6630));
		assertEquals(colourTest2[11], theIterator.colourPicker(-0.7061, -0.2737,1.309,0.121));
	}



	/**
	 * This tests values close to the boundary for returning the first and second
	 * colours in the colourTest array. The first colour is
	 * returned because the value of Z_0 is already at a distance of 2 or more 
	 * from the origin.
	 */
	@Test
	public void loopNonEntry() // TEST 3A
	{
		theIterator = new Iterator(150, colourTest, testCutOffs1);
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, 2.001, 0));
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, -2.001, 0));
		assertEquals(new Color(36, 159, 120), theIterator.colourPicker(-0.07, 0.67, 0, 2.001));

	}
	/**
	 * This tests values close to the boundary for returning the first and second
	 * colours in the colourTest array. The second colour is returned 
	 * returned because the value of Z_0 is not already at a distance of 2 or more 
	 * from the origin, so the loop is entered.
	 */	
	public void loopEntry() // TEST 3B
	{
		theIterator = new Iterator(150, colourTest, testCutOffs1);
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 2, 0));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 2, 0));
		assertEquals(new Color(0,50,150), theIterator.colourPicker(-0.07, 0.67, 0, 2));

	}
	
	/**
	 * Tests that non divergent values of Z and C return correct colour (black)
	 * This tests second loop condition in juliaIteration and if clause of the 
	 * if-else statement in colourPicker and checks correct execution when the 
	 * first if-condition in colourPicker is true.
	 */
	@Test
	public void nonDivergenceTest() // TEST 4
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
	 * invoked then any values of z and c should return one of these two colours or black.
	 * This tests correct execution of statement iMax := Math.min(colours.length, cutoffs.length)
	 */
	@Test
	public void arrayLengthTest() // TEST 5
	{
		theIterator = new Iterator(150, colourTest, testCutOffs2);
		for(double i = -2.2; i <= 2.2; i += 0.1)
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
	 * Tests that the constructor raises a run-time exception in all situations
	 * that it should: (i) if it is initialised with an empty colour array, initialised 
	 * with an empty cut-offs array, or with both arrays empty or with unordered 
	 * cut-offs or with the first cut-off less than zero, or with a value < 25 for
	 * the maximum number of iterations. 
	 */
	
	@Test(expected=RuntimeException.class)
	public void emptyColoursArrayTest()  // TEST 6
	{
		Color[] emptyColours = {};
		theIterator = new Iterator(150, emptyColours, testCutOffs0);
	}
	
	@Test(expected=RuntimeException.class)
	public void emptyCutoffArrayTest() // TEST 7
	{
		int[] emptyCutOffs = {};
		theIterator = new Iterator(150, colourTest, emptyCutOffs);
	}
	@Test(expected=RuntimeException.class)
	public void emptyBothTest()   // TEST 8
	{
		Color[] emptyColours = {};
		int[] emptyCutOffs = {};
		theIterator = new Iterator(150,emptyColours , emptyCutOffs);
	}
	
	@Test(expected=RuntimeException.class) 
	public void negativeCutoffTest()  // TEST 9
	{
		int[] invalidCutoffs = {-1, 2, 3, 4}; 
		theIterator = new Iterator(150, colourTest, invalidCutoffs);
	}
	
	@Test(expected=RuntimeException.class) // TEST 10
	public void unorderedCutoffTest()
	{
		int[] invalidCutoffs = {1, 2, 4, 3}; 
		theIterator = new Iterator(150, colourTest, invalidCutoffs);
	}
	@Test(expected=RuntimeException.class)  // TEST 11
	public void loMaxIterations()
	{
		theIterator = new Iterator(24, colourTest, testCutOffs0);
	}
	
	@Test // TEST 12
	public void doubleFalse1()
	{
		theIterator = new Iterator(59, colourTest, testCutOffs0);
		double reC = -0.0722;
		double imC = -0.6675;
		assertEquals(Color.black, theIterator.colourPicker(reC, imC, 0.094, 0.654));
	}
	
	@Test // TEST 13
	public void doubleFalse2()
	{ 
		final Color[] shortColours =  {new Color(36, 159, 120), new Color(0,50,150), new Color(36, 159, 120),
				new Color(80, 180, 80), new Color(140, 50, 180)}; 
		final int[] testCutOffs3 = {2,3,4,15,70,99,111,114, 117, 125, 150, 158}; 
		theIterator = new Iterator(160, shortColours, testCutOffs3);
		assertEquals(new Color(140, 50, 180), theIterator.colourPicker(-0.07,0.67, 0.411552, -0.651201));
	}
}
