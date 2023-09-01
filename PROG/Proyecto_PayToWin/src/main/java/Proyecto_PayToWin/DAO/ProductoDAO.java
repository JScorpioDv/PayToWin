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
import java.util.LinkedHashSet;
import Proyecto_PayToWin.DTO.Categoria;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.Usuario;

/**
 *
 * @author jmas
 */
public class ProductoDAO extends TablaDAO<Producto> {

    public ProductoDAO() {
        this.tabla = "PROYECTO_productos";
    }

    @Override
    public int actualizar(Producto p) throws SQLException {
        String sentenciaSQL = "UPDATE " + tabla + " SET nombre=?, precio=?, iva=?, "
                + "stockMinimo=?, stock=?, foto=?, usuarioCrea=?, fechacrea=?,"
                + "usuarioModif=?, fechamodif=?, descripcion=? WHERE codigo=?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setString(1, p.getNombre());
        prepared.setDouble(2, p.getPrecio());
        prepared.setDouble(3, p.getIva());
        prepared.setInt(4, p.getStockMinimo());
        prepared.setInt(5, p.getStock());
        prepared.setString(6, p.getFoto());
        prepared.setString(7, p.getUsuarioCrea().getNombreUsuario());
        prepared.setTimestamp(8, Timestamp.valueOf(p.getFechaCrea()));
        
        //Comprovar Usuario Modificación
        if(p.getUsuarioModif() == null){
            prepared.setNull(9, java.sql.Types.VARCHAR);
        } else {
            prepared.setString(9, p.getUsuarioModif().getNombreUsuario());
        }
        
        //Comprovar Fecha Modificación
        if(p.getFechaModif() == null){
            prepared.setNull(10, java.sql.Types.TIMESTAMP);
        } else {
            prepared.setTimestamp(10, Timestamp.valueOf(p.getFechaModif()));
        }
        
        prepared.setString(11, p.getDescripcion());
        prepared.setInt(12, p.getCodigo());
        
        int resultado = prepared.executeUpdate();
        if (resultado > 0) {
            eliminarCategorias(p);
            anyadirCategorias(p);
        }
        return resultado;
    }

    @Override
    public int anyadir(Producto p) throws SQLException {
        String sentenciaSQL = "INSERT INTO " + tabla + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, p.getCodigo());
        prepared.setString(2, p.getNombre());
        prepared.setDouble(3, p.getPrecio());
        prepared.setInt(4, p.getIva());
        prepared.setInt(5, p.getStockMinimo());
        prepared.setInt(6, p.getStock());
        prepared.setString(7, p.getFoto());
        prepared.setString(8, p.getUsuarioCrea().getNombreUsuario());
        prepared.setTimestamp(9, Timestamp.valueOf(p.getFechaCrea()));
        
        //Comprovar Usuario Modificación
        if(p.getUsuarioModif() == null){
            prepared.setNull(10, java.sql.Types.VARCHAR);
        } else {
            prepared.setString(10, p.getUsuarioModif().getNombreUsuario());
        }
        
        //Comprovar Fecha Modificación
        if(p.getFechaModif() == null){
            prepared.setNull(11, java.sql.Types.TIMESTAMP);
        } else {
            prepared.setTimestamp(11, Timestamp.valueOf(p.getFechaModif()));
        }
        
        prepared.setString(12, p.getDescripcion());
        
