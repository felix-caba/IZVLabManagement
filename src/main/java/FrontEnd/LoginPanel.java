/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.MySQL;
import BackEnd.MySQLThread;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class LoginPanel extends JFrame implements Themeable {
    private PanelRound panelWindowLoginIn;
    private JPanel panelWindowLogin;
    private JButton loginButton;
    private JTextField loginUsernameField;
    private JTextField loginPasswordField;
    private JButton backButton;


    public LoginPanel() {



        panelWindowLoginIn.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        loginButton.setName("loginButton");
        backButton.setName("backButton");



        initComponents();

        setIcons(this);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new MainMenu().setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                MySQL.getInstance().connect();
                checkLogin(MySQL.getInstance().getTable("usuarios"));









            }


        });
    }

    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        setTitle("Login Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelWindowLogin);

        pack();

    }


    public void checkLogin(ResultSet rs) {

        String user = loginUsernameField.getText();
        String password = loginPasswordField.getText();


        try {
            if (rs.next()) {
                if (rs.getString(2).equals(user) && rs.getString(3).equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
