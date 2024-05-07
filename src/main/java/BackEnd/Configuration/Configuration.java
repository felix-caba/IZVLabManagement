package BackEnd.Configuration;

public class Configuration {

    // Singleton Patron

    private static Configuration instance = null;

    private Configuration() {

    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }




}
