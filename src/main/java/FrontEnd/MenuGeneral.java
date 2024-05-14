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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import static java.lang.Thread.sleep;

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


                TYPE type = comboProducto.getSelectedIndex() == 0 ? TYPE.REACTIVOS : comboProducto.getSelectedIndex() == 1 ? TYPE.PROD_AUX : TYPE.MATERIALES;

                LoadingFrame dialog = new LoadingFrame();

                /*OBSERVA LOS CAMBIOS*/

                MySQL sql = MySQL.getInstance();

                sql.addPropertyChangeListener(dialog);
                System.out.println("Añadido observer");
                loadingWorker(dialog, type).execute();
                System.out.println("Ejecutado worker");

                dialog.setVisible(true);


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
            ProductoDAOImpl productoDAO = new ProductoDAOImpl();


            @Override
            protected Void doInBackground() throws Exception {



                productos = productoDAO.selectPType(type);


                return null;


            }

            @Override
            protected void done() {

                if (productos == null) {

                    JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Error", JOptionPane.ERROR_MESSAGE);

                } else {

                    SearchResultMenu ventanaRes = new  SearchResultMenu(productos, isAdmin, type);
                    ventanaRes.setVisible(true);
                    frame.onSucess();


                }

        };



    };


        return worker;


}



}
