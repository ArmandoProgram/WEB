<%-- 
    Document   : editarpokemon
    Created on : 15/10/2024, 01:11:05 AM
    Author     : arman
--%>

<%@ page import="java.sql.*" %>
<%@ page import="configuration.ConnectionBD" %>
<%
    int idPokemon = Integer.parseInt(request.getParameter("id")); // Obtener el ID del Pokémon desde la URL
    ConnectionBD conexion = new ConnectionBD();
    Connection conn = conexion.getConnectionBD();
    String nombrePokemon = "";
    String tipoPokemon = "";

    // Obtener los datos del Pokémon
    try {
        String sql = "SELECT * FROM Pokemon WHERE id = ?"; // Asegúrate de que "id" es el nombre de la columna en tu tabla
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idPokemon);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            nombrePokemon = rs.getString("nombre");
            tipoPokemon = rs.getString("tipo");
        }

        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        conn.close(); // Cerrar la conexión
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Editar Pokémon</title>
</head>
<body>
    <h1>Editar Pokémon</h1>
    <form method="post" action="${pageContext.request.contextPath}/editarpokemonservlet">
        <input type="hidden" name="id" value="<%= idPokemon %>">
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" value="<%= nombrePokemon %>" required><br>
        <label for="tipo">Tipo:</label>
        <input type="text" name="tipo" value="<%= tipoPokemon %>" required><br>
        <input type="submit" value="Actualizar">
    </form>
                <a href="./pokemonstorage.jsp">Regreso</a>

</body>
</html>

