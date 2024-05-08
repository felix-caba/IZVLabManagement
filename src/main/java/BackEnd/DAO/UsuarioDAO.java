/*
 * @AUTHOR Felix
 */

package BackEnd.DAO;

import BackEnd.Usuario;

import java.util.ArrayList;

public interface UsuarioDAO {

    void insert(Usuario usuario);
    void update(Usuario usuario);
    void delete(Usuario usuario);
    boolean checkAdmin(Usuario usuario);

    ArrayList<Usuario> select();


}
