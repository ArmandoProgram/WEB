<%-- 
    Document   : registrovivienda
    Created on : 10/10/2024, 05:47:43 PM
    Author     : arman
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Vivienda</title>
</head>
<body>
    <h2>Registrar Vivienda</h2>

    <form action="${pageContext.request.contextPath}/registervivienda" method="post">
        <label for="calle">Calle:</label>
        <input type="text" name="calle" id="calle" required><br><br>

        <label for="colonia">Colonia:</label>
        <input type="text" name="colonia" id="colonia" required><br><br>

        <label for="mun">Municipio:</label>
        <input type="text" name="mun" id="mun" required><br><br>

        <label for="cp">Código Postal (CP):</label>
        <input type="text" name="cp" id="cp" required pattern="[0-9]{5}" title="Ingrese un código postal válido de 5 dígitos"><br><br>

        <button type="submit">Registrar Vivienda</button>
    </form>
</body>
</html>

