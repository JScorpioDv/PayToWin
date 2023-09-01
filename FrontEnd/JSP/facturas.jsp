<%@page import="Servlets.ExportarPDF"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="Proyecto_PayToWin.DTO.Factura"%>
<%@page import="Proyecto_PayToWin.DAO.FacturaDAO"%>
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
        <title>Tus Facturas</title>
        <%  } else { %>
        <title>Facturas</title>
        <% } %>
        <link rel="stylesheet" href="./css/estilos.css">
        <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body class="facturas">
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

            <div class="margen-pag">
            <% if (usuarioSesion != null && usuarioSesion.esCliente()) {%>
            <h2>Tus Facturas</h2>
            <%      } else if(usuarioSesion != null && usuarioSesion.esAdmin()) { %>
            <h2>Todas los Facturas</h2>
            <%      } else { %>
            <h2>Facturas</h2>
                    
            <% } 

                List<Factura> facturas = null;
                FacturaDAO facturaDAO = new FacturaDAO();
                if ((usuarioSesion == null) || usuarioSesion.esAnonimo()) {
                    out.println("<h1>¡No has iniciado sesion!</h1><h1>Inicia Sesión para poder acceder a facturas</h1><p><a href=\"./login.jsp\" class=\"boton-anonimo\">Iniciar Sesión</a></p>");
                } else {
                    facturas = (usuarioSesion.esAdmin()) ? facturaDAO.getAll() : facturaDAO.getByCodigoUsuario(usuarioSesion.getNombreUsuario());%>
                    
                <table class="tresultados">
                    <thead>
                        <tr>
                            <% if (usuarioSesion.esAdmin())
                                    out.println("<th>Cliente</th>"); %>
                            <th>Código</th>
                            <th>Pedido Asociado</th>
                            <th>Dirección Facturación</th>
                            <th>Fecha de Facturación</th>
                            <th>IVA</th>
                            <th>Total con IVA</th>
                            <th>Descargar</th>
                            <th>Factura</th>
                        </tr>
                    </thead>

                    <tbody>

                        <%
                            for (Factura factura : facturas) {%>
                        <tr>
                            <% if(usuarioSesion.esAdmin()) out.println("<th>"+ factura.getPedido().getCliente().getCorreo() +"</th>"); %>
                            <td><%= factura.getCodigo()%></td>
                            <td><%= factura.getPedido().getCodigo()%></td>
                            <td><%= factura.getDirecFactura().getCalle() + "-" + factura.getDirecFactura().getCiudad() + ", " + factura.getDirecFactura().getCp() + ". " + factura.getDirecFactura().getPais() + " - " + factura.getDirecFactura().getProvincia()%></td>
                            <td><%= factura.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></td>
                            <td><%= factura.getIva()%> %</td>
                            <td><%= ExportarPDF.moneda(factura.getPedido().getPrecioTotal())%></td>
                            <td><a class="facturasaPDF" href="ExportarXML?id=<%= factura.getCodigo()%>"><img src="./img/xml.webp" height="50"/></a></td>
                            <td><a class="facturasaXML" href="ExportarPDF?id=<%= factura.getCodigo()%>"><img src="./img/pdf.webp" height="50"/></a></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
                <footer class="fondofooter" >
                    <p><span class="textoColor">Copyright &copy; 2021 PAY TO WIN - All rights reserved</span></p>
                </footer>
        </main>
    </body>
</html>
<% }%>