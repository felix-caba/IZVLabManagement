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
import BackEnd.SQLBroadcaster;
import FrontEnd.LoadingFrame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAOImpl implements ProductoDAO, SQLBroadcaster {

    private String sqlBROADCAST;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void sendBroadcast(String message) {

        this.sqlBROADCAST = message;
        pcs.firePropertyChange("sqlBROADCAST", null, message);

    }


    public void loadTable (String path, TYPE type) {

        MySQL sql = MySQL.getInstance();
        sql.connect();

        sql.loadCSV(path, type.toString().toLowerCase());

        sql.disconnect();

    }





    @Override
    public void insert(Producto producto) {


        if (producto instanceof Reactivo) {

            try {
                insertReactivo((Reactivo) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }


        } else if (producto instanceof Auxiliar) {

            try {
                insertAuxiliar((Auxiliar) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }


        } else if (producto instanceof Material) {

            try {
                insertMaterial((Material) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }

        }


    }

    @Override
    public void update(Producto producto) {




        if (producto instanceof Reactivo) {

            try {
                updateReactivo((Reactivo) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }


        } else if (producto instanceof Auxiliar) {

            try {
                updateAuxiliar((Auxiliar) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }


        } else if (producto instanceof Material) {

            try {
                updateMaterial((Material) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }

        }


    }

    @Override
    public void delete(Producto producto) {



        if (producto instanceof Reactivo) {

            try {
                deleteReactivo((Reactivo) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }

        } else if (producto instanceof Auxiliar) {

            try {
                deleteAuxiliar((Auxiliar) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }

        } else if (producto instanceof Material) {

            try {
                deleteMaterial((Material) producto);
            } catch (SQLException e) {
                sendBroadcast(e.getMessage());
            }

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
                case AUXILIARES:
                    productos.addAll(getAuxiliares());
                    break;
                case MATERIALES:
                    productos.addAll(getMateriales());
                    break;
            }
        } catch (SQLException e) {

            sendBroadcast(e.getMessage());

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

    private void deleteAuxiliar(Auxiliar auxiliar) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlDELETEAuxiliar = "DELETE FROM auxiliares WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlDELETEAuxiliar)) {
            pstmt.setInt(1, auxiliar.getId());
            pstmt.executeUpdate();
        }

        sql.disconnect();
    }

    private void updateAuxiliar(Auxiliar auxiliar) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();
        final String sqlUPDATEAuxiliar = "UPDATE auxiliares SET nombre=?, cantidad=?, localizacion=?, ubicacion=?, formato=? WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlUPDATEAuxiliar)) {
            pstmt.setString(1, auxiliar.getNombre());
            pstmt.setInt(2, auxiliar.getCantidad());
            pstmt.setString(3, auxiliar.getLocalizacion());
            pstmt.setString(4, auxiliar.getUbicacion());
            pstmt.setString(5, auxiliar.getFormato());
            pstmt.setInt(6, auxiliar.getId());
            pstmt.executeUpdate();
        }
        sql.disconnect();
    }

    private void insertAuxiliar(Auxiliar auxiliar) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlINSERTAuxiliar = "INSERT INTO auxiliares (nombre, cantidad, localizacion, ubicacion, formato, id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlINSERTAuxiliar)) {
            pstmt.setString(1, auxiliar.getNombre());
            pstmt.setInt(2, auxiliar.getCantidad());
            pstmt.setString(3, auxiliar.getLocalizacion());
            pstmt.setString(4, auxiliar.getUbicacion());
            pstmt.setString(5, auxiliar.getFormato());
            pstmt.setInt(6, auxiliar.getId());
            pstmt.executeUpdate();
        }

        sql.disconnect();
    }

    private void deleteMaterial(Material material) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlDELETEMaterial = "DELETE FROM materiales WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlDELETEMaterial)) {
            pstmt.setInt(1, material.getId());
            pstmt.executeUpdate();
        }

        sql.disconnect();
    }

    private void updateMaterial(Material material) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();
        final String sqlUPDATEMaterial = "UPDATE materiales SET nombre=?, cantidad=?, localizacion=?, ubicacion=?, subcategoria=?, descripcion=?, fechaDeCompra=?, Nserie=? WHERE id = ?";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlUPDATEMaterial)) {
            pstmt.setString(1, material.getNombre());
            pstmt.setInt(2, material.getCantidad());
            pstmt.setString(3, material.getLocalizacion());
            pstmt.setString(4, material.getUbicacion());
            pstmt.setString(5, material.getSubcategoria());
            pstmt.setString(6, material.getDescripcion());
            pstmt.setString(7, CustomDateFormatter.convertToString(material.getFechaCompra()));
            pstmt.setString(8, material.getNserie());
            pstmt.setInt(9, material.getId());
            pstmt.executeUpdate();
        }
        sql.disconnect();
    }

    private void insertMaterial(Material material) throws SQLException {
        MySQL sql = MySQL.getInstance();
        sql.connect();

        final String sqlINSERTMaterial = "INSERT INTO materiales (nombre, cantidad, localizacion, ubicacion, subcategoria, descripcion, fechaDeCompra, Nserie, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = sql.getConnection().prepareStatement(sqlINSERTMaterial)) {
            pstmt.setString(1, material.getNombre());
            pstmt.setInt(2, material.getCantidad());
            pstmt.setString(3, material.getLocalizacion());
            pstmt.setString(4, material.getUbicacion());
            pstmt.setString(5, material.getSubcategoria());
            pstmt.setString(6, material.getDescripcion());
            pstmt.setString(7, CustomDateFormatter.convertToString(material.getFechaCompra()));
            pstmt.setString(8, material.getNserie());
            pstmt.setInt(9, material.getId());
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



        sql.disconnect();

        return auxiliares;
    }

    private ArrayList<Producto> getMateriales() throws SQLException {

        ArrayList<Producto> materiales = new ArrayList<>();

        MySQL sql = MySQL.getInstance();
        sql.connect();

        ResultSet rs = null;
        String query = "SELECT * FROM materiales";

        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        rs = ps.executeQuery();


        while (rs.next()) {
            System.out.println("ENTRANDO EN EL WHILE");
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

            System.out.println(material.getNombre());
            materiales.add(material);


        }

        sql.disconnect();


        return materiales;
    }

    private String filterString(String string) {
        if (string == null) {
            return ""; // o cualquier otro valor predeterminado que desees
        }
        return string.replaceAll("(?i)No viene reflejad[oa]", "");
    }



}
