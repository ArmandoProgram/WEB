<%-- 
    Document   : display_images
    Created on : 9/10/2024, 09:37:40 PM
    Author     : arman
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
      <body>
        <h2>Imágenes subidas</h2> 
        <%
            List<String> imagePaths = (List<String>) request.getAttribute("imagePaths");
            if (imagePaths != null && !imagePaths.isEmpty()){
                for (String imagePath : imagePaths) {
        %>
        <div>
            <img src="<%=request.getContextPath() + "/" + imagePath %>" alt="Imagen" width="300">
        </div>
        <%
            }
        } else {
        %>
        <p>No hay imagenes para mostrar.</p>
        <%
            }
         %>
    </body>
</html>
