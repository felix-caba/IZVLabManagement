/*
 * @AUTHOR Felix
 */

package BackEnd.Tablas;

import BackEnd.Configuration.IPAddress;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


import BackEnd.Configuration.IPAddress;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class IPTableModel extends AbstractTableModel {

    private final List<IPAddress> ipAddresses;
    private final String[] columnNames = {"IP", "PHPMYADMIN"};

    public IPTableModel(List<IPAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @Override
    public int getRowCount() {
        return ipAddresses.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IPAddress ipAddress = ipAddresses.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return ipAddress.getIp();
            case 1:
                return ipAddress.isPhpmyadmin();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
            return Boolean.class;
        }
        return String.class;
    }

    public IPAddress getIPAddress(int rowIndex) {
        return ipAddresses.get(rowIndex);
    }
}
