/*
 * @AUTHOR Felix
 */

package FrontEnd;

import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.MySQL;
import BackEnd.Usuario;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPanel extends JFrame implements Themeable {
    private PanelRound panelWindowLoginIn;
    private JPanel panelWindowLogin;
    private JButton loginButton;
    private JTextField loginUsernameField;
    private JTextField loginPasswordField;
    private JButton backButton;


    public LoginPanel() {



        panelWindowLoginIn.putClientProperty( FlatClientProperties.STYLE,
                "background: lighten(@background,3%);");

        loginButton.setName("loginButton");
        backButton.setName("backButton");



        initComponents();

        setIcons(this);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new MainMenu().setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                LoadingFrame dialog = new LoadingFrame();

                /*OBSERVA LOS CAMBIOS*/

                MySQL sql = MySQL.getInstance();

                sql.addPropertyChangeListener(dialog);
                loginWorker(dialog).execute();

                dialog.setVisible(true);

            }


        });
    }

    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        setTitle("Login Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelWindowLogin);

        pack();

    }






    public SwingWorker<Void, Void> loginWorker(LoadingFrame frame) {


        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
            private ArrayList<Usuario> usuarios;

            @Override
            protected Void doInBackground() throws Exception {

                usuarios = usuarioDAO.select();

                return null;
            }

            @Override
            protected void done() {

                // Este método se llama cuando doInBackground() ha terminado
                // Aquí puedes realizar las acciones necesarias después de la conexión a la base de datos




                if (usuarios == null) {

                    return;

                } else {


                    for (Usuario usuario : usuarios) {


                        if (usuario.getNombre().equals(loginUsernameField.getText()) && usuario.getContrasena().equals(loginPasswordField.getText())) {



                            new MenuGeneral(usuario.Es_admin(), usuario.getNombre()).setVisible(true);

                            dispose();

                            frame.setMessage("Login successful");


                        }

                }
                }


            }

        };

        return worker;

}

}
