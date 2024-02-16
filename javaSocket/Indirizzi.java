import java.net.InetAddress;
import java.net.UnknownHostException;

public class Indirizzi {

    public static void main(String[] args) {
        String nome = "www.unimi.it";

        try {
            InetAddress ia = InetAddress.getByName(nome);
            byte[] ndp = ia.getAddress();
            System.out.println("Indirizzo: " + (ndp[0] & 0xff) + "." + (ndp[1] & 0xff) + "." + (ndp[2] & 0xff) + "."
                    + (ndp[3] & 0xff));
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }

        String nome2 = "www.google.com";

        try {
            InetAddress[] iaa = InetAddress.getAllByName(nome2);
            for (int i = 0; i < iaa.length; i++) {
                System.out.println("Indirizzo " + iaa[i].getHostName() + " --> " + iaa[i].getHostAddress());
            }
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }

    }
}