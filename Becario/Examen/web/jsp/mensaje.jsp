<%-- 
    Document   : mensaje
    Created on : 10/10/2024, 05:00:48 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mensaje/title>
    </head>
    <body>
        <h1>Operación</h1>
        <p><%= request.getAttribute("mensaje")%></p>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp">Iniciar seción</a>
    </body>
</html>

