package TCP_iterativo;

import java.io.*;
import java.net.*;

public class IterServer {
    public static void main(String[] args) {
        try {
            ServerSocket sSrv = new ServerSocket(0);
            System.out.println("porta: "+ sSrv.getLocalPort());
            Socket connessione;
            

            while(true) {
                connessione = sSrv.accept();
                InputStream is = connessione.getInputStream();
                OutputStream os = connessione.getOutputStream();

                while(true) {
                    byte[] buffer = new byte[100];
                    is.read(buffer);
                    String m = new String(buffer, 0, buffer.length).trim();
                    System.out.println(m);

                    if(m.startsWith(".")) {
                        System.out.println("Chiudo connessione con client");
                        os.write(String.valueOf(".").getBytes());
                        break;
                    } else {
                        os.write(String.valueOf("ack").getBytes());
                    }

                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
