/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Extra.CustomDateFormatter;
import BackEnd.Producto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class Reactivo extends Producto {


    private String formato;
    private String riesgos;
    private String gradoPureza;
    private LocalDate fechaCaducidad;
    private int stockMinimo;


    public Reactivo(int id, int cantidad, String nombre, String localizacion, String ubicacion, String formato,
                    String riesgos, String gradoPureza, LocalDate fechaCaducidad, int stockMinimo) {
        super(id, cantidad, nombre, localizacion, ubicacion);
        this.formato = formato;
        this.riesgos = riesgos;
        this.gradoPureza = gradoPureza;
        this.fechaCaducidad = fechaCaducidad;
        this.stockMinimo = stockMinimo;
    }

    public Reactivo(int id) {
        super(id);
    }


    public Reactivo(){

    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(String riesgos) {
        this.riesgos = riesgos;
    }

    public String getGradoPureza() {
        return gradoPureza;
    }

    public void setGradoPureza(String gradoPureza) {
        this.gradoPureza = gradoPureza;
    }

    public LocalDate getFechaCaducidad() {

        return fechaCaducidad;

    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String[] getAllAttributesNamesString() {
        return super.getAllAttributesNamesString(); // Llamada al método del padre
    }

    public Object getValueForAttribute(String attributeName) {
        // Llama al método de la clase Productos para obtener los valores de los atributos heredados
        Object value = super.getValueForAttribute(attributeName);

        // Si el valor no es null, significa que el atributo está definido en Productos

        if (value != null) {
            return value;
        }

        // Si el atributo no está definido en Productos, busca en los atributos específicos de Reactivos

        switch (attributeName) {
            case "formato":
                return this.formato;
            case "riesgos":
                return this.riesgos;
            case "gradoPureza":
                return this.gradoPureza;
            case "fechaCaducidad":
                return this.fechaCaducidad;
            case "stockMinimo":
                return this.stockMinimo;
        }

        return null;


    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public int getCantidad() {
        return super.getCantidad();
    }

    @Override
    public String getLocalizacion() {
        return super.getLocalizacion();
    }

    @Override
    public String getUbicacion() {
        return super.getUbicacion();
    }





}
