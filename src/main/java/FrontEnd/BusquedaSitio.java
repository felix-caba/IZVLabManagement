/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.Impl.SitioDAOImpl;
import BackEnd.Extra.*;
import BackEnd.Sitio;
import BackEnd.Sitios.Localizacion;
import BackEnd.Sitios.Ubicacion;
import BackEnd.Tablas.TableModelGlobal;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BusquedaSitio extends JFrame implements Themeable {

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

    private ArrayList<Sitio> searchResults;

    private ArrayList<TableChange> tableChanges = new ArrayList<TableChange>();

    private final String[] columnNames;

    public BusquedaSitio(ArrayList<Sitio> searchResults, TYPE typeProduct) {

        this.searchResults = searchResults;
        this.typeProduct = typeProduct;

        this.columnNames = getColumnNames();

        adminButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        addButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        deleteButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        panelRoundSearchResults.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,3%);");

        initComponents();

        DefaultTableModel model = (DefaultTableModel) tableResults.getModel();

        adminButton.setEnabled(true);

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
                Sitio sitio = createNewSitio();
                searchResults.add(sitio);
                addToRow(sitio);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int selectedRow = tableResults.getSelectedRow();
                model.removeRow(selectedRow);


            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SitioDAOImpl sitioDAO = new SitioDAOImpl();
                for (TableChange change : tableChanges) {
                    switch (change.getChangeType()) {
                        case INSERT:
                            System.out.println("INSERT EJECUTADO");
                            sitioDAO.insertarSitio(change.getSitio());
                            break;
                        case UPDATE:
                            System.out.println("UPDATE EJECUTADO");
                            sitioDAO.modificarSitio(change.getSitio());
                            break;
                        case DELETE:
                            System.out.println("DELETE EJECUTADO");
                            sitioDAO.eliminarSitio(change.getSitio());
                            break;
                    }
                }
                tableChanges.clear();
            }
        });

    }


    public void initComponents() {

        TableModelGlobal model = new TableModelGlobal(getData(searchResults), columnNames, tableResults);
        tableResults.setModel(model);

        /*Tamaño de la ventana y posicion*/
        // Set size para ocupar el 70% de la pantalla consigue valores de la pantalla a través de screensize

        int sizeX = (int) (ScreenSize.getScreenWidth() * 0.3);
        int sizeY = (int) (ScreenSize.getScreenHeight() * 0.3);

        Dimension dim = new Dimension(sizeX, sizeY);

        setSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);

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

                    System.out.println("UPDATE");
                    tableChanges.add(new TableChange(TableChange.ChangeType.UPDATE, searchResults.get(row)));

                } else if (getType == TableModelEvent.INSERT) {

                    System.out.println("INSERT");
                    tableChanges.add(new TableChange(TableChange.ChangeType.INSERT, searchResults.get(row)));
                    tableResults.scrollRectToVisible(tableResults.getCellRect(row+1, 0, true));

                } else if (getType == TableModelEvent.DELETE) {

                    System.out.println("UPDATE");
                    tableChanges.add(new TableChange(TableChange.ChangeType.DELETE, searchResults.get(row)));
                    searchResults.remove(row);

                }



            }
        });

        tableResults.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    if (tableResults.getSelectedRow() != -1 && isAdmin && isButtonPressed) {
                        deleteButton.setEnabled(true);
                    } else {
                        deleteButton.setEnabled(false);
                    }
                }
            }
        });

        tableResults.setRowSorter(model.getRowSorter());
        filterFunc(model.getRowSorter());


        pack();
        setLocationRelativeTo(null);

    }



    // Metodos Actuales

    private String[] getColumnNames() {

        return ConseguirCampos.getColumnNames(searchResults.get(0).getClass());

    }

    public Object[][] getData(ArrayList<Sitio> searchResults) {


        Object[][] data = new Object[searchResults.size()][columnNames.length];

        for (int i = 0; i < searchResults.size(); i++) {

            Sitio sitio = searchResults.get(i);

            Object[] rowData = new Object[columnNames.length];

            for (int j = 0; j < rowData.length; j++) {

                rowData[j] = sitio.getValueForAttribute(columnNames[j]);

            }
            data[i] = rowData;
        }

        return data;
    }

    public Sitio createNewSitio() {

        int lastID = searchResults.isEmpty() ? 0 : searchResults.get(searchResults.size() - 1).getId();
        // Sumarle uno a la ID del último producto
        int newID = lastID + 1;

        switch (typeProduct) {

            case LOCALIZACION:

                return new Localizacion(newID, "Nombre");

            case UBICACION:

                return new Ubicacion(newID, "Nombre", 1, null);



        }

        return null;
    }

    public void addToRow(Sitio sitio) {

        // Obtener los nombres de los atributos del Producto

        // Crear un array para almacenar los valores de los atributos del Producto
        Object[] rowData = new Object[columnNames.length];

        for (int i = 0; i < columnNames.length; i++) {

            Object attributeValue = sitio.getValueForAttribute(columnNames[i]);

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
                throw new UnsupportedOperationException("ETC");
            }

        });


    }



}





