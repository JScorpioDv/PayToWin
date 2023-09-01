<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./css/estilos.css">
	<link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="./img/favicon.ico" type="image/x-icon">
	<title>Iniciar Sesion</title>
</head>
<body class="acceso">
	<main>	
		</nav>
		<section>
                    <a class="logo" href="./index.jsp"><img src="./img/Logo 500x500 px.png" alt="imagen empresa" height="200">
                <!--<p class="sombra-logo">________</p>-->
            </a>     

            <div class="loginBox">
                <h2>Iniciar sesión</h2>
                <form method="post" action="LoginServlet">
                    <div class="usuarioBox">
                        <input type="text" name="usuario" id="usuario" autofocus>
                        <label class="labels" for="usuario">Usuario</label>
                    </div>
                    
                    <div class="usuarioBox">
                        <input type="password" name="contrasenya" id="contrasenya">
                        <label class="labels" for="contrasenya">Contraseña</label>
                    </div>

                    <input class="iniciar_sesion" name="login" id="login" type="submit" value="Iniciar Sesion">
                <p>¿No te acuerdas de tu contraseña?<a href="../pagina_principal.html" class="signup">¡Pinchale Aquí!</a></p>
                </form> 
                    
                    <%
                        if (request.getAttribute("error") != null && (boolean) request.getAttribute("error")) {
                            out.println("<p>(*) Nombre de correo o contraseña inválidos. Vuelve a intentarlo</p>");
                        }
                    %>
                    
                <a class="crearcuenta" href="./register.jsp">CREAR CUENTA</a>
            </div>
                
        </section>
		<footer class="fondofooter" >
			<p><span class="textoColor">Copyright &copy; 2021 PAY TO WIN - All rights reserved</span></p>
		</footer>
	</main>
</body>
</html>
                    