package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(0);
            System.out.println("indrizzo: " + ds.getLocalAddress() + "porta: " + ds.getLocalPort());
            
            while(true) {
                byte[] buffer = new byte[100];
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ds.receive(dp);
                InetSocketAddress isa = new InetSocketAddress(dp.getAddress(), dp.getPort());
                String msg = new String(buffer, 0, buffer.length).trim();
                System.out.println(msg);

                if (msg.startsWith(".")) {
                    System.out.println("VUOLE CHIUDERE");
                    DatagramPacket dpRisp = new DatagramPacket(".".getBytes(), ".".length());
                    dpRisp.setSocketAddress(isa);
                    ds.send(dpRisp);
                    break;
                } else {
                    DatagramPacket dpRisp = new DatagramPacket("ack".getBytes(), "ack".length());
                    dpRisp.setSocketAddress(isa);
                    ds.send(dpRisp);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
