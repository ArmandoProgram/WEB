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
 * Servlet para manejar el login de entrenador.
 */
@WebServlet(name = "LoginEntrenadorServlet", urlPatterns = {"/loginentrenador"})
public class LoginEntrenadorServlet extends HttpServlet {

    /**
     * Processes requests for HTTP <code>GET</code> method.
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
            // Recibir los parámetros del formulario
            String nombre = request.getParameter("nombre");
            String password = request.getParameter("password");

            // Validar que los campos no estén vacíos
            if (nombre == null || password == null || nombre.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "Debes ingresar todos los campos.");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                return;
            }

            // Conectar a la base de datos
            try {
                ConnectionBD conexion = new ConnectionBD();
                Connection conn = conexion.getConnectionBD();

                // Consulta SQL para verificar si el entrenador existe
                String sql = "SELECT id_entrenador FROM Entrenador WHERE nombre = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Si el entrenador es válido, guardar el ID en la sesión
                    int idEntrenador = rs.getInt("id_entrenador");
                    HttpSession session = request.getSession();
                    session.setAttribute("id_entrenador", idEntrenador);  // Guardar en sesión el "id_entrenador"
                    session.setAttribute("nombreEntrenador", nombre);

                    // Redirigir al almacenamiento de Pokémon
                    response.sendRedirect(request.getContextPath() + "/jsp/pokemonstorage.jsp");
                } else {
                    // Si no coincide, mostrar error
                    request.setAttribute("error", "Nombre o contraseña incorrectos.");
                    request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                }

                ps.close();
                rs.close();
                conn.close();

            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para iniciar sesión de un entrenador.";
    }// </editor-fold>

}


