<%@page import="java.util.ArrayList"%>
<%@page import="Model.LibroModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Libros</title>
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
            function eliminarLibro(id) {
                console.log(`eliminarLibro?id=` + id);
                if (confirm("¿Estás seguro de que quieres eliminar este libro?")) {
                    fetch(`libro_servlet?id=` + id, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            alert('Libro eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar libro');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>
    </head>
    <body>
        <h2>Lista de Libros</h2>
        <a href="${pageContext.request.contextPath}/views/registro_libro.jsp">Registrar Libro</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>TITULO</th>
                    <th>AUTOR</th>
                    <th>PAGINAS</th>
                    <th>FECHA DE PUBLICACION</th>
                    <th>ISBN</th>
                    <th>PRECIO</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<LibroModel> listaLibros = (ArrayList<LibroModel>) request.getAttribute("libros");

                    if (listaLibros != null && !listaLibros.isEmpty()) {
                        for (LibroModel libro : listaLibros) {
                %>
                <tr>
                    <td><%= libro.getIdLibro()%></td>
                    <td><%= libro.getTitulo()%></td>
                    <td><%= libro.getAutor()%></td>
                    <td><%= libro.getPaginas()%></td>
                    <td><%= libro.getFecha_publicacion()%></td>
                    <td><%= libro.getIsbn()%></td>
                    <td><%= libro.getPrecio()%></td>

                    <td> <button onclick="eliminarLibro(<%= libro.getIdLibro()%>)">Eliminar</button> </td>

                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay libros registrados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
