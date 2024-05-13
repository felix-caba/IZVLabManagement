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


                JOptionPane dialog = new JOptionPane("Logging in...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

                loginWorker(dialog).execute();

                dialog.createDialog(null, "Logging in...").setVisible(true);
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






    public SwingWorker<Void, Void> loginWorker(JOptionPane messageLogging) {



        // swingWorker segundo plano hilo
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                // se conecta a la bd

                MySQL.getInstance().connect();

                return null;
            }

            @Override
            protected void done() {

                // Este método se llama cuando doInBackground() ha terminado
                // Aquí puedes realizar las acciones necesarias después de la conexión a la base de datos

                UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
                ArrayList<Usuario> usuarios = usuarioDAO.select();

                boolean isLogged = false;

                // check if the user is in the database

                // TODO hacer que si no se logea con un timeout de 10 segundos pare de intentar y no acceda.


                for (Usuario usuario : usuarios) {


                    if (usuario.getNombre().equals(loginUsernameField.getText()) && usuario.getContrasena().equals(loginPasswordField.getText())) {

                        MySQL.getInstance().disconnect();

                        isLogged = true;

                        new MenuGeneral(usuario.Es_admin(), usuario.getNombre()).setVisible(true);

                        dispose();

                        messageLogging.setMessage("Login successful");


                    }


                }

                if (!isLogged) {

                    messageLogging.setMessage("Login failed");

                }

            }

        };

        return worker;

}

}
