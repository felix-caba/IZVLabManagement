/*
 * @AUTHOR Felix
 */

package BackEnd;

public class Producto {

    private int id;
    private int cantidad;
    private String nombre;
    private String localizacion;
    private String ubicacion;


    public Producto(int id, int cantidad, String nombre, String localizacion, String ubicacion) {

        this.id = id;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.ubicacion = ubicacion;

    }

    public Producto() {


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
}
