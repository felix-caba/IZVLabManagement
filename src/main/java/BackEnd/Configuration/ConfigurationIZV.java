package BackEnd.Configuration;

import java.io.*;
import java.util.Properties;

public class Configuration {

    // Singleton Patron

    private String user;
    private String password;
    private String url;
    private String appearance;
    private String driver;
    private String bdName;
    private Properties properties;


    private static Configuration instance = null;

    private Configuration() {

        properties = new Properties();

    }

    public static Configuration getInstance() {
        if (instance == null) {

                instance = new Configuration();

        }
        return instance;
    }

    public void loadConfigurationFile() {

        try{

            FileReader fr = new FileReader("src/main/java/BackEnd/Configuration/izvlab.config");

            properties.load(fr);

            loadConfiguration();

            fr.close();


        } catch (IOException e) {

            System.out.println("No se ha podido cargar la configuración. " +
                    "Revisa la existencia del archivo izvlab.config");
        }



    }


    public void loadConfiguration() {


            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            bdName = properties.getProperty("bdName");
            appearance = properties.getProperty("appearance");
            driver = properties.getProperty("driver");

    }



    public void saveConfiguration(String newUser, String newPassword, String newUrl,
                                  String newBdName, String newAppearance, String newDriver) {



            properties.setProperty("user", newUser);
            properties.setProperty("password", newPassword);
            properties.setProperty("url", newUrl);
            properties.setProperty("bdName", newBdName);
            properties.setProperty("appearance", newAppearance);
            properties.setProperty("driver", newDriver);



            try {
                properties.store(new FileWriter("src/main/java/BackEnd/Configuration/izvlab.config"), "Configuration file");
            } catch (IOException e) {
                System.out.println("No se ha podido guardar la configuración. " +
                        "Revisa la existencia del archivo izvlab.config");
            }


        loadConfiguration();




    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
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

    public Properties getProperties() {
        return properties;
    }
}
