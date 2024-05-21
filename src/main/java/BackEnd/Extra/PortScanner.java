/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;/*
 * @AUTHOR Felix
 */

import BackEnd.Configuration.IPAddress;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PortScanner {


    private static final int PORT = 3306;
    private static final int TIMEOUT = 500;
    private static final int THREAD_POOL_SIZE = 500;
    private static final String PHPMYADMIN_PATH = "/phpmyadmin";


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


    public static ArrayList<IPAddress> getServers(String ip) throws InterruptedException {

        ArrayList<IPAddress> servers = new ArrayList<>();

        ip = getThisIP().substring(0, getThisIP().lastIndexOf('.') + 1);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i < 255; i++) {
            String host = ip + i;
            executor.execute(() -> {
                if (isPortOpen(host, PORT, TIMEOUT)) {
                    servers.add(new IPAddress(host, isPhpMyAdminAvailable(host)));
                }
            });
        }


        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        return servers;


    }

    private static boolean isPortOpen(String ip, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), timeout);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



    public static boolean isPhpMyAdminAvailable(String ip) {
        try {
            URL url = new URL("http://" + ip + PHPMYADMIN_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            return false;
        }
    }



}
