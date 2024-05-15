/*
 * @AUTHOR Felix
 */

package BackEnd.Sitios;

import BackEnd.Sitio;

public class Ubicacion extends Sitio {

    private int localizacionID;


    public Ubicacion(int id, String nombre, int localizacionID) {
        super(id, nombre);
        this.localizacionID = localizacionID;
    }

    public Ubicacion() {

    }

    public int getLocalizacionID(int localizacionId) {
        return localizacionID;
    }

    public void setLocalizacionID(int localizacionID) {
        this.localizacionID = localizacionID;
    }



}
