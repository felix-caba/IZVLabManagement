/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.*;
import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.ConseguirCampos;
import BackEnd.Extra.TYPE;
import BackEnd.Extra.TableChange;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GlobalResultEditor  extends JFrame implements Themeable{
    private JPanel panel1;
    private JPanel panelSearchMenu;
    private PanelRound panelRoundSearchResults;
    private JScrollPane scrollPane;
    private JXTable tableResults;
    private JButton adminButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField filterField;
    private boolean isAdmin = true;

    public GlobalResultEditor(ArrayList<?> searchResults, boolean isAdmin) {
        initComponents();

        String[] columnNames = getColumnNames(searchResults);

        TablaObjetos<?> model = new TablaObjetos<>(searchResults, columnNames, new Class<?>[columnNames.length], tableResults);


        setIcons(this);

        if (isAdmin) {
            adminButton.setEnabled(true);
        }



        tableResults.setModel(model);


        tableResults.setEditable(true);



        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<? extends TableChange<?>> changes = model.getChanges();

                for (TableChange change : changes) {

                    Object obj = change.getObject();

                    if (obj instanceof Usuario) {
                        UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
                        switch (change.getChangeType()) {
                            case INSERT:
                                usuarioDAO.insert((Usuario) obj);
                                break;
                            case UPDATE:
                                usuarioDAO.update((Usuario) obj);
                                break;
                            case DELETE:
                                usuarioDAO.delete((Usuario) obj);
                                break;
                        }
                    }

                    if (obj instanceof Producto) {
                        ProductoDAOImpl productoDAO = new ProductoDAOImpl();
                        switch (change.getChangeType()) {
                            case INSERT:
                                productoDAO.insert((Producto) obj);
                                break;
                            case UPDATE:
                                productoDAO.update((Producto) obj);
                                break;
                            case DELETE:
                                productoDAO.delete((Producto) obj);
                                break;
                        }

                    }

                    if (obj instanceof Sitio){

                    }

                }
            }
        });

    }

    public static void main(String[] args) {

        ConfigurationIZV.getInstance().loadConfigurationFile();
        ConfigurationIZV.LoadTheme();
        MySQL sql = MySQL.getInstance();
        sql.connect();

        System.out.println(sql.getConnection());

        ArrayList<Producto> productos = new ProductoDAOImpl().selectPType(TYPE.REACTIVOS);

        GlobalResultEditor globalResultEditor = new GlobalResultEditor(productos, true);
        globalResultEditor.setVisible(true);




    }





    public void initComponents() {


        panelRoundSearchResults.putClientProperty(FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");
        panelSearchMenu.putClientProperty(FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");
        panel1.putClientProperty(FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        /*Tamaño de la ventana y posicion*/

        // Set size para ocupar el 70% de la pantalla consigue valores de la pantalla a través de screensize

        int sizeX = (int) (ScreenSize.getScreenWidth() * 0.7);
        int sizeY = (int) (ScreenSize.getScreenHeight() * 0.7);
        int sizeXMax = (ScreenSize.getScreenWidth());
        int sizeYMax = (ScreenSize.getScreenHeight());
        Dimension dim = new Dimension(sizeX, sizeY);
        Dimension dimMax = new Dimension(sizeXMax, sizeYMax);

        setSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dimMax);
        setResizable(true);
        setTitle("Resultados de la busqueda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelSearchMenu);

        saveButton.setName("saveButton");
        adminButton.setName("adminButton");
        addButton.setName("addButton");
        deleteButton.setName("deleteButton");


    }




    public String[] getColumnNames(ArrayList<?> searchResults) {

        return ConseguirCampos.getColumnNames(searchResults.get(0).getClass());

    }






}
