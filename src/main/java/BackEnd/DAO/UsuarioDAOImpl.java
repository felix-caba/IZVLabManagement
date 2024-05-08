/*
 * @AUTHOR Felix
 */

package BackEnd.DAO;

import BackEnd.MySQL;
import BackEnd.Usuario;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAOImpl implements UsuarioDAO{

    // singleton

    private static UsuarioDAOImpl instance;

    private UsuarioDAOImpl(){

    }

    public static UsuarioDAOImpl getInstance(){

        if(instance == null){
            instance = new UsuarioDAOImpl();
        }

        return instance;
    }

    @Override
    public void insert(Usuario usuario) {

        String nombre = usuario.getNombre();
        String contrasena = usuario.getContrasena();
        boolean es_admin = usuario.Es_admin();

    }

    @Override
    public void update(Usuario usuario) {

    }

    @Override
    public void delete(Usuario usuario) {

    }

    @Override
    public boolean checkAdmin(Usuario usuario) {



        return usuario.Es_admin();

    }

    @Override
    public ArrayList<Usuario> select() {


        ResultSet rs = MySQL.getInstance().getTable("usuarios");

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            while (rs.next()) {
                String nombre = rs.getString("nombre_usuario");
                String contrasena = rs.getString("contrasena");
                boolean es_admin = rs.getBoolean("es_admin");
                Usuario usuario = new Usuario(nombre, contrasena, es_admin);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;

    }


}
