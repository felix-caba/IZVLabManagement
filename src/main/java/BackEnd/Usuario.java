/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd;

public class Usuario {

    private String nombre;

    private String contrasena;

    private boolean es_admin;

    private int id;

    public Usuario(String nombre, String contrasena, boolean es_admin, int id) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.es_admin = es_admin;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean Es_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    public boolean getEs_admin() {
        return es_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", es_admin=" + es_admin +
                ", id=" + id +
                '}';
    }
}
