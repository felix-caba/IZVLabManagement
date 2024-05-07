

/*
 * @AUTHOR Felix
 */

package FrontEnd;

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
            }
        });

    }


    public void initComponents() {

        // set configLoginButton the same name as it is declared as, without using a literal string, so it is automated


        setMinimumSize(new Dimension(800, 600));
        setSize(800, 600);
        setTitle("IZV Lab Management Tool 2024");
        setVisible(true);
        setContentPane(panelLogin);
        setResizable(false);
        pack();


    }




    @Override
    public JPanel[] getPanels(JFrame frame) {
        return Themeable.super.getPanels(frame);
    }


    @Override
    public void setIcons(JFrame frame) {
        Themeable.super.setIcons(frame);
    }


    private void createUIComponents() {

        try {

            panelLogin = new JPanelBackground("src/main/resources/photos/fondoLogin.png");


        } catch (IOException e) {
            e.printStackTrace();
        }


}}


























