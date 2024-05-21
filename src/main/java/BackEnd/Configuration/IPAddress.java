/*
 * @AUTHOR Felix
 */

package BackEnd.Configuration;

public class IPAddress {
    private String ip;
    private boolean phpmyadmin;

    public IPAddress(String ip, boolean phpmyadmin) {
        this.ip = ip;
        this.phpmyadmin = phpmyadmin;
    }

    public String getIp() {
        return ip;
    }

    public boolean isPhpmyadmin() {
        return phpmyadmin;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPhpmyadmin(boolean phpmyadmin) {
        this.phpmyadmin = phpmyadmin;
    }




}