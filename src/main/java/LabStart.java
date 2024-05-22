
/*
 * @AUTHOR Felix
 */

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Extra.PortScanner;
import BackEnd.NetworkScanner.MySQLPortScanner;
import FrontEnd.Menu.MenuDeBusqueda;
import FrontEnd.Menu.MenuDeInicio;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;


public class LabStart {

    public static void main(String[] args) throws IOException, InterruptedException {



        try {

            ConfigurationIZV.getInstance().loadConfigurationFile();
            setFont();
            setPropertiesTheme();
            ConfigurationIZV.LoadTheme();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new MenuDeInicio().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public static void setFont() throws IOException, FontFormatException {

        // Gets the font from resources and installs it

        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/RobotoMono-Regular.ttf"));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        // applies it to swing globally
        font = font.deriveFont(Font.PLAIN, 12);
        Font fontComponentes = font.deriveFont(Font.PLAIN, 16);

        FontUIResource fontUIResource = new FontUIResource(font);

        UIManager.put("defaultFont", fontUIResource);
        UIManager.put("Button.font", fontComponentes);
        UIManager.put("Label.font", fontComponentes);
        UIManager.put("TextField.font", fontComponentes);
        UIManager.put("TextArea.font", fontComponentes);
        UIManager.put("ComboBox.font", fontComponentes);
        UIManager.put("PasswordField.font", fontComponentes);
        UIManager.put("OptionPane.messageFont", fontComponentes);

    }

    public static void setPropertiesTheme() {
        UIManager.put( "Button.arc", 75 );
        UIManager.put( "TextComponent.arc", 75 );
        UIManager.put( "ComboBox.selectionArc", 75 );
        UIManager.put( "Component.arc", 75 );
        UIManager.put( "ProgressBar.arc", 75 );
        UIManager.put("List.arc", 75);
        UIManager.put("ScrollPane.List.arc", 75);

    }

}




