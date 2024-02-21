package TCP_iterativo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IterClient {
    public static void main(String[] args) {
        try {
            //inizializzazione
            InetAddress ia = InetAddress.getLocalHost();
            InetSocketAddress isa = new InetSocketAddress(ia, Integer.parseInt(args[0]));
            Socket client = new Socket();
            client.connect(isa);
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();

            while(true) {
                String messaggio = br.readLine().trim();
                os.write(messaggio.getBytes());

                byte[] buffer = new byte[100];
                is.read(buffer);
                String ack = new String(buffer, 0, buffer.length).trim();
                
                if(ack.startsWith(".")) {
                    System.out.println("ADDIO");
                    break;
                } else {
                    System.out.println(ack);
                }


            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
