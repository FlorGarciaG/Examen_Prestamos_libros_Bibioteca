<%@page import="Model.UsuarioModel"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuarios</title>
        <style>
            table {
                width: 80%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
        <script>
            function eliminarUser(id) {
                console.log(`eliminarUser?id=` + id);
                if (confirm("¿Estás seguro de que quieres eliminar este usuario?")) {
                    fetch(`usuario_servlet?id=` + id, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            alert('Usuario eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar usuario');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>
    </head>
    <body>
        <h2>Lista de Usuarios</h2>
        <a href="${pageContext.request.contextPath}/views/registro_usuario.jsp">Registrar Usuario</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <th>APELLIDO PATERNO</th>
                    <th>APELLIDO MATERNO</th>
                    <th>CORREO</th>
                    <th>TELEFONO</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<UsuarioModel> listaUsers = (ArrayList<UsuarioModel>) request.getAttribute("libros");

                    if (listaUsers != null && !listaUsers.isEmpty()) {
                        for (UsuarioModel user : listaUsers) {
                %>
                <tr>
                    <td><%= user.getIdUsuario()%></td>
                    <td><%= user.getNombre()%></td>
                    <td><%= user.getApellidoP()%></td>
                    <td><%= user.getApellidoM()%></td>
                    <td><%= user.getCorreo()%></td>
                    <td><%= user.getTelefono()%></td>

                    <td> <button onclick="eliminarUser(<%= user.getIdUsuario()%>)">Eliminar</button> </td>

                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay usuarios registrados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
