<%-- 
    Document   : registroentrenador
    Created on : 14/10/2024, 08:43:32 PM
    Author     : arman
--%>

<!DOCTYPE html>
<html>
<head>
    <title>Registro de Entrenador</title>
</head>
<body>
    <h1>Registro de Entrenador</h1>
    <form method="post" action="${pageContext.request.contextPath}/registerentrenador">
        
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="fecha_nacimiento">Fecha de Nacimiento:</label>
        <input type="date" id="fecha_nacimiento" name="fecha_nacimiento" required><br><br>

        <label for="ciudad">Ciudad:</label>
        <input type="text" id="ciudad" name="ciudad" required><br><br>

        <input type="submit" value="Registrar">
    </form>
</body>
</html>
