/*
 * @AUTHOR Felix
 */

package BackEnd;
import BackEnd.Configuration.ScreenSize;
import BackEnd.Extra.TableChange;
import FrontEnd.LocalDateCellEditor;
import org.jdesktop.swingx.JXTable;

import javax.swing.table.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TablaObjetos<T> extends AbstractTableModel {

    private final ArrayList<T> objects;
    private final ArrayList<TableChange<T>> changes;
    private final String[] columnNames;
    private final Class<?>[] columnClasses;
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();



    public TablaObjetos(ArrayList<T> objects, String[] columnNames, Class<?>[] columnClasses, JXTable tableResults) {
        this.objects = objects;
        this.changes = new ArrayList<>();
        this.columnNames = columnNames;
        this.columnClasses = getColumnClasses();


        int sizeX = (int) (ScreenSize.getScreenWidth() * 0.7);
        int sizeY = (int) (ScreenSize.getScreenHeight() * 0.7);
        int sizeXMax = (ScreenSize.getScreenWidth());
        int sizeYMax = (ScreenSize.getScreenHeight());
        Dimension dim = new Dimension(sizeX, sizeY);
        Dimension dimMax = new Dimension(sizeXMax, sizeYMax);


        TableColumnModel columnModel = tableResults.getColumnModel();
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(this);


        tableResults.setRowSorter(rowSorter);


        for (int indColumna = 0; indColumna < columnModel.getColumnCount(); indColumna++) {
            Class<?> columnClass = this.getColumnClass(indColumna);
            if (LocalDate.class.isAssignableFrom(columnClass)) {
                // pone el editor de celda fecha
                columnModel.getColumn(indColumna).setCellEditor(new LocalDateCellEditor());
            }
        }


        tableResults.setModel(this);
        leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        tableResults.packAll();
        tableResults.setPreferredScrollableViewportSize(dim);





    }


    @Override
    public int getRowCount() {
        return objects.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T object = objects.get(rowIndex);
        try {
            String fieldName = columnNames[columnIndex];  // Nombre del campo según la convención de la tabla
            String getterMethodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            return object.getClass().getMethod(getterMethodName).invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
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

    public Class<?>[] getColumnClasses() {

        Class<?>[] columnClasses = new Class<?>[getColumnCount()];

        for (int column = 0; column < getColumnCount(); column++) {

            for (int row = 0; row < getRowCount(); row++) {
                Object value = getValueAt(row, column);
                if (value != null) {
                    columnClasses[column] = value.getClass();
                    break;
                }
            }

            if (columnClasses[column] == null) {
                columnClasses[column] = Object.class;
            }
        }
        return columnClasses;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex != 0;

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        T object = objects.get(rowIndex);

        System.out.println(object.getClass().getName());
        if (aValue == null) {
            aValue = "xd";
        }


        System.out.println(aValue.getClass().getName());

;


        String setterMethodName = "set" + Character.toUpperCase(columnNames[columnIndex].charAt(0)) + columnNames[columnIndex].substring(1);



        try {


            System.out.println(Arrays.toString(object.getClass().getMethods()));



            object.getClass().getMethod(setterMethodName, columnClasses[columnIndex]).invoke(object, aValue);


        } catch (Exception e) {

            e.printStackTrace();

        }

        changes.add(new TableChange<>(TableChange.ChangeType.UPDATE, object));
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addObject(T object) {
        objects.add(object);
        changes.add(new TableChange<>(TableChange.ChangeType.INSERT, object));
        fireTableRowsInserted(objects.size() - 1, objects.size() - 1);
    }

    public void removeObject(int rowIndex) {
        T object = objects.remove(rowIndex);
        changes.add(new TableChange<>(TableChange.ChangeType.DELETE, object));
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public ArrayList<TableChange<T>> getChanges() {
        return changes;
    }




}