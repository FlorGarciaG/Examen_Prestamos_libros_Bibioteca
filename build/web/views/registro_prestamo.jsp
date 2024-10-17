<%@page import="Model.UsuarioModel"%>
<%@page import="Model.LibroModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="Configuration.ConnectionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro prestamos</title>
    </head>
    <body>
        <%
            ConnectionBD conexion = new ConnectionBD();
            Connection conn = conexion.getConnectionBD();
            PreparedStatement ps = null;
            ResultSet rs = null;
            List<LibroModel> libros = new ArrayList<>();
            List<UsuarioModel> usuarios = new ArrayList<>();

            try {
                // Consulta para obtener los libros
                String sqlLibros = "SELECT id_libro, titulo FROM libro";
                ps = conn.prepareStatement(sqlLibros);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int idLibro = rs.getInt("id_libro");
                    String titulo = rs.getString("titulo");
                    libros.add(new LibroModel(idLibro, titulo));
                }

                // Cerrar ResultSet y PreparedStatement antes de la nueva consulta
                rs.close();
                ps.close();

                // Consulta para obtener los usuarios
                String sqlUsuarios = "SELECT id_usuario, nombre FROM usuario";
                ps = conn.prepareStatement(sqlUsuarios);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");
                    String nombreUsuario = rs.getString("nombre");
                    usuarios.add(new UsuarioModel(idUsuario, nombreUsuario));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>
        <h1>Registro de prestamos</h1>
        <form action="${pageContext.request.contextPath}/prestamo_servlet" method="POST">
            ID DEL LIBRO:
            <select name="id_libro" id="id_libro" required>
                <option value="" disabled selected>Seleccione un libro</option>
                <%
                    for (LibroModel libro : libros) {
                %>
                <option value="<%= libro.getIdLibro()%>"><%= libro.getTitulo()%></option>
                <%
                    }
                %>
            </select><br>
            ID DEL USUARIO:
            <select name="id_usuario" id="id_usuario" required>
                <option value="" disabled selected>Seleccione un usuario</option>
                <%
                    for (UsuarioModel user : usuarios) {
                %>
                <option value="<%= user.getIdUsuario()%>"><%= user.getNombre()%></option>
                <%
                    }
                %>
            </select><br>
            FECHA DE PRESTAMO:
            <input type="date" name="fecha_prestamo" id="fecha_prestamo" required><br>                
            HORA DE PRESTAMO:
            <input type="time" name="hora_prestamo" step="1" id="hora_prestamo" required><br>
            FECHA DE DEVOLUCION:
            <input type="date" name="fecha_devolucion" id="fecha_devolucion"><br>                
            HORA DE DEVOLUCION:
            <input type="time" name="hora_devolucion" step="1" id="hora_devolucion"> 
            <br><br>                        
            <input type="submit" name="accion" value="Agregar">
        </form>
        <br>
        <a href="${pageContext.request.contextPath}/prestamo_servlet">Lista de prestamos</a> <br>
        <a href="${pageContext.request.contextPath}/views/registro_libro.jsp">Registro de libro</a><br>
        <a href="${pageContext.request.contextPath}/views/registro_usuario.jsp">Registro de usuario</a>
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
