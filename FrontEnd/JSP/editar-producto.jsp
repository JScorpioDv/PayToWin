<%-- 
    Document   : editar-producto.jsp
    Created on : 16 may 2023, 0:40:26
    Author     : juanp
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Proyecto_PayToWin.DAO.ProductoDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Producto"%>
<%@page import="Proyecto_PayToWin.DAO.CategoriaDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Categoria"%>
<%@page import="Proyecto_PayToWin.DTO.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
    
    //Sacar el producto
    Producto producto = null;
    if (request != null && request.getParameter("id") != null && request.getParameter("id").chars().allMatch(Character::isDigit)) {
        int codigo = Integer.parseInt(request.getParameter("id"));
        producto = new ProductoDAO().getByCodigo(codigo);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Producto</title>
        <link rel="stylesheet" href="./css/estilos.css">
        <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body class="cambiar-productos">
        <main class="editar-producto">
            <!--Cabecera de la página web:-->

            <!-- Barra de navegación de la página web:-->
            <header class="headerColor" >
                <nav class="textoColor">
                    <ul class="informacion">
                        <li>C\Padre Manjon, 21</li>
                        <li>+34 645 85 12 25</li>
                        <li>paytowin@gmail.com</li>
                    </ul>
                    <ul class="redes">
                        <li>
                            <a href="https://www.facebook.com" target="blank"><img src="./img/facebook.png" height="35" /></a>
                        </li>
                        <li>
                            <a href="https://www.twitter.com"target="blank"><img src="./img/twitter.png" height="40" /></a>
                        </li>
                        <li>
                            <a href="https://www.linkedin.com"target="blank"><img src="./img/linkedin.png" height="40"></a>
                        </li>
                        <li>
                            <a href="https://www.instagram.com/juanpa.s.a/"target="blank"><img src="./img/instagram.png" height="30"></a>
                        </li>
                    </ul>
                    <label for="chk_menu"><i class="fa fa-bars"></i></label>
                </nav>



                <nav class="menu-contenedor">
                    <h3 class="textoColor">PAY TO WIN</h3>
                    <input type="checkbox" id="chk_menu"/>
                    <ul class="menu-main">
                        <li class="menu-inicio"id="inicio" class="colorPurple"><a href="./index.jsp" class="selectColor">Inicio</a></li>
                        <li class="dropdown" id="productos">
                            <label for="chk_dropdown" class="dropbtn"><a href="./productos.jsp?id=null">Productos</a></label>
                            <ul class="dropdown-content">
                                <%
                                    for (Categoria categorias : new CategoriaDAO().getAll()) {
                                %>

                                <li><a href="./productos.jsp?id=<%= categorias.getCodigo()%>" class="selectColor"><%= categorias.getNombre()%></a></li>

                                <% } %>

                            </ul>
                        </li>

                        <li class="dropdown" id="tienda">
                            <label for="chk_dropdown" class="dropbtn"><a href="./nuestra_tienda.jsp">Nuestra Tienda</a></label>
                            <ul class="dropdown-content">
                                <li id="contacto"><a href="./contacto.jsp" class="selectColor">Contacto</a></li>
                                <% if (usuarioSesion != null && usuarioSesion.esAdmin()){ %>
                                    <li id="stock"><a href="./stock.jsp" class="selectColor">Stock</a></li>
                                    <li id="estadisticas"><a href="./estadisticas.jsp" class="selectColor">Estadísticas</a></li>
                                <% } %>                                
                            </ul>
                        </li>


                        <%
                            if (usuarioSesion != null && usuarioSesion.esCliente()) {
                        %>
                        <li class="menu-carrito"id="carrito" class="selectColor"><a href="./carrito.jsp" class="selectColor"><img src="./img/carrito.png" height="40"></a></li>

                        <li class="dropdown" id="sesion">
                            <label for="chk_dropdown" class="dropbtn">
                                <a href="LogoutServlet" class="selectColor"><%= usuarioSesion.getNombreUsuario()%></a>
                                <a href="LogoutServlet" class="selectColor"><img src="./img/logout.png" height="18"></a>
                            </label>
                            <ul class="dropdown-content">
                                <li class="menu-pedidos"id="pedidos"><a href="./pedidos.jsp" class="selectColor">Mis Pedidos</h3></a></li>
                                <li class="menu-facturas"id="facturas"><a href="./facturas.jsp" class="selectColor">Mis Facturas</h3></a></li>
                            </ul>
                        </li>
                        <%
                        } else if (usuarioSesion != null && usuarioSesion.esAdmin()) {
                        %>

                        <li class="menu-pedidos"id="pedidos"><a href="./pedidos.jsp" class="selectColor">Pedidos</h3></a></li>

                        <li class="menu-facturas"id="facturas"><a href="./facturas.jsp" class="selectColor">Facturas</h3></a></li>

                        <li class="dropdown" id="sesion">
                            <label for="chk_dropdown" class="dropbtn">
                                <a href="LogoutServlet" class="selectColor"><%= usuarioSesion.getNombreUsuario()%></a>
                                <a href="LogoutServlet" class="selectColor"><img src="./img/logout.png" height="18"></a>
                            </label>
                        </li>

                        <%
                        } else {
                        %>

                        <li class="sesion" id="sesion" class="selectColor"><a href="./login.jsp" class="selectColor"><img src="./img/sesion.png" height="35"></a></li>

                        <% }%>


                    </ul>

                </nav>
            </header>
            <!--___________________________________________________________________________________________________________________________________________-->

            <!--Información principal de la página web:-->
            
            <% 
                if (usuarioSesion == null || !usuarioSesion.esAdmin()) {
                out.println("<h2>¡Ey Ey Ey, no tan rapido McQueen, no puedes entrar aquí!</h2><p><a class=\"boton-anonimo\" href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                } else {
                    if (producto == null) {
                        out.println("<h2>Código erròneo o inexistente</h2><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
                    } else {
            
            %>

            <form class="loginBox" action="EditarProducto" method="post" enctype="multipart/form-data">
                <h2>Editar <%= producto.getNombre() %></h2>
                <div class="fbody">
                    <input type="hidden"name="cod-producto" id="cod-producto" value="<%= producto.getCodigo() %>" />
                                        
                    <div class="usuarioBox">
                        <input type="text" name="nom-producto" id="nom-producto" required autofocus value="<%= producto.getNombre() %>">
                        <label for="nom-producto">Nombre</label>

                    </div>

                    <div class="usuarioBox">
                        <input type="number" name="precio" id="precio" required value="<%= producto.getPrecio() %>">
                        <label for="precio">Precio Venta</label>

                    </div>
                    
                    <div class="usuarioBox">
                        <input type="number" name="iva" id="iva" min="0" max="100" step="any" required value="<%= producto.getIva() %>">
                        <label for="iva">IVA</label>

                    </div>

                    <div class="usuarioBox">
                        <input type="number" name="stockMinimo" id="stockMinimo" min="0" step="1" required value="<%= producto.getStockMinimo() %>">
                        <label for="stockMinimo">Stock Minimo</label>
                    </div>

                    <div class="usuarioBox">
                        <input type="number" name="stock" id="stock" min="0" step="1" required value="<%= producto.getStock() %>">
                        <label for="stock">Stock</label>
                    </div>

                    <div class="usuarioBox">
                        <input type="file" name="foto-producto" id="foto-producto" value="<%= producto.getFoto() %>">
                        <label class="alta-foto" for="foto-producto">Foto</label>
                    </div>

                    <div class="usuarioBox">
                        <textarea name="descripcion" id="descripcion" cols="20" rows="2"><%= producto.getDescripcion() %></textarea>
                        <label class="alta-descripcion" for="descripcion">Descripcion</label>
                    </div>
                    
                    <div>
                        <p></p> <!--Vacio para que quede bien alineado todo-->
                    </div>
                    
                        <%
                            ArrayList<Categoria> categorias = new CategoriaDAO().getAll();
                            for (Categoria categoria : categorias) {
                                    out.println("<div>");
                                    out.println("<input type=\"checkbox\" name=\"categorias\" id=\"" + categoria.getNombre() + "\" value=\"" + categoria.getCodigo() + "\" " + (producto.getCategoria().contains(categoria) ? "checked" : "") + " />");
                                    out.println("<label class=\"chklbl\" for=\"" + categoria.getNombre() + "\">" + categoria.getNombre() + "</label>");
                                    out.println("</div>");
                                }
                        %>
                </div>
                        
                <input class="form-boton" type="submit" value="Enviar" name="enviado"/>&nbsp;&nbsp;
                <input class="form-boton" type="reset" value="Esborrar" />

            </form>
            
            <%      }
                }
            %>
            <!--___________________________________________________________________________________________________________________________________________-->

            <!--Institucional de la página web:-->
            <footer class="fondofooter" >
                <p><span class="textoColor"><small>Copyright &copy; 2021 PAY TO WIN - All rights reserved</small></span></p>
            </footer>
        </main>
    </body>
</html>