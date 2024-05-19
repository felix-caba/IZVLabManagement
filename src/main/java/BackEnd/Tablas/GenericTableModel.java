/*
 * @AUTHOR Felix
 */

package BackEnd.Tablas;

import BackEnd.Configuration.ScreenSize;
import BackEnd.Extra.TableChange;
import org.jdesktop.swingx.JXTable;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

public class GenericTableModel<T> extends AbstractTableModel {

    private List<T> data;
    private String[] columnNames;
    private DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
    private ArrayList<TableChange> changes;

    public GenericTableModel(List<T> data, String[] columnNames, JXTable tableResults) {
        this.data = data;
        this.columnNames = columnNames;
        this.changes = new ArrayList<>();

        int sizeX = (int) (ScreenSize.getScreenWidth() * 0.7);
        int sizeY = (int) (ScreenSize.getScreenHeight() * 0.7);
        Dimension dim = new Dimension(sizeX, sizeY);

        tableResults.setPreferredScrollableViewportSize(dim);


    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T item = data.get(rowIndex);
        try {
            Field field = getField(item.getClass(), columnNames[columnIndex]);
            field.setAccessible(true);
            return field.get(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        T item = data.get(rowIndex);

        try {


            Field field = getField(item.getClass(), columnNames[columnIndex]);
            field.setAccessible(true);
            field.set(item, aValue);

            changes.add(new TableChange(TableChange.ChangeType.UPDATE, item));

            fireTableCellUpdated(rowIndex, columnIndex);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    public void removeRow(int rowIndex) {

        changes.add(new TableChange(TableChange.ChangeType.DELETE, data.get(rowIndex)));
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);

    }

    public void addRow(T item) {
        data.add(item);
        changes.add(new TableChange(TableChange.ChangeType.INSERT, item));
        fireTableRowsInserted(data.size() - 1, data.size() - 1);

    }


    @Override
    public Class<?> getColumnClass(int column) {

        for (int row = 0; row < getRowCount(); row++) {

            Object o = getValueAt(row, column);

            if (o != null) {
                return o.getClass();
            }

        }

        return Object.class;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    private Field getField(Class<?> clase, String fieldName) throws NoSuchFieldException {
        while (clase != null) {
            try {
                return clase.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clase = clase.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in class hierarchy.");
    }

    public ArrayList<TableChange> getChanges() {
        return changes;
    }





}