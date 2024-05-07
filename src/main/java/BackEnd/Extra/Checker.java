/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

public class Checker {


    public static boolean isIP(String ip){

        String ipRegex = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}:\\d{1,5}\\b";

        return ip.matches(ipRegex);

    }




}