        int resultado = prepared.executeUpdate();
        anyadirCategorias(p);
        return resultado;
    }

    private void anyadirCategorias(Producto p) throws SQLException {
        for (Categoria c : p.getCategoria()) {
            String sentenciaSQL = "INSERT INTO PROYECTO_clasificacionProducto VALUES(?, ?)";
            PreparedStatement prepared = getPrepared(sentenciaSQL);
            prepared.setInt(1, p.getCodigo());
            prepared.setInt(2, c.getCodigo());
            prepared.executeUpdate();
        }
    }

    private void eliminarCategorias(Producto p) throws SQLException {
        String sentenciaSQL = "DELETE FROM PROYECTO_clasificacionProducto WHERE productos=?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, p.getCodigo());
        prepared.executeUpdate();

    }

    @Override
    public Producto eliminar(Producto p) throws SQLException {
        if (p == null) {
            return null;
        } else {
            return eliminar(p.getCodigo()) != null ? p : null;
        }
    }

    @Override
    public boolean existe(Producto p) throws SQLException {
        return existe(p.getCodigo());
    }

    @Override
    public ArrayList<Producto> getAll() throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sentenciaSQL = "SELECT * FROM " + tabla + " ORDER BY codigo";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo");
            String nombre = resultSet.getString("nombre");
            double precio = resultSet.getDouble("precio");
            int iva = resultSet.getInt("iva");
            int stockMinimo = resultSet.getInt("stockMinimo");
            int stock = resultSet.getInt("stock");
            String foto = resultSet.getString("foto");
            Usuario usuarioCrea = new UsuarioDAO().getByUsuario(resultSet.getString("usuarioCrea"));
            LocalDateTime fechaCreacion = resultSet.getTimestamp("fechaCrea").toLocalDateTime();
            Usuario usuarioModifica = new UsuarioDAO().getByUsuario(resultSet.getString("usuarioModif"));
            
            //Insertar Fecha Modificación
            Timestamp fechaModificacionTS = resultSet.getTimestamp("fechaModif");
            LocalDateTime fechaModificacion = (fechaModificacionTS == null) ? null : fechaModificacionTS.toLocalDateTime();
            
            String descripcion = resultSet.getString("descripcion");
            LinkedHashSet<Categoria> categorias = getCategoriasByCodProducto(codigo);

            lista.add(new Producto(codigo, nombre, precio, iva, stockMinimo, stock, foto, usuarioCrea, fechaCreacion, usuarioModifica, fechaModificacion, descripcion, categorias));
        }

        return lista;
    }
    
    public ArrayList<Producto> getByCodigoCategoria(int codigoCategoria) throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
       String sentenciaSQL = "SELECT P.*, C.categoria FROM PROYECTO_productos P, PROYECTO_clasificacionProducto C WHERE P.codigo = C.productos AND C.categoria = ?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, codigoCategoria);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo");
            String nombre = resultSet.getString("nombre");
            double precio = resultSet.getDouble("precio");
            int iva = resultSet.getInt("iva");
            int stockMinimo = resultSet.getInt("stockMinimo");
            int stock = resultSet.getInt("stock");
            String foto = resultSet.getString("foto");
            Usuario usuarioCrea = new UsuarioDAO().getByUsuario(resultSet.getString("usuarioCrea"));
            LocalDateTime fechaCreacion = resultSet.getTimestamp("fechaCrea").toLocalDateTime();
            
            //Insertar Usuario Modificación
            String usuarioModificaTS = resultSet.getString("usuarioCrea");
            Usuario usuarioModifica = (usuarioModificaTS == null) ? null : new UsuarioDAO().getByUsuario(resultSet.getString("usuarioModif"));
            
            //Insertar Fecha Modificación
            Timestamp fechaModificacionTS = resultSet.getTimestamp("fechaModif");
            LocalDateTime fechaModificacion = (fechaModificacionTS == null) ? null : fechaModificacionTS.toLocalDateTime();
            
            String descripcion = resultSet.getString("descripcion");
            LinkedHashSet<Categoria> categorias = getCategoriasByCodProducto(codigo);

            lista.add(new Producto(codigo, nombre, precio, iva, stockMinimo, stock, foto, usuarioCrea, fechaCreacion, usuarioModifica, fechaModificacion, descripcion, categorias));
        }

        return lista;
    }

    @Override
    public Producto getByCodigo(int codigo) throws SQLException {
        String sentenciaSQL = "SELECT * FROM " + tabla + " WHERE codigo=?";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, codigo);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            String nombre = resultSet.getString("nombre");
            double precio = resultSet.getDouble("precio");
            int iva = resultSet.getInt("iva");
            int stockMinimo = resultSet.getInt("stockMinimo");
            int stock = resultSet.getInt("stock");
            String foto = resultSet.getString("foto");
            Usuario usuarioCrea = new UsuarioDAO().getByUsuario(resultSet.getString("usuarioCrea"));
            LocalDateTime fechaCreacion = resultSet.getTimestamp("fechaCrea").toLocalDateTime();
            Usuario usuarioModifica = new UsuarioDAO().getByUsuario(resultSet.getString("usuarioModif"));
            
            //Insertar Fecha Modificación
            Timestamp fechaModificacionTS = resultSet.getTimestamp("fechaModif");
            LocalDateTime fechaModificacion = (fechaModificacionTS == null) ? null : fechaModificacionTS.toLocalDateTime();
            
            String descripcion = resultSet.getString("descripcion");
            LinkedHashSet<Categoria> categorias = getCategoriasByCodProducto(codigo);

            return new Producto(codigo, nombre, precio, iva, stockMinimo, stock, foto, usuarioCrea, fechaCreacion, usuarioModifica, fechaModificacion, descripcion, categorias);
        }

        return null;
    }

    public LinkedHashSet<Categoria> getCategoriasByCodProducto(int codProducto) throws SQLException {
        LinkedHashSet<Categoria> categorias = new LinkedHashSet<>();
        String sentenciaSQL = "SELECT ca.codigo, ca.nombre, ca.descripcion FROM PROYECTO_clasificacionProducto cp, PROYECTO_categoria ca WHERE ca.codigo = cp.categoria AND cp.productos = ?  ORDER BY ca.codigo";
        PreparedStatement prepared = getPrepared(sentenciaSQL);
        prepared.setInt(1, codProducto);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            int codigo = resultSet.getInt("codigo");
            String nombre = resultSet.getString("nombre");
            String descripcion = resultSet.getString("descripcion");
            categorias.add(new Categoria(codigo, nombre, descripcion));
        }
        return categorias;
    }

}
