/*
 * @AUTHOR Felix
 */

package FrontEnd;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SettingMenu extends JFrame{
    private JPanel panelSettings;
    private JLabel temaLabel;
    private JComboBox comboBox1;
    private JPanel xdPanel;


    public SettingMenu() {

        temaLabel.setName("temaLabel");
        panelSettings.setBackground( Color.red);
        xdPanel.setBackground( Color.magenta );
        xdPanel.putClientProperty( FlatClientProperties.STYLE, "arc: 8" );

        initComponents();

    }

    public void initComponents() {

        // set configLoginButton the same name as it is declared as, without using a literal string, so it is automated


        setSize(800, 600);
        setTitle("IZV Lab Management Tool 2024");
        setContentPane(panelSettings);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();

    }



}
