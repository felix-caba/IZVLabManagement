/*
 * @AUTHOR Felix
 */

package BackEnd.NetworkScanner;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MySQLPortScanner {

    private static final int PORT = 3306;
    private static final int TIMEOUT = 500;
    private static final int THREAD_POOL_SIZE = 510;

    private static ArrayList<String> retrievedIPs = new ArrayList<>();


    public static String[] getRetrievedIPs() throws SocketException, InterruptedException {

        scanNetwork();

        return retrievedIPs.toArray(new String[0]);

    }


    private static void scanNetwork() throws SocketException, InterruptedException {

        String subnet = getSubnet();
        if (subnet == null) {
            System.out.println("ERROR: Could not determine subnet.");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i < 255; i++) {

            String host = subnet + i;

            executor.execute(() -> {

                if (isPortOpen(host, PORT, TIMEOUT)) {

                    System.out.println(subnet  + " is open");
                    System.out.println("Encontrado; " + host + ":" + PORT);

                    retrievedIPs.add(host);

                }

            });

        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Escaneo finalizado.");

    }

    private static boolean isPortOpen(String ip, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), timeout);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static String getSubnet() throws SocketException {

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {


            NetworkInterface networkInterface = networkInterfaces.nextElement();

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

            System.out.println("Interfaz de red: " + networkInterface.getDisplayName());

            while (inetAddresses.hasMoreElements()) {


                InetAddress inetAddress = inetAddresses.nextElement();


                if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {

                    System.out.println("Dirección IP: " + inetAddress.getHostAddress());

                    byte[] ip = inetAddress.getAddress();
                    ip[3] = 0;  // Establece el último byte a 0
                    try {
                        return InetAddress.getByAddress(ip).getHostAddress().substring(0, InetAddress.getByAddress(ip).getHostAddress().lastIndexOf('.') + 1);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
