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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ConsultarDatosServlet", urlPatterns = {"/consultardatosservlet"})
public class ConsultarDatosServlet extends HttpServlet {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String curp = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("curp")) {
                    curp = cookie.getValue();
                    break;
                }
            }
        }

        if (curp == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();

            // Consultar datos del becario
            String sql = "SELECT nombre, apellido_paterno, apellido_materno " +
                         "FROM becario WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);
            rs = ps.executeQuery();

            String nombre = null;
            String apellidoPaterno = null;
            String apellidoMaterno = null;

            if (rs.next()) {
                nombre = rs.getString("nombre");
                apellidoPaterno = rs.getString("apellido_paterno");
                apellidoMaterno = rs.getString("apellido_materno");
            }

            // Obtener los datos de la sesión
            HttpSession session = request.getSession();
            String calle = (String) session.getAttribute("calle");
            String colonia = (String) session.getAttribute("colonia");
            String municipio = (String) session.getAttribute("mun");
            String cp = (String) session.getAttribute("cp");

            // Pasar los datos al JSP
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidoPaterno", apellidoPaterno);
            request.setAttribute("apellidoMaterno", apellidoMaterno);
            request.setAttribute("calle", calle);
            request.setAttribute("colonia", colonia);
            request.setAttribute("municipio", municipio);
            request.setAttribute("cp", cp);

        } catch (Exception e) {
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/jsp/consultardatos.jsp").forward(request, response);
    }
}


