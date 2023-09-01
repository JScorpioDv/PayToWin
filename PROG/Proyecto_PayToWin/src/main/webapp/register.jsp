<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Crear Cuenta</title>
    <link rel="stylesheet" href="./css/estilos.css">
	<link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="./img/favicon.ico" type="image/x-icon">
</head>
<body class="acceso">
	<main>
		<section>
            <a class="logo" href="index.jsp"><img src="./img/Logo 500x500 px.png" alt="imagen empresa" height="200">
                <!--<p class="sombra-logo">________</p>-->
            </a>     
            

            <div class="loginBox">
                <h2>Crear Cuenta</h2>
                <form action="#" method="post">
                    <div class="usuarioBox">
                        <input type="email" name="email" id="email" autofocus>
                        <label class="labels" for="email">Correo</label>
                    </div>

                    <div class="usuarioBox">
                        <input type="password" name="contrasenya" id="contrasenya">
                        <label class="labels" for="contrasenya">Contraseña</label>
                    </div>

                    <input class="crear_cuenta" type="submit" value="Crear Cuenta">
					<p>¿Tienes una cuenta?<a class="iniciar_sesion" href="./login.jsp">Iniciar Sesión</a></p>
                </form> 
            </div>

        </section>
		<footer class="fondofooter" >
			<p><span class="textoColor">Copyright &copy; 2021 PAY TO WIN - All rights reserved</span></p>
		</footer>
	</main>
</body>
</html>