import BackEnd.Configuration.ConfigurationIZV;

public class TestingConfigFile {

    public static void main(String[] args) {


        // Works

        ConfigurationIZV myConf = ConfigurationIZV.getInstance();
        myConf.loadConfigurationFile();

        System.out.println("User: " + myConf.getUser());
        System.out.println("Password: " + myConf.getPassword());
        System.out.println("URL: " + myConf.getUrl());
        System.out.println("BD Name: " + myConf.getBdName());
        System.out.println("Appearance: " + myConf.getAppearance());
        System.out.println("Driver: " + myConf.getDriver());

        myConf.saveConfiguration("user1", "password1", "url1", "bdName1", "appearance1", "driver1");

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("User: " + myConf.getUser());
        System.out.println("Password: " + myConf.getPassword());
        System.out.println("URL: " + myConf.getUrl());
        System.out.println("BD Name: " + myConf.getBdName());
        System.out.println("Appearance: " + myConf.getAppearance());
        System.out.println("Driver: " + myConf.getDriver());

    }

}



