/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "liberarpokemonservlet", urlPatterns = {"/liberarpokemonservlet"})
public class liberarpokemonservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPokemon = request.getParameter("id");

        try {
            // Conectar a la base de datos
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();

            // Consulta para eliminar el Pokémon
            String sql = "DELETE FROM pokemon WHERE id_pokemon = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPokemon);
            ps.executeUpdate();

            // Redirigir de nuevo a la página de almacenamiento de Pokémon
            response.sendRedirect("/examen2parcial/jsp/pokemonstorage.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al liberar Pokémon: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response); // Redirigir a una página de error
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para liberar un Pokémon del entrenador.";
    }
}
