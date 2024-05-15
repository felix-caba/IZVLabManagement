/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.DAO.Impl.ProductoDAOImpl;
import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.TableChange;
import BackEnd.Producto;
import BackEnd.Sitio;
import BackEnd.Usuario;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserControlPanel extends JFrame implements Themeable {
    private PanelRound panelRound1;
    private JPanel panel1;
    private JTable table1;
    private JButton editButton;
    private JButton addButon;
    private JButton backButton;
    private JButton deleteButton;

    public UserControlPanel(ArrayList<Usuario> usuarios) {

        backButton.setName("backButton");
        addButon.setName("addButton");
        editButton.setName("editButton");
        deleteButton.setName("deleteButton");

        UsuarioTableModel tableModel = new UsuarioTableModel(usuarios, new ArrayList<>());




        table1.setModel(tableModel);
        initComponents();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        addButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int lastUserId = 0;

                if (!usuarios.isEmpty()) {

                    lastUserId = usuarios.get(usuarios.size() - 1).getId();

                }

                Usuario nuevoUsuario = new Usuario("", "", false,lastUserId + 1);

                tableModel.addUsuario(nuevoUsuario);

                tableModel.getChanges().add(new TableChange(TableChange.ChangeType.INSERT, nuevoUsuario));


            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    tableModel.removeUsuario(selectedRow);
                   // tableModel.getChanges().add(new TableChange(TableChange.ChangeType.DELETE, usuarios.get(selectedRow)));
                }
            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TableChange> changes = tableModel.getChanges();

                for (TableChange change : changes) {

                    Object obj = change.getObject();

                    if (obj instanceof Usuario) {
                        UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
                        switch (change.getChangeType()) {
                            case INSERT:
                                usuarioDAO.insert((Usuario) obj);
                                break;
                            case UPDATE:
                                usuarioDAO.update((Usuario) obj);
                                break;
                            case DELETE:
                                usuarioDAO.delete((Usuario) obj);
                                break;
                        }
                    }

                    if (obj instanceof Producto) {
                        ProductoDAOImpl productoDAO = new ProductoDAOImpl();
                        switch (change.getChangeType()) {
                            case INSERT:
                                productoDAO.insert((Producto) obj);
                                break;
                            case UPDATE:
                                productoDAO.update((Producto) obj);
                                break;
                            case DELETE:
                                productoDAO.delete((Producto) obj);
                                break;
                        }

                    }

                    if (obj instanceof Sitio){

                    }

                }
            }
        });

    }

    public void initComponents() {

        panelRound1.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);

        TableColumn adminColumn = table1.getColumnModel().getColumn(3); // Cambia el número 3 por el índice de la columna "Es Admin?"
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"true", "false"});
        adminColumn.setCellEditor(new DefaultCellEditor(comboBox));

        setIcons(this);



    }


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
                    return usuario.isEs_admin();
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














}
