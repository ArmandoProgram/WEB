<%-- 
    Document   : usuario
    Created on : 30/09/2024, 08:50:40 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio Usuario</title>
        <% 
            Cookie [] cookies = request.getCookies();
            String valor = "";
            for(Cookie c:cookies){
                if(c.getName().equals("matricula")){
                valor = c.getValue();
                break;
            }
            }
            
        %>
        
    </head>
    <body>
        <h1>Bienvenido <%= valor %>,ha iniciado sesión de manera correcta  </h1>
        <a href="${pageContext.request.contextPath}/login_servlet">Cerrar Sesión</a>
    </body>
</html>
