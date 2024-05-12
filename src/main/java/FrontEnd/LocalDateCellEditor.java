/*
 * @AUTHOR Felix
 */

package FrontEnd;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.EventObject;


// editor de celdas


public class LocalDateCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final JXDatePicker datePicker;

    public LocalDateCellEditor() {

        // crea el datePicker jxdatepicker
        datePicker = new JXDatePicker();
        datePicker.setFormats("yyyy-MM-dd");

    }



    @Override
    public Object getCellEditorValue() {


        // coge la fecha seleccionada
        Date fechaSeleccionada = datePicker.getDate();

        if (fechaSeleccionada != null) {
            // valor
            return datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        } else {
            return null;
        }

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // esto pilla el componente de la celda de nuestro modelo
        if (value instanceof LocalDate) {

            datePicker.setDate(Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } else {

            datePicker.setDate(null);
        }

        return datePicker;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {

        return true;

    }
}