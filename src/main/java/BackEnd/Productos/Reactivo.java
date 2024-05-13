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

    @Override
    public String toString() {
        return "Reactivo{" +
                "formato='" + formato + '\'' +
                ", riesgos='" + riesgos + '\'' +
                ", gradoPureza='" + gradoPureza + '\'' +
                ", fechaCaducidad=" + fechaCaducidad +
                ", stockMinimo=" + stockMinimo +
                "} " + super.toString();
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

    @Override
    public Reactivo getProductFromRow(Object[] row) {

        Reactivo reactivo = new Reactivo();

        try {
            reactivo.setId((int) row[0]);
            reactivo.setNombre((String) row[1]);
            reactivo.setUbicacion((String) row[2]);
            reactivo.setLocalizacion((String) row[3]);
            reactivo.setCantidad((int) row[4]);
            reactivo.setFormato((String) row[5]);
            reactivo.setRiesgos((String) row[6]);
            reactivo.setGradoPureza((String) row[7]);
            reactivo.setFechaCaducidad((LocalDate) row[8]);
            reactivo.setStockMinimo((int) row[9]);
        } catch (Exception e) {
            System.out.println("UNO DE LOS VALORES ES NULL Y NO SE PUEDE CONVERTIR");
        }
        return reactivo;
    }


}
