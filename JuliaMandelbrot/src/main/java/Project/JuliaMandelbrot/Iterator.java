package Project.JuliaMandelbrot;
import java.awt.Color;
/**
 * This class is intialised by its constructor with a list of colours and cutOffs,
 * provided in an array of base type Colour and an array of base type int. The array of
 * base type int must hold a list of strictly increasing positive numbers. constructor 
 * also initialises the variable max (which must be >= 25), with the maximum number of 
 * iterations allowed before it is assumed that a point will not diverge.
 * The method ColurPicker will return a colour based on the number of iterations required
 * for the iterates of a point to diverge to a distance > 2 from the  origin, under the
 * iteration scheme z = z^ + c. If the number of iterations required is >= cutOffs[i] and 
 * less than cutOffs[i+1] then colours[i+1] is returned.
 */
public class Iterator {
	int max;
	Color[] colours;
	int[] cutoffs;

	/**
	 * Initialises the cutoff points for the 
	 */
	public Iterator(int maxIterations, Color[] newColours, int[] newCutoffs) {
		max = maxIterations;
		colours = newColours;
		cutoffs = newCutoffs;
		if(!validate(cutoffs, colours, max))
		{
			throw new RuntimeException("Cutoffs or colours invalid");
		}
	}

	/**
	 * represents 2 complex numbers with two pairs of a real number and imaginary number
	 * Carries out the iteration of Z = Z^2+C until Z is at a distance greater than or equal to 2 from the origin
	 * or the number of iterations exceeds maxIterations, returns number of iterations carried out.
	 * @Param reC - real component of C
	 * @Param imC - imaginary component of C
	 * @Param reZ - real component of Z
	 * @Param imZ - imaginary component of Z
	 * @Param maxIterations - number of iterations the method will stop iterating at
	 */
	public int juliaIteration(double reC, double imC, double reZ, double imZ, int maxIterations) {
		double temp = 0;
		int iterations = 0;
		while (reZ * reZ + imZ * imZ <= 4 && iterations <= maxIterations) {
			temp = (reZ * reZ) - (imZ * imZ) + reC;
			imZ = (2 * reZ * imZ) + imC;
			reZ = temp;
			iterations++;
		}
		return iterations;
	}

	/**
	 * Selects and returns a colour according to the number of iterations returned from juliaIteration.
	 * The colour will be used to plot the pixel which represents the point.
	 * @Param reC - real component of C
	 * @Param imC - imaginary component of C
	 * @Param reZ - real component of Z
	 * @param imZ - imaginary component of Z
	 */
	public Color colourPicker(double reC, double imC, double reZ, double imZ)
	{
		Color currentColour;
		int iterations = juliaIteration(reC, imC, reZ, imZ, max);
		if (iterations > max)
			currentColour = new Color(00, 00, 00);
		else 
		{
			int iMax = Math.min(colours.length, cutoffs.length);
			int i = 0;
			while (iterations >= cutoffs[i] && i < iMax - 1) 
			{
				i++;
			}
			currentColour = colours[i];
			if (iterations >= cutoffs[i] && colours.length > i + 1)
			{
				currentColour = colours[i+1];
			}
		}
		return currentColour;
	}
	/**
	 * This method is invoked by the constructor, in order to validate the input
	 * provided to the constructor
	 */
	
	public boolean validate(int[] cutoffs, Color[] colours, int max)
	{
		if(colours.length == 0 || cutoffs.length == 0)
		{
			return false; 			 
		}
		if (cutoffs[0] < 1)
		{
			return false; 			 
		}
		if (max < 25)
		{
			return false; 			 
		}	
		for(int i = 1; i < cutoffs.length; i++ )
		{
			if(cutoffs[i] <= cutoffs[i-1])
			{
				return false; 
			}
		}
		return true;		
	}
}
