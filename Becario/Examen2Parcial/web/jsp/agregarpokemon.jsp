<%-- 
    Document   : agregarpokemon
    Created on : 14/10/2024, 09:45:10 PM
    Author     : arman
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Pokémon</title>
</head>
<body>
    <h1>Agregar Pokémon</h1>
    <form method="post" action="${pageContext.request.contextPath}/agregarpokemon">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="tipo">Tipo:</label>
        <input type="text" id="tipo" name="tipo" required><br><br>

        <label for="nivel">Nivel:</label>
        <input type="number" id="nivel" name="nivel" required><br><br>

        <input type="submit" value="Agregar Pokémon">
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
