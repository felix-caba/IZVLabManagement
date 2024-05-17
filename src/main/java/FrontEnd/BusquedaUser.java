/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.Extra.TableChange;
import BackEnd.Tablas.UsuarioTableModel;
import BackEnd.Usuario;
import com.formdev.flatlaf.FlatClientProperties;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class BusquedaUser extends JFrame implements Themeable {
    private PanelRound panelRound1;
    private JPanel panel1;
    private JTable table1;
    private JButton editButton;
    private JButton addButon;
    private JButton backButton;
    private JButton deleteButton;

    public BusquedaUser(ArrayList<Usuario> usuarios) {

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

                int lastID = usuarios.isEmpty() ? 0 : usuarios.get(usuarios.size() - 1).getId();
                int newID = lastID + 1;

                Usuario nuevoUsuario = new Usuario("", "", false,newID);
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
                UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();

                for (TableChange change : changes) {

                    System.out.println(change.getChangeType());

                    switch (change.getChangeType()) {
                        case INSERT:
                            System.out.println("Inserting");
                            usuarioDAO.insert(change.getUsuario());
                            break;
                        case UPDATE:
                            System.out.println("Updating");
                            usuarioDAO.update(change.getUsuario());
                            break;
                        case DELETE:
                            System.out.println("Deleting");
                            usuarioDAO.delete(change.getUsuario());
                            break;
                    }
                }
                changes.clear();
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

        TableColumn adminColumn = table1.getColumnModel().getColumn(3);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"true", "false"});
        adminColumn.setCellEditor(new DefaultCellEditor(comboBox));

        setIcons(this);



    }

















}
