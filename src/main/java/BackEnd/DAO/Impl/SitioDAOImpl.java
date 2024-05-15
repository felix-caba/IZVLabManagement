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

    }

    @Override
    public void modificarSitio(Sitio sitio) {
            sql.connect();



    }

    @Override
    public void eliminarSitio(Sitio sitio) {
            sql.connect();


    }

    @Override
    public ArrayList<Sitio> getSitios(TYPE type) {


        sql.connect();

        try {

            if (type == TYPE.LOCALIZACION) {

                return getLocalizaciones();

            } else {

                return getUbicaciones();

            }
        } catch (SQLException e) {

            sendBroadcast(e.getMessage());

        }


        return null;

    }

    private ArrayList<Sitio> getLocalizaciones() throws SQLException {

        ArrayList<Sitio> localizaciones = new ArrayList<>();

        String query = "SELECT * FROM localizacion";

        ResultSet rs = null;
        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();


        while (rs.next()){

            Localizacion localizacion = new Localizacion();
            localizacion.setId(rs.getInt("id"));
            localizacion.setNombre(rs.getString("nombre"));

    }

        return localizaciones;

    }

    private ArrayList<Sitio> getUbicaciones() throws SQLException {

        ArrayList<Sitio> ubicaciones = new ArrayList<>();

        String query = "SELECT * FROM ubicacion";

        ResultSet rs = null;
        PreparedStatement ps = sql.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = ps.executeQuery();

        while (rs.next()){
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setId(rs.getInt("id"));
            ubicacion.setNombre(rs.getString("nombre"));
            ubicacion.getLocalizacionID(rs.getInt("localizacion_id"));
        }

        return ubicaciones;

    }




    @Override
    public void sendBroadcast(String message) {

    }



}


