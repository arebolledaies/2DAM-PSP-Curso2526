class Contador implements Runnable {
    private int valor = 0; // variable de instancia

    public void run() {
       valor=valor+1;
        System.out.println(Thread.currentThread().getName() + ": " + valor);
    }
}

public class Var1 {
    public static void main(String[] args) {
        // Cada hilo con su propio objeto
        new Thread(new Contador(), "Hilo 1").start();
        new Thread(new Contador(), "Hilo 2").start();
    }
}
