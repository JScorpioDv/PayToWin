/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Proyecto_PayToWin.DAO.DireccionDAO;
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
public class ActualizarCarrito extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
            if (usuarioSesion == null || usuarioSesion.esAnonimo()) {
                out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí!</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");

            } else if (session.getAttribute("carrito") == null) {
                response.sendRedirect("carrito.jsp");
            } else {
                Pedido carritoAnterior = (Pedido) session.getAttribute("carrito");
                LinkedHashMap<Producto, Map.Entry<Integer, Double>> lineas = new LinkedHashMap<>(carritoAnterior.getLineas());
                for(Map.Entry<Producto, Map.Entry<Integer, Double>> linea: lineas.entrySet()){
                    String strCodigo = String.valueOf(linea.getKey().getCodigo());
                    Integer nuevaCantidad = Integer.valueOf(request.getParameter(strCodigo));
                    if(nuevaCantidad > 0){
                        lineas.put(linea.getKey(), Map.entry(nuevaCantidad, linea.getValue().getValue()));
                    }
                    
                }
                TiposPago metPago = TiposPago.valueOf(request.getParameter("metodopago"));
                Direccion direccion = new DireccionDAO().getByCodigo(Integer.parseInt(request.getParameter("direccion")));
                Pedido carritoNuevo = new Pedido(0, LocalDateTime.MIN, metPago, "NO", direccion, usuarioSesion, lineas);
                session.setAttribute("carrito", carritoNuevo);
                //request.getRequestDispatcher("carrito.jsp").forward(request, response);
                response.sendRedirect("carrito.jsp");
                
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
