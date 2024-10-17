<%@page import="Model.PrestamoModel"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prestamos</title>
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
            function eliminarPrestamo(id) {
                console.log(`eliminarPrestamo?id=` + id);
                if (confirm("¿Estás seguro de que quieres eliminar este prestamo?")) {
                    fetch(`prestamo_servlet?id=` + id, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            alert('Prestamo eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar prestamo');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>
    </head>
    <body>
        <h2>Lista de Prestamos</h2>
        <a href="${pageContext.request.contextPath}/views/registro_prestamo.jsp">Registrar prestamo</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>LIBRO</th>
                    <th>USUARIO</th>
                    <th>FECHA DE PRESTAMO</th>
                    <th>HORA DE PRESTAMO</th>
                    <th>FECHA DE DEVOLUCIÓN</th>
                    <th>HORA DE DEVOLUCIÓN</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<PrestamoModel> listaPrestamos = (ArrayList<PrestamoModel>) request.getAttribute("prestamos");
                    System.out.println(listaPrestamos);
                    if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
                        for (PrestamoModel prestamo : listaPrestamos) {
                %>
                <tr>
                    <td><%= prestamo.getIdPrestamo()%></td>
                    <td><%= prestamo.getIdLibro()%></td>
                    <td><%= prestamo.getIdUsuario()%></td>
                    <td><%= prestamo.getFechaPrestamo()%></td>
                    <td><%= prestamo.getHoraPrestamo()%></td>
                    <td><%= prestamo.getFechaDevolucion()%></td>
                    <td><%= prestamo.getHoraDevolucion()%></td>

                    <td> <button onclick="eliminarPrestamo(<%= prestamo.getIdPrestamo()%>)">Eliminar</button> </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/views/actualizar_prestamos.jsp" method="GET"> 
                            <input type="hidden" name="id" value="<%= prestamo.getIdPrestamo()%>"> 
                            <input type="submit" value="Actualizar"> 
                        </form> 
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay prestamos guardados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
