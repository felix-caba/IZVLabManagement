/*
 * @AUTHOR Felix
 */

package FrontEnd;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

public class LoginPanel extends JFrame {
    private PanelRound panelWindowLoginIn;
    private JPanel panelWindowLogin;
    private JButton loginButton;
    private JTextField loginUsernameField;
    private JTextField loginPasswordField;


    public LoginPanel() {


        panelWindowLoginIn.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        loginButton.setName("loginButton");

        initComponents();



    }

    public void initComponents() {



        setResizable(false);

        setMinimumSize(new java.awt.Dimension(400, 300));
        setTitle("Login Window");

        setContentPane(panelWindowLogin);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

        pack();



    }



}
