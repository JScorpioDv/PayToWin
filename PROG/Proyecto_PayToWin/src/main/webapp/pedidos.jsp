<%@page import="Proyecto_PayToWin.DAO.UsuarioDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Direccion"%>
<%@page import="Servlets.ExportarPDF"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="Proyecto_PayToWin.DAO.PedidoDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Pedido"%>
<%@page import="Proyecto_PayToWin.DAO.CategoriaDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Categoria"%>
<%@page import="Proyecto_PayToWin.DTO.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%
            if (usuarioSesion != null && usuarioSesion.esCliente()) { %>
        <title>Tus Pedidos</title>
        <%  } else { %>
        <title>Pedidos</title>
        <% } %>
        <link rel="stylesheet" href="./css/estilos.css">
        <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body class="pedidos">
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
            
            <main class="contenido-pedidos" id="footerCorto">
                <%                    
                    if (usuarioSesion != null && usuarioSesion.esCliente()) { %>
                        <h2>Tus Pedidos</h2>
                <%  } else if(usuarioSesion != null && usuarioSesion.esAdmin()) { %>
                        <h2>Todos los Pedidos</h2>
                <% } else { %>
                    <h2>Pedidos</h2>
                        
                <% }
                
                List<Pedido> pedidos = null;
                PedidoDAO pedidoDAO = new PedidoDAO();
                if ((usuarioSesion == null) || usuarioSesion.esAnonimo()) {
                    out.println("<h1>¡No has iniciado sesion!</h1><h1>Inicia Sesión para poder acceder a pedidos</h1><p><a class=\"boton-anonimo\" href=\"./login.jsp\">Iniciar Sesión</a></p>");
                } else {
                    pedidos = (usuarioSesion.esAdmin()) ? pedidoDAO.getAll() : pedidoDAO.getByCodigoUsuario(usuarioSesion.getNombreUsuario());%>
                <section class="pedido">
                    <table class="tresultados">
                        <thead>
                            <tr>
                                <% if(usuarioSesion.esAdmin()) out.println("<th>Cliente</th>"); %>
                                <th>Código</th>
                                <th>Fecha</th>
                                <th>Metodo Pago</th>
                                <th>Total con IVA</th>
                                <th>Dirección y Facturar</th>
                            </tr>
                        </thead>
                        
                        <tbody>
                            <%
                                for (Pedido pedido : pedidos) { 
                                     if(usuarioSesion.esAdmin()) out.println("<th>"+ pedido.getCliente().getCorreo() +"</th>"); %>
                                    <td><%= pedido.getCodigo() %></td>
                                    <td><%= pedido.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></td>
                                    <td><%= pedido.getMetpago().toString() %></td>
                                    <td><%= ExportarPDF.moneda(pedido.getPrecioTotal()) %></td>
                                    
                                    <% if (!pedido.getFacturado().contains("SI")) {%>
                                    <td class="facturar">
                                        <form name="facturar-<%= pedido.getCodigo() %>" method="post" action="Facturar">
                                            <input name="id" type="hidden" value="<%= pedido.getCodigo() %>" />
                                            <select name="direccion">
                                                <% 
                                                    
                                                    for(Direccion dirFactura: new UsuarioDAO().getDirecciones(pedido.getCliente().getNombreUsuario())){
                                                        out.println("<option value=\""+dirFactura.getCodigo()+"\">"+dirFactura.getCalle()+"-"+dirFactura.getCiudad()+", "+ dirFactura.getCp()+". "+dirFactura.getPais()+" - "+ dirFactura.getProvincia() + ")</option>");
                                                    }
                                                 %>
                                            </select>
                                            <input type="submit" value="Facturar" />
                                        </form>
                                    </td>
                            <%
                                    } else {
                                        out.println("<td>¡Ya está Facturado!</td>");
                                    }
                                    out.println("</tr>");
                                }
                            %>               
                        </tbody>
                        <% } %> 
                    </table>
                </section>
            </main>


            <!--___________________________________________________________________________________________________________________________________________-->

            <!--Institucional de la página web:-->
            <footer class="fondofooter" >
                <p><center class="textoColor"><small>Copyright &copy; 2021 PAY TO WIN - All rights reserved</small></center></p>
            </footer>
        </main>
    </body>
</html>