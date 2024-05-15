/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.DAO.Impl;

import BackEnd.DAO.UsuarioDAO;
import BackEnd.MySQL;
import BackEnd.Producto;
import BackEnd.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAOImpl implements UsuarioDAO {

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
        int id = usuario.getId();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        String query = "INSERT INTO usuarios (id, nombre_usuario, contrasena, es_admin) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            pstmt = sql.getConnection().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, contrasena);
            pstmt.setBoolean(4, es_admin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql.disconnect();

    }

    @Override
    public void update(Usuario usuario) {

        String nombre = usuario.getNombre();
        String contrasena = usuario.getContrasena();
        boolean es_admin = usuario.Es_admin();
        int id = usuario.getId();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        String query = "UPDATE usuarios SET nombre_usuario = ?, contrasena = ?, es_admin = ? WHERE id = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = sql.getConnection().prepareStatement(query);
            pstmt.setString(1, nombre);
            pstmt.setString(2, contrasena);
            pstmt.setBoolean(3, es_admin);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Usuario usuario) {

        int id = usuario.getId();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        String query = "DELETE FROM usuarios WHERE id = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = sql.getConnection().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkAdmin(Usuario usuario) {
        return usuario.Es_admin();
    }

    @Override
    public ArrayList<Usuario> select()  {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {

            usuarios.addAll(getUsuarios());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return usuarios;

    }


    private ArrayList<Usuario> getUsuarios() throws SQLException {

        MySQL sql = MySQL.getInstance();
        sql.connect();

        ResultSet rs = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuarios";

        PreparedStatement pstmt = sql.getConnection().prepareStatement(query);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre_usuario");
            String contrasena = rs.getString("contrasena");
            boolean es_admin = rs.getBoolean("es_admin");
            int id = rs.getInt("id");
            Usuario usuario = new Usuario(nombre, contrasena, es_admin, id);
            usuarios.add(usuario);
        }

        sql.disconnect();

        return usuarios;

    }







}
