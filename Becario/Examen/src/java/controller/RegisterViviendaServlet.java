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
 * Servlet para registrar una vivienda en la base de datos.
 */
@WebServlet(name = "RegisterViviendaServlet", urlPatterns = {"/registervivienda"})
public class RegisterViviendaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Recibir los parámetros del formulario
            String calle = request.getParameter("calle");
            String colonia = request.getParameter("colonia");
            String mun = request.getParameter("mun");
            String cp = request.getParameter("cp");

            // Obtener la CURP desde la sesión
            HttpSession session = request.getSession();
            String curp = (String) session.getAttribute("curp");

            // Validación básica de entrada
            if (calle == null || colonia == null || mun == null || cp == null || curp == null) {
                out.println("Todos los campos son obligatorios.");
                return;
            }

            try {
                // Conexión a la base de datos
                ConnectionBD conexion = new ConnectionBD();
                Connection conn = conexion.getConnectionBD();
                
                   // Verificar si ya existe una vivienda registrada para el CURP
                String checkSql = "SELECT COUNT(*) FROM Vivienda WHERE curp = ?";
                PreparedStatement checkPs = conn.prepareStatement(checkSql);
                checkPs.setString(1, curp);
                ResultSet checkRs = checkPs.executeQuery();
                
                 int count = 0;
                if (checkRs.next()) {
                    count = checkRs.getInt(1);
                }
                
                 if (count > 0) {
                    // Si ya existe una vivienda para este CURP, no permitir registrar otra
                    out.println("Ya tienes una vivienda registrada.");
                    response.sendRedirect("/examen/jsp/dashboard.jsp");
                }
                 
                 else {

                // SQL para insertar la vivienda
                String sql = "INSERT INTO Vivienda (calle, colonia, mun, cp, curp) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, calle);
                ps.setString(2, colonia);
                ps.setString(3, mun);
                ps.setString(4, cp);
                ps.setString(5, curp);  // Insertar la CURP obtenida de la sesión

                // Ejecutar la inserción
                int result = ps.executeUpdate();

                if (result > 0) {
                    out.println("Vivienda registrada exitosamente.");
                    
                    session.setAttribute("calle", calle);
                     session.setAttribute("colonia", colonia);
                      session.setAttribute("mun", mun);
                       session.setAttribute("cp", cp);
                       
                    response.sendRedirect("/examen/jsp/dashboard.jsp"); // Redirigir al dashboard o página principal
                
                } else {
                    out.println("Error al registrar la vivienda.");
                }

                ps.close();
                 }
                checkRs.close();
                checkPs.close();
                conn.close();

            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registrar una vivienda";
    }
}

