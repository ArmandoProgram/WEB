<%-- 
    Document   : pokemonstorage
    Created on : 14/10/2024, 08:50:26 PM
    Author     : arman
--%>

<%@ page import="java.sql.*" %>
<%@ page import="configuration.ConnectionBD" %>
<%
    // Obtener el ID del entrenador de la sesi�n
    Integer idEntrenador = (Integer) session.getAttribute("id_entrenador");

    if (idEntrenador == null) {
        response.sendRedirect("jsp/login.jsp"); // Redirigir si no est� logueado
        return;
    }

    // Conexi�n a la base de datos
    ConnectionBD conexion = new ConnectionBD();
    Connection conn = conexion.getConnectionBD();
    ResultSet rs = null;

    try {
        // Consulta SQL para obtener los Pok�mon del entrenador
        String sql = "SELECT * FROM Pokemon WHERE id_entrenador = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEntrenador);
        rs = ps.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Almacenamiento de Pok�mon</title>
</head>
<body>
        <h1>Bienvenido, <%= session.getAttribute("nombreEntrenador") %>!</h1>

    <h1>Pok�mon del Entrenador</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Tipo</th>
            <th>Acciones</th>
        </tr>

<%
        boolean hasResults = false; // Variable para verificar si hay resultados

        while (rs.next()) {
            hasResults = true; // Hay al menos un Pok�mon
            int idPokemon = rs.getInt("id_pokemon"); // Aseg�rate de que "id_pokemon" sea el nombre correcto de la columna
            String nombrePokemon = rs.getString("nombre"); // Cambia "nombre" al nombre de la columna correspondiente
            String tipoPokemon = rs.getString("tipo"); // Cambia "tipo" al nombre de la columna correspondiente
%>
        <tr>
            <td><%= idPokemon %></td>
            <td><%= nombrePokemon %></td>
            <td><%= tipoPokemon %></td>
            <td>
            <td>
                 <a href="${pageContext.request.contextPath}/editarpokemonservlet?id=<%= idPokemon %>">Editar</a> |
                <a href="${pageContext.request.contextPath}/liberarpokemonservlet?id=<%= idPokemon %>">Liberar</a>
            </td>
            </td>
        </tr>
<%
        }

        if (!hasResults) {
%>
        <tr>
            <td colspan="4">No hay Pok�mon disponibles.</td>
        </tr>
<%
        }
%>
    </table>

    <br>
    <a href="agregarpokemon.jsp">Agregar Pok�mon</a> |
  
</body>
</html>

<%
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Cerrar conexiones
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
%>
