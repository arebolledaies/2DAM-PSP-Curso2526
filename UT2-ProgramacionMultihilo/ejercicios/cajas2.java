// ----------------------------------------------------------
// PSP - Unidad 2: Programación Multihilo
// Ejercicio: Supermercado con 3 cajas y 15 clientes
// Versión: 3 cajas en ArrayList, reparto round-robin
// ----------------------------------------------------------

import java.util.ArrayList;

// ----------------------------------------------------------
// Clase Caja: representa una caja registradora
// ----------------------------------------------------------
class Caja {
    private final int id;

    public Caja(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Solo un hilo puede estar dentro de cobrar() por caja
    public synchronized void cobrar(int clienteId) {
        try {
            System.out.println("Caja " + id + " atendiendo al cliente " + clienteId);
            Thread.sleep(5000); // Simula el cobro (2 s para que se vea el efecto)
            System.out.println("Caja " + id + " terminó con el cliente " + clienteId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// ----------------------------------------------------------
// Clase Supermercado: gestiona la lista de cajas
// ----------------------------------------------------------
class Supermercado {
    private final ArrayList<Caja> cajas;
    private int indiceSiguiente = 0;   // para repartir en round-robin

    public Supermercado(int numCajas) {
        cajas = new ArrayList<>();
        for (int i = 0; i < numCajas; i++) {
            cajas.add(new Caja(i + 1));
        }
    }

    // Asigna una caja de forma circular: 1,2,3,1,2,3,...
    public synchronized Caja asignarCaja() {
        Caja caja = cajas.get(indiceSiguiente);
        indiceSiguiente = (indiceSiguiente + 1) % cajas.size();
        return caja;
    }
}

// ----------------------------------------------------------
// Clase Cliente: cada cliente es un hilo independiente
// ----------------------------------------------------------
class Cliente extends Thread {
    private final int id;
    private final Supermercado supermercado;

    public Cliente(int id, Supermercado supermercado) {
        this.id = id;
        this.supermercado = supermercado;
    }

    @Override
    public void run() {
        System.out.println("Cliente " + id + " entra en la cola de espera.");

        // El supermercado le asigna una caja (reparto circular)
        Caja cajaAsignada = supermercado.asignarCaja();
        //System.out.println("Cliente " + id + " se dirige a la caja " + cajaAsignada.getId());

        // El cliente paga en esa caja
        cajaAsignada.cobrar(id);
    }
}

// ----------------------------------------------------------
// Clase principal
// ----------------------------------------------------------
public class cajas2 {
    public static void main(String[] args) {
        // Supermercado con 3 cajas
        Supermercado supermercado = new Supermercado(3);

        // Crear los 15 clientes
        Thread[] clientes = new Thread[15];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(i + 1, supermercado);
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
