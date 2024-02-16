import java.net.ServerSocket;
import java.net.Socket;

public class es1SrvIter {
    public static void main(String[] args) {
        ServerSocket sSrv;
        Socket toClient;
        try {
            sSrv = new ServerSocket(0);
            System.out.println("indirizzo: " + sSrv.getInetAddress() + "; porta: " + sSrv.getLocalPort());
            toClient = sSrv.accept();
            System.out.println("Ind Client: " + toClient.getInetAddress() + "; porta: " + toClient.getLocalPort());
            Thread.sleep(240 * 1000);
            sSrv.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
