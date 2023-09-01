/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Proyecto_PayToWin.DAO.ProductoDAO;
import Proyecto_PayToWin.DAO.UsuarioDAO;
import Proyecto_PayToWin.DTO.Direccion;
import Proyecto_PayToWin.DTO.Pedido;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.TiposPago;
import Proyecto_PayToWin.DTO.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juanp
 */
public class AnyadirAlCarrito extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try ( PrintWriter out = response.getWriter()) {
            Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
            if (usuarioSesion == null || usuarioSesion.esAnonimo()) {
                out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí!</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");

            } else {

                // Validamos los datos del formulario
                String strId = request.getParameter("id");
                String strCantidad = request.getParameter("cantidad");
                if (strId != null && strCantidad != null && strId.chars().allMatch(Character::isDigit) && strCantidad.chars().allMatch(Character::isDigit)) {
                    Producto producto = new ProductoDAO().getByCodigo(Integer.parseInt(strId));

                    int cantidad = Integer.parseInt(strCantidad);
                    //Si existe el carrito en la sesión obtenemos las líneas y añadimos la del formulario
                    LinkedHashMap<Producto, Map.Entry<Integer, Double>> lineas = new LinkedHashMap<>();
                    TiposPago metPago = TiposPago.Paypal;
                    Direccion direccion = null;
                    if (session.getAttribute("carrito") != null) {
                        Pedido carrito = (Pedido) session.getAttribute("carrito");
                        lineas = new LinkedHashMap<>(carrito.getLineas());
                        metPago = carrito.getMetpago();
                        direccion = carrito.getDireccion();

                    } //Si no existe el carrito añadimos las líneas desde cero
                    else{
                        direccion = new UsuarioDAO().getDirecciones(usuarioSesion.getNombreUsuario()).get(0);
                    }
                    lineas.putIfAbsent(producto, Map.entry(cantidad, producto.getPrecio()));
                    Pedido pedido = new Pedido(1, LocalDateTime.now(), metPago, "NO", direccion, usuarioSesion, lineas);
                    session.setAttribute("carrito", pedido);
                    response.sendRedirect("carrito.jsp");
                } else {
                    out.println("<h2>Datos incorrectos. Revisa el formulario</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                }

            }
        } catch (SQLException ex) {
            System.out.println("Error SQL");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
