/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Proyecto_PayToWin.DAO.DireccionDAO;
import Proyecto_PayToWin.DAO.FacturaDAO;
import Proyecto_PayToWin.DAO.PedidoDAO;
import Proyecto_PayToWin.DTO.Direccion;
import Proyecto_PayToWin.DTO.Factura;
import Proyecto_PayToWin.DTO.Pedido;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.TiposPago;
import Proyecto_PayToWin.DTO.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
public class Facturar extends HttpServlet {

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
        String strId = request.getParameter("id");
        PedidoDAO pedidoDAO = new PedidoDAO();
        FacturaDAO facturaDAO = new FacturaDAO();

        Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;

        try (PrintWriter out = response.getWriter()) {
            // Primero validamos que haya usuario logueado y no sea anónimo
            if (usuarioSesion == null || usuarioSesion.esAnonimo()) {
                out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí!</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                // Después validamos que el código de pedido que nos hay llegado sea un valor entero    
            } else if (!strId.chars().allMatch(Character::isDigit)) {
                out.println("<h2>Código incorrecto.</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
            } else {
                Pedido pedidoAFacturar = pedidoDAO.getByCodigo(Integer.parseInt(strId));
                // Si el código es numérico pero no existe en la base de datos, o bien no pertenece al usuario conectado (excepto si es admin) mostraremos error.
                if (pedidoAFacturar == null || (!pedidoAFacturar.perteneceAUsuario(usuarioSesion) && !usuarioSesion.esAdmin())) {
                    out.println("<h2>Pedido no encontrado.</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                } else {
                    String strDireccion = request.getParameter("direccion");
                    Direccion direccionEnvio = new DireccionDAO().getByCodigo(Integer.parseInt(strDireccion));
                    if (direccionEnvio == null || (!direccionEnvio.perteneceA(usuarioSesion) && !usuarioSesion.esAdmin())) {
                        out.println("<h2>Dirección no encontrada</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                    } else {
                        // Si la factura pertenece al usuario en sesión (o es admin) entonces creamos una nueva factura con los datos del formulario y actualizamos el pedido (facturado = true)
                        Pedido pedidoFacturado = new Pedido(pedidoAFacturar.getCodigo(), pedidoAFacturar.getFecha(), pedidoAFacturar.getMetpago(), "SI", pedidoAFacturar.getDireccion(), pedidoAFacturar.getCliente(), pedidoAFacturar.getLineas());
                        HashMap<Producto, Map.Entry<Integer, Double>> lineas = pedidoFacturado.getLineas();
                        int iva = 21;
                        for (Map.Entry<Producto, Map.Entry<Integer, Double>> linea : lineas.entrySet()) {
                            iva = linea.getKey().getIva();
                        }
                        Factura factura = new Factura(facturaDAO.siguienteCodigo(), LocalDateTime.now(), iva,pedidoFacturado, direccionEnvio);
                        facturaDAO.anyadir(factura);
                        //pedidoDAO.actualizar(pedidoFacturado);
                        response.sendRedirect("index.jsp");
                    }

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
