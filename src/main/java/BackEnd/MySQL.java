/*
 * @AUTHOR Felix
 */

package BackEnd;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Extra.TYPE;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.Timer;

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
    private String sqlBROADCAST;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private static final int TIMEOUT_SECONDS = 10; // Tiempo de espera en segundos
    private Timer timer; // Temporizador para el tiempo de espera


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }


    public static String getJDBC() {
        return JDBC;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getIp() {
        return ip;
    }

    public static String getBdName() {
        return bdName;
    }

    public String getBroadCast() {
        return sqlBROADCAST;
    }

    private MySQL() {


    }


    public void setSqlBROADCAST(String value) {
        this.sqlBROADCAST = value;
        pcs.firePropertyChange("sqlBROADCAST", null, value);
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


    public void connect() {

        ConfigurationIZV configuration = ConfigurationIZV.getInstance();
        String url;
        String ip;

        try {




            ip = configuration.getIp();
            url = DB_URL + ip + "/" + configuration.getBdName();

            DriverManager.setLoginTimeout(5);
            connection = DriverManager.getConnection(url, user, password);




        } catch (SQLException e) {


            System.out.println(e);
            setSqlBROADCAST(e.getMessage());


        }


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






    public Connection getConnection() {
        return connection;
    }




}
