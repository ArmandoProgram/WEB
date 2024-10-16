/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Redirector {

    // Método para redirigir a la página de registro de vivienda
    public static void redirectToRegistroVivienda(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/registrovivienda.jsp").forward(request, response);
    }

    // Método para redirigir a la página de consulta de datos
    public static void redirectToConsultaDatos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/consultadatos.jsp").forward(request, response);
    }
    
    
}
