/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Producto;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Arrays;

public class Auxiliar extends Producto {

    private String formato;

    public Auxiliar(int id, int cantidad, String nombre, String localizacion, String ubicacion, String formato) {
        super(id, cantidad, nombre, localizacion, ubicacion);
        this.formato = formato;
    }

    public Auxiliar() {

    }

    public Object getValueForAttribute(String attributeName) {


        Object value = super.getValueForAttribute(attributeName);


        if (value != null) {
            return value;
        }


        switch (attributeName) {

            case "formato":
                return this.formato;

        }

        return null;

    }


    public String[] getAllAttributesNamesString() {
        return super.getAllAttributesNamesString(); // Llamada al método del padre
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }


}

