import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MySQLPortScanner {

    private static final int PORT = 3306;
    private static final int TIMEOUT = 500; // Tiempo de espera aumentado
    private static final int THREAD_POOL_SIZE = 500;





    public static void main(String[] args) throws IOException, InterruptedException {

        String subnet = getSubnet();

        System.out.println("Subnet: " + subnet);
        if (subnet == null) {
            System.out.println("Could not determine subnet.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i < 255; i++) {
            String host = subnet + i;
            executor.execute(() -> {
                if (isPortOpen(host, PORT, TIMEOUT)) {
                    System.out.println("Puerto MYSQL Abierto en: " + host);
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

    private static String getSubnet() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
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
