package UDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getByName(args[0]);
            InetSocketAddress isa = new InetSocketAddress(ia, Integer.valueOf(args[1]));
            DatagramSocket ds = new DatagramSocket();

            while(true) {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String msg = br.readLine().trim();
                DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length());
                dp.setSocketAddress(isa);
                ds.send(dp);

                byte[] buffer = new byte[100];
                DatagramPacket dpRisp = new DatagramPacket(buffer, buffer.length);
                ds.receive(dpRisp);
                String ack = new String(buffer, 0, buffer.length).trim();

                if (ack.startsWith(".")) {
                    System.out.println("CHIUDO");
                    break;
                } else {
                    System.out.println(ack);
                }

            }
            ds.close();
            


            

            ds.close();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
