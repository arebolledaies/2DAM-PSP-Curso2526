class Ejemplo implements Runnable {
    public void run() {
        int contador = 0; //  variable local, exclusiva de este hilo
        if (Thread.currentThread().getName()=="Hilo 1") contador++;
        if (Thread.currentThread().getName()=="Hilo 2") contador--;
        System.out.println(Thread.currentThread().getName() + ": " + contador);
    }
}

public class Var2 {
    public static void main(String[] args) {
        new Thread(new Ejemplo(), "Hilo 1").start();
        new Thread(new Ejemplo(), "Hilo 2").start();
    }
}
