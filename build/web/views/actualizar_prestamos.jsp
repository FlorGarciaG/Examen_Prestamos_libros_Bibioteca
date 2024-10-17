<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.LibroModel"%>
<%@page import="Model.UsuarioModel"%>
<%@page import="Configuration.ConnectionBD"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"> 
        <title>Actualizar prestamo</title>
    </head>
    <body>
        <h2>Actualizar prestamo</h2> 
        <%
            String id_prestamo = request.getParameter("id");
            String id_libro = "";
            String id_usuario = "";
            String fecha_prestamo = "";
            String hora_prestamo = "";
            String fecha_devolucion = "";
            String hora_devolucion = "";

            ConnectionBD conexion = new ConnectionBD();
            Connection connection = conexion.getConnectionBD();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List<LibroModel> libros = new ArrayList<>();
            List<UsuarioModel> usuarios = new ArrayList<>();

            try {
                // Consulta para obtener los datos del préstamo por ID 
                String sqlPrestamo = "SELECT id_prestamo, id_libro, id_usuario, fecha_prestamo,"
                        + " hora_prestamo, fecha_devolucion, hora_devolucion FROM prestamos WHERE id_prestamo = ?";
                statement = connection.prepareStatement(sqlPrestamo);
                statement.setString(1, id_prestamo);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    id_libro = resultSet.getString("id_libro");
                    id_usuario = resultSet.getString("id_usuario");
                    fecha_prestamo = resultSet.getString("fecha_prestamo");
                    hora_prestamo = resultSet.getString("hora_prestamo");
                    fecha_devolucion = resultSet.getString("fecha_devolucion");
                    hora_devolucion = resultSet.getString("hora_devolucion");
                }

                // Cerrar ResultSet antes de hacer otra consulta
                resultSet.close();
                statement.close();

                // Consulta para obtener los libros disponibles
                String sqlLibros = "SELECT id_libro, titulo FROM libro";
                statement = connection.prepareStatement(sqlLibros);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int libroId = resultSet.getInt("id_libro");
                    String titulo = resultSet.getString("titulo");
                    libros.add(new LibroModel(libroId, titulo));
                }

                // Cerrar ResultSet y PreparedStatement antes de hacer otra consulta
                resultSet.close();
                statement.close();

                // Consulta para obtener los usuarios disponibles
                String sqlUsuarios = "SELECT id_usuario, nombre FROM usuario";
                statement = connection.prepareStatement(sqlUsuarios);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int usuarioId = resultSet.getInt("id_usuario");
                    String nombre = resultSet.getString("nombre");
                    usuarios.add(new UsuarioModel(usuarioId, nombre));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>

        <!-- Formulario con los datos del usuario para actualizar --> 
        <form id="formActualizarPrestamo"> 
            ID PRESTAMO: <br>
            <input type="number" id="id_prestamo" value="<%= id_prestamo%>" disabled><br>

            ID DEL LIBRO:
            <select name="id_libro" id="id_libro" required>
                <option disabled>Seleccione un libro</option>
                <%
                    for (LibroModel libro : libros) {
                        String selected = (libro.getIdLibro() == Integer.parseInt(id_libro)) ? "selected" : "";
                %>
                <option value="<%= libro.getIdLibro()%>" <%= selected%>><%= libro.getTitulo()%></option>
                <%
                    }
                %>
            </select><br>

            ID DEL USUARIO:
            <select name="id_usuario" id="id_usuario" required>
                <option disabled>Seleccione un usuario</option>
                <%
                    for (UsuarioModel user : usuarios) {
                        String selected = (user.getIdUsuario() == Integer.parseInt(id_usuario)) ? "selected" : "";
                %>
                <option value="<%= user.getIdUsuario()%>" <%= selected%>><%= user.getNombre()%></option>
                <%
                    }
                %>
            </select><br>

            FECHA DE PRESTAMO:
            <input type="date" name="fecha_prestamo" id="fecha_prestamo" value="<%= fecha_prestamo%>" required><br>                
            HORA DE PRESTAMO:
            <input type="time" name="hora_prestamo" step="1"  id="hora_prestamo" value="<%= hora_prestamo%>" required><br>
            FECHA DE DEVOLUCION:
            <input type="date" name="fecha_devolucion" id="fecha_devolucion" value="<%= fecha_devolucion%>" required><br>                
            HORA DE DEVOLUCION:
            <input type="time" name="hora_devolucion" step="1" id="hora_devolucion" value="<%= hora_devolucion%>" required> 
            <br><br>
            <input type="button" value="Actualizar" onclick="actualizarPrestamo()"><br>
            <a href="${pageContext.request.contextPath}/prestamo_servlet">Lista de prestamos</a>
        </form> 
        <br>
        <div id="resultado"></div> 
        <script>
            function actualizarPrestamo() {
                const id_prestamo = document.getElementById("id_prestamo").value;
                const id_libro = document.getElementById("id_libro").value;
                const id_usuario = document.getElementById("id_usuario").value;
                const fecha_prestamo = document.getElementById("fecha_prestamo").value;
                const hora_prestamo = document.getElementById("hora_prestamo").value;
                const fecha_devolucion = document.getElementById("fecha_devolucion").value;
                const hora_devolucion = document.getElementById("hora_devolucion").value;

                const datos = {
                    id_prestamo: id_prestamo,
                    id_libro: id_libro,
                    id_usuario: id_usuario,
                    fecha_prestamo: fecha_prestamo,
                    hora_prestamo: hora_prestamo,
                    fecha_devolucion: fecha_devolucion,
                    hora_devolucion: hora_devolucion
                };

                console.log("Datos:", JSON.stringify(datos));

                fetch(`${pageContext.request.contextPath}/prestamo_servlet?id=` + id_prestamo, {
                    method: "PUT",
                    body: JSON.stringify(datos),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                })

                        .then(response => {
                            if (response.ok) {
                                return response.text();
                            } else {
                                throw new Error('Error en la actualización');
                            }
                        })
                        .then(data => {
                            console.log(data);
                            alert("Prestamo actualizado exitosamente");
                        })
                        .catch(error => {
                            alert("Error al actualizar prestamo.")
                            console.error('Error:', error);
                        });
            }

        </script> 

    </body>
</html>
