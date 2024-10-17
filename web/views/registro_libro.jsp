<%-- 
    Document   : registro.jsp
    Created on : 14/10/2024, 08:39:45 PM
    Author     : florc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Libros</title>
    </head>
    <body>
        <h1>Registro de libros</h1>
        <form action="${pageContext.request.contextPath}/libro_servlet" method="POST">
            TITULO:
            <input type="text" name="titulo" id="titulo" required><br>
            AUTOR:
            <input type="text" name="autor" id="autor" required><br>
            PAGINAS:
            <input type="number" step="0" name="paginas" id="paginas" required><br>
            FECHA DE PUBLICACIÓN:
            <input type="date" name="fecha_publicacion" id="fecha_publicacion" required><br>
            ISBN:
            <input type="text" maxlength="17" minlength="17" name="isbn" id="isbn" required><br>
            PRECIO:
            <input type="number" step="0.01" name="precio" id="precio" required>
            <br><br>
            <input type="submit" name="accion" value="Agregar">
        </form>
            <br>
        <a href="${pageContext.request.contextPath}/libro_servlet">Lista de Libros</a> <br>
        <a href="${pageContext.request.contextPath}/views/registro_usuario.jsp">Registro de usuario</a><br>
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
