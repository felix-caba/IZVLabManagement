/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.Extra.ParseRiesgos;
import BackEnd.Extra.RIESGOS;
import BackEnd.Extra.TYPE;
import BackEnd.Extra.TableChange;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;
import BackEnd.Tablas.TableModelGlobal;
import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BusquedaProducto extends JFrame implements Themeable {
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

    public BusquedaProducto(ArrayList<Producto> searchResults, boolean isAdmin, TYPE typeProduct) {


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
                Producto productoNuevo = createNewProduct();
                searchResults.add(productoNuevo);
                addProductToRow(productoNuevo);
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
                ProductoDAOImpl productoDAO = new ProductoDAOImpl();
                for (TableChange change : tableChanges) {
                    switch (change.getChangeType()) {
                        case INSERT:
                            System.out.println("INSERT EJECUTADO");
                            System.out.println(change.getProducto().toString());
                            productoDAO.insert(change.getProducto());
                            break;
                        case UPDATE:
                            System.out.println("UPDATE EJECUTADO");
                            System.out.println(change.getProducto().toString());
                            productoDAO.update(change.getProducto());
                            break;
                        case DELETE:
                            System.out.println("DELETE EJECUTADO");
                            System.out.println(change.getProducto().toString());
                            productoDAO.delete(change.getProducto());
                            break;
                    }
                }
                tableChanges.clear();
            }
        });

    }

    public void initComponents() {


        TableModelGlobal model = new TableModelGlobal(getData(searchResults), getColumnNames(searchResults), tableResults);
        tableResults.setModel(model);

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

        tableResults.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tableResults.rowAtPoint(e.getPoint());
                    int column = tableResults.columnAtPoint(e.getPoint());
                    String columnName = tableResults.getColumnName(column);
                    if (columnName.equals("riesgos")) {

                        Object cellValue = tableResults.getValueAt(row, column);

                        RIESGOS[] riesgos = ParseRiesgos.parseRiesgos((String) cellValue);

                        JPanel panel = new JPanel(new GridLayout(2, 3)); // 2 filas, 3 columnas
                        boolean encontradoAtencion = false;

                        if (riesgos.length > 0) {

                            System.out.println(riesgos);

                            if (ConfigurationIZV.getInstance().isDark()) {

                                for (RIESGOS riesgo : riesgos) {
                                    // Verificar si el riesgo es "Corrosivo", "Irritante" o "Nocivo"
                                    if (!encontradoAtencion && (riesgo == RIESGOS.CORROSIVO || riesgo == RIESGOS.IRRITANTE || riesgo == RIESGOS.NOCIVO)) {
                                        // Mostrar el icono de atención
                                        ImageIcon iconAtencion = new ImageIcon("src/main/resources/riesgos/white/atencion.png");
                                        JLabel labelAtencion = new JLabel(iconAtencion);
                                        panel.add(labelAtencion);

                                        // Mostrar el icono correspondiente al riesgo
                                        ImageIcon iconRiesgo = new ImageIcon("src/main/resources/riesgos/white/" + riesgo.toString() + ".png");
                                        JLabel labelRiesgo = new JLabel(iconRiesgo);
                                        panel.add(labelRiesgo);

                                        encontradoAtencion = true; // Marcar que se ha encontrado un riesgo de atención
                                    } else {
                                        // Mostrar solo el icono correspondiente al riesgo
                                        ImageIcon icon = new ImageIcon("src/main/resources/riesgos/white/" + riesgo.toString() + ".png");
                                        JLabel label = new JLabel(icon);
                                        panel.add(label);
                                    }
                                }

                            } else {

                                for (RIESGOS riesgo : riesgos) {

                                }

                            }


                        } else {

                            JLabel label = new JLabel("No hay riesgos");
                            panel.add(label);

                        }



                        JOptionPane.showMessageDialog(null, panel, "Título", JOptionPane.PLAIN_MESSAGE);




                    }


                }
            }
        });

        TableColumnModel columnModel = tableResults.getColumnModel();
        for (int indColumna = 0; indColumna < columnModel.getColumnCount(); indColumna++) {
            Class<?> columnClass = model.getColumnClass(indColumna);
            if (LocalDate.class.isAssignableFrom(columnClass)) {
                // pone el editor de celda fecha
                columnModel.getColumn(indColumna).setCellEditor(new LocalDateCellEditor());
            }
        }

        tableResults.setRowSorter(model.getRowSorter());
        filterFunc(model.getRowSorter());

        pack();

        setLocationRelativeTo(null);

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
               throw new UnsupportedOperationException("ETC");
           }

       });


    }



}





