/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package FrontEnd.Menu;

import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.DAO.Impl.SitioDAOImpl;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.TYPE;
import BackEnd.MySQL;
import BackEnd.Sitios.Localizacion;
import BackEnd.Sitios.Ubicacion;
import FrontEnd.Auxiliares.Busqueda;
import FrontEnd.Auxiliares.LoadingFrame;
import FrontEnd.ElementosSwing.PanelRound;
import FrontEnd.Themeable;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MenuDeBusqueda extends JFrame implements Themeable {
    private PanelRound panelRoundMenuGeneral;
    private JPanel panelMenuGeneral;
    private JButton backButton;
    private JButton lupaButton;
    private JLabel loggedAsLabel;
    private PanelRound panelRoundMenuGeneralPreg;
    private JComboBox comboProducto;
    private JButton importarButton;
    private final LoadingFrame dialog = LoadingFrame.getInstance();

    private final boolean isAdmin;
    private final String username;
    private boolean hasOpened = false;


    public MenuDeBusqueda(boolean isAdmin, String username) {

        MySQL sql = MySQL.getInstance();
        sql.addPropertyChangeListener(dialog);

        this.isAdmin = isAdmin;
        this.username = username;

        initComponents();
        setIcons(this);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuDeInicio().setVisible(true);
            }
        });
        lupaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                hasOpened = false;
                TYPE type = null;
                int selectedIndex = comboProducto.getSelectedIndex();

                if (selectedIndex == 0) {
                    type = TYPE.REACTIVOS;
                } else if (selectedIndex == 1) {
                    type = TYPE.AUXILIARES;
                } else if (selectedIndex == 2) {
                    type = TYPE.MATERIALES;
                } else if (selectedIndex == 3) {
                    type = TYPE.USUARIOS;
                } else if (selectedIndex == 4) {
                    type = TYPE.LOCALIZACION;
                } else if (selectedIndex == 5) {
                    type = TYPE.UBICACION;
                }

                loadingWorker(dialog, type).execute();
                dialog.setVisible(true);

            }
        });
        importarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProductoDAOImpl productoDAO = new ProductoDAOImpl();

                TYPE type = comboProducto.getSelectedIndex() == 0 ? TYPE.REACTIVOS : comboProducto.getSelectedIndex() == 1 ?
                        TYPE.AUXILIARES : comboProducto.getSelectedIndex() == 2 ? TYPE.MATERIALES : TYPE.USUARIOS;

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Solo Archivos CSV", "csv");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    productoDAO.loadTable(path, type);

                    dialog.onSucess("Carga completada");

                }

            }
        });

    }

    public void initComponents() {
        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Login Window");
        properties();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelMenuGeneral);
        pack();
    }

    public SwingWorker<Void, Void> loadingWorker(LoadingFrame frame, TYPE type) {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            // ? Por que no sé que tipo de dato voy a usar.

            private ArrayList<?> result;
            private HashMap<Localizacion, ArrayList<Ubicacion> > hashMapSitio = new HashMap<>();

            final ProductoDAOImpl productoDAO = new ProductoDAOImpl();
            final SitioDAOImpl sitioDAO = new SitioDAOImpl();
            final UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();

            @Override
            protected Void doInBackground() throws Exception {

                if (type == TYPE.USUARIOS) {

                     result = usuarioDAO.select();

                } else if (type == TYPE.LOCALIZACION || type == TYPE.UBICACION) {

                      result = sitioDAO.getSitios(type);

                } else {

                    result = productoDAO.selectPType(type);

                    hashMapSitio = sitioDAO.getSitioHash();



                }
                return null;
            }

            @Override
            protected void done() {


                if (result != null) {


                    if (isAdmin) {
                        if ((type) == TYPE.USUARIOS) {
                            hasOpened = true;
                            System.out.println(hashMapSitio);
                            new Busqueda(result, isAdmin, type, null).setVisible(true);
                        }
                        if (type == TYPE.LOCALIZACION || type == TYPE.UBICACION) {
                            hasOpened = true;
                            new Busqueda(result, isAdmin, type, null).setVisible(true);
                        }
                        if (type == TYPE.REACTIVOS || type == TYPE.AUXILIARES || type == TYPE.MATERIALES) {
                            hasOpened = true;
                            new Busqueda(result, isAdmin, type, hashMapSitio).setVisible(true);
                        }
                    } else {
                        if (type == TYPE.USUARIOS || type == TYPE.LOCALIZACION || type == TYPE.UBICACION) {
                            frame.onFail("No tienes permisos para acceder a esta sección");

                        } else {
                            hasOpened = true;
                            new Busqueda(result, isAdmin, type, null).setVisible(true);
                        }
                    }

                    if (hasOpened) {
                        frame.onSucess("Carga completada");
                    }

                }

        };



    };


        return worker;


}

        public void properties() {
            loggedAsLabel.setText("Has iniciado sesión como: " + username+ " " + (isAdmin ? "(Administrador)" : "(Usuario)"));
            panelRoundMenuGeneral.putClientProperty( FlatClientProperties.STYLE,
                    "background: lighten(@background,3%);");
            panelRoundMenuGeneralPreg.putClientProperty( FlatClientProperties.STYLE,
                    "background: lighten(@background,3%);");
            backButton.setName("backButton");
            lupaButton.setName("lupaButton");
            importarButton.setName("loadButton");
        }

}
