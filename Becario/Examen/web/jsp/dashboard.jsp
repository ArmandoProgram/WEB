<%-- 
    Document   : dashboard
    Created on : 10/10/2024, 05:18:38 PM
    Author     : arman
--%>

<%@page import="java.util.List"%>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <style>
            .container {
                text-align: center;
                margin-top: 50px;
            }
            .image-container {
                border: 1px solid #ccc;
                width: 200px;
                height: 200px;
                margin: 0 auto;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f0f0f0;
            }
            button {
                margin-top: 15px;
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            button:hover {
                background-color: #45a049;
            }
            #contador {
                font-size: 20px;
                color: red;
                margin-top: 20px;
            }
        </style>

        <script>
            // Tiempo inicial (2 minutos en milisegundos)
            let countdown = 120000;

            // Función que convierte milisegundos a minutos y segundos
            function formatTime(ms) {
                let minutes = Math.floor(ms / 60000);
                let seconds = ((ms % 60000) / 1000).toFixed(0);
                return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
            }

            // Actualizar el contador en la página cada segundo
            let countdownTimer = setInterval(function() {
                countdown -= 1000; // Restar 1 segundo

                // Actualizar el contador visible
                document.getElementById("contador").innerHTML = "Tiempo restante: " + formatTime(countdown);

                if (countdown <= 0) {
                    clearInterval(countdownTimer);
                    window.location.href = "${pageContext.request.contextPath}/logout"; // Redirige a la página de cierre de sesión
                }
            }, 1000);
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Bienvenido a tu Dashboard, <%= (String) session.getAttribute("nombreUsuario") %>!</h1>

            <!-- Mostrar contador visible -->
            <div id="contador">Tiempo restante: 2:00</div>

            <br><br>

            <!-- Botón para Registrar Datos -->
            <form action="${pageContext.request.contextPath}/redirect" method="post">
                <input type="hidden" name="action" value="registroVivienda">
                <button type="submit">Registrar datos</button>
            </form>

            <br><br>

         <!-- Formulario para subir la imagen de perfil -->
    <h2>Subir imagen de perfil</h2>
    <form action="${pageContext.request.contextPath}/uploadimageservlet" method="post" enctype="multipart/form-data">
        <label for="image">Selecciona una imagen:</label>
        <input type="file" name="image" id="image" required>
        <br><br>
        <button type="submit">Añadir imagen</button>
    </form>

    <!-- Mostrar las imágenes subidas -->
    <h2>Imágenes subidas</h2>
    <%
        List<String> imagePaths = (List<String>) request.getAttribute("imagePaths");
        if (imagePaths != null && !imagePaths.isEmpty()) {
            for (String imagePath : imagePaths) {
    %>
    <div>
        <img src="<%=request.getContextPath() + "/" + imagePath %>" alt="Imagen" width="150">
    </div>
    <%
            }
        } else {
    %>
    <p>No hay imágenes para mostrar.</p>
    <%
        }
    %>

            <!-- Botón para ver más datos -->
            <form action="${pageContext.request.contextPath}/redirect" method="post">
                <input type="hidden" name="action" value="consultaDatos">
                <button type="submit">Ver más</button>
            </form>
        </div>
    </body>
</html>



