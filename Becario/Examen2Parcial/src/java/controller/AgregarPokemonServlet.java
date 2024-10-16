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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para agregar un nuevo Pokémon a la base de datos.
 */
@WebServlet(name = "AgregarPokemonServlet", urlPatterns = {"/agregarpokemon"})
public class AgregarPokemonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String tipo = request.getParameter("tipo");
            String nivelStr = request.getParameter("nivel");

            // Validar que los campos no estén vacíos
            if (nombre == null || tipo == null || nivelStr == null ||
                nombre.isEmpty() || tipo.isEmpty() || nivelStr.isEmpty()) {
                out.println("Todos los campos son obligatorios.");
                return;
            }

            // Convertir nivel a entero
            int nivel;
            try {
                nivel = Integer.parseInt(nivelStr);
            } catch (NumberFormatException e) {
                out.println("El nivel debe ser un número.");
                return;
            }

            // Obtener ID del entrenador desde la sesión
            HttpSession session = request.getSession();
            Integer idEntrenador = (Integer) session.getAttribute("id_entrenador");
            if (idEntrenador == null) {
                out.println("Debes iniciar sesión primero.");
                return;
            }

            // Insertar Pokémon en la base de datos
            try {
                ConnectionBD conexion = new ConnectionBD();
                Connection conn = conexion.getConnectionBD();

                String sql = "INSERT INTO Pokemon (nombre, tipo, nivel, id_entrenador) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, tipo);
                ps.setInt(3, nivel);
                ps.setInt(4, idEntrenador);

                int result = ps.executeUpdate();
                if (result > 0) {
                    out.println("Pokémon agregado exitosamente.");
                    response.sendRedirect("/examen2parcial/jsp/pokemonstorage.jsp");
                } else {
                    out.println("Error al agregar el Pokémon. Inténtalo de nuevo.");
                }

                ps.close();
                conn.close();
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/agregarpokemon.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para agregar un nuevo Pokémon";
    }
}
