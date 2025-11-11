// ----------------------------------------------------------
// PSP - UT2: Programación Multihilo
// Ejercicio: Supermercado con 1 caja y 15 clientes
// Versión: sin wait()/notify(), pero con método synchronized
// ----------------------------------------------------------
// Clave: el método cobrar() está sincronizado, así solo un hilo
// (cliente) puede ejecutarlo a la vez. Los demás esperan su turno
// automáticamente antes de entrar al método.
// ----------------------------------------------------------

class CajaUnica {
    private final int id;             // Identificador de la caja 
    private boolean ocupada = false;  // Estado de la caja

    public CajaUnica(int id) {
        this.id = id;
    }

    // Método sincronizado: solo un hilo puede ejecutar este método a la vez.
    public synchronized void cobrar(int clienteId) {
        try {
            System.out.println("Cliente " + clienteId + " entrando en la caja...");

            // Si la caja está ocupada, el cliente esperará fuera del método synchronized
            // (en realidad, el bloqueo del monitor hace que solo entre un hilo cada vez)
            ocupada = true;
            System.out.println("Caja " + id + " atendiendo al cliente " + clienteId);

            // Simula el proceso de pago (5 segundos)
            Thread.sleep(5000);

            System.out.println("Caja " + id + " terminó con el cliente " + clienteId);
            ocupada = false;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Cliente " + clienteId + " interrumpido durante el cobro.");
        }
    }
}

// ----------------------------------------------------------
// Clase Cliente: cada cliente es un hilo independiente
// ----------------------------------------------------------
class Cliente extends Thread {
    private final int id;
    private final CajaUnica caja;

    public Cliente(int id, CajaUnica caja) {
        this.id = id;
        this.caja = caja;
    }

   
    public void run() {
        System.out.println("Cliente " + id + " entra en la cola de espera.");
        caja.cobrar(id); // Cada cliente pasa por la caja (uno por uno)
    }
}

// ----------------------------------------------------------
// Clase principal: crea la caja y los 15 clientes
// ----------------------------------------------------------
public class cajas1 {
    public static void main(String[] args) {
        CajaUnica caja = new CajaUnica(1);

        // Crear los 15 clientes
        Thread[] clientes = new Thread[15];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(i + 1, caja);
            clientes[i].start();
        }

        // Esperar a que todos los clientes terminen
        for (Thread c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Supermercado cerrado.");
    }
}
