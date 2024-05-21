/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package FrontEnd.Auxiliares;

import BackEnd.DAO.Impl.UsuarioDAOImpl;
import BackEnd.MySQL;
import BackEnd.Usuario;
import FrontEnd.Menu.MenuDeBusqueda;
import FrontEnd.Menu.MenuDeInicio;
import FrontEnd.ElementosSwing.PanelRound;
import FrontEnd.Themeable;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LoginPanel extends JFrame implements Themeable {

    private PanelRound panelWindowLoginIn;
    private JPanel panelWindowLogin;
    private JButton loginButton;
    private JTextField loginUsernameField;
    private JTextField loginPasswordField;
    private JButton backButton;
    private JPasswordField passwordField1;
    private final LoadingFrame dialog = LoadingFrame.getInstance();

    public LoginPanel() {

        MySQL sql = MySQL.getInstance();
        sql.addPropertyChangeListener(dialog);

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
                new MenuDeInicio().setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                dialog.setVisible(true);
                loginWorker(dialog).execute();


            }


        });
        passwordField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public void initComponents() {

        /*Tamaño de la ventana y posicion*/
        setMinimumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Login Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelWindowLogin);
        pack();

    }






    public SwingWorker<Void, Void> loginWorker(LoadingFrame frame) {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            UsuarioDAOImpl usuarioDAO = UsuarioDAOImpl.getInstance();
            private ArrayList<Usuario> usuarios;
            String loggedMessage;

            @Override
            protected Void doInBackground() throws Exception {
                usuarios = usuarioDAO.select();
                return null;
            }

            @Override
            protected void done() {

                if (usuarios != null) {
                    boolean usuarioEncontrado = false;

                    for (Usuario usuario : usuarios) {

                        if (usuario.getNombre().equals(loginUsernameField.getText()) && usuario.getContrasena().equals(passwordField1.getText())) {

                            loggedMessage = "Bienvenido " + usuario.getNombre();
                            new MenuDeBusqueda(usuario.Es_admin(), usuario.getNombre()).setVisible(true);
                            dispose();
                            usuarioEncontrado = true;

                            frame.onSucess(loggedMessage);

                            break;

                        }
                    }
                    if (!usuarioEncontrado) {
                        loggedMessage = "Usuario o contraseña incorrectos";

                        frame.onFail(loggedMessage);

                    }
                }
            }

        };

        return worker;

}

}
