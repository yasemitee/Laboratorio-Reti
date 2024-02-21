import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        InetAddress ia;
        InetSocketAddress isa;
        Socket socket;

        try {
            ia = InetAddress.getLocalHost();
            isa = new InetSocketAddress(ia, 59732);
            socket = new Socket();
            socket.connect(isa);

            OutputStream toServer = socket.getOutputStream();
            InputStream toClient = socket.getInputStream();
            int dimBuffer = 100;
            byte buffer[] = new byte[dimBuffer];

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(tastiera);

            // Inserimento operazione
            while (true) {
                System.out.print("Inserisci l'operazione richiesta: ");
                String operazione = reader.readLine();
                byte[] input = String.valueOf(operazione).getBytes();
                toServer.write(input, 0, input.length);
                String ack = new String(buffer, 0, toClient.read(buffer));
                String[] ackSplit = ack.split(":");
                String esito = ackSplit[0];
                String messaggio = ackSplit[1];
                if (esito.equals("ok")) {
                    System.out.println(messaggio + "\n");
                    break;
                } else {
                    System.out.println(messaggio + "\n");
                }
            }

            while (true) {

                // Inserimento primo operando
                while (true) {
                    System.out.print("Inserisci il primo operando: ");
                    String operando1 = reader.readLine();
                    byte[] input = String.valueOf(operando1).getBytes();
                    toServer.write(input, 0, input.length);
                    String ack = new String(buffer, 0, toClient.read(buffer));
                    String[] ackSplit = ack.split(":");
                    String esito = ackSplit[0];
                    String messaggio = ackSplit[1];
                    if (esito.equals("ok")) {
                        System.out.println(messaggio + "\n");
                        break;
                    } else {
                        System.out.println(messaggio + "\n");
                    }
                }

                // Inserimento secondo operando
                while (true) {
                    System.out.print("Inserisci il secondo operando: ");
                    String operando2 = reader.readLine();
                    byte[] input = String.valueOf(operando2).getBytes();
                    toServer.write(input, 0, input.length);
                    String ack = new String(buffer, 0, toClient.read(buffer));
                    String[] ackSplit = ack.split(":");
                    String esito = ackSplit[0];
                    String messaggio = ackSplit[1];
                    if (esito.equals("ok")) {
                        System.out.println(messaggio + "\n");
                        break;
                    } else {
                        System.out.println(messaggio + "\n");
                    }
                }

                // Stampa risultato
                String risultato = new String(buffer, 0, toClient.read(buffer));
                System.out.println("Risultato: " + risultato + "\n");

                System.out.print("Inserisci comando: ");
                String comando = reader.readLine();
                try {
                    if (comando.equals(".")) {
                        System.out.println("Chiusura client");
                        byte[] input = String.valueOf(comando).getBytes();
                        toServer.write(input, 0, input.length);
                        socket.close();
                        break;
                    } else {
                        System.out.println("--- Inizio nuova iterazione ---");
                        byte[] input = String.valueOf(comando).getBytes();
                        toServer.write(input, 0, input.length);
                        String ack = new String(buffer, 0, toClient.read(buffer));
                        String[] ackSplit = ack.split(":");
                        String esito = ackSplit[0];
                        String messaggio = ackSplit[1];
                        if (esito.equals("ok")) {
                            System.out.println(messaggio + "\n");
                        } else {
                            System.out.println("operazione non valida");
                            System.out.println("Chiusura client");
                            socket.close();
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Errore nella chiusura del client");
                    e.printStackTrace();
                }
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
