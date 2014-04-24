package Project.JuliaMandelbrot;

class App {
	public static void main(String args[]) 
	{ 
		 ApplicationM mandelbrot = (new ApplicationM()); // Start application
		 ApplicationJ julia = (new ApplicationJ(mandelbrot)); 
		 ApplicationJOrbit juliaOrbit = (new ApplicationJOrbit(mandelbrot,julia));
		 mandelbrot.heresJulia(julia);
	} 
}  


