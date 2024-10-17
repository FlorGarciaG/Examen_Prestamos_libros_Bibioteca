<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro usuario</title>
    </head>
    <body>
        <h1>Registro de usuarios</h1>
        <form action="${pageContext.request.contextPath}/usuario_servlet" method="POST">
            NOMBRE:
            <input type="text" maxlength="30" name="nombre" id="nombre" required><br>
            APELLIDO PATERNO:
            <input type="text" maxlength="30" name="apellidoP" id="apellidoP" required><br>
            APELLIDO MATERNO:
            <input type="text" maxlength="30" name="apellidoM" id="apellidoM" required><br>                
            CORREO:
            <input type="email" maxlength="100" name="correo" id="correo" required><br>
            TELEFONO:
            <input type="tel" maxlength="10" name="telefono" id="telefono" required>
            <br><br>                        
            <input type="submit" name="accion" value="Agregar">
        </form>
        <br>
        <a href="${pageContext.request.contextPath}/usuario_servlet">Lista de usuarios</a> <br>
        <a href="${pageContext.request.contextPath}/views/registro_libro.jsp">Registro de libro</a><br>
        <a href="${pageContext.request.contextPath}/views/registro_prestamo.jsp">Registro de prestamo</a>
        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
        <script>
            alert("<%= mensaje%>");
        </script>
        <%
            }
        %>
    </body>
</html>
