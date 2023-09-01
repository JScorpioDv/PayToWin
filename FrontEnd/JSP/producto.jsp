<%@page import="java.time.format.DateTimeFormatter"%>
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
        <title>Consolas</title>
        <link rel="stylesheet" href="./css/estilos.css">
        <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body class="ficha-producto">
        <main>
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
            
            <% if(producto == null){
                out.println("<p>Error. Producto no encontrado. </p><p><a href=\"javascript: history.go(-1)\">Volver atrás</a></p>");
            } else {
                if (usuarioSesion != null && usuarioSesion.esAdmin()){ %>
                
                <p class="boton-editar"><a href="./editar-producto.jsp?id=<%= producto.getCodigo() %>"><img class="imagenes_pequeñitas" src="./img/lapiz.webp"></img></a></p>
            
            <% } %>
            <section class="foto-eleccion">
                <article class="XBOX">
                    <div class="div1">
                        
                        <div class="separado">
                            <div class="menu_XBOX">
                                <h2><%= producto.getNombre() %></h2>
                                <p><%= producto.getPrecio() %> €</p>
                                <p><strong>Envío: </strong> ¡¡ENVÍO GRATIS!!</p>
                            </div>
                            
                            <% if (usuarioSesion != null && usuarioSesion.esAdmin()){ %>
                            
                                <p><strong>Stock Mínimo: </strong><%= producto.getStockMinimo() %></p>
                                <p><strong>Stock: </strong><%= producto.getStock() %></p>
                                <p><strong>Fecha Creación: </strong><%= producto.getFechaCrea().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) %></p>
                                <p><strong>Persona que lo creo: </strong><%= producto.getUsuarioCrea().getNombreReal() %></p>
                                
                                <%  if (producto.getFechaModif() != null){ %>
                                        <p><strong>Fecha ultima Modificación: </strong><%= producto.getFechaModif().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) %></p>
                                <%  }
                                
                                    if (producto.getUsuarioModif() != null) {%>
                                        <p><strong>Ultima persona que Modificó: </strong><%= producto.getUsuarioModif().getNombreReal() %></p>
                                <%  } 
                            
                                 } %>
                                
                            <div>
                                <p><strong>Garantía: </strong> Garantía de Sustitución y reembolso hasta 2 años.</p>                                
                                <br />
                                
                            <%  if(usuarioSesion != null && usuarioSesion.esCliente()){ %>
                                <form name="anyadircarrito" method="post" action="AnyadirAlCarrito">
                                    <input type="hidden" name="id" value="<%= producto.getCodigo()%>" />
                                    <label for="cantidad">Cantidad: </label><input type="number" id="cantidad" name="cantidad" value="1" min="1" required />&nbsp;&nbsp;
                                    <input type="submit" value="Añadir al carrito" />
                                </form>
                            <% } %>
                            </div>
                        </div>
                            
                        <div>
                            <a href="./img/<%= producto.getFoto() %>"><img class="XBOX" src="./img/<%= producto.getFoto() %>" /></a>
                        </div>
                    </div>

                </article>
                <section class="detalles">
                    <article class="caracteristicas">
                        <h3>CARACTERÍSTICAS:</h3>
                        <%= producto.getDescripcion() %>
                    </article>
                </section>
            </section>
            
            <% } %>
            <!--___________________________________________________________________________________________________________________________________________-->

            <!--Institucional de la página web:-->
            <footer class="fondofooter" >
                <p><span class="textoColor"><small>Copyright &copy; 2021 PAY TO WIN - All rights reserved</small></span></p>
            </footer>
        </main>
    </body>
</html>