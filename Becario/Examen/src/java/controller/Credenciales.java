package controller;

import com.mysql.cj.xdevapi.Statement;
import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/credenciales")
public class Credenciales extends HttpServlet {

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Credenciales</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Credenciales at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperar los datos del formulario
        String curp = request.getParameter("curp");
        String apellidoPaterno = request.getParameter("apellido_paterno");
        String apellidoMaterno = request.getParameter("apellido_materno");
        String nombre = request.getParameter("nombre");
        String genero = request.getParameter("genero");
        String password = request.getParameter("password");

        // Extraer fecha de nacimiento de la CURP
        String fechaNacimientoStr = curp.substring(4, 10); // AAMMDD
        SimpleDateFormat formatoCurp = new SimpleDateFormat("yyMMdd");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formatoCurp.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            request.setAttribute("mensaje", "Error al extraer la fecha de nacimiento de la CURP");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        // Hashear la contraseña usando bcrypt
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Conectar a la base de datos
            ConnectionBD conexion = new ConnectionBD();

            // SQL para insertar los datos (sin la foto por ahora)
            String sql = "INSERT INTO becario (CURP, apellido_paterno, apellido_materno, nombre, genero, password, fecha_nac, foto) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);
            ps.setString(2, apellidoPaterno);
            ps.setString(3, apellidoMaterno);
            ps.setString(4, nombre);
            ps.setString(5, genero);
            ps.setString(6, hashPassword);
            ps.setDate(7, new java.sql.Date(fechaNacimiento.getTime()));

            // Ejecutar la consulta
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, redirigir al usuario a una página de éxito
                            HttpSession session = request.getSession();
                 session.setAttribute("apellidoPaterno", apellidoPaterno);
                     session.setAttribute("apellidoMaterno", apellidoMaterno);
                      session.setAttribute("nombre", nombre);
                       session.setAttribute("genero", genero);
                request.setAttribute("mensaje", "Becario registrado con éxito!");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            } else {
                // Si falló, redirigir a una página de error
                request.setAttribute("mensaje", "Error al registrar becario");
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            }

            // Cerrar recursos
            ps.close();
            conn.close();

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registrar becarios en la base de datos";
    }
}
