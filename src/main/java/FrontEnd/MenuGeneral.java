/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.TYPE;
import BackEnd.MySQL;
import BackEnd.Producto;
import BackEnd.Usuario;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MenuGeneral extends JFrame implements Themeable {
    private PanelRound panelRoundMenuGeneral;
    private JPanel panelMenuGeneral;
    private JButton backButton;
    private JButton lupaButton;
    private JLabel loggedAsLabel;
    private PanelRound panelRoundMenuGeneralPreg;
    private JComboBox comboProducto;
    private JButton importarButton;
    private JButton adminButton;
    private final LoadingFrame dialog = new LoadingFrame();


    private String message;

    private boolean isAdmin;
    private String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MenuGeneral(boolean isAdmin, String username) {

        this.isAdmin = isAdmin;
        this.username = username;

        MySQL sql = MySQL.getInstance();
        sql.addPropertyChangeListener(dialog);

        loggedAsLabel.setText("Has iniciado sesión como: " + username+ " " + (isAdmin ? "(Administrador)" : "(Usuario)"));

        backButton.setName("backButton");
        lupaButton.setName("lupaButton");
        importarButton.setName("loadButton");

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

                TYPE type = comboProducto.getSelectedIndex() == 0 ? TYPE.REACTIVOS : comboProducto.getSelectedIndex() == 1 ? TYPE.AUXILIARES
                        : comboProducto.getSelectedIndex() == 2 ? TYPE.MATERIALES : TYPE.USUARIOS;

                /*OBSERVA LOS CAMBIOS*/


                System.out.println("Añadido observer");
                loadingWorker(dialog, type).execute();
                System.out.println("Ejecutado worker");

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
                    System.out.println(path);
                    productoDAO.loadTable(path, type);

                    dialog.setVisible(true);

                }

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
        setContentPane(panelMenuGeneral);
        pack();



    }

    public SwingWorker<Void, Void> loadingWorker(LoadingFrame frame, TYPE type) {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            private ArrayList<Producto> productos;
            private ArrayList<Usuario> usuarios;
            ProductoDAOImpl productoDAO = new ProductoDAOImpl();
            UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();


            @Override
            protected Void doInBackground() throws Exception {

                if (type == TYPE.USUARIOS) {
                     usuarios = usuarioDAO.select();
                } else {
                    productos = productoDAO.selectPType(type);
                }
                return null;
            }

            @Override
            protected void done() {

                    if (type == TYPE.USUARIOS && isAdmin) {
                        UserControlPanel ventana = new UserControlPanel(usuarios);
                        ventana.setVisible(true);

                        frame.onSucess("Carga completada");

                    } else if (type == TYPE.USUARIOS && !isAdmin) {

                        frame.onFail("No tienes permisos para acceder a esta sección");

                    } else if (type != TYPE.USUARIOS){
                        SearchResultMenu ventanaRes = new  SearchResultMenu(productos, isAdmin, type);
                        ventanaRes.setVisible(true);

                        frame.onSucess("Carga completada");

                    }



        };



    };


        return worker;


}



}
