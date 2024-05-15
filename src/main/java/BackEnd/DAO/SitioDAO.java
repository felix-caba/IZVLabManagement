/*
 * @AUTHOR Felix
 */

package BackEnd.DAO;

import BackEnd.Extra.TYPE;
import BackEnd.Sitio;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface SitioDAO {

    void insertarSitio(Sitio sitio);
    void modificarSitio(Sitio sitio);
    void eliminarSitio(Sitio sitio);
    ArrayList<Sitio> getSitios(TYPE type);

}
