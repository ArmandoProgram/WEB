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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author arman
 */
@WebServlet("/login_servlet")
public class LoginServlet extends HttpServlet {
    
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
    // Invalidar la sesión del usuario si existe
    HttpSession session = request.getSession(false); // No crear una nueva sesión si no existe
    if (session != null) {
        session.invalidate();
    }

    // Eliminar cookie
    Cookie[] cookies = request.getCookies(); // Se debe llamar a `request.getCookies()`
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("matricula".equals(cookie.getName())) { // Usar el método `getName()` del objeto `cookie`
                cookie.setMaxAge(0); // Eliminar la cookie
                cookie.setPath("/"); // Asegurarse de que se elimine para toda la aplicación
                response.addCookie(cookie);
                break;
            }
        }
    }

    // Redirigir al login
    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp"); // Usar `getContextPath` para la ruta correcta
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

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Conectar a la base de datos
        ConnectionBD conexion = new ConnectionBD();
        conn = conexion.getConnectionBD();

        // Consulta a la base de datos
        String sql = "SELECT password FROM autenticaction WHERE matricula = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, matricula);

        rs = ps.executeQuery();

        if (rs.next()) {
            String hashPassword = rs.getString("password");
            if (BCrypt.checkpw(password, hashPassword)) {
                // Cuando la contraseña es correcta creará la cookie
                Cookie cookie = new Cookie("matricula", matricula);

                if (request.getParameter("recordar") != null
                        && request.getParameter("recordar").equals("on")) {
                    cookie.setMaxAge(60 * 60 * 24); // Vida de 24 horas
                } else {
                    cookie.setMaxAge(60 * 5); // Vida de 5 minutos
                }

                // Establecer la ruta
                cookie.setPath("/");
                // Agregar la cookie a la respuesta
                response.addCookie(cookie);

                // Redirigir a usuario.jsp
                request.getRequestDispatcher("/jsp/usuario.jsp").forward(request, response);
            } else {
                // Contraseña incorrecta
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }
        } else {
            // Matricula no encontrada
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }

        // Cerrar recursos
        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
}

        
        
        //("000009999".equals(matricula) && "1234".equals(password)) {
            // Si la matricula y pass son correctos abrir usuario.jsp
        //    request.getRequestDispatcher("/jsp/usuario.jsp").forward(request, response);
            //validar la selección del checkbox
        //    try{
        //       if (request.getParameter("recordar") !=null
        //               && request.getParameter("recordar").equals("on")){
        //       Cookie cookie = new Cookie("matricula", matricula);
        //       cookie.setMaxAge(60*5); //Vida de 5 minutos
        //       response.addCookie(cookie);
         //      } else {
        //           Cookie cookie = new Cookie("matricula", matricula);
        //           cookie.setMaxAge(60 *1);//Vida de 1 minuto
        //           response.addCookie(cookie);
        //       }
        //    } catch (Exception e) {
        //        System.out.println("Error: " + e);
        //    }
        //} else {
            //Si la matricula y pass no son correctos abirr error.jsp
       // request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
       // }
    

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
