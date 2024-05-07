/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.Productos;

import BackEnd.Producto;

public class Reactivo extends Producto {


    private String formato;
    private String riesgos;
    private String gradoPureza;
    private String fechaCaducidad;
    private int stockMinimo;


    public Reactivo(int id, int cantidad, String nombre, String localizacion, String ubicacion, String formato,
                    String riesgos, String gradoPureza, String fechaCaducidad, int stockMinimo) {
        super(id, cantidad, nombre, localizacion, ubicacion);
        this.formato = formato;
        this.riesgos = riesgos;
        this.gradoPureza = gradoPureza;
        this.fechaCaducidad = fechaCaducidad;
        this.stockMinimo = stockMinimo;
    }


}
