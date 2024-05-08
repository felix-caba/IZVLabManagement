package BackEnd;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Extra.Encriptador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL{

    // Singleton Patron

    private static MySQL instance = null;
    private static String JDBC = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://";
    private static Connection connection;
    private static String user;
    private static String password;
    private static String ip;
    private static String bdName;







    private MySQL() {



    }

    public static MySQL getInstance() {

        user = ConfigurationIZV.getInstance().getUser();
        password = ConfigurationIZV.getInstance().getPassword();
        ip = ConfigurationIZV.getInstance().getIp();
        bdName = ConfigurationIZV.getInstance().getBdName();




        if (instance == null) {
            instance = new MySQL();
        }

        return instance;

    }


    public boolean connect() {


        ConfigurationIZV configuration = ConfigurationIZV.getInstance();


        String url;
        String ip;


        try {

            ip = configuration.getIp();
            url = DB_URL + ip + "/" + configuration.getBdName();
            connection = DriverManager.getConnection(url, user, password);


            return true;

        } catch (Exception e) {


            System.out.println(e);


        }

        return false;



    }

    public void disconnect() {


        try {

            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {

            System.out.println(e);

        }


    }

    public ResultSet getTable(String tableName) {

        ResultSet rs = null;
        String query = "SELECT * FROM " + tableName;

        try {

            PreparedStatement ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);

        }

        return rs;
        

    }









    public Connection getConnection() {
        return connection;
    }




}
