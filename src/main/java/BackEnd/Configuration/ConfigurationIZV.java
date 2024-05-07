package BackEnd.Configuration;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.io.*;
import java.util.Properties;

public class ConfigurationIZV {

    // Singleton Patron

    private String url;
    private String appearance;
    private String driver;
    private String bdName;
    private static Properties properties;


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

            url = properties.getProperty("url");
            bdName = properties.getProperty("bdName");
            appearance = properties.getProperty("appearance");
            driver = properties.getProperty("driver");

    }



    // Guarda la configuracion en un archivo, posteriormente la carga.
    public void saveConfiguration(String newUrl, String newBdName, String newAppearance) {



            properties.setProperty("url", newUrl);
            properties.setProperty("bdName", newBdName);
            properties.setProperty("appearance", newAppearance);



            try {
                properties.store(new FileWriter("src/main/java/BackEnd/Configuration/izvlab.config"), "ConfigurationIZV file");
            } catch (IOException e) {
                System.out.println("No se ha podido guardar la configuración. " +
                        "Revisa la existencia del archivo izvlab.config");
            }


        loadConfiguration();




    }




    public String getUrl() {
        return url;
    }

    public String getAppearance() {
        return appearance;
    }

    public String getDriver() {
        return driver;
    }

    public String getBdName() {
        return bdName;
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
