import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(59732);
            System.out.println("Server in ascolto sulla porta " + serverSocket.getLocalPort());
            while (true) {
                clientSocket = serverSocket.accept();
                if (clientSocket == null) {
                    System.out.println("Nessun client in coda, il server verrà chiuso");
                    break;
                }
                System.out.println("Client " + clientSocket.getInetAddress() + " connesso");

                InputStream toServer = clientSocket.getInputStream();
                int dimBuffer = 100;
                byte buffer[] = new byte[dimBuffer];
                OutputStream toClient = clientSocket.getOutputStream();
                String ack;
                byte[] messaggio;

                // Attributi richiesti
                String operazione;
                double valore1;
                double valore2;
                double risultato = 0;

                // Ricezione operazione
                while (true) {
                    operazione = new String(buffer, 0, toServer.read(buffer));
                    if (!operazione.equals("+") && !operazione.equals("-") && !operazione.equals("*")
                            && !operazione.equals("/")) {
                        ack = "fail: operazione non valida, ripere l'indicazione di una operazione";
                        messaggio = ack.getBytes();
                        toClient.write(messaggio, 0, messaggio.length);
                    } else {
                        ack = "ok: operazione inoltrata con successo";
                        messaggio = ack.getBytes();
                        toClient.write(messaggio, 0, messaggio.length);
                        break;
                    }
                }

                while (true) {
                    System.out.println("Operazione in corso: " + operazione);
                    ack = "";
                    // Ricezione primo operando
                    while (true) {
                        try {
                            valore1 = Double.parseDouble(new String(buffer, 0, toServer.read(buffer)));
                            System.out.println("Primo valore: " + valore1);
                            ack = "ok: primo operando inoltrata con successo";
                            messaggio = ack.getBytes();
                            toClient.write(messaggio, 0, messaggio.length);
                            break;
                        } catch (NumberFormatException e) {
                            ack = "fail: operazione non valida, ripere l'indicazione del primo operando";
                            messaggio = ack.getBytes();
                            toClient.write(messaggio, 0, messaggio.length);
                        }
                    }

                    // Ricezione secondo operando
                    while (true) {
                        try {
                            double tempValore2 = Double.parseDouble(new String(buffer, 0, toServer.read(buffer)));
                            System.out.println("Secondo valore: " + tempValore2);
                            if (operazione.equals("/") && tempValore2 == 0) {
                                ack = "fail: operazione non valida, ripere l'indicazione del secondo operando";
                                messaggio = ack.getBytes();
                                toClient.write(messaggio, 0, messaggio.length);
                            } else {
                                valore2 = tempValore2;
                                ack = "ok: secondo operando inoltrata con successo";
                                messaggio = ack.getBytes();
                                toClient.write(messaggio, 0, messaggio.length);
                                break;
                            }
                        } catch (NumberFormatException e) {
                            ack = "fail: operazione non valida, ripere l'indicazione del secondo operando";
                            messaggio = ack.getBytes();
                            toClient.write(messaggio, 0, messaggio.length);
                        }
                    }

                    // Invio risultato

                    if (operazione.equals("+")) {
                        risultato = valore1 + valore2;
                    } else if (operazione.equals("-")) {
                        risultato = valore1 - valore2;
                    } else if (operazione.equals("*")) {
                        risultato = valore1 * valore2;
                    } else if (operazione.equals("/")) {
                        risultato = valore1 / valore2;
                    }
                    System.out.println(risultato);
                    messaggio = Double.toString(risultato).getBytes();
                    toClient.write(messaggio, 0, messaggio.length);

                    String comando = new String(buffer, 0, toServer.read(buffer));
                    System.out.println("Comando finale: " + comando);
                    if (comando.equals(".")) {
                        System.out.println("Chiusura connessione con client");
                        break;
                    } else {
                        if (!comando.equals("+") && !comando.equals("-") && !comando.equals("*")
                                && !comando.equals("/")) {
                            ack = "fail: operazione non valida";
                            messaggio = ack.getBytes();
                            toClient.write(messaggio, 0, messaggio.length);
                            break;
                        } else {
                            operazione = comando;
                            ack = "ok: operazione inoltrata con successo";
                            messaggio = ack.getBytes();
                            toClient.write(messaggio, 0, messaggio.length);
                        }
                    }
                    System.out.println("--- Fine Iterazione ---");
                }
                System.out.println("--- Fine client" + clientSocket.getInetAddress() + " ---");
            }
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
