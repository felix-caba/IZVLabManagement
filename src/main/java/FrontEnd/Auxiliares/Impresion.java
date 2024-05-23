/*
 * @AUTHOR Felix
 */

package FrontEnd.Auxiliares;

import FrontEnd.ElementosSwing.JPanelBackground;
import FrontEnd.ElementosSwing.PanelRound;
import FrontEnd.Themeable;
import com.formdev.flatlaf.FlatClientProperties;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Impresion extends JFrame implements Themeable {
    private JPanel panel1;
    private PanelRound panelRound1;
    private JComboBox<String> comboColumn;
    private JComboBox comboTamano;
    private JComboBox comboOrientacion;
    private JCheckBox checkboxAllRows;
    private JCheckBox checkBoxColumns;
    private PanelRound panelRound2;
    private JList list1;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JButton imprimirButton;
    private JXTable tableImpresion;




    public Impresion(JXTable tableImpresion) {

        this.tableImpresion = tableImpresion;



        panelRound1.putClientProperty( FlatClientProperties.STYLE,
                "background: fade(@background,50%);");
        panelRound2.putClientProperty( FlatClientProperties.STYLE,
                "background: fade(@background,50%);");


        backButton.setName("backButton");
        imprimirButton.setName("printButton");

        initComponents();

        setIcons(this);

        String[] columnNames = getColumns();

        DefaultListModel<JCheckBox> model = new DefaultListModel<>();
        for (String columnName : columnNames) {
            JCheckBox checkbox = new JCheckBox(columnName);
            checkbox.setOpaque(false);
            model.addElement(checkbox);
        }


        list1.setCellRenderer(new CheckBoxListCellRenderer());

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list1.locationToIndex(e.getPoint());
                JCheckBox checkbox = (JCheckBox) list1.getModel().getElementAt(index);
                checkbox.setSelected(!checkbox.isSelected());
                list1.repaint(list1.getCellBounds(index, index));

            }
        });

        list1.setModel(model);


        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println(Arrays.toString(tableImpresion.getSelectedRows()));

                try {

                    generatePDF(getSelectedColumns());

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        checkboxAllRows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (checkboxAllRows.isSelected()) {
                    tableImpresion.selectAll();
                } else {
                    tableImpresion.clearSelection();
                }

            }
        });
        checkBoxColumns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (checkBoxColumns.isSelected()) {
                    list1.setEnabled(true);
                } else {
                    list1.setEnabled(false);
                }
            }

        });
    }



    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new Dimension(800, 600));
        setSize(800, 600);

        setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("IZV Lab Management Tool 2024");
        setContentPane(panel1);

        pack();
        setLocationRelativeTo(null);

    }

    public String[] getColumns() {
        String[] columns = new String[tableImpresion.getColumnCount()];
        for (int i = 0; i < tableImpresion.getColumnCount(); i++) {
            columns[i] = tableImpresion.getColumnName(i);
        }
        return columns;
    }



    public String[] getSelectedColumns() {


        if (checkBoxColumns.isSelected()) {
            return getColumns();
        }

        DefaultListModel<JCheckBox> model = (DefaultListModel<JCheckBox>) list1.getModel();
        ArrayList<String> selectedColumns = new ArrayList<>();

        for (int i = 0; i < model.getSize(); i++) {
            JCheckBox checkbox = model.getElementAt(i);
            if (checkbox.isSelected()) {
                selectedColumns.add(checkbox.getText());
            }
        }

        // Convertir la lista a un array de String
        String[] selectedColumnsArray = new String[selectedColumns.size()];
        selectedColumnsArray = selectedColumns.toArray(selectedColumnsArray);
        return selectedColumnsArray;
    }

    public Rectangle getPageSize() {
        String selectedSize = (String) comboTamano.getSelectedItem();
        if (selectedSize.equals("A4")) {
            return PageSize.A4;
        } else if (selectedSize.equals("A3")) {
            return PageSize.A3;
        } else if (selectedSize.equals("A2")) {
            return PageSize.A2;
        } else if (selectedSize.equals("A1")) {
            return PageSize.A1;
        } else if (selectedSize.equals("A0")) {
            return PageSize.A0;
        }
        return PageSize.A4;
    }

    public Rectangle getOrientation(Rectangle pageSize) {
        String selectedOrientation = (String) comboOrientacion.getSelectedItem();
        if (selectedOrientation.equals("Vertical")) {
            return pageSize;
        } else if (selectedOrientation.equals("Horizontal")) {
            return pageSize.rotate();
        }
        return pageSize;
    }


    public void generatePDF( String[] selectedColumns) throws FileNotFoundException, DocumentException {


        int[] selectedRows = tableImpresion.getSelectedRows();
        System.out.println(Arrays.toString(selectedRows));

        com.itextpdf.text.Document documento = null;
        documento = new com.itextpdf.text.Document(getOrientation(getPageSize()));


        PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream("tabla.pdf"));

        documento.open();


        int columnCount = selectedColumns.length;

        PdfPTable tabla = new PdfPTable(columnCount);

        for (int i = 0; i < columnCount; i++) {
            System.out.println(selectedColumns[i]);
            tabla.addCell(selectedColumns[i]);
        }

        for (int row : selectedRows) {
            // Iterar sobre las columnas seleccionadas
            for (String column : selectedColumns) {
                int columnIndex = tableImpresion.getColumnModel().getColumnIndex(column);
                Object cellValue = tableImpresion.getValueAt(row, columnIndex);
                tabla.addCell(cellValue != null ? cellValue.toString() : "");
            }
        }


            documento.add(tabla);
            documento.close();

    }


    private static class CheckBoxListCellRenderer implements ListCellRenderer<JCheckBox> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected, boolean cellHasFocus) {



            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            value.setOpaque(false);

            return value;

        }
    }







    private void createUIComponents() {
        try {
            panel1 = new JPanelBackground("src/main/resources/photos/fondoImpresion.png");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }}



