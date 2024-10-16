<%-- 
    Document   : consultadatos
    Created on : 10/10/2024, 05:48:02 PM
    Author     : arman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Datos del Becario</title>
    <style>
        table {
            width: 50%;
            margin: 0 auto;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

    <h2 style="text-align: center;">Datos del Becario</h2>

    <c:if test="${not empty error}">
        <p style="color: red; text-align: center;">${error}</p>
    </c:if>

    <c:if test="${empty error}">
        <table>
            <tr>
                <th>CURP</th>
                <td>${curp}</td>
            </tr>
            <tr>
                <th>Nombre</th>
                <td>${nombre} ${apellido_paterno} ${apellido_materno}</td>
            </tr>
            <tr>
                <th>Género</th>
                <td>${genero}</td>
            </tr>
            <tr>
                <th>Calle</th>
                <td>${calle}</td>
            </tr>
            <tr>
                <th>Colonia</th>
                <td>${colonia}</td>
            </tr>
            <tr>
                <th>Municipio</th>
                <td>${mun}</td>
            </tr>
            <tr>
                <th>Código Postal</th>
                <td>${cp}</td>
            </tr>
        </table>
    </c:if>

                        <a href="./dashboard.jsp">regresar</a>

</body>
</html>

