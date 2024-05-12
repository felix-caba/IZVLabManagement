/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.DAO.ProductoDAOImpl;
import BackEnd.DAO.TYPE;
import BackEnd.MySQL;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGeneral extends JFrame implements Themeable{
    private PanelRound panelRoundMenuGeneral;
    private JPanel panelMenuGeneral;
    private JButton backButton;
    private JButton lupaButton;
    private JLabel loggedAsLabel;
    private PanelRound panelRoundMenuGeneralPreg;
    private JComboBox comboProducto;
    private JTextField fieldUbicacion;
    private JButton adminButton;

    private boolean isAdmin;
    private String username;


    public MenuGeneral(boolean isAdmin, String username) {

        this.isAdmin = isAdmin;
        this.username = username;

        loggedAsLabel.setText("Has iniciado sesión como: " + username+ " " + (isAdmin ? "(Administrador)" : "(Usuario)"));

        backButton.setName("backButton");
        lupaButton.setName("lupaButton");

        initComponents();
        setIcons(this);

        panelRoundMenuGeneral.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");
        panelRoundMenuGeneralPreg.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");



        // Prog funcional para ahorrarme unas lineas


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu().setVisible(true);
            }
        });
        lupaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

             showSearchResults();

            }
        });

    }

    public void showSearchResults() {
        TYPE type = comboProducto.getSelectedIndex() == 0 ? TYPE.REACTIVOS : comboProducto.getSelectedIndex() == 1 ? TYPE.PROD_AUX : TYPE.MATERIALES;
        MySQL.getInstance().connect();
        new SearchResultMenu(new ProductoDAOImpl().selectPType(type),  isAdmin, type).setVisible(true);
        MySQL.getInstance().disconnect();
    }


    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        setTitle("Login Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelMenuGeneral);

        pack();

    }












}
