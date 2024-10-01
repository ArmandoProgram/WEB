<%-- 
    Document   : login
    Created on : 30/09/2024, 08:49:58 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="${pageContext.request.contextPath}/login_servlet">
                Matricula: <br>
                <input type="text" name="matricula" id="matricula" size="9"> 
                <br>
                Password: <br>
                <input type="password" name="password" id="password" size="9">
                <br>
                <input type="checkbox" name="recordar" id="recordar">
                Recordar mis datos
                <br>
                <input type="submit" value="Iniciar Sesión">
            </form>

    </body>
</html>
