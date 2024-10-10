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
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; // Importación añadida
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author arman
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use the following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //oBTENER LA SESION ACTUAL
            HttpSession session = request.getSession(false); //No crear nueva sesion
            if (session != null) {
            //invalidar la sesion actual
            session.invalidate();
            }
            //Redirigir al usuario a la pagina de login
            response.sendRedirect("/httpsession/jsp/login.jsp");
            
             if (session != null) {
        session.invalidate(); // Cierra la sesión
    }
    response.sendRedirect("login.jsp"); // Redirige a la página de inicio de sesión
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la matricula y password del formulario
        String matricula = request.getParameter("matricula");
        String password = request.getParameter("password");

        // Operaciones con la base de datos
        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();
            // consulta a la base de datos
            String sql = "SELECT password FROM autenticacion WHERE matricula = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hashPassword = rs.getString("password");
                if (password.equals(hashPassword)) {
                    // Crear una sesión
                    HttpSession session = request.getSession();
                    // Guardar el nombre del usuario en la sesión
                    session.setAttribute("matricula", matricula);
                    // Redirigir a la página de bienvenida
                    response.sendRedirect("/httpsesion/jsp/usuario.jsp");
                } else {
                    request.setAttribute("error", "Credenciales Incorrectas");
                    request.getRequestDispatcher("/httpsesion/jsp/login.jsp").forward(request, response); // Corrección aquí
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado");
                request.getRequestDispatcher("/httpsesion/jsp/login.jsp").forward(request, response); // Corrección aquí
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    } // </editor-fold>
}
