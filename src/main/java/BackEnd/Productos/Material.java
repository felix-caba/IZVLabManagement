/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Producto;

import java.time.LocalDate;
import java.util.Date;

public class Material extends Producto {

    private String subcategoria;
    private String descripcion;
    private LocalDate fechaCompra;
    private int stockMinimo;
    private String Nserie;

    public Material(int id, int cantidad, String nombre, String localizacion, String ubicacion,
                    String subcategoria, String descripcion, LocalDate fechaCompra, String Nserie, int stockMinimo) {

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

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getNserie() {
        return Nserie;
    }

    public void setNserie(String nserie) {
        Nserie = nserie;
    }






}
