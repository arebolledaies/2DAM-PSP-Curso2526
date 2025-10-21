public class Hilo3 extends Thread {
	// constructor
	public Hilo3(String nombre) {
		super(nombre);
		System.out.println("CREANDO HILO:" + getName());
	}

	// metodo run
	public void run() {
		for (int i=0; i<5; i++) 
			System.out.println("Hilo:" + getName() + " C = " + i);
	}

	//
	public static void main(String[] args) {
		Hilo3 h1 = new Hilo3("Hilo 1");
		Hilo3 h2 = new Hilo3("Hilo 2");
		Hilo3 h3 = new Hilo3("Hilo 3");
			
		h1.start();
		h2.start();
		h3.start();
		
		System.out.println("3 HILOS INICIADOS...");
	}// main
	
}// Hilo3