<%-- 
    Document   : login
    Created on : 8/10/2024, 06:51:07 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
    </head>
    <body>
        <h1>Iniciar Sesión</h1>
        <!-- En action se coloca el nombre del servlet LoginServlet.java -->
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <label for="matricula">Nombre de Usuario</label>
            <input type="text" id="matricula" name="matricula" required><br>
            
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required><br>
            
            <button type="submit">Iniciar Sesión</button>
        </form>
        
        <!-- Mostrar mensajes de error si existen -->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p style="color: red;"><%= error %></p>
        <% } %>
    </body>
</html>
