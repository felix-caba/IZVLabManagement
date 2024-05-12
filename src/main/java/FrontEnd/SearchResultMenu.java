/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.TYPE;
import BackEnd.Extra.TableChange;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultMenu extends JFrame implements Themeable {
    private JPanel panelSearchMenu;
    private PanelRound panelRoundSearchResults;
    private JScrollPane scrollPane;
    private JXTable tableResults;
    private JButton adminButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton deleteButton;
    public boolean isButtonPressed = false;
    public boolean isAdmin;
    private TYPE typeProduct;

    private ArrayList<Producto> searchResults;
    private ArrayList<TableChange> tableChanges = new ArrayList<TableChange>();




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

        saveButton.setName("saveButton");
        adminButton.setName("adminButton");
        addButton.setName("addButton");
        deleteButton.setName("deleteButton");

        setIcons(this);

        if (isAdmin) {
            adminButton.setEnabled(true);
        }

        // El table row sorter, cuando recibe Object, no hace la conversion. Hace toString. Si recibe Object Int, le hace toString. Por ello, debemos darle
        // directamente el tipo de dato que queremos que muestre. override de columnclassget

        TableModel model = new DefaultTableModel(getData(searchResults), getColumnNames(searchResults)) {
            public Class<?> getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }

        };

        /*
         *
         *     https://docs.oracle.com/javase/tutorial/uiswing/events/tablemodellistener.html
         *
         */

        model.addTableModelListener(new TableModelListener() {

            // Listener de la tabla.

            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {

                int row = tableModelEvent.getFirstRow();
               int getType = tableModelEvent.getType();



                if (getType == TableModelEvent.UPDATE) {



                }



            }

        });

        tableResults.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                    if (!listSelectionEvent.getValueIsAdjusting()) {
                        // verifica

                        if (tableResults.getSelectedRow() != -1 && isAdmin && isButtonPressed) {

                            deleteButton.setEnabled(true);
                        } else {
                            deleteButton.setEnabled(false);
                        }
                    }
                }


        });

        tableResults.setModel(model);
        tableResults.packAll();
        tableResults.setPreferredScrollableViewportSize(dim);



        TableColumnModel columnModel = tableResults.getColumnModel();
        for (int indColumna = 0; indColumna < columnModel.getColumnCount(); indColumna++) {
            Class<?> columnClass = model.getColumnClass(indColumna);
            if (LocalDate.class.isAssignableFrom(columnClass)) {
                // pone el editor de celda fecha
                columnModel.getColumn(indColumna).setCellEditor(new LocalDateCellEditor());
            }
        }

        pack();
        setLocationRelativeTo(null);

    }

    public SearchResultMenu(ArrayList<Producto> searchResults, boolean isAdmin, TYPE typeProduct) {


        this.searchResults = searchResults;
        this.isAdmin = isAdmin;
        this.typeProduct = typeProduct;


        adminButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        addButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        deleteButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        panelRoundSearchResults.putClientProperty( FlatClientProperties.STYLE, "background: lighten(@background,3%);");

        initComponents();

        DefaultTableModel model = (DefaultTableModel) tableResults.getModel();

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if (isButtonPressed) {

                    isButtonPressed = false;
                    tableResults.setEditable(false);
                    tableResults.setEditable(false);

                    adminButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
                    addButton.setEnabled(false);

                    saveButton.setEnabled(false);

                } else {


                    isButtonPressed = true;
                    tableResults.setEditable(true);
                    tableResults.setEditable(true);

                    adminButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,40%);");
                    addButton.setEnabled(true);



                    saveButton.setEnabled(true);
                }






            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {




                model.addRow(new Object[model.getColumnCount()]);

                int nuevaRow = model.getRowCount() - 1;
                int previaRow = nuevaRow - 1;
                int valorIDprevio = (int) model.getValueAt(previaRow, 0);

                model.setValueAt(valorIDprevio + 1, nuevaRow, 0);

                tableResults.scrollRectToVisible(tableResults.getCellRect(nuevaRow, 0, true));
                tableResults.setRowSelectionInterval(nuevaRow, nuevaRow);




            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                int selectedRow = tableResults.getSelectedRow();

                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                }



            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Producto producto = getProductoFromRow(tableResults.getSelectedRow());
                System.out.println(producto.toString());


            }
        });
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

    public Producto getProductoFromRow(int row) {


        // get a producto from the Arraylist of productos. See which type it is

        Object[] dataRow = getData(searchResults)[row];

        System.out.println(Arrays.toString(dataRow));

        switch (typeProduct) {
            case REACTIVOS:

                Reactivo reactivo = new Reactivo();

                reactivo = reactivo.createProductFromRow(dataRow);

                System.out.println(reactivo);

                return new Reactivo();



            case PROD_AUX:



                return new Auxiliar();


            case MATERIALES:







                return new Material();



        }


















        return null;

    }






}
