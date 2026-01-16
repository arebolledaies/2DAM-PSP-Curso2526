import java.io.*;
import java.net.*;

public class UDPObjetoCliente1 {

    private static final String HOST = "localhost";
    private static final int PUERTO_SERVIDOR = 6000;
    private static final int MAX_DATAGRAMA = 1024;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("PROGRAMA CLIENTE INICIADO....");

        InetAddress ipServidor = InetAddress.getByName(HOST);

        try (DatagramSocket cliente = new DatagramSocket()) {

            // 1) Datagrama vacío: sólo para que el servidor conozca IP/puerto del cliente
            byte[] vacio = new byte[0];
            DatagramPacket pSolicitud = new DatagramPacket(vacio, 0, ipServidor, PUERTO_SERVIDOR);
            cliente.send(pSolicitud);

            // 2) Recibe Persona del servidor
            byte[] bufferEntrada = new byte[MAX_DATAGRAMA];
            DatagramPacket pEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            cliente.receive(pEntrada);

            Persona dato = (Persona) deserializar(pEntrada.getData(), pEntrada.getLength());
            System.out.println("Recibo: " + dato.getNombre() + "*" + dato.getEdad());

            // 3) Modifica el objeto
            dato.setNombre("Juan Ramos");
            dato.setEdad(22);

            // 4) Envía Persona modificada al servidor
            byte[] datosPersona = serializar(dato);
            DatagramPacket pEnvio = new DatagramPacket(datosPersona, datosPersona.length, ipServidor, PUERTO_SERVIDOR);
            cliente.send(pEnvio);

            System.out.println("Envio: " + dato.getNombre() + "*" + dato.getEdad());
        }
    }

    private static byte[] serializar(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        
    }

    private static Object deserializar(byte[] data, int length) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data, 0, length);
        ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        
    }
}
