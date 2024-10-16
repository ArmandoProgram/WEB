/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para mostrar los Pokémon del entrenador.
 */
@WebServlet(name = "PokemonStorageServlet", urlPatterns = {"/pokemonstorageservlet"})
public class PokemonStorageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Integer idEntrenador = (Integer) session.getAttribute("id_entrenador");

        if (idEntrenador == null) {
            response.sendRedirect("jsp/login.jsp"); // Redirigir si no está logueado
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            // Conexión a la base de datos
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();

            // Consulta SQL para obtener los Pokémon del entrenador
            String sql = "SELECT * FROM Pokemon WHERE id_entrenador = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();

            // Generar tabla con los Pokémon
            out.println("<html><body>");
            out.println("<h1>Pokémon del Entrenador</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Nombre</th><th>Tipo</th><th>Acciones</th></tr>");

            boolean hasResults = false; // Variable para verificar si hay resultados

            while (rs.next()) {
                hasResults = true; // Hay al menos un Pokémon
                int idPokemon = rs.getInt("id_pokemon"); // Asegúrate de que "id_pokemon" sea el nombre correcto de la columna
                String nombrePokemon = rs.getString("nombre"); // Cambia "nombre" al nombre de la columna correspondiente
                String tipoPokemon = rs.getString("tipo"); // Cambia "tipo" al nombre de la columna correspondiente

                out.println("<tr>");
                out.println("<td>" + idPokemon + "</td>");
                out.println("<td>" + nombrePokemon + "</td>");
                out.println("<td>" + tipoPokemon + "</td>");
                out.println("<td><a href='editarpokemon?id=" + idPokemon + "'>Editar</a> | <a href='liberarpokemon?id=" + idPokemon + "'>Liberar</a></td>");
                out.println("</tr>");
            }

            if (!hasResults) {
                out.println("<tr><td colspan='4'>No hay Pokémon disponibles.</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            // Cerrar conexiones
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los Pokémon."); // Muestra un error si falla
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para mostrar Pokémon del entrenador";
    }
}


