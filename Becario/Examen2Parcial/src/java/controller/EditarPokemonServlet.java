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

@WebServlet(name = "EditarPokemonServlet", urlPatterns = {"/editarpokemonservlet"})
public class EditarPokemonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String idPokemon = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {
            // Conexión a la base de datos
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();

            // Consulta SQL para obtener el Pokémon
            String sql = "SELECT * FROM pokemon WHERE id_pokemon = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPokemon);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int nivel = rs.getInt("nivel");

                // Mostrar formulario para editar Pokémon
                out.println("<h1>Editar Pokémon</h1>");
                out.println("<form method='post' action='editarpokemonservlet'>");
                out.println("ID: <input type='text' name='id_pokemon' value='" + idPokemon + "' readonly><br>");
                out.println("Nombre: <input type='text' name='nombre' value='" + nombre + "'><br>");
                out.println("Tipo: <input type='text' name='tipo' value='" + tipo + "'><br>");
                out.println("Nivel: <input type='number' name='nivel' value='" + nivel + "'><br>");
                out.println("<input type='submit' value='Guardar cambios'>");
                out.println("</form>");
            } else {
                out.println("No se encontró el Pokémon con ID: " + idPokemon);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al obtener el Pokémon: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String idPokemon = request.getParameter("id_pokemon");
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        int nivel = Integer.parseInt(request.getParameter("nivel"));

        try {
            // Conexión a la base de datos
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();

            // Actualizar el Pokémon en la base de datos
            String sql = "UPDATE pokemon SET nombre = ?, tipo = ?, nivel = ? WHERE id_pokemon = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, tipo);
            ps.setInt(3, nivel);
            ps.setString(4, idPokemon);
            int rowsUpdated = ps.executeUpdate();

            ps.close();
            conn.close();

            // Redirigir de nuevo a la página de almacenamiento de Pokémon o mostrar un mensaje
            if (rowsUpdated > 0) {
                response.getWriter().println("Pokémon actualizado exitosamente.");
                response.sendRedirect("/examen2parcial/jsp/pokemonstorage.jsp");  // Redirigir a la página principal de almacenamiento de Pokémon
            } else {
                response.getWriter().println("No se pudo actualizar el Pokémon.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar el Pokémon: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para editar un Pokémon";
    }
}
