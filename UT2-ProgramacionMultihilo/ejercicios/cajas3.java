// ----------------------------------------------------------
// PSP - Unidad 2: Programación Multihilo
// Ejercicio: Supermercado con 3 cajas y 15 clientes
// Versión: 3 cajas con reparto round-robin
// Caja 2 es más lenta que las demás
// ----------------------------------------------------------

import java.util.ArrayList;

// ----------------------------------------------------------
// Clase Caja
// ----------------------------------------------------------
class Caja {
    private final int id;

    public Caja(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Solo un hilo por caja puede ejecutar este método a la vez
    public synchronized void cobrar(int clienteId) {
        try {
            System.out.println("Caja " + id + " atendiendo al cliente " + clienteId);

            // ⚡ Diferencia de velocidad según la caja
            if (id == 2) {
                Thread.sleep(5000); // Caja lenta
            } else {
                Thread.sleep(2000); // Cajas rápidas
            }

            System.out.println("Caja " + id + " terminó con el cliente " + clienteId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// ----------------------------------------------------------
// Clase Supermercado
// ----------------------------------------------------------
class Supermercado {
    private final ArrayList<Caja> cajas;
    private int indiceSiguiente = 0; // reparto circular

    public Supermercado(int numCajas) {
        cajas = new ArrayList<>();
        for (int i = 0; i < numCajas; i++) {
            cajas.add(new Caja(i + 1));
        }
    }

    // Asigna una caja de forma circular
    public synchronized Caja asignarCaja() {
        Caja caja = cajas.get(indiceSiguiente);
        indiceSiguiente = (indiceSiguiente + 1) % cajas.size();
        return caja;
    }
}

// ----------------------------------------------------------
// Clase Cliente
// ----------------------------------------------------------
class Cliente extends Thread {
    private final int id;
    private final Supermercado supermercado;

    public Cliente(int id, Supermercado supermercado) {
        this.id = id;
        this.supermercado = supermercado;
    }

    public void run() {
        System.out.println("Cliente " + id + " entra en la cola de espera.");

        Caja cajaAsignada = supermercado.asignarCaja();
        

        cajaAsignada.cobrar(id);
    }
}

// ----------------------------------------------------------
// Clase principal
// ----------------------------------------------------------
public class cajas3 {
    public static void main(String[] args) {
        Supermercado supermercado = new Supermercado(3);

        Thread[] clientes = new Thread[15];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(i + 1, supermercado);
            clientes[i].start();
        }

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
