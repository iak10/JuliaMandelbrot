package Project.JuliaMandelbrot;

class App {
	public static void main(String args[]) 
	{ 
		 Mandelbrot mandelbrot = (new Mandelbrot()); // Start application
		 Julia julia = (new Julia(mandelbrot)); 
		 JuliaOrbit juliaOrbit = (new JuliaOrbit(mandelbrot,julia));
		 mandelbrot.heresJulia(julia);
	} 
}  


