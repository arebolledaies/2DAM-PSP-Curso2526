import java.io.*;
import java.net.*;

public class UDPObjetoServidor1 {

    private static final int PUERTO = 6000;
    private static final int MAX_DATAGRAMA = 1024;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Esperando al cliente.....");

        try (DatagramSocket servidor = new DatagramSocket(PUERTO)) {

            // 1) Espera un datagrama (vacío) para conocer IP/puerto del cliente
            byte[] buffer = new byte[MAX_DATAGRAMA];
            DatagramPacket pSolicitud = new DatagramPacket(buffer, buffer.length);
            servidor.receive(pSolicitud);

            InetAddress ipCliente = pSolicitud.getAddress();
            int puertoCliente = pSolicitud.getPort();

            // 2) Envía Persona al cliente
            Persona per = new Persona("Juan", 20);
            byte[] datosPersona = serializar(per);

            DatagramPacket pEnvio = new DatagramPacket(datosPersona, datosPersona.length, ipCliente, puertoCliente);
            servidor.send(pEnvio);
            System.out.println("Envio: " + per.getNombre() + "*" + per.getEdad());

            // 3) Recibe Persona modificada
            byte[] bufferVuelta = new byte[MAX_DATAGRAMA];
            DatagramPacket pVuelta = new DatagramPacket(bufferVuelta, bufferVuelta.length);
            servidor.receive(pVuelta);

            Persona dato = (Persona) deserializar(pVuelta.getData(), pVuelta.getLength());
            System.out.println("Recibo: " + dato.getNombre() + "*" + dato.getEdad());
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
