/*
 * @AUTHOR Felix
 */

package BackEnd;

public abstract class Sitio {

    private int id;
    private String nombre;


    public Sitio(int id, String nombre) {

        this.id = id;
        this.nombre = nombre;

    }

    public Sitio() {

    }



    public Object getValueForAttribute(String nombreColumna) {

        return switch (nombreColumna) {

            case "id" -> this.getId();
            case "nombre" -> this.getNombre();

            default -> null;

        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }






}
