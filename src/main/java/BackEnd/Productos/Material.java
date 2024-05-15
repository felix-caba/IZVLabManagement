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


    public Object getValueForAttribute(String attributeName) {

        Object value = super.getValueForAttribute(attributeName);

        if (value != null) {
            return value;
        }

        switch (attributeName) {
            case "subcategoria":
                return this.subcategoria;
            case "descripcion":
                return this.descripcion;
            case "fechaCompra":
                return this.fechaCompra;
            case "Nserie":
                return this.Nserie;
            case "stockMinimo":
                return this.stockMinimo;
            default:
                throw new IllegalArgumentException("Atributo desconocido: " + attributeName);
        }
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

/*
    @Override
    public Material getProductFromRow(Object[] row) {

        Material material = new Material();

        try {
            material.setId((int) row[0]);
            material.setNombre((String) row[1]);
            material.setLocalizacion((String) row[2]);
            material.setUbicacion((String) row[3]);
            material.setCantidad((int) row[4]);
            material.setSubcategoria((String) row[5]);
            material.setDescripcion((String) row[6]);
            material.setFechaCompra((LocalDate) row[7]);
            material.setNserie((String) row[8]);
            material.setStockMinimo((int) row[9]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return material;
    }
*/




}
