/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.Extra.TYPE;
import BackEnd.Extra.TableChange;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
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
    private JTextField filterField;
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
            @Override
            public boolean isCellEditable(int row, int column) {
                // Devuelve false para la primera columna y true para las demás
                return column != 0;
            }

            public Class<?> getColumnClass(int column) {


                for (int row = 0; row < getRowCount(); row++) {

                    Object o = getValueAt(row, column);

                    if (o != null) {

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

                    System.out.println("Añadido a Update" + getProductoFromRow(row));

                    tableChanges.add(new TableChange(TableChange.ChangeType.UPDATE, getProductoFromRow(row)));

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





        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);

        tableResults.setRowSorter(rowSorter);

        filterFunc(rowSorter);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
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
        panelRoundSearchResults.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,3%);");

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

                int nuevaRow = model.getRowCount() - 1;

                Producto productoNuevo = createNewProduct();

                addProductToRow(productoNuevo);

                searchResults.add(productoNuevo);
                tableChanges.add(new TableChange(TableChange.ChangeType.INSERT, productoNuevo));

                tableResults.scrollRectToVisible(tableResults.getCellRect(nuevaRow+1, 0, true));
                tableResults.setRowSelectionInterval(nuevaRow, nuevaRow+1);


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {



                int selectedRow = tableResults.getSelectedRow();

                tableChanges.add(new TableChange(TableChange.ChangeType.DELETE, getProductoFromRow(selectedRow)));

                model.removeRow(selectedRow);




            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProductoDAOImpl productoDAO = new ProductoDAOImpl();

                    for (TableChange change : tableChanges) {

                        switch (change.getChangeType()) {

                            case INSERT:
                                productoDAO.insert(change.getProducto());
                                break;

                            case UPDATE:
                                productoDAO.update(change.getProducto());
                                break;

                            case DELETE:
                                productoDAO.delete(change.getProducto());
                                break;
                        }

                    }

                    tableChanges.clear();

            }
        });

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

    public Object[] getAttributesFromRow(int row) {

        TableModel model = tableResults.getModel();
        int columnCount = model.getColumnCount();
        Object[] rowData = new Object[columnCount];

        for (int column = 0; column < columnCount; column++) {
            System.out.println(model.getValueAt(row, column));
            rowData[column] = model.getValueAt(row, column);
        }
        return rowData;
    }

    public Producto getProductoFromRow(int row) {

        Object[] dataRow = getAttributesFromRow(row);
        System.out.println(Arrays.toString(dataRow));

        switch (typeProduct) {

            case REACTIVOS:

                Reactivo reactivo = new Reactivo();
                reactivo = reactivo.getProductFromRow(dataRow);


                System.out.println(reactivo.toString());

                return reactivo;

            case AUXILIARES:

                Auxiliar auxiliar = new Auxiliar();
                auxiliar = auxiliar.getProductFromRow(dataRow);
                System.out.println(auxiliar.toString());

                return auxiliar;


            case MATERIALES:

                Material material = new Material();
                material = material.getProductFromRow(dataRow);

                return material;

        }


        return null;

    }

    public Producto createNewProduct() {

        int lastID = searchResults.isEmpty() ? 0 : searchResults.get(searchResults.size() - 1).getId();
        // Sumarle uno a la ID del último producto
        int newID = lastID + 1;

        switch (typeProduct) {

            case REACTIVOS:

                return new Reactivo(newID, 0, null, null, null,
                        null, null, null, null, 0);

            case AUXILIARES:

                return new Auxiliar(newID, 0, null, null, null, null);

            case MATERIALES:

                return new Material(newID, 0, null, null, null,
                        null, null, null, null, 0);


        }

        return null;
    }

    public void addProductToRow(Producto producto) {
        // Obtener los nombres de los atributos del Producto
        String[] attributeNames = producto.getAllAttributesNamesString();

        // Crear un array para almacenar los valores de los atributos del Producto
        Object[] rowData = new Object[attributeNames.length];

        for (int i = 0; i < attributeNames.length; i++) {

            Object attributeValue = producto.getValueForAttribute(attributeNames[i]);

            rowData[i] = attributeValue;

            }

        DefaultTableModel model = (DefaultTableModel) tableResults.getModel();
        model.addRow(rowData);

        }


    public void filterFunc(TableRowSorter rowSorter){

       filterField.getDocument().addDocumentListener(new DocumentListener(){

           @Override
           public void insertUpdate(DocumentEvent e) {
               String text = filterField.getText();

               if (text.trim().length() == 0) {
                   rowSorter.setRowFilter(null);
               } else {
                   rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
               }
           }

           @Override
           public void removeUpdate(DocumentEvent e) {
               String text = filterField.getText();

               if (text.trim().length() == 0) {
                   rowSorter.setRowFilter(null);
               } else {
                   rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
               }
           }

           @Override
           public void changedUpdate(DocumentEvent e) {
               throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           }

       });


    }



}





