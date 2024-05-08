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
    private String Nserie;

    public Material(int id, int cantidad, String nombre, String localizacion, String ubicacion,
                    String subcategoria, String descripcion, String fechaCompra, String Nserie, int stockMinimo) {

        super(id, cantidad, nombre, localizacion, ubicacion);
        this.subcategoria = subcategoria;
        this.descripcion = descripcion;
        this.fechaCompra = fechaCompra;
        this.Nserie = Nserie;
        this.stockMinimo = stockMinimo;
    }


    public Material() {

    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNSerie() {
        return numeroSerie;
    }

    public void setNserie(String Nserie) {
        this.numeroSerie = Nserie;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
}
