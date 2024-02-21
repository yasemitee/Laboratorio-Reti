package TCP_multithread;

import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.transform.Source;

public class ServerMT {
    public static void main(String[] args) {
        ServerSocket srv;
        Socket client;

        try {
            srv = new ServerSocket(0);
            System.out.println("porta: " + srv.getLocalPort());
            while(true) {
                client = srv.accept();
                System.out.println("indirizzo: " + client.getInetAddress());
                Thread t = new ThreadClass(client);
                t.start();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
