/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import BackEnd.Extra.WordWrapRenderer;
import BackEnd.Producto;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultMenu extends JFrame {
    private JPanel panelSearchMenu;
    private PanelRound panelRoundSearchResults;
    private JScrollPane scrollPane;
    private JXTable tableResults;
    private ArrayList<Producto> searchResults;
    private ResultSet rs;


    public void initComponents() {

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


        // El table row sorter, cuando recibe Object, no hace la conversion. Hace toString. Si recibe Object Int, le hace toString. Por ello, debemos darle
        // directamente el tipo de dato que queremos que muestre. override de columnclassget


        TableModel model = new DefaultTableModel(getData(searchResults), getColumnNames(searchResults)) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {

                if (searchResults.isEmpty()) {
                    return Object.class;
                }

                if (getValueAt(0, columnIndex) == null) {
                    return Object.class;
                }

                return getValueAt(0, columnIndex).getClass();

            }
        };


        tableResults.setModel(model);
        tableResults.packAll();
        tableResults.setPreferredScrollableViewportSize(dim);


        pack();


        setLocationRelativeTo(null);
    }


    @Deprecated
    public SearchResultMenu(ResultSet rs) {

        this.rs = rs;

        panelRoundSearchResults.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        initComponents();


    }

    public SearchResultMenu(ArrayList<Producto> searchResults) {

        this.searchResults = searchResults;

        panelRoundSearchResults.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        initComponents();


    }



    // Deprecados porque se ha cambiado la forma de obtener los datos de la tabla.
    // Es mejor separar los metodos de obtencion de columnas.
    // Así puedo administrar mejor el tipo de datos de cada columna y hacer que se muestren de forma correcta en la tabla.

    @Deprecated
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        ArrayList<String> columnNames = new ArrayList<>();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {

            columnNames.add(metaData.getColumnName(i));

        }

        ArrayList<Object[]> data = new ArrayList<>();

        while (rs.next()) {

            Object[] row = new Object[columnCount];

            for (int indiceColumna = 1; indiceColumna <= columnCount; indiceColumna++) {


                row[indiceColumna - 1] = rs.getObject(indiceColumna);

            }


            data.add(row);
        }

        Object[][] dataArray = new Object[data.size()][metaData.getColumnCount()];

        data.toArray(dataArray);

        return new DefaultTableModel(dataArray, columnNames.toArray());

    }


    @Deprecated
    public DefaultTableModel buildTableModel(ArrayList<Producto> searchResults) {

        if (searchResults.isEmpty()) {
            return new DefaultTableModel();
        }

        String[] columnNames = searchResults.get(0).getAllAttributesNamesString();
        Object[][] dataArray = new Object[searchResults.size()][columnNames.length];

        for (int i = 0; i < searchResults.size(); i++) {

            Producto producto = searchResults.get(i);
            Object[] rowData = new Object[columnNames.length];

            for (int j = 0; j < columnNames.length; j++) {
                rowData[j] = producto.getValueForAttribute(columnNames[j]);
            }
            dataArray[i] = rowData;
        }
        return new DefaultTableModel(dataArray, columnNames);
    }


    // Metodos Actuales

    public String[] getColumnNames(ArrayList<Producto> searchResults) {

        return searchResults.get(0).getAllAttributesNamesString();

    }

    public Object[][] getData(ArrayList<Producto> searchResults) {

        Object[][] data = new Object[searchResults.size()][searchResults.get(0).getAllAttributesNamesString().length];

        for (int i = 0; i < searchResults.size(); i++) {

            Producto producto = searchResults.get(i);
            Object[] rowData = new Object[producto.getAllAttributesNamesString().length];

            for (int j = 0; j < producto.getAllAttributesNamesString().length; j++) {
                rowData[j] = producto.getValueForAttribute(producto.getAllAttributesNamesString()[j]);
            }
            data[i] = rowData;
        }

        return data;
    }






}
