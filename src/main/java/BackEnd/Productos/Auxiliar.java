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

    @Override
    public Object getValueForAttribute(String attributeName) {

        return switch (attributeName) {

            case "id" -> this.getId();
            case "cantidad" -> this.getCantidad();
            case "nombre" -> this.getNombre();
            case "localizacion" -> this.getLocalizacion();
            case "ubicacion" -> this.getUbicacion();
            case "formato" -> this.formato;
            default -> throw new IllegalArgumentException("Atributo desconocido: " + attributeName);

        };
    }

    @Override
    public Producto getProductFromRow(Object[] row) {
        return null;
    }



    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }




}

