import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class esempio1 {
    public static void main(String[] args) {
        Socket sClient;
        InetAddress ia; // IP adress client
        InetSocketAddress isa; // socket adress client

        sClient = new Socket();
        try {
            ia = InetAddress.getLocalHost();
            isa = new InetSocketAddress(ia, 0); // S.O sceglie #port libero
            sClient.bind(isa);
            System.out.println("Porta allocata: " + sClient.getLocalPort());
            Thread.sleep(120 * 1000);
            sClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}