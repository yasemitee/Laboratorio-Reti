package TCP_multithread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadClass extends Thread{
    private Socket socket;

    public ThreadClass(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                byte[] buffer = new byte[100];
                is.read(buffer);
                String m = new String(buffer, 0, buffer.length).trim();
                System.out.println(m);

                if(m.startsWith(".")) {
                    System.out.println("CHIUDO");
                    os.write(String.valueOf(".").getBytes());
                    break;
                } else {
                    os.write(String.valueOf("ack").getBytes());
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
