/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import BackEnd.Producto;
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

    private T object;

    public TableChange(ChangeType changeType, Producto producto) {
        this.changeType = changeType;
        this.producto = producto;
    }

    public TableChange(ChangeType changeType, Usuario usuario) {
        this.changeType = changeType;
        this.usuario = usuario;

    }

    public TableChange(ChangeType changeType, T object) {
        this.changeType = changeType;
        this.object = object;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public Producto getProducto() {
        return producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "TableChange{" +
                "changeType=" + changeType +
                ", producto=" + usuario.toString() +
                ", usuario=" + usuario +
                '}';
    }
}
