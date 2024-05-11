/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.DAO;

import BackEnd.Producto;

import java.util.ArrayList;

public interface ProductoDAO {

    void insert(Producto producto);
    void update(Producto producto);
    void delete(Producto producto);
    ArrayList<Producto> selectPType(TYPE type);
    ArrayList<Producto> selectAllProducts();

}
