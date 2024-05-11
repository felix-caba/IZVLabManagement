
package BackEnd.Extra;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WordWrapRenderer extends JTextArea implements TableCellRenderer
{
    public WordWrapRenderer()
    {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        setText( (value == null) ? "" : value.toString() );
        setSize(table.getColumnModel().getColumn(column).getWidth(), table.getRowHeight(row));

        //  Recalculate the preferred height now that the text and renderer width have been set.

        int preferredHeight = getPreferredSize().height;

        if (table.getRowHeight(row) != preferredHeight)
        {
            table.setRowHeight(row, preferredHeight);
        }

        return this;
    }
}