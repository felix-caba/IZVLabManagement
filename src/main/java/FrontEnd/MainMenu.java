

/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenu extends JFrame implements Themeable {

    private JPanel panelLogin;
    private JButton configLoginButton;
    private JLabel login_label;
    private PanelRound panelRoundLoginButtons;
    private JButton loginButton;
    private PanelRound panelRoundLabelBienv;
    private PanelRound panelRoundLogo;


    public MainMenu() {


        login_label.setText("Bienvenido, " + System.getProperty("user.name"));
        configLoginButton.setName("configLoginButton");
        loginButton.setName("loginbutton");

        panelRoundLoginButtons.putClientProperty( FlatClientProperties.STYLE,
                "background: fade(@background,50%);");

        panelRoundLabelBienv.putClientProperty( FlatClientProperties.STYLE,
                "background: fade(@background,50%);");

        panelRoundLogo.putClientProperty( FlatClientProperties.STYLE,
                "background: fade(@background,70%);");


        initComponents();

        setIcons(this);


        configLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingMenu().setVisible(true);
                dispose();

            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPanel().setVisible(true);
                dispose();
            }
        });
    }


    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new Dimension(800, 600));
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("IZV Lab Management Tool 2024");
        setContentPane(panelLogin);


        pack();

    }







    private void createUIComponents() {

        try {

            panelLogin = new JPanelBackground("src/main/resources/photos/fondoLogin.png");


        } catch (IOException e) {
            e.printStackTrace();
        }


}}


























