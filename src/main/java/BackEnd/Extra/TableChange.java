/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import BackEnd.Producto;
import BackEnd.Productos.Reactivo;
import BackEnd.Sitio;
import BackEnd.Usuario;

public class TableChange<T> {

    public Object getObject() {
        return object;
    }

    // CON LA T ACEPTAR CUALQUIER OBJETO COMO PARAMETRIZADO. LO QUE SEA

    public enum ChangeType{
        INSERT,
        DELETE,
        UPDATE
    }

    private ChangeType changeType;
    private Producto producto;
    private Usuario usuario;
    private Sitio sitio;

    private T object;

    public TableChange(ChangeType changeType, Producto producto) {
        this.changeType = changeType;
        this.producto = producto;
    }

    public TableChange(ChangeType changeType, Usuario usuario) {
        this.changeType = changeType;
        this.usuario = usuario;

    }

    public TableChange(ChangeType changeType, Sitio sitio) {
        this.changeType = changeType;
        this.sitio = sitio;
    }

    public TableChange(ChangeType changeType, T object) {
        this.changeType = changeType;
        this.object = object;
    }



    public ChangeType getChangeType() {
        return changeType;
    }

    public Producto getProducto() {
        // Gets actual Object and casts it to Producto
        return (Producto) object;
    }

    public Usuario getUsuario() {
        return (Usuario) object;
    }

    public Sitio getSitio() {
        return (Sitio) object;
    }


    @Override
    public String toString() {
        return "TableChange{" +
                "object=" + object.toString().toString() +
                ", changeType=" + changeType +
                '}';
    }

    public String objectToReactivo() {

        // cast object to Reactivo

        Reactivo reactivo = (Reactivo) object;
        return reactivo.toString();

    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }



    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }
}
