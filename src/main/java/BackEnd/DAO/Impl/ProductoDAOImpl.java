/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

/*
 * @AUTHOR Felix
 */

package BackEnd.DAO.Impl;

import BackEnd.DAO.ProductoDAO;
import BackEnd.Extra.TYPE;
import BackEnd.Extra.CustomDateFormatter;
import BackEnd.MySQL;
import BackEnd.Producto;
import BackEnd.Productos.Auxiliar;
import BackEnd.Productos.Material;
import BackEnd.Productos.Reactivo;
import FrontEnd.LoadingFrame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {


    @Override
    public void insert(Producto producto) {

        if (producto instanceof Reactivo) {

            try {


                insertReactivo((Reactivo) producto);



            } catch (SQLException e) {

                LoadingFrame dialog = new LoadingFrame();


                if (e.getErrorCode() == 1062) {

                    dialog.setMessage("Un producto con ese mismo ID ya existe en la BD");

                }


            }


        } else if (producto instanceof Auxiliar) {

        } else if (producto instanceof Material) {

        }


    }

    @Override
    public void update(Producto producto) {


        if (producto instanceof Reactivo) {

            try {
                updateReactivo((Reactivo) producto);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        } else if (producto instanceof Auxiliar) {



        } else if (producto instanceof Material) {

        }


    }

    @Override
    public void delete(Producto producto) {

        if (producto instanceof Reactivo) {

            try {

                deleteReactivo((Reactivo) producto);

            } catch (SQLException e) {

                e.printStackTrace();

            }

        } else if (producto instanceof Auxiliar) {



        } else if (producto instanceof Material) {

        }

    }

    @Override
    public ArrayList<Producto> selectPType(TYPE type) {

        ResultSet rs = null;
        ArrayList<Producto> productos = new ArrayList<>();

        try{

            switch (type) {
                case REACTIVOS:
                    productos.addAll(getReactivos());
                    break;
                case PROD_AUX:
                    productos.addAll(getAuxiliares());
                    break;
                case MATERIALES:
                    productos.addAll(getMateriales());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return productos;
    }

    private void deleteReactivo(Reactivo reactivo) throws SQLException{
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlDELETEReactivo = "DELETE FROM reactivos WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlDELETEReactivo)) {
            pstmt.setInt(1, reactivo.getId());
            pstmt.executeUpdate();
        }

        sql.disconnect();

    }

    private void updateReactivo(Reactivo reactivo) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();
        final String sqlUPDATEReactivo ="UPDATE reactivos SET nombre=?, cantidad=?, localizacion=?, " +
                "ubicacion=?, riesgos=?, gradoPureza=?, stockMinimo=?, formato=?, fechaCaducidad=? WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlUPDATEReactivo)) {
            pstmt.setString(1, reactivo.getNombre());
            pstmt.setInt(2, reactivo.getCantidad());
            pstmt.setString(3, reactivo.getLocalizacion());
            pstmt.setString(4, reactivo.getUbicacion());
            pstmt.setString(5, reactivo.getRiesgos());
            pstmt.setString(6, reactivo.getGradoPureza());
            pstmt.setInt(7, reactivo.getStockMinimo());
            pstmt.setString(8, reactivo.getFormato());
            pstmt.setString(9, CustomDateFormatter.convertToString(reactivo.getFechaCaducidad()));
            pstmt.setInt(10, reactivo.getId());
            pstmt.executeUpdate();
        }
        sql.disconnect();
    }

    private void insertReactivo(Reactivo reactivo) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlINSERTReactivos = "INSERT INTO reactivos (nombre, cantidad, localizacion, ubicacion, riesgos, gradoPureza, " +
                "stockMinimo, formato, fechaCaducidad, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlINSERTReactivos)) {
            pstmt.setString(1, reactivo.getNombre());
            pstmt.setInt(2, reactivo.getCantidad());
            pstmt.setString(3, reactivo.getLocalizacion());
            pstmt.setString(4, reactivo.getUbicacion());
            pstmt.setString(5, reactivo.getRiesgos());
            pstmt.setString(6, reactivo.getGradoPureza());
            pstmt.setInt(7, reactivo.getStockMinimo());
            pstmt.setString(8, reactivo.getFormato());
            pstmt.setString(9, CustomDateFormatter.convertToString(reactivo.getFechaCaducidad()));
            pstmt.setInt(10, reactivo.getId());
            pstmt.executeUpdate();
        }

        sql.disconnect();
    }


    private ArrayList<Producto> getReactivos() throws SQLException {

        ArrayList<Producto> reactivos = new ArrayList<>();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        ResultSet rs = null;
        String query = "SELECT * FROM reactivos";



        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();


        while (rs.next()) {
            Reactivo reactivo = new Reactivo();

            reactivo.setNombre(rs.getString("Nombre"));
            reactivo.setCantidad(rs.getInt("Cantidad"));
            reactivo.setLocalizacion(rs.getString("Localizacion"));
            reactivo.setUbicacion(rs.getString("Ubicacion"));
            reactivo.setRiesgos(rs.getString("Riesgos"));
            reactivo.setGradoPureza(rs.getString("GradoPureza"));
            reactivo.setStockMinimo(rs.getInt("StockMinimo"));
            reactivo.setFormato(rs.getString("Formato"));
            reactivo.setId(rs.getInt("id"));
            reactivo.setFechaCaducidad(CustomDateFormatter.formatear(rs.getString("FechaCaducidad")));
            reactivos.add(reactivo);

        }



        sql.disconnect();

        return reactivos;
    }

    private ArrayList<Producto> getAuxiliares() throws SQLException {

        ArrayList<Producto> auxiliares = new ArrayList<>();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        ResultSet rs = null;

        String query = "SELECT * FROM auxiliares";

        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();



        while (rs.next()) {

            Auxiliar auxiliar = new Auxiliar();
            auxiliar.setNombre(rs.getString("Nombre"));
            auxiliar.setFormato(rs.getString("Formato"));
            auxiliar.setCantidad(rs.getInt("Cantidad"));
            auxiliar.setLocalizacion(rs.getString("Localizacion"));
            auxiliar.setUbicacion(rs.getString("Ubicacion"));
            auxiliar.setId(rs.getInt("id"));
            auxiliares.add(auxiliar);

        }



        MySQL.getInstance().disconnect();

        return auxiliares;
    }

    private ArrayList<Producto> getMateriales() throws SQLException {

        ArrayList<Producto> materiales = new ArrayList<>();

        MySQL sql = MySQL.getInstance();

        ResultSet rs = null;
        String query = "SELECT * FROM materiales";

        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        rs = ps.executeQuery();
        while (rs.next()) {
            Material material = new Material();
            material.setNombre(rs.getString("Nombre"));
            material.setCantidad(rs.getInt("Cantidad"));
            material.setLocalizacion(rs.getString("Localizacion"));
            material.setUbicacion(rs.getString("Ubicacion"));
            material.setSubcategoria(rs.getString("Subcategoria"));
            material.setDescripcion(rs.getString("Descripcion"));
            material.setNserie(rs.getString("Nserie"));

            material.setFechaCompra(CustomDateFormatter.formatear(rs.getString("FechaDeCompra")));


            material.setId(rs.getInt("id"));
            materiales.add(material);
        }



        MySQL.getInstance().disconnect();

        return materiales;
    }





}
