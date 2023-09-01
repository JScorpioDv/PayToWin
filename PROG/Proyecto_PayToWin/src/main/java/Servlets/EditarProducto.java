/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Proyecto_PayToWin.DAO.CategoriaDAO;
import Proyecto_PayToWin.DAO.ProductoDAO;
import Proyecto_PayToWin.DTO.Categoria;
import Proyecto_PayToWin.DTO.Producto;
import Proyecto_PayToWin.DTO.Usuario;
import com.itextpdf.text.xml.xmp.XmpWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author juanp
 */
public class EditarProducto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String DIRECTORIO_GUARDADO = "img";
    
    protected static Producto recogerProductoFormulario(HttpServletRequest request){
        try{
            request.setCharacterEncoding("utf-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            List<FileItem> listaParametros = upload.parseRequest(request);
            
            System.out.println("EL METODO A CREADO LA LISTA DE PARAMETROS");
            
            // Recogemos los campos del formulario
            int iterador = 0;
            Integer codigo = listaParametros.get(iterador).getString().equals("") ? null : Integer.valueOf(listaParametros.get(iterador).getString());
            iterador++;
            String nombre =  listaParametros.get(iterador++).getString();
            Double precio = listaParametros.get(iterador).getString().equals("") ? null : Double.valueOf(listaParametros.get(iterador).getString());
            iterador++;
            Integer iva = listaParametros.get(iterador).getString().equals("") ? null : Integer.valueOf(listaParametros.get(iterador).getString());
            iterador++;
            Integer stockMinimo = listaParametros.get(iterador).getString().equals("") ? null : Integer.valueOf(listaParametros.get(iterador).getString());
            iterador++;
            Integer stock = listaParametros.get(iterador).getString().equals("") ? null : Integer.valueOf(listaParametros.get(iterador).getString());
            int contadorFoto = iterador+1;
            iterador+= 2;
            String descripcion = listaParametros.get(iterador++).getString();
            System.out.println("A COGIDO TODO MENOS CATEGORIAS");
            // RECOGER CATEGORIAS
            LinkedHashSet<Categoria> categorias = new LinkedHashSet<>();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            for (; iterador < listaParametros.size() - 1; iterador++) {
                categorias.add(categoriaDAO.getByCodigo(Integer.parseInt(listaParametros.get(iterador).getString())));
                System.out.println("A ENTRADO AL FOR CATEGORIAS");
            }
            
            System.out.println(codigo+" | "+nombre+" | "+ precio+" | "+ iva+" | "+stockMinimo+" | "+stock+" | "+descripcion+" | "+categorias);
            System.out.println("HA COGIDO TODOS LOS ELEMENTOS DEL PRODUCTO");
            for (FileItem elemento : listaParametros) {
                System.out.println(elemento);
            }
            System.out.println("\n");
            
            // Ruta absoluta de l'aplicació
            String appPath = request.getServletContext().getRealPath("");
            // Ruta completa on es guarda l'arxiu
            String savePath = appPath + DIRECTORIO_GUARDADO;
            // Crea el directori de guardat si no existeix
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            
            FileItem fichero = listaParametros.get(contadorFoto);
            String fileName = null;
            System.out.println(fichero); //Imprimir que contiene el fichero
            // Si s'ha pujat fitxer el guardem.
            if (fichero.getSize() != 0) {
                fileName = new File(fichero.getName()).getName();
                String filePath = savePath + File.separator + fileName;
                File storeFile = new File(filePath);
                System.out.println("FICHERO: "+storeFile);
                
                // Guarda en el disc
                fichero.write(storeFile);
            }
            
            
            
            // ¡¡ Importante validar los datos también en el servidor!! Por si nos llega alguno de los campos obligatorios vacios !! Si alguno llega vacio retornamos NULL
            if (codigo != null && nombre != null && descripcion != null && precio != null && iva != null && stock != null && stockMinimo != null && !categorias.isEmpty()) {
                System.out.println("NINGÚN CAMPO VACIO, ENTRAMOS EN EL IF");
                Usuario usuarioModifica = (Usuario) request.getSession().getAttribute("usuario");
                System.out.println(usuarioModifica);
                Producto antiguo = new ProductoDAO().getByCodigo(codigo);
                System.out.println(antiguo);
                String rutaImagen = (fichero.getSize() != 0) ? fileName : antiguo.getFoto();
                Producto producto = new Producto(antiguo.getCodigo(), utf8(nombre), precio, iva, stockMinimo, stock, utf8(rutaImagen), antiguo.getUsuarioCrea(), antiguo.getFechaCrea(), usuarioModifica, LocalDateTime.now(), utf8(descripcion), categorias);
                return producto;
            }
        }catch(FileUploadException | SQLException ex){
            System.err.println(ex);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static String utf8(String s) {
        String out;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (NullPointerException | UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Producto producto = recogerProductoFormulario(request);
            System.out.println(producto);
            Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
            
            if (usuarioSesion == null || !usuarioSesion.esAdmin()) {
                out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí!</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
            } else if (producto == null) {
                out.println("<h2>Se ha producido un error. Revisa los datos del formulario</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
            } else {
                try {
                    new ProductoDAO().actualizar(producto);
                } catch (SQLException ex) {
                    out.println("<h2>Se ha producido un error al ejecutar la sentencia en la Base de Datos</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                }
                response.sendRedirect("producto.jsp?id="+producto.getCodigo());
            }
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
