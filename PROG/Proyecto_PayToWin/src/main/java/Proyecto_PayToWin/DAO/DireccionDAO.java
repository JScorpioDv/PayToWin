/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Proyecto_PayToWin.DTO.Direccion;
import Proyecto_PayToWin.DTO.TiposDireccion;
import Proyecto_PayToWin.DTO.Usuario;

/**
 *
 * @author jmas
 */
public class DireccionDAO extends TablaDAO<Direccion> {

    public DireccionDAO() {
        this.tabla = "PROYECTO_direccion";
    }

    @Override
    public int actualizar(Direccion d) throws SQLException {
        // NO SE UTILIZA EN NUESTRO PROYECTO
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int anyadir(Direccion d) throws SQLException {
        String sentenciaSQL = "INSERT INTO " + tabla + " VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, d.getCodigo());
        prepared.setString(2, d.getCalle());
        prepared.setString(3, d.getCiudad());
        prepared.setString(4, d.getProvincia());
        prepared.setInt(5, d.getCp());
        prepared.setString(6, d.getPais());
        prepared.setString(7, d.getUsuario().getNombreUsuario());
        
        //Insertar Tipo
        if(d.getTipo() == null){
            prepared.setNull(8, java.sql.Types.VARCHAR);
        } else {
            prepared.setString(8, String.valueOf(d.getTipo()));
        }
        return prepared.executeUpdate();
    }

    @Override
    public Direccion eliminar(Direccion d) throws SQLException {
        if (d == null) {
            return null;
        } else {
            return eliminar(d.getCodigo()) != null ? d : null;
        }
    }

    @Override
    public boolean existe(Direccion d) throws SQLException {
        return existe(d.getCodigo());
    }

    @Override
    public ArrayList<Direccion> getAll() throws SQLException {
        ArrayList<Direccion> lista = new ArrayList<>();
        String sentenciaSQL = "SELECT * FROM " + tabla + " ORDER BY codigo";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo");
            String calle = resultSet.getString("calle");
            String ciudad = resultSet.getString("ciudad");
            String provincia = resultSet.getString("provincia");
            int cp = resultSet.getInt("cp");
            String pais = resultSet.getString("pais");
            Usuario usuario = new UsuarioDAO().getByUsuario(resultSet.getString("usuario"));
            TiposDireccion tipoDireccion = TiposDireccion.valueOf(resultSet.getString("tipo"));
            
            lista.add(new Direccion(codigo, calle, ciudad, provincia, cp, pais, usuario, tipoDireccion));
        }
        return lista;
    }

    @Override
    public Direccion getByCodigo(int codigo) throws SQLException {
        String sentenciaSQL = "SELECT * FROM " + tabla + " WHERE codigo=?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, codigo);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            String calle = resultSet.getString("calle");
            String ciudad = resultSet.getString("ciudad");
            String provincia = resultSet.getString("provincia");
            int cp = resultSet.getInt("cp");
            String pais = resultSet.getString("pais");
            Usuario usuario = new UsuarioDAO().getByUsuario(resultSet.getString("usuario"));
            TiposDireccion tipoDireccion = TiposDireccion.valueOf(resultSet.getString("tipo"));
            
            return new Direccion(codigo, calle, ciudad, provincia, cp, pais, usuario, tipoDireccion);
        }
        return null;
    }

    public ArrayList<Direccion> getDireccionesDe(Usuario u) throws SQLException {
        ArrayList<Direccion> lista = new ArrayList<>();
        String sentenciaSQL = "SELECT * FROM " + tabla + " WHERE usuario=? ORDER BY codigo";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setString(1, u.getNombreUsuario());
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo");
            String calle = resultSet.getString("calle");
            String ciudad = resultSet.getString("ciudad");
            String provincia = resultSet.getString("provincia");
            int cp = resultSet.getInt("cp");
            String pais = resultSet.getString("pais");
            Usuario usuario = new UsuarioDAO().getByUsuario(resultSet.getString("usuario"));
            TiposDireccion tipoDireccion = TiposDireccion.valueOf(resultSet.getString("tipo"));
            
            lista.add(new Direccion(codigo, calle, ciudad, provincia, cp, pais, usuario, tipoDireccion));
        }
        return lista;
    }

}
