/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import BackEnd.Producto;

public class TableChange {

    public enum ChangeType{
        INSERT,
        DELETE,
        UPDATE
    }

    private ChangeType changeType;
    private Producto producto;

    public TableChange(ChangeType changeType, Producto producto) {
        this.changeType = changeType;
        this.producto = producto;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public Producto getProducto() {
        return producto;
    }




}
