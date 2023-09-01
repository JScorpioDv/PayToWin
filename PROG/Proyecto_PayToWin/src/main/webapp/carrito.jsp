<%@page import="Servlets.ExportarPDF"%>
<%@page import="java.util.List"%>
<%@page import="Proyecto_PayToWin.DAO.UsuarioDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Direccion"%>
<%@page import="Proyecto_PayToWin.DTO.TiposPago"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="Proyecto_PayToWin.DTO.Producto"%>
<%@page import="Proyecto_PayToWin.DTO.Pedido"%>
<%@page import="Proyecto_PayToWin.DAO.CategoriaDAO"%>
<%@page import="Proyecto_PayToWin.DTO.Categoria"%>
<%@page import="Proyecto_PayToWin.DTO.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuario usuarioSesion = (session != null && session.getAttribute("usuario") != null) ? (Usuario) session.getAttribute("usuario") : null;

    //Sacar la sesión del carrito
    /*if (session.getAttribute("carrito") == null) {
        out.println("<h1> No tienes creado ningún carrito</h1>");
    } else {
        Pedido carrito = (Pedido) session.getAttribute("carrito");*/

%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tu Carrito</title>
        <link rel="stylesheet" href="./css/estilos.css">
        <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="icon" href="./img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body class="carrito">
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
                                <%  for (Categoria categorias : new CategoriaDAO().getAll()) {%>

                                <li><a href="./productos.jsp?id=<%= categorias.getCodigo()%>" class="selectColor"><%= categorias.getNombre()%></a></li>

                                <% } %>

                            </ul>
                        </li>

                        <li class="dropdown" id="tienda">
                            <label for="chk_dropdown" class="dropbtn"><a href="./nuestra_tienda.jsp">Nuestra Tienda</a></label>
                            <ul class="dropdown-content">
                                <li id="contacto"><a href="./contacto.jsp" class="selectColor">Contacto</a></li>
                                    <% if (usuarioSesion != null && usuarioSesion.esAdmin()) { %>
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
                <h2 class="carrito">Tu Carrito</h2>
                <%
                    Pedido carrito = (Pedido) session.getAttribute("carrito");

                    if (usuarioSesion == null) {
                        out.println("<h1>¡No has iniciado sesion!</h1><h1>Inicia Sesión para poder acceder a tu carrito</h1><p><a href=\"./login.jsp\">Iniciar Sesión</a></p>");
                    } else if (usuarioSesion.esAdmin()) {
                        out.println("<h1>¡Un administrador no puede hacer nada en el carrito!</h1>");
                    } else if (session.getAttribute("carrito") == null && usuarioSesion.esCliente() || carrito.getLineas().isEmpty()) {
                        out.println("<h1> Todavía no tienes nada introducido en tu carrito </h1><p><a class=\"boton-anonimo\" href=\"./productos.jsp?id=null\">Todos los Productos</a></p>");
                    } else {
                %>

                <form method="post" action="ActualizarCarrito" id="actualizarForm">
                    <table class="tresultados">
                        <thead>
                            <tr>
                                <th>Foto</th>
                                <th>Código</th>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Total</th>
                                <th>Eliminar del carrito</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                    for (Entry<Producto, Map.Entry<Integer, Double>> linea : carrito.getLineas().entrySet()) {%>
                            <tr>
                                <td><img src="./img/<%= linea.getKey().getFoto()%>" height="150"/></td>
                                <td><%= linea.getKey().getCodigo()%></td>
                                <td><%= linea.getKey().getNombre()%></td>
                                <td>
                                    <input type='number' min='1' value="<%= linea.getValue().getKey()%>" name="<%= linea.getKey().getCodigo()%>" onchange="this.form.submit()" />
                                </td>
                                <td><%= ExportarPDF.moneda(linea.getValue().getValue())%></td>
                                <td><%= ExportarPDF.moneda(linea.getValue().getKey() * linea.getValue().getValue())%></td>
                                <td style='text-align: center;'>
                                    <a href="EliminarDelCarrito?id=<%= linea.getKey().getCodigo()%>"><img src="./img/basura.png" alt="basura" height="50"/></a>
                                </td>
                            </tr>
                            <%  } %>

                        </tbody>
                    </table>


                    <div>
                        <strong><label for="metodopago">Método de pago: </label></strong>
                        <select name="metodopago" id="metodopago" onchange="this.form.submit()">
                            <%
                            for (TiposPago metPago : TiposPago.values()) {%>

                            <option value="<%= metPago.toString()%>" <%= (carrito.getMetpago().equals(metPago) ? "selected" : "")%>> <%= metPago.toString()%> </option> 

                            <%  } %>

                            %>  
                        </select>
                    </div>
                    <div>
                        <strong><label for="direccion">Dirección de Envío </label></strong>
                        <select name="direccion" id="direccion" onchange="this.form.submit()">
                            <%
                                List<Direccion> direcciones = new UsuarioDAO().getDirecciones(carrito.getCliente().getNombreUsuario());

                                for (Direccion direccion : direcciones) {%>

                            <option value="<%= direccion.getCodigo()%>" <%= (carrito.getDireccion().equals(direccion)) ? "selected" : ""%> ><%= direccion.getCalle() + "-" + direccion.getCiudad() + ", " + direccion.getCp() + ". " + direccion.getPais() + " - " + direccion.getProvincia()%></option>

                            <%  }%>
                        </select>
                    </div>
                </form>

                <h2>Total del carrito (IVA incluido): <%= ExportarPDF.moneda(carrito.getPrecioTotal())%></h2>
                <button onclick="confirmarPedido()"> Confirmar Pedido</button>

            </div>
            <!--___________________________________________________________________________________________________________________________________________-->

            <!--Institucional de la página web:-->
            <footer class="fondofooter" >
                <p><span class="textoColor">Copyright &copy; 2021 PAY TO WIN - All rights reserved</span></p>
            </footer>
        </main>

    </body>
    <script>
        function confirmarPedido() {
            if (confirm("¿Desea confirmar el pedido?")) {
                window.location.replace("ConfirmarPedido");
            }
        }
    </script>
</html>
<% }%>