/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package FrontEnd.Menu;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Extra.Checker;
import FrontEnd.Auxiliares.ConfigAvanzada;
import FrontEnd.Auxiliares.LoadingFrame;
import FrontEnd.ElementosSwing.PanelRound;
import FrontEnd.Themeable;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSettings extends JFrame implements Themeable{
    private JPanel panelSettings;
    private PanelRound panelSettingsRound;
    private JLabel configLabelBDName;
    private JButton saveButton;
    private JButton backButton;
    private JTextField ipBDField;
    private JTextField bdNameField;
    private JComboBox comboTheme;
    private JTextField textField1;
    private JPasswordField textField2;
    private JButton avanzadoButton;
    private JLabel temaLabel;
    private final LoadingFrame dialog = LoadingFrame.getInstance();


    public MenuSettings() {

        saveButton.setName("saveButton");
        backButton.setName("backButton");


        panelSettingsRound.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");


        initComponents();
        setIcons(this);


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConfigurationIZV configurationIZV = ConfigurationIZV.getInstance();


                    // if a textbox is empty, do not change the value

                    if (!ipBDField.getText().isEmpty() && areValuesCorrect()) {
                        configurationIZV.setIp(ipBDField.getText());

                    }
                    if (!bdNameField.getText().isEmpty()) {
                        configurationIZV.setBdName(bdNameField.getText());
                    }

                    if (!textField1.getText().isEmpty()) {
                        configurationIZV.setUser(textField1.getText());
                    }


                    configurationIZV.setAppearance(comboTheme.getSelectedItem().toString());

                    ConfigurationIZV.getInstance().saveConfiguration();

                    ConfigurationIZV.getInstance().loadConfiguration();

                    ConfigurationIZV.LoadTheme();

                    FlatLaf.updateUI();

                    setIcons(dialog);

                    setIcons(MenuSettings.this);


            }

        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                new MenuDeInicio().setVisible(true);


            }

        });
        avanzadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                new ConfigAvanzada().setVisible(true);



            }
        });
    }

    public void initComponents() {


        /*Tamaño de la ventana y posicion*/
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        setTitle("IZV Lab Management Tool 2024");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelSettings);


        fillFields();

        pack();

    }

    public void fillFields(){

        ConfigurationIZV configurationIZV = ConfigurationIZV.getInstance();

        ipBDField.setText(configurationIZV.getIp());
        bdNameField.setText(configurationIZV.getBdName());
        comboTheme.setSelectedItem(configurationIZV.getAppearance());

        textField1.setText(configurationIZV.getUser());
        textField2.setText(configurationIZV.getPasswordUnencrypted());


    }





    public boolean areValuesCorrect(){

        if (!Checker.isIP(ipBDField.getText())){

            JOptionPane.showMessageDialog(this, "Error en los valores introducidos", "Error", JOptionPane.ERROR_MESSAGE);

            return false;

        }



        return true;

    }








}
