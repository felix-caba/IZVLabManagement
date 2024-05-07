/*
 * @AUTHOR Felix
 */

package BackEnd.Configuration;

import java.awt.*;

public class ScreenSize {

    private int screenWidth;
    private int screenHeight;


    public static int getScreenWidth() {


        return java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;

    }

    public static int getScreenHeight() {

        return  Toolkit.getDefaultToolkit().getScreenSize().height;

    }

    public static int getMiddleOfScreenX() {

        return getScreenWidth() / 2;

    }

    public static int getMiddleOfScreenY() {

        return getScreenHeight() / 2;

    }


}

