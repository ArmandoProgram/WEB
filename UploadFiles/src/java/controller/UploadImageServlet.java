/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author arman
 */
@WebServlet(name = "UploadImageServlet", urlPatterns = {"/upload_image_servlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
            maxFileSize = 1024 * 1024 * 10, //10MB
            maxRequestSize = 1024 * 1024 * 50)
          //Ruta donde se guardan las imagenes (dentro del proyecto)
public class UploadImageServlet extends HttpServlet {
  //Ruta donde se guardan las imagenes (dentro del proyecto)
        private static final String UPLOAD_DIR = "images";
        
        Connection conn;
        PreparedStatement ps;
        Statement statement;
        ResultSet rs;
    
   private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }
            
    
   private void saveImagePathToDatabase(String imagePath) {
    try {
        ConnectionBD conexion = new ConnectionBD();
        conn = conexion.getConnectionBD();
        //Consulta a la base de datos
        String sql = "INSERT INTO imagenes (ruta_imagen) VALUES (?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, imagePath);
        ps.executeUpdate();
        int filasInsertadas = ps.executeUpdate();
        if (filasInsertadas > 0) {
            System.out.println("Imagen Registrada");
        } else {
            System.out.println("Imagen no Registrada");
        }
        ps.close();
        conn.close();
    } catch (Exception e) {
        // Manejo de excepciones
    }
}

   
    
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
            out.println("<title>Servlet UploadImageServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadImageServlet at " + request.getContextPath() + "</h1>");
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
        List<String> imagePaths = getImagePathsFromDatabase();
        request.setAttribute("imagePaths", imagePaths);
        
        //Redirigir a la JSP que muestra las imagenes
        request.getRequestDispatcher("/views/display_images.jsp").forward(request, response);
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
      
        // Obtener la ruta absoluta de la carpeta "images"
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // Crear la carpeta "images" si no existe
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
              uploadDir.mkdirs();
        }

        // Obtener la imagen subida
        Part part = request.getPart("image");
        String fileName = getFileName(part);

        // Guardar la imagen en el servidor (en la carpeta "images")
        String filePath = uploadFilePath + File.separator + fileName;
        part.write(filePath);

        //Guardsr la ruta relativa de la imagen (para almacenarla en la BD)
        String relativePath = UPLOAD_DIR + File.separator +fileName;
        System.out.print("Path: " + relativePath);
       
        
        // Guardar la ruta de la imagen en la base de datos
        saveImagePathToDatabase(relativePath);

        // Responder al usuario
        response.getWriter().println("Imagen subida correctamente. Ruta: " + relativePath);

        
    }
    
    private List<String> getImagePathsFromDatabase() {
        List<String> imagePaths = new ArrayList<>();
        try {
          ConnectionBD conexion = new ConnectionBD();
          String sql = "SELECT ruta_imagen FROM imagenes";

          // Consulta a la base de datos
          conn = conexion.getConnectionBD();
          PreparedStatement ps = conn.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
            imagePaths.add(rs.getString("ruta_imagen"));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return imagePaths;
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
