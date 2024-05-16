/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Tablas;

import BackEnd.Configuration.ScreenSize;
import FrontEnd.LocalDateCellEditor;
import org.jdesktop.swingx.JXTable;

import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;

public class TableModelGlobal extends DefaultTableModel {

    private TableRowSorter<TableModel> rowSorter ;
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();

    public TableModelGlobal(Object[][] data, Object[] columnNames, JXTable tableResults) {


        super(data, columnNames);
        TableColumnModel columnModel = tableResults.getColumnModel();


        int sizeX = (int) (ScreenSize.getScreenWidth() * 0.7);
        int sizeY = (int) (ScreenSize.getScreenHeight() * 0.7);
        int sizeXMax = (ScreenSize.getScreenWidth());
        int sizeYMax = (ScreenSize.getScreenHeight());
        Dimension dim = new Dimension(sizeX, sizeY);
        Dimension dimMax = new Dimension(sizeXMax, sizeYMax);

        rowSorter = new TableRowSorter<>(this);

        for (int indColumna = 0; indColumna < columnModel.getColumnCount(); indColumna++) {
            Class<?> columnClass = this.getColumnClass(indColumna);
            if (LocalDate.class.isAssignableFrom(columnClass)) {
                // pone el editor de celda fecha
                columnModel.getColumn(indColumna).setCellEditor(new LocalDateCellEditor());
            }
        }



        tableResults.setModel(this);



        tableResults.setRowSorter(rowSorter);
        leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        tableResults.setPreferredScrollableViewportSize(dim);

        tableResults.packAll();


    }


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


    public TableRowSorter<TableModel> getRowSorter() {
        return rowSorter;
    }

    public void setRowSorter(TableRowSorter<TableModel> rowSorter) {
        this.rowSorter = rowSorter;
    }


}



