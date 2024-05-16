/*
 * @AUTHOR Felix
 */

package BackEnd.Sitios;

import BackEnd.Sitio;

public class Ubicacion extends Sitio {

    private String nombreLocalizacion;
    private int localizacionID;



    public Ubicacion(int id, String nombre, int localizacionID, String nombreLocalizacion) {
        super(id, nombre);
        this.localizacionID = localizacionID;
        this.nombreLocalizacion = nombreLocalizacion;
    }

    public Ubicacion() {

    }


    public Object getValueForAttribute(String attributeName) {
        // Llama al método de la clase Productos para obtener los valores de los atributos heredados
        Object value = super.getValueForAttribute(attributeName);
        // Si el valor no es null, significa que el atributo está definido en Productos
        if (value != null) {
            return value;
        }
        // Si el atributo no está definido en Productos, busca en los atributos específicos de Reactivos
        switch (attributeName) {
            case "localizacionID":
                return this.localizacionID;
                case "nombreLocalizacion":
                return this.nombreLocalizacion;
        }
        return null;
    }

    public int getLocalizacionID(int localizacionId) {
        return localizacionID;
    }

    public void setLocalizacionID(int localizacionID) {
        this.localizacionID = localizacionID;
    }

    public int getLocalizacionID() {
        return localizacionID;
    }

    public String getNombreLocalizacion() {
        return nombreLocalizacion;
    }

    public void setNombreLocalizacion(String nombreLocalizacion) {
        this.nombreLocalizacion = nombreLocalizacion;
    }
}
