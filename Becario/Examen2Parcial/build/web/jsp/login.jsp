<%-- 
    Document   : login
    Created on : 14/10/2024, 08:19:53 PM
    Author     : arman
--%>

<!DOCTYPE html>
<html>
<head>
    <title>Login de Entrenador</title>
</head>
<body>
    <h1>Login de Entrenador</h1>
     <form method="get" action="${pageContext.request.contextPath}/loginentrenador">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="contraseña">Contraseña:</label>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Iniciar Sesión">
    </form>

    <!-- Mostrar errores si los hay -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
            out.println("<p style='color:red;'>" + error + "</p>");
        }
    %>
</body>
</html>

