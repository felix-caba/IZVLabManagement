/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.Configuration.ConfigurationIZV;
import BackEnd.Configuration.ScreenSize;
import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.DAO.Impl.SitioDAOImpl;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.*;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;
import BackEnd.Sitio;
import BackEnd.Sitios.Localizacion;
import BackEnd.Sitios.Ubicacion;
import BackEnd.Tablas.GenericTableModel;
import BackEnd.Usuario;
import com.formdev.flatlaf.FlatClientProperties;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private JButton printButton;
    public boolean isButtonPressed = false;
    public boolean isAdmin;
    private final TYPE typeProduct;
    private ArrayList searchResults;
    private Document document;



    @SuppressWarnings({"unchecked, rawtypes, unchecked cast", "Unchecked cast"})

    public BusquedaProducto(ArrayList searchResults, boolean isAdmin, TYPE typeProduct) {

        if (isAdmin) {
            adminButton.setEnabled(true);
        }


        initComponents();

        GenericTableModel model = new GenericTableModel(searchResults, ConseguirCampos.getColumnNames(searchResults.get(0).getClass()), tableResults);
        tableResults.setModel(model);
        TableRowSorter<AbstractTableModel> rowSorter = new TableRowSorter<>(model);
        tableResults.setRowSorter(rowSorter);
        filterFunction(rowSorter, filterField);


        TableColumnModel columnModel = tableResults.getColumnModel();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);



        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            Class<?> columnClass = tableResults.getColumnClass(i);
            if (columnClass == Integer.class || columnClass == int.class) {
                tableResults.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
            }
        }

        for (int indColumna = 0; indColumna < columnModel.getColumnCount(); indColumna++) {

            Class<?> columnClass = model.getColumnClass(indColumna);
            if (LocalDate.class.isAssignableFrom(columnClass)) {
                columnModel.getColumn(indColumna).setCellEditor(new LocalDateCellEditor());
            }

        }


        this.searchResults = searchResults;
        this.isAdmin = isAdmin;
        this.typeProduct = typeProduct;

        adminButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        saveButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        addButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        deleteButton.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,15%);");
        panelRoundSearchResults.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background,3%);");


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

                Object objetoDeMiLista = searchResults.get(0);

                if (objetoDeMiLista instanceof Producto) {

                    Producto productoNuevo = (Producto) createNewObject();
                    model.addRow(productoNuevo);
                    System.out.println(searchResults.size());
                }

                if (objetoDeMiLista instanceof Usuario) {

                    Usuario usuarioNuevo = (Usuario) createNewObject();
                    model.addRow(usuarioNuevo);

                }

                if (objetoDeMiLista instanceof Sitio) {

                    Sitio sitioNuevo = (Sitio) createNewObject();
                    model.addRow(sitioNuevo);

                }


                tableResults.changeSelection(searchResults.size(), 0, true, true);
                tableResults.scrollRectToVisible(tableResults.getCellRect(searchResults.size(), 0, true));

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int selectedRow = tableResults.getSelectedRow();
                System.out.println(selectedRow);
                model.removeRow(selectedRow);

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<TableChange> tableChanges = model.getChanges();

                ProductoDAOImpl productoDAO = new ProductoDAOImpl();
                UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
                SitioDAOImpl sitioDAO = new SitioDAOImpl();

                Object newObject = searchResults.get(0);


                if (newObject instanceof Producto) {
                    for (TableChange change : tableChanges) {

                        switch (change.getChangeType()) {
                            case INSERT:
                                System.out.println("INSERT EJECUTADO");
                                productoDAO.insert(change.getProducto());
                                break;
                            case UPDATE:
                                System.out.println("UPDATE EJECUTADO");
                                productoDAO.update(change.getProducto());
                                break;
                            case DELETE:
                                System.out.println("DELETE EJECUTADO");
                                productoDAO.delete(change.getProducto());
                                break;
                        }
                    }
                    tableChanges.clear();
                }

                if (newObject instanceof Usuario) {
                    for (TableChange change : tableChanges) {
                        switch (change.getChangeType()) {
                            case INSERT:
                                System.out.println("INSERT EJECUTADO");
                                usuarioDAO.insert(change.getUsuario());
                                break;
                            case UPDATE:
                                System.out.println("UPDATE EJECUTADO");
                                usuarioDAO.update(change.getUsuario());
                                break;
                            case DELETE:
                                System.out.println("DELETE EJECUTADO");
                                usuarioDAO.delete(change.getUsuario());
                                break;
                        }
                    }
                    tableChanges.clear();
                }

                if (newObject instanceof Sitio) {
                    for (TableChange change : tableChanges) {

                        System.out.println(tableChanges.size()   );

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







            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                com.itextpdf.text.Document documento = new com.itextpdf.text.Document(PageSize.A4.rotate());

                try {

                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("tabla.pdf"));
                    documento.open();
                    PdfPTable pdfTable = new PdfPTable(tableResults.getColumnCount());

                    // coger columnas
                    for (int i = 0; i < tableResults.getColumnCount(); i++) {
                        pdfTable.addCell(tableResults.getColumnName(i));
                    }

                    // coger filas
                    for (int rows = 0; rows < tableResults.getRowCount() - 1; rows++) {
                        for (int cols = 0; cols < tableResults.getColumnCount(); cols++) {

                            if (tableResults.getModel().getValueAt(rows, cols) == null) {
                                pdfTable.addCell("");
                            } else {
                            pdfTable.addCell(tableResults.getModel().getValueAt(rows, cols).toString());
                        }}
                    }

                    documento.add(pdfTable);
                    documento.close();

                } catch (DocumentException e) {

                    throw new RuntimeException(e);

                } catch (FileNotFoundException e) {

                    throw new RuntimeException(e);

                }

            }
        });
    }

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

        /*
         *
         *     https://docs.oracle.com/javase/tutorial/uiswing/events/tablemodellistener.html
         *
         */


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






        tableResults.packAll();

        pack();

        setLocationRelativeTo(null);

    }


    // Metodos Actuales

    public Object createNewObject() {

        Object newObject = searchResults.get(0);

        System.out.println(newObject.getClass());

        if (newObject instanceof Producto) {

            ArrayList<Producto> searchResults = (ArrayList<Producto>) this.searchResults;

            int lastID = searchResults.get(searchResults.size() - 1).getId();
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

        } else if (newObject instanceof Sitio) {

            ArrayList<Sitio> searchResults = (ArrayList<Sitio>) this.searchResults;

            int lastID = searchResults.get(searchResults.size() - 1).getId();
            int newID = lastID + 1;

            System.out.println("UBICACION");

            switch (typeProduct) {
                case LOCALIZACION:
                    return new Localizacion(newID, "");
                case UBICACION:
                    System.out.println("UBICACION");
                    return new Ubicacion(newID, "", 0, "");
            }

        } else if (newObject instanceof Usuario) {

            ArrayList<Usuario> searchResults = (ArrayList<Usuario>) this.searchResults;

            int lastID = searchResults.get(searchResults.size() - 1).getId();
            int newID = lastID + 1;

            return new Usuario("", "", false, newID);

        }

        return null;

    }




    public void filterFunction(TableRowSorter rowSorter, JTextField filterField) {

        filterField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filterField.getText();

                if (text.trim().isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = filterField.getText();

                if (text.trim().isEmpty()) {
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










