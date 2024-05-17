/*
 * @AUTHOR Felix
 */

package BackEnd.DAO.Impl;

import BackEnd.DAO.SitioDAO;
import BackEnd.Extra.TYPE;
import BackEnd.MySQL;
import BackEnd.SQLBroadcaster;
import BackEnd.Sitio;
import BackEnd.Sitios.Localizacion;
import BackEnd.Sitios.Ubicacion;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SitioDAOImpl implements SitioDAO, SQLBroadcaster {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    MySQL sql = MySQL.getInstance();


    @Override
    public void insertarSitio(Sitio sitio) {

        sql.connect();

        if (sitio instanceof Localizacion) {

            insertarLocalizacion((Localizacion) sitio);
        } else {
            insertarUbicacion((Ubicacion) sitio);
        }

        sql.disconnect();

    }

    private void insertarLocalizacion(Localizacion localizacion) {

        String query = "INSERT INTO localizacion (nombre) VALUES (?)";

        try {

            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setString(1, localizacion.getNombre());
            ps.executeUpdate();

        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }

    }

    private void insertarUbicacion(Ubicacion ubicacion) {

        String query = "INSERT INTO ubicacion (nombre, localizacion_ID) VALUES (?, ?)";



        try {

            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setString(1, ubicacion.getNombre());
            ps.setInt(2, ubicacion.getLocalizacionID());
            ps.executeUpdate();

        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }

    }



    @Override
    public void modificarSitio(Sitio sitio) {
            sql.connect();

            if (sitio instanceof Localizacion) {
                modificarLocalizacion((Localizacion) sitio);
            } else {
                modificarUbicacion((Ubicacion) sitio);
            }

            sql.disconnect();
    }

    private void modificarLocalizacion(Localizacion localizacion) {

        String query = "UPDATE localizacion SET nombre = ? WHERE id = ?";
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setString(1, localizacion.getNombre());
            ps.setInt(2, localizacion.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }

    }

    private void modificarUbicacion(Ubicacion ubicacion) {
        String query = "UPDATE ubicacion SET nombre = ?, localizacion_ID = ? WHERE id = ?";
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setString(1, ubicacion.getNombre());
            ps.setInt(2, ubicacion.getLocalizacionID());
            ps.setInt(3, ubicacion.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }
    }






    @Override
    public void eliminarSitio(Sitio sitio) {

            sql.connect();

            if (sitio instanceof Localizacion) {
                eliminarLocalizacion((Localizacion) sitio);
            } else {
                eliminarUbicacion((Ubicacion) sitio);
            }
            sql.disconnect();
    }

    private void eliminarLocalizacion(Localizacion localizacion) {

        String query = "DELETE FROM localizacion WHERE id = ?";

        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setInt(1, localizacion.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }

    }

    private void eliminarUbicacion(Ubicacion ubicacion) {

        String query = "DELETE FROM ubicacion WHERE id = ?";

        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(query);
            ps.setInt(1, ubicacion.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            sendBroadcast(e.getMessage());
        }

    }



    @Override
    public ArrayList<Sitio> getSitios(TYPE type) {

        sql.connect();

        ArrayList<Sitio> sitios = new ArrayList<>();

        try {

            if (type == TYPE.LOCALIZACION) {
                sitios.addAll(getLocalizaciones());
            } else {
                sitios.addAll(getUbicaciones());
            }

        } catch (SQLException e) {

            sendBroadcast(e.getMessage());

        }

        sql.disconnect();
        return sitios;

    }

    private ArrayList<Sitio> getLocalizaciones() throws SQLException {

        ArrayList<Sitio> localizaciones = new ArrayList<>();

        String query = "SELECT * FROM localizacion";

        ResultSet rs = null;
        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();


        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("nombre"));
            Localizacion localizacion = new Localizacion();
            localizacion.setId(rs.getInt("id"));
            localizacion.setNombre(rs.getString("nombre"));
            localizaciones.add(localizacion);

    }

        return localizaciones;

    }

    private ArrayList<Sitio> getUbicaciones() throws SQLException {

        ArrayList<Sitio> ubicaciones = new ArrayList<>();

        String query = "SELECT U.id, U.nombre, U.localizacion_ID, L.nombre AS nombre_localizacion " +
                "FROM ubicacion U " +
                "INNER JOIN localizacion L ON U.localizacion_ID = L.ID";

        ResultSet rs = null;
        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();

        while (rs.next()){


            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setId(rs.getInt("id"));
            ubicacion.setNombre(rs.getString("nombre"));
            ubicacion.setLocalizacionID(rs.getInt("localizacion_id"));
            ubicacion.setNombreLocalizacion(rs.getString("nombre_localizacion"));
            ubicaciones.add(ubicacion);

        }

        return ubicaciones;

    }


    @Override
    public void sendBroadcast(String message) {

        pcs.firePropertyChange("SQL", null, message);

    }



}


