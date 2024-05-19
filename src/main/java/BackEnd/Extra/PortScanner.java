/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;/*
 * @AUTHOR Felix
 */

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PortScanner {


    private static final int PORT = 3306;
    private static final int TIMEOUT = 500;
    private static final int THREAD_POOL_SIZE = 500;


    public static String getThisIP(){

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com", 80));
            String ipLocal = socket.getLocalAddress().toString().substring(1);
            socket.close();
            return ipLocal;

        } catch (Exception e) {
            return null;
        }

    }


    public static void getServers(String ip) throws InterruptedException {

        ip = getThisIP().substring(0, getThisIP().lastIndexOf('.') + 1);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i < 255; i++) {
            String host = ip + i;
            executor.execute(() -> {
                if (isPortOpen(host, PORT, TIMEOUT)) {
                    System.out.println("Puerto MYSQL Abierto en:::: " + host);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

    }

    private static boolean isPortOpen(String ip, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), timeout);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



}
