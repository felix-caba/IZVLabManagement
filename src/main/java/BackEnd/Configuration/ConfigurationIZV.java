package BackEnd.Configuration;

import BackEnd.Extra.Encriptador;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.io.*;
import java.util.Properties;

public class ConfigurationIZV {

    // Singleton Patron

    private String ip;
    private String appearance;
    private String bdName;
    private static Properties properties;
    private String user;
    private String password;



    private static ConfigurationIZV instance = null;

    private ConfigurationIZV() {



    }

    public static ConfigurationIZV getInstance() {

        if (instance == null) {

                properties = new Properties();
                instance = new ConfigurationIZV();

        }
        return instance;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Carga el archivo de configuracion, no la configuracion.
    public void loadConfigurationFile() {

        try{

            FileReader fr = new FileReader("src/main/java/BackEnd/Configuration/izvlab.config");

            properties.load(fr);

            // Carga la configuracion una vez abierto el archivo.

            loadConfiguration();

            fr.close();


        } catch (IOException e) {

            System.out.println("No se ha podido cargar la configuración. " +
                    "Revisa la existencia del archivo izvlab.config");
        }



    }

    // Carga la configuracion en las variables de la clase.
    public void loadConfiguration() {

            ip = properties.getProperty("ip");
            bdName = properties.getProperty("bdName");
            appearance = properties.getProperty("appearance");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        System.out.println(password);

    }

    // Set values

    public void setIp(String ip) {

        properties.setProperty("ip", ip);

    }

    public void setAppearance(String appearance) {

        properties.setProperty("appearance", appearance);

    }

    public void setBdName(String bdName) {

        properties.setProperty("bdName", bdName);

    }







    // Guarda la configuracion en un archivo, posteriormente la carga.
    public void saveConfiguration() {



        try {
            properties.store(new FileWriter("src/main/java/BackEnd/Configuration/izvlab.config"), "ConfigurationIZV file");
        } catch (IOException e) {
            System.out.println("No se ha podido guardar la configuración. " +
                    "Revisa la existencia del archivo izvlab.config");
        }


        loadConfiguration();




    }




    public String getIp() {
        return ip;
    }

    public String getAppearance() {
        return appearance;
    }

    public String getBdName() {
        return bdName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {

        try {

            return Encriptador.desencriptar(password, Encriptador.claveSecreta);

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;

    }

    public String getPasswordUnencrypted() {
        return password;
    }

    public static void LoadTheme() {
        switch (ConfigurationIZV.getInstance().getAppearance()) {
            case "OSCURO":

                FlatMacDarkLaf.setup();
                break;
            case "CLARO":
                FlatMacLightLaf.setup();
                break;

        }
    }


}
