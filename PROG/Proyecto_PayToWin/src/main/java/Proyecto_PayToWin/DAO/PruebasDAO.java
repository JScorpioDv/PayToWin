/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_PayToWin.DAO;

import Proyecto_PayToWin.DTO.Categoria;
import Proyecto_PayToWin.DTO.Direccion;
import Proyecto_PayToWin.DTO.Factura;
import Proyecto_PayToWin.DTO.Pedido;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.TiposDireccion;
import Proyecto_PayToWin.DTO.TiposPago;
import Proyecto_PayToWin.DTO.TiposUsuario;
import Proyecto_PayToWin.DTO.Usuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 *
 * @author ciclost
 */
public class PruebasDAO {
    public static void main(String[] args) {
        try{
            //USUARIO:
            //------------
            //Ya creados //FUNCIONA
            Usuario u1 = new Usuario("Ray", "Ivan", "ivan@gmail.com", "usuario", LocalDate.of(2003, Month.MARCH, 5), 0, null, null, TiposUsuario.Cliente);
            //new UsuarioDAO().anyadir(u1); //FUNCIONA
            
            Usuario u2 = new Usuario("Altaskur", "Isaac", "isaac@gmail.com", "usuario", LocalDate.of(1980, Month.MARCH, 25), 657152364, LocalDateTime.now(), "fotoIsaac.jpg", TiposUsuario.Cliente);
            //new UsuarioDAO().anyadir(u2); //FUNCIONA
            
            Usuario u3 = new Usuario("Pepe", "Pepe", "pepe@gmail.com", "cliente", LocalDate.of(1950, Month.MARCH, 5), 526481362, null, null, TiposUsuario.Cliente);
            
            //Usuario u3 = new Usuario("Altaskur", "Miguel", "miguel@gmail.com", "usuario", LocalDate.of(2002, Month.MARCH, 16), 0, null, null, TiposUsuario.Cliente);
            //System.out.println(new UsuarioDAO().existe(u3)); //FUNCIONA
            //new UsuarioDAO().anyadir(u3);
            
            
            //System.out.println(new UsuarioDAO().getByUsuario("Altaskur")); //FUNCIONA
            //new UsuarioDAO().eliminar("Altaskur"); //FUNCIONA
            //System.out.println(new UsuarioDAO().existe(u3)); //FUNCIONA
            //System.out.println(new UsuarioDAO().getByUsuario("LaVillenera"));
            //System.out.println(new UsuarioDAO().getUsuario("Ray", "cliente")); //FUNCIONA
            /*for (Usuario u : new UsuarioDAO().getAll()) {
                u.imprimir();
            }*/ // FUNCIONA
            /*for (Direccion d : new UsuarioDAO().getDirecciones("Ray")) {
                d.imprimir();
            }*/ //FUNCIONA
            
            //DIRECCION
            //------------
            Direccion d1 = new Direccion(5, "C\\ Juan Carlos", "Elda", "Alicante", 3610, "España", u2, TiposDireccion.Facturación);
            //new DireccionDAO().anyadir(d1); //FUNCIONA
            
            Direccion d2 = new Direccion(6, "C\\Medico Rafael Navarro", "Novelda", "Alicante", 3660, "España", u1, TiposDireccion.Envío);
            //new DireccionDAO().anyadir(d2); //FUNCIONA
            
            Direccion d3 = new Direccion(7, "C\\Gustavo, 3", "Petrer", "Alicante", 3610, "España", u1, TiposDireccion.Envío);
            //new DireccionDAO().anyadir(d3); //FUNCIONA
            
            //System.out.println(new DireccionDAO().getByCodigo(1)); //FUNCIONA
            //new DireccionDAO().eliminar(d2); //FUNCIONA
            //System.out.println(new DireccionDAO().existe(d2)); //FUNCIONA
            /*for (Direccion d : new DireccionDAO().getAll()) {
                d.imprimir();
            }*/ // FUNCIONA
            /*for (Direccion d : new DireccionDAO().getDireccionesDe(u2)) {
                d.imprimir();
            }*/ //FUNCIONA
            
            //PRODUCTO
            //----------
            LinkedHashSet<Categoria> animales = new LinkedHashSet<>();
            Categoria gatos = new Categoria(4, "Gatitos", "Todos los michis que quieras, aqui los tienes.");
            //animales.add(gatos);
            //new CategoriaDAO().anyadir(gatos); //FUNCIONA
            
            LinkedHashSet<Categoria> comidaItaliana = new LinkedHashSet<>();
            Categoria pasta = new Categoria(5, "Pasta", "¿Italiano? Ven para aca.");
            //comidaItaliana.add(pasta);
            //new CategoriaDAO().anyadir(pasta); //FUNCIONA
            

            
            Producto p1 = new Producto(11, "Mechin", 12.99, 21, 0, 1, "mechin.jpg", new UsuarioDAO().getByUsuario("JScorpioDv"), LocalDateTime.now(), null, null, "Es un bixo estremedamente bixo, y goldito y hermoso", animales);
            //new ProductoDAO().anyadir(p1); //FUNCIONA
            
            Producto p2 = new Producto(12, "Plato de Macarrones", 200.99, 21, 0, 50, "macarrones.jpg", new UsuarioDAO().getByUsuario("JScorpioDv"), LocalDateTime.now(), new UsuarioDAO().getByUsuario("JScorpioDv"), LocalDateTime.now(), "Macarrones re100 echos.", comidaItaliana);
            //new ProductoDAO().anyadir(p2); //FUNCIONA
            
            //System.out.println(new ProductoDAO().getByCodigo(1)); //FUNCIONA
            //new ProductoDAO().eliminar(new ProductoDAO().getByCodigo(11)); //FUNCIONA
            //System.out.println(new ProductoDAO().existe(p2)); //FUNCIONA
            /*for (Producto p : new ProductoDAO().getAll()) {
                p.imprimir();
            }*/ // FUNCIONA
            /*for (Categoria c : new ProductoDAO().getCategoriasByCodProducto(1)) {
                c.imprimir();
            }*/ //FUNCIONA
            
            //CATEGORIA
            //-----------
            //PRUEBAS DE CREACIÓN DE CATEGORIA ESTAN EN PRODUCTO.
            
            //System.out.println(new CategoriaDAO().getByCodigo(1)); //FUNCIONA
            //new CategoriaDAO().eliminar(new CategoriaDAO().getByCodigo(5)); //FUNCIONA
            //System.out.println(new CategoriaDAO().existe(new CategoriaDAO().getByCodigo(10))); //FUNCIONA
            /*for (Categoria c : new CategoriaDAO().getAll()) {
                c.imprimir();
            }*/ // FUNCIONA
            //System.out.println(new CategoriaDAO().getCodigoDe("Ordenadores")); //FUNCIONA
            
            //System.out.println(new ProductoDAO().getByCodigoCategoria(1)); //FUNCIONA
            
            
            //PEDIDO
            //--------
            HashMap<Producto,Map.Entry<Integer, Double>> lineas = new HashMap<>();
            lineas.put(p2, Map.entry(2,98.99));
            lineas.put(p1, Map.entry(3, 100.00));
            
            Pedido ped1 = new Pedido(5, LocalDateTime.now(), TiposPago.Paypal, "NO", new DireccionDAO().getByCodigo(5), new UsuarioDAO().getByUsuario("Altaskur"), lineas);
            //new PedidoDAO().anyadir(ped1); //FUNCIONA
            
            //System.out.println(new PedidoDAO().getByCodigo(1)); //FUNCIONA
            //new PedidoDAO().eliminar(new PedidoDAO().getByCodigo(5)); //FUNCIONA
            //System.out.println(new PedidoDAO().existe(new PedidoDAO().getByCodigo(1))); //FUNCIONA
            /*for (Pedido c : new PedidoDAO().getAll()) {
                c.imprimir();
            }*/ // FUNCIONA
            /*for (Map.Entry<Producto, Map.Entry<Integer, Double>> linea : new PedidoDAO().getLineas(1).entrySet()) {
                System.out.println(linea);
            }*/ //FUNCIONA
            //System.out.println(new PedidoDAO().estaFacturado(5)); //FUNCIONA
            
            //FACTURA
            //---------
            Factura f1 = new Factura(5, LocalDateTime.now() , 21, new PedidoDAO().getByCodigo(5), new PedidoDAO().getByCodigo(5).getDireccion());
            //new FacturaDAO().anyadir(f1);
            
            //System.out.println(new FacturaDAO().getByCodigo(1)); //FUNCIONA
            //new FacturaDAO().eliminar(new FacturaDAO().getByCodigo(5)); //FUNCIONA
            //System.out.println(new FacturaDAO().existe(new FacturaDAO().getByCodigo(1))); //NO FUNCIONA. El metodo existe si no existe da nullPointerException.
            /*for (Factura c : new FacturaDAO().getAll()) {
                c.imprimir();
            }*/ // FUNCIONA
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
}
