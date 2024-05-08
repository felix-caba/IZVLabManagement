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

    public Usuario(String nombre, String contrasena, boolean es_admin) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.es_admin = es_admin;
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
}
