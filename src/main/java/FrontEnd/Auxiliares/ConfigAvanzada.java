/*
 * @AUTHOR Felix
 */

package FrontEnd.Auxiliares;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Configuration.IPAddress;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.PortScanner;
import BackEnd.Tablas.IPTableModel;
import BackEnd.Usuario;
import FrontEnd.ElementosSwing.PanelRound;
import FrontEnd.Menu.MenuDeBusqueda;
import FrontEnd.Menu.MenuSettings;
import FrontEnd.Themeable;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class ConfigAvanzada extends JFrame implements Themeable {
    private JPanel panelConfigAv;
    private PanelRound panelRound1;
    private JTextField fieldIP;
    private JLabel labelIP;
    private PanelRound panelRound2;
    private JButton guardarButton;
    private JButton volverButton;
    private JButton mostrarDireccionesSQLEnButton;
    private JTable table1;
    private ArrayList<IPAddress> listaIP;

    public ConfigAvanzada() {

        panelRound1.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");
        panelRound2.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,6%);");
        loginWorker().execute();
        listaIP = new ArrayList<>();

        IPTableModel model = new IPTableModel(listaIP);
        table1.setModel(model);

        guardarButton.setName("saveButton");
        volverButton.setName("backButton");


        initComponents();
        setIcons(this);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuSettings().setVisible(true);
                dispose();

            }
        });
        mostrarDireccionesSQLEnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginWorker().execute();


            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        IPTableModel model = (IPTableModel) table1.getModel();
                        IPAddress selectedIP = model.getIPAddress(selectedRow);
                        fieldIP.setText(selectedIP.getIp() + ":3306");



                    }
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ConfigurationIZV configurationIZV = ConfigurationIZV.getInstance();
                System.out.println(fieldIP.getText());
                configurationIZV.setIp(fieldIP.getText());

                ConfigurationIZV.getInstance().saveConfiguration();
                ConfigurationIZV.getInstance().loadConfiguration();


            }
        });
    }




    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Configuración Avanzada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelConfigAv);
        pack();

    }




    public SwingWorker<Void, Void> loginWorker() {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {


            @Override
            protected Void doInBackground() throws Exception {

                String IP_LOCAL = PortScanner.getThisIP();

                try {
                    listaIP.clear();
                    ArrayList<IPAddress> nuevasIPs = PortScanner.getServers(IP_LOCAL);
                    listaIP.addAll(nuevasIPs);
                    ((IPTableModel) table1.getModel()).fireTableDataChanged();

                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            }

            @Override
            protected void done() {

                if (listaIP != null) {
                    IPTableModel model = new IPTableModel(listaIP);
                    table1.setModel(model);
                }

            }

        };

        return worker;

    }









}

