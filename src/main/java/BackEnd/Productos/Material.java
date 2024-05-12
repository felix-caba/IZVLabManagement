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
        // Llama al método de la clase Productos para obtener los valores de los atributos heredados
        Object value = super.getValueForAttribute(attributeName);

        // Si el valor no es null, significa que el atributo está definido en Productos

        if (value != null) {
            return value;
        }

        // Si el atributo no está definido en Productos, busca en los atributos específicos de Reactivos
        return switch (attributeName) {

            case "subcategoria" -> this.subcategoria;
            case "descripcion" -> this.descripcion;
            case "fechaCompra" -> this.fechaCompra;
            case "Nserie" -> this.Nserie;
            case "stockMinimo" -> this.stockMinimo;

            default -> throw new IllegalArgumentException("Atributo desconocido: " + attributeName);
        };
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


    @Override
    public Producto createProductFromRow(Object[] row) {


          return null;

    }
}
