import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
    private static ArrayList<Thread> arr = new ArrayList<Thread> ();

    public static void main (String[] args) {
        ServerSocket ss = null;
        int id = 0;
        try {
            ss = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server is ready");
        while (true) {
            try {
                Socket soc = ss.accept();
                System.out.println("new connection arrived");
                Thread t = new BJThread(soc, id++);
                t.start();
                arr.add(t);
                Iterator<Thread> iter = arr.iterator();
                while (iter.hasNext()) {
                    t = iter.next();
                    if (!t.isAlive()) {
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
