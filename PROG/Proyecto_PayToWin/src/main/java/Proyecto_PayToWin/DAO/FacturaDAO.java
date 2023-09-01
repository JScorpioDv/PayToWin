/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Proyecto_PayToWin.DTO.Direccion;
import Proyecto_PayToWin.DTO.Factura;
import Proyecto_PayToWin.DTO.Pedido;

/**
 *
 * @author jrmd
 */
public class FacturaDAO extends TablaDAO<Factura>{

    public FacturaDAO() {
        this.tabla = "PROYECTO_factura";
    }

    @Override
    public int actualizar(Factura f) throws SQLException {
        //No necesario para el proyecto
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int anyadir(Factura f) throws SQLException {
        String sentenciaSQL = "INSERT INTO " + tabla + " VALUES(?,?,?,?,?)";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, f.getCodigo());
        prepared.setTimestamp(2, Timestamp.valueOf(f.getFecha()));
        prepared.setInt(3, f.getIva());
        prepared.setInt(4, f.getPedido().getCodigo());
        prepared.setInt(5, f.getDirecFactura().getCodigo());
        return prepared.executeUpdate();

    }

    @Override
    public Factura eliminar(Factura f) throws SQLException {
        if (f == null) {
            return null;
        } else {
            return eliminar(f.getCodigo()) != null ? f : null;
        }
    }

    @Override
    public boolean existe(Factura f) throws SQLException {
        return existe(f.getCodigo());
    }

    @Override
    public ArrayList<Factura> getAll() throws SQLException {
        ArrayList<Factura> lista = new ArrayList<>();
        String sentenciaSQL = "SELECT * FROM " + tabla + " ORDER BY codigo";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo"); 
            LocalDateTime fecha = resultSet.getTimestamp("fecha").toLocalDateTime();
            int iva = resultSet.getInt("iva");
            Pedido pedidoAsociado = new PedidoDAO().getByCodigo(resultSet.getInt("pedido"));
            Direccion direccion = new DireccionDAO().getByCodigo(resultSet.getInt("direcFactura"));
            
            lista.add(new Factura(codigo, fecha, iva, pedidoAsociado, direccion));
        }

        return lista;
    }
    
    public ArrayList<Factura> getByCodigoUsuario(String nombreUsuario){
        try {
            ArrayList<Factura> lista = new ArrayList<>();
            String sentenciaSQL = "SELECT F.*, P.cliente FROM PROYECTO_pedido P, PROYECTO_factura F WHERE P.CODIGO = F.PEDIDO AND P.cliente=?";
            PreparedStatement prepared = getPrepared(sentenciaSQL);
            prepared.setString(1, nombreUsuario);
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                LocalDateTime fecha = resultSet.getTimestamp("fecha").toLocalDateTime();
                int iva = resultSet.getInt("iva");
                Pedido pedidoAsociado = new PedidoDAO().getByCodigo(resultSet.getInt("pedido"));
                Direccion direccion = new DireccionDAO().getByCodigo(resultSet.getInt("direcFactura"));
                lista.add(new Factura(codigo, fecha, iva, pedidoAsociado, direccion));
            }
            
            return lista;
        } catch (SQLException ex) {
            System.out.println("Error SQL");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Factura getByCodigo(int codigo) throws SQLException {
        String sentenciaSQL = "SELECT * FROM " + tabla + " WHERE codigo=?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, codigo);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            LocalDateTime fecha = resultSet.getTimestamp("fecha").toLocalDateTime();
            int iva = resultSet.getInt("iva");
            Pedido pedidoAsociado = new PedidoDAO().getByCodigo(resultSet.getInt("pedido"));
            Direccion direccion = new DireccionDAO().getByCodigo(resultSet.getInt("direcFactura"));
            return new Factura(codigo, fecha, iva, pedidoAsociado, direccion);
        }

        return null;
    }
    
    
    
    
}
