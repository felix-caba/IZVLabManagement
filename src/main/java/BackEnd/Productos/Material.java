/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Producto;

public class Material extends Producto {

    private String subcategoria;
    private String descripcion;
    private String fechaCompra;
    private String numeroSerie;
    private int stockMinimo;

    public Material(int id, int cantidad, String nombre, String localizacion, String ubicacion,
                    String subcategoria, String descripcion, String fechaCompra, String numeroSerie, int stockMinimo) {

        super(id, cantidad, nombre, localizacion, ubicacion);
        this.subcategoria = subcategoria;
        this.descripcion = descripcion;
        this.fechaCompra = fechaCompra;
        this.numeroSerie = numeroSerie;
        this.stockMinimo = stockMinimo;
    }

}
