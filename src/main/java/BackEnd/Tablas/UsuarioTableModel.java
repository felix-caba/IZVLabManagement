/*
 * @AUTHOR Felix
 */

package BackEnd.Tablas;

import BackEnd.Extra.TableChange;
import BackEnd.Usuario;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


    public class UsuarioTableModel extends AbstractTableModel {

        private final String[] columnNames = {"ID", "Nombre", "Contraseña", "Es Admin?"};
        private ArrayList<Usuario> usuarios;
        private ArrayList<TableChange> changes;

        public UsuarioTableModel(ArrayList<Usuario> usuarios, ArrayList<TableChange> changes) {
            this.usuarios = usuarios;
            this.changes = new ArrayList<>();
        }


        @Override
        public int getRowCount() {
            return usuarios.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Usuario usuario = usuarios.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return usuario.getId();
                case 1:
                    return usuario.getNombre();
                case 2:
                    return usuario.getContrasena();
                case 3:
                    return usuario.getEs_admin();
                default:
                    return null;

            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return false;
            }
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Usuario usuario = usuarios.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    usuario.setId((int) aValue);
                    break;
                case 1:
                    usuario.setNombre((String) aValue);
                    break;
                case 2:
                    usuario.setContrasena((String) aValue);
                    break;
                case 3:
                    if (aValue instanceof Boolean) {
                        usuario.setEs_admin((Boolean) aValue);
                    } else if (aValue instanceof String) {
                        String value = (String) aValue;
                        boolean esAdmin = value.equalsIgnoreCase("true");
                        usuario.setEs_admin(esAdmin);
                    }
                    break;
            }




            changes.add(new TableChange(TableChange.ChangeType.UPDATE, usuario));
            fireTableCellUpdated(rowIndex, columnIndex);



        }


        public void addUsuario(Usuario usuario) {
            usuarios.add(usuario);
            changes.add(new TableChange(TableChange.ChangeType.INSERT, usuario));
            fireTableRowsInserted(usuarios.size() - 1, usuarios.size() - 1);
        }

        public void removeUsuario(int rowIndex) {
            Usuario usuario = usuarios.remove(rowIndex);
            changes.add(new TableChange(TableChange.ChangeType.DELETE, usuario));
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public ArrayList<TableChange> getChanges() {
            return changes;
        }





    }


