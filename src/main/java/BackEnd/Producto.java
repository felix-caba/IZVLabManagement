/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Producto {

    private int id;
    private String nombre;
    private String localizacion;
    private String ubicacion;
    private int cantidad;




    public Producto(int id, int cantidad, String nombre, String localizacion, String ubicacion) {

        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.localizacion = localizacion;
        this.ubicacion = ubicacion;

    }

    public Producto() {

    }

    public Producto(int id) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    // Gets all the attributes names

    public String[] getAllAttributesNamesString() {

        List<String> attributeNames = new ArrayList<>();

        // Nombres de los atributos de la clase padre (Producto)
        Class<?> superClass = this.getClass().getSuperclass();

        while (superClass != null) {
            Field[] superFields = superClass.getDeclaredFields();
            for (Field superField : superFields) {
                attributeNames.add(superField.getName());
            }
            superClass = superClass.getSuperclass();
        }

        // Nombres de los atributos de la clase actual (Reactivo)
        Class<?> currentClass = this.getClass();
        Field[] fields = currentClass.getDeclaredFields();
        for (Field field : fields) {
            attributeNames.add(field.getName());
        }

        return attributeNames.toArray(new String[0]);
    }





    public Object getValueForAttribute(String nombreColumna) {

        return switch (nombreColumna) {


            case "id" -> this.getId();
            case "cantidad" -> this.getCantidad();
            case "nombre" -> this.getNombre();
            case "localizacion" -> this.getLocalizacion();
            case "ubicacion" -> this.getUbicacion();

            default -> null;

        };
    }


    public abstract Producto getProductFromRow(Object[] row);


    protected void setCamposComunes(Object[] row) {

        this.id = (int) row[0];
        this.nombre = (String) row[1];
        this.ubicacion = (String) row[3];
        this.localizacion = (String) row[2];
        this.cantidad = (int) row[4];

    }


}





