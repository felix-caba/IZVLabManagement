package BackEnd;

import javax.swing.*;

public class MySQLThread extends Thread {



    public MySQLThread() {



    }

    public void run() {





        MySQL.getInstance().connect();

        System.out.println("Conexion abierta...");



    }




    public void closeConnection(){

        MySQL.getInstance().disconnect();

        System.out.println("Conexion cerrada...");

    }

}
