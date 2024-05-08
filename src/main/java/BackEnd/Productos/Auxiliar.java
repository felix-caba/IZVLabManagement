/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Producto;

public class Auxiliar extends Producto {

    private String formato;

    public Auxiliar(int id, int cantidad, String nombre, String localizacion, String ubicacion, String formato) {
        super(id, cantidad, nombre, localizacion, ubicacion);
        this.formato = formato;
    }

    public Auxiliar() {

    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}

