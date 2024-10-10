<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession session_web = request.getSession(false);
    String matricula = null;

    if (session_web != null) {
        matricula = (String) session_web.getAttribute("matricula");
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Página de Bienvenida</title>
    <script type="text/javascript">
        var contador = 10; // Contador en segundos

        function iniciarCuentaRegresiva() {
            var intervalo = setInterval(function() {
                document.getElementById("contador").innerText = contador;
                contador--;

                if (contador < 0) {
                    clearInterval(intervalo);
                     // Mostrar alerta de que la sesión ha caducado
                    alert("La sesión ha caducado. Serás redirigido al inicio de sesión.");
                    // Redirigir al servlet para cerrar la sesión
                    window.location.href = "<%= request.getContextPath() %>/jsp/login.jsp";
                }
            }, 1000);
        }

        window.onload = iniciarCuentaRegresiva;
    </script>
</head>
<body>
    <h2>Bienvenido, <%= (matricula != null) ? matricula : "Invitado" %></h2>

    <% if (matricula != null) { %>
        <p>Has iniciado sesión correctamente.</p>
        <p>La sesión se cerrará automáticamente en <span id="contador">10</span> segundos.</p>
        <a href="<%= request.getContextPath() %>/jsp/login.jsp">Cerrar sesión ahora</a>
    <% } else { %>
        <p>No has iniciado sesión.</p>
        <a href="login.jsp">Iniciar sesión</a>
    <% } %>
</body>
</html>

    