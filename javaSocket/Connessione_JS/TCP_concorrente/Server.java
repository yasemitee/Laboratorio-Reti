package TCP_concorrente;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        ServerSocket sSocket = null;
        int max_client = 5;
        int i = 0; // indice del client che servo
        List<Socket> listClient = new ArrayList<>();
        try {
            sSocket = new ServerSocket(62128);
            System.out.println("Server in ascolto sulla porta " + sSocket.getLocalPort());
            try {
                sSocket.setSoTimeout(10000);

                // fase di accettazione
                while (listClient.size() < max_client) {
                    listClient.add(sSocket.accept());
                }
            } catch (SocketTimeoutException ste) {
                System.out.println("Tempo di accetazione client scaduto");
                System.out.println("Accettati " + listClient.size() + " client");
            }
            while (true) {
                while (listClient.size() > 0) {
                    try {
                        Socket currSocket = listClient.get(i);
                        System.out.println("Sto servendo il client " + i);
                        System.out.print(currSocket.getLocalAddress() + " ");
                        System.out.print(currSocket.getLocalPort());

                        InputStream toServer = currSocket.getInputStream();
                        OutputStream toClient = currSocket.getOutputStream();

                        currSocket.setSoTimeout(5000);

                        while (true) {
                            byte[] buffer = new byte[100];
                            toServer.read(buffer);
                            String m = new String(buffer, 0, buffer.length).trim();
                            System.out.println(m);

                            if (m.equals(".")) {
                                System.out.println("Chiudo connessione con client");
                                toClient.write(String.valueOf(".").getBytes());

                                currSocket.close();
                                listClient.remove(i);
                                if (listClient.size() != 0) {
                                    i = (i + 1) % listClient.size();
                                } else {
                                    i = 0;
                                }
                                break;
                            } else {
                                toClient.write(String.valueOf("ack").getBytes());
                            }
                        }
                    } catch (SocketTimeoutException ste) {
                        System.out.println("Pausa");
                        if (listClient.size() != 0) {
                            i = (i + 1) % listClient.size();
                        } else {
                            i = 0;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}