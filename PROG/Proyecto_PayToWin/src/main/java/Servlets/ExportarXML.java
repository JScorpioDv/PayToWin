/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Proyecto_PayToWin.DAO.FacturaDAO;
import Proyecto_PayToWin.DTO.Factura;
import Proyecto_PayToWin.DTO.Pedido;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class ExportarXML extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        boolean errorFactura = false;
        // Recogemos al usuario que está en la sesión
        HttpSession session = request.getSession(false);
        Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
        try (PrintWriter out = response.getWriter()) {
            try {
                // Recogemos el codigo de factura que se solicita exportar
                if (request.getParameter("id") != null && request.getParameter("id").chars().allMatch(Character::isDigit)) {
                    int codigo = Integer.parseInt(request.getParameter("id"));
                    Factura factura = new FacturaDAO().getByCodigo(codigo);
                    // Si la factura existe en la BD y además está asociada al mismo usuario que hay en la sesión entonces exportamos (excepto si es admin)
                    if (usuarioSesion != null && factura != null && (factura.getPedido().getCliente().equals(usuarioSesion) || usuarioSesion.esAdmin())) {
                        response.setContentType("text/plain;charset=UTF-8");
                        out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                        out.println("<factura codigo_factura=\"" + factura.getCodigo() + "\">");

                        out.println("\t<fecha>");
                        out.println("\t\t<dia>" + factura.getFecha().getDayOfMonth() + "</dia>");
                        out.println("\t\t<mes>" + factura.getFecha().getMonthValue() + "</mes>");
                        out.println("\t\t<anyo>" + factura.getFecha().getYear() + "</anyo>");
                        out.println("\t\t<hora>" + factura.getFecha().getHour() + "</hora>");
                        out.println("\t\t<minuto>" + factura.getFecha().getMinute() + "</minuto>");
                        out.println("\t\t<segundo>" + factura.getFecha().getSecond() + "</segundo>");
                        out.println("\t</fecha>");

                        out.println("\t<direccion_facturacion>");
                        out.println("\t\t<direccion>" + factura.getDirecFactura().getCalle()+"-"+factura.getDirecFactura().getCiudad()+", "+ factura.getDirecFactura().getCp()+". "+factura.getDirecFactura().getPais() + "</direccion>");
                        out.println("\t\t<provincia>" + factura.getDirecFactura().getProvincia() + "</provincia>");
                        out.println("\t</direccion_facturacion>");

                        Pedido pedido = factura.getPedido();
                        out.println("\t<pedido_asociado codigo_pedido=\"" + pedido.getCodigo() + "\">");
                        out.println("\t\t<cliente>" + pedido.getCliente().getNombreReal() + "</cliente>");
                        out.println("\t\t<metodo_pago>" + pedido.getMetpago().toString() + "</metodo_pago>");
                        out.println("\t\t<direccion_envio>");
                        out.println("\t\t\t<direccion>" + pedido.getDireccion().getCalle()+"-"+pedido.getDireccion().getCiudad()+", "+ pedido.getDireccion().getCp()+". "+pedido.getDireccion().getPais() + "</direccion>");
                        out.println("\t\t\t<provincia>" + pedido.getDireccion().getProvincia() + "</provincia>");
                        out.println("\t\t</direccion_envio>");
                        out.println("\t\t<lineas>");

                        HashMap<Producto, Map.Entry<Integer, Double>> lineas = pedido.getLineas();
                        for (Map.Entry<Producto, Map.Entry<Integer, Double>> linea : lineas.entrySet()) {
                            out.println("\t\t\t<producto codigo_producto=\"" + linea.getKey().getCodigo() + "\">");
                            out.println("\t\t\t\t<nombre>" + linea.getKey().getNombre() + "</nombre>");
                            out.println("\t\t\t\t<precio_unitario>" + redondear(linea.getKey().getPrecio()) + "</precio_unitario>");
                            out.println("\t\t\t\t<iva>" + linea.getKey().getIva() + "</iva>");
                            out.println("\t\t\t\t<cantidad_productos>" + linea.getValue().getKey() + "</cantidad_productos>");
                            out.println("\t\t\t\t<total_linea_con_iva>" + redondear(linea.getValue().getKey() * linea.getValue().getValue()) + "</total_linea_con_iva>");
                            out.println("\t\t\t</producto>");
                        }

                        out.println("\t\t</lineas>");
                        out.println("\t</pedido_asociado>");

                        out.println("\t<total>" + redondear(factura.getPedido().getPrecioTotal()) + "</total>");
                        out.println("</factura>");
                        response.setHeader("Content-Disposition", "attachment; filename=\"Factura_PayToWin_" + factura.getCodigo() + ".xml\"");

                    } else {
                        errorFactura = true;
                    }

                } else {
                    errorFactura = true;
                }

                if (errorFactura) {
                    out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí! </h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                }
            } catch (SQLException e) {
                out.println("<h1>ERROR SQL</h1>");
            }
        }
    }
    
    private static double redondear(double valor){
        return Math.round(valor*100)/100.0;
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
