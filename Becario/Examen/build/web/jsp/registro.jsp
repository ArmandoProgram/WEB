<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de Becario</title>
    </head>
    <body>
        <h1>Registro de Becario</h1>
        
        <form method="post" action="${pageContext.request.contextPath}/credenciales">
            <!-- CURP -->
            CURP: <br>
            <input type="text" name="curp" id="curp" maxlength="18" required><br><br>

            <!-- Apellido Paterno -->
            Apellido Paterno: <br>
            <input type="text" name="apellido_paterno" id="apellido_paterno" required><br><br>

            <!-- Apellido Materno -->
            Apellido Materno: <br>
            <input type="text" name="apellido_materno" id="apellido_materno" required><br><br>

            <!-- Nombre -->
            Nombre: <br>
            <input type="text" name="nombre" id="nombre" required><br><br>

            <!-- Género (Botones de radio) -->
            Género: <br>
            <input type="radio" name="genero" id="genero_masculino" value="M" required>
            <label for="genero_masculino">Masculino</label><br>
            
            <input type="radio" name="genero" id="genero_femenino" value="F" required>
            <label for="genero_femenino">Femenino</label><br><br>

            <!-- Contraseña -->
            Contraseña: <br>
            <input type="password" name="password" id="password" required><br><br>

            <!-- Botón de envío -->
            <input type="submit" value="Registrar">
        </form>
    </body>
</html>
