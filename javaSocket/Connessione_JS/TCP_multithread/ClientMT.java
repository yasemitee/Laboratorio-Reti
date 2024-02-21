package TCP_multithread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.sound.sampled.SourceDataLine;

public class ClientMT {
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            InetSocketAddress isa = new InetSocketAddress(ia, Integer.parseInt(args[0]));
            Socket client = new Socket();
            client.connect(isa);
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();

            while(true) {
                
                String msg = br.readLine().trim();
                os.write(msg.getBytes());

                byte[] buffer = new byte[100];
                is.read(buffer);
                String ack = new String(buffer, 0, buffer.length);

                if(ack.startsWith(".")) {
                    System.out.println("SMETTO DI PARLARE CON TE");
                    break;
                } else {
                    System.out.println(ack);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
