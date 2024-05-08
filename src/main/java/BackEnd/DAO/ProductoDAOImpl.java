/*
 * @AUTHOR Felix
 */

package BackEnd.DAO;

import BackEnd.MySQL;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAOImpl implements ProductoDAO{
    @Override
    public void insert(Producto producto) {



    }

    @Override
    public void update(Producto producto) {



    }

    @Override
    public void delete(Producto producto) {

    }

    @Override
    public ArrayList<Producto> selectPType(TYPE type) {
        ResultSet rs = null;
        ArrayList<Producto> productos = new ArrayList<>();

        try{

            switch (type) {
                case REACTIVO:
                    rs = getResultSet("reactivos");
                    productos.addAll(getReactivos(rs));
                    break;
                case AUXILIAR:
                    rs = getResultSet("prod_aux");
                    productos.addAll(getAuxiliares(rs));
                    break;
                case MATERIAL:
                    rs = getResultSet("materiales");
                    productos.addAll(getMateriales(rs));
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return productos;
    }

    private ResultSet getResultSet(String tableName) {
        return MySQL.getInstance().getTable(tableName);
    }



    @Override
    public ArrayList<Producto> selectAllProducts() {
        return null;
    }

    private ArrayList<Producto> getReactivos(ResultSet rs) throws SQLException {
        ArrayList<Producto> reactivos = new ArrayList<>();
        while (rs.next()) {

            Reactivo reactivo = new Reactivo();
            reactivo.setNombre(rs.getString("Nombre"));
            reactivo.setCantidad(rs.getInt("Cantidad"));
            reactivo.setLocalizacion(rs.getString("Localizacion"));
            reactivo.setUbicacion(rs.getString("Ubicacion"));
            reactivo.setRiesgos(rs.getString("Riesgos"));
            reactivo.setGradoPureza(rs.getString("GradoPureza"));
            reactivo.setFechaCaducidad(rs.getString("FechaCaducidad"));
            reactivo.setStockMinimo(rs.getInt("StockMinimo"));
            reactivos.add(reactivo);

        }
        return reactivos;
    }

    private ArrayList<Producto> getAuxiliares(ResultSet rs) throws SQLException {
        ArrayList<Producto> auxiliares = new ArrayList<>();
        while (rs.next()) {

            Auxiliar auxiliar = new Auxiliar();
            auxiliar.setNombre(rs.getString("Nombre"));
            auxiliar.setFormato(rs.getString("Formato"));
            auxiliar.setCantidad(rs.getInt("Cantidad"));
            auxiliar.setLocalizacion(rs.getString("Localizacion"));
            auxiliar.setUbicacion(rs.getString("Ubicacion"));
            auxiliares.add(auxiliar);

        }
        return auxiliares;
    }

    private ArrayList<Producto> getMateriales(ResultSet rs) throws SQLException {
        ArrayList<Producto> materiales = new ArrayList<>();
        while (rs.next()) {
            Material material = new Material();
            material.setNombre(rs.getString("Nombre"));
            material.setCantidad(rs.getInt("Cantidad"));
            material.setLocalizacion(rs.getString("Localizacion"));
            material.setUbicacion(rs.getString("Ubicacion"));
            material.setSubcategoria(rs.getString("Subcategoria"));
            material.setDescripcion(rs.getString("Descripcion"));
            material.setNserie(rs.getString("Nserie"));
            material.setFechaCompra(rs.getString("FechaDeCompra"));
            materiales.add(material);
        }
        return materiales;
    }


}
