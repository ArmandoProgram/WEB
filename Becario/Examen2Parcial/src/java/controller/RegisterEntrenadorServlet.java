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
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para registrar a un nuevo entrenador en la base de datos.
 */
@WebServlet(name = "RegisterEntrenadorServlet", urlPatterns = {"/registerentrenador"})
public class RegisterEntrenadorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener parámetros del formulario de registro
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");
        String fechaNacimiento = request.getParameter("fecha_nacimiento");
        String ciudad = request.getParameter("ciudad");

        // Validar que ningún campo esté vacío
        if (nombre == null || password == null || fechaNacimiento == null || ciudad == null ||
            nombre.isEmpty() || password.isEmpty() || fechaNacimiento.isEmpty() || ciudad.isEmpty()) {
            try (PrintWriter out = response.getWriter()) {
                out.println("Todos los campos son obligatorios.");
            }
            return;
        }

        // Insertar los datos en la base de datos
        try {
            // Conexión a la base de datos
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();

            // Consulta SQL para insertar un nuevo entrenador
            String sql = "INSERT INTO Entrenador (nombre, password, fecha_nacimiento, ciudad) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, password);
            ps.setString(3, fechaNacimiento);
            ps.setString(4, ciudad);

            // Ejecutar la consulta
            int result = ps.executeUpdate();

            if (result > 0) {
                // Crear una sesión para el nuevo entrenador
                HttpSession session = request.getSession();
                session.setAttribute("nombreEntrenador", nombre);

                // Redirigir a la página de almacenamiento de Pokémon
                response.sendRedirect("/examen2parcial/jsp/login.jsp");
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("Error al registrar al entrenador. Inténtalo de nuevo.");
                }
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            try (PrintWriter out = response.getWriter()) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("jsp/registerentrenador.jsp"); // Redirigir a la página de registro si se intenta un GET
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registrar un nuevo entrenador";
    }
}
