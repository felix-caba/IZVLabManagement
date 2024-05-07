/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Extra.Checker;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingMenu extends JFrame implements Themeable, Validable{
    private JPanel panelSettings;
    private PanelRound panelSettingsRound;
    private JLabel configLabelBDName;
    private JButton saveConfigButton;
    private JButton loadButton;
    private JTextField urlBDField;
    private JTextField bdNameField;
    private JComboBox comboTheme;
    private JLabel temaLabel;


    public SettingMenu() {

        saveConfigButton.setName("saveConfigButton");
        loadButton.setName("loadButton");


        panelSettingsRound.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");



        initComponents();

        setIcons(this);


        saveConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (areValuesCorrect()){

                    ConfigurationIZV.getInstance().saveConfiguration(urlBDField.getText(), bdNameField.getText(), comboTheme.getSelectedItem().toString());
                    System.out.println(comboTheme.getSelectedItem().toString());

                }

            }

        });


        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConfigurationIZV.getInstance().loadConfiguration();

                System.out.println(ConfigurationIZV.getInstance().getAppearance());

                ConfigurationIZV.LoadTheme();

                FlatLaf.updateUI();

                setIcons(SettingMenu.this);






            }

        });
    }

    public void initComponents() {

        // set configLoginButton the same name as it is declared as, without using a literal string, so it is automated

        setResizable(false);
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 300));
        setTitle("IZV Lab Management Tool 2024");
        setContentPane(panelSettings);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();


    }

    @Override
    public void setIcons(JFrame frame) {
        Themeable.super.setIcons(frame);
    }



    public boolean areValuesCorrect(){

        if (isEmpty() && !Checker.isIP(urlBDField.getText())){

            JOptionPane.showMessageDialog(this, "Error en los valores introducidos", "Error", JOptionPane.ERROR_MESSAGE);

            return false;

        }

        return true;

    }

    @Override
    public boolean isEmpty() {
        return Validable.super.isEmpty();
    }



}
