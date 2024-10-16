<%-- 
    Document   : login
    Created on : 10/10/2024, 05:01:46 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login de Becario</title>
    </head>
    <body>
        <h1>Iniciar Sesión</h1>

        <form method="post" action="${pageContext.request.contextPath}/loginservlet">
            CURP: <br>
            <input type="text" name="curp" id="curp" maxlength="18" required><br><br>

            Contraseña: <br>
            <input type="password" name="password" id="password" required><br><br>

            <input type="submit" value="Ingresar">
        </form>

        <br>
        <!-- Mostrar error si existe -->
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </body>
</html>
