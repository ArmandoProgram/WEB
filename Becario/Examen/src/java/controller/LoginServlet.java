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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author arman
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginservlet"})
public class LoginServlet extends HttpServlet {
    Connection conn;
    PreparedStatement ps;
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
           //OBTENER LA SESION ACTUAL
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
         // Obtener los datos enviados desde el formulario
        String curp = request.getParameter("curp");
        String password = request.getParameter("password");

        // Conectar a la base de datos
        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();

            // Consulta para obtener los datos del becario basado en la CURP
            String sql = "SELECT password FROM becario WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                // Verificar la contraseña con bcrypt
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Si la contraseña es correcta, crear sesión y redirigir
                    HttpSession session = request.getSession();
                    session.setAttribute("curp", curp);
                    response.sendRedirect("/examen/jsp/dashboard.jsp");  // Cambiar a la página a la que desees redirigir tras el login exitoso
                } else {
                    // Contraseña incorrecta
                    request.setAttribute("error", "Contraseña incorrecta.");
                    request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                }
            } else {
                // CURP no encontrada
                request.setAttribute("error", "CURP no encontrada.");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    }// </editor-fold>

}
