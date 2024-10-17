/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Configuration.ConnectionBD;
import Model.PrestamoModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author florc
 */
@WebServlet(name = "prestamo_servlet", urlPatterns = {"/prestamo_servlet"})
public class prestamo_servlet extends HttpServlet {

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet prestamo_servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet prestamo_servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        List<PrestamoModel> listaPrestamos = new ArrayList<>();
        String sql = "SELECT id_prestamo, id_libro, id_usuario, fecha_prestamo, "
                + "hora_prestamo, fecha_devolucion, hora_devolucion FROM prestamos";

        try {
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Itera sobre los resultados y crea objetos UsuarioModel
            while (rs.next()) {
                PrestamoModel prestamo = new PrestamoModel();
                int idFinal = Integer.parseInt(rs.getString("id_prestamo"));
                int idLibroFinal = Integer.parseInt(rs.getString("id_libro"));
                int idUserFinal = Integer.parseInt(rs.getString("id_usuario"));
                String fechaprestamo = rs.getString("fecha_prestamo");
                String horaprestamo = rs.getString("hora_prestamo");
                String fechadevolucion = rs.getString("fecha_devolucion");
                String horadevolucion = rs.getString("hora_devolucion");

                prestamo.setIdPrestamo(idFinal);
                prestamo.setIdLibro(idLibroFinal);
                prestamo.setIdUsuario(idUserFinal);

                Date fechaPrefinal = null;
                if (fechaprestamo != null && !fechaprestamo.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date utilDate = sdf.parse(fechaprestamo);
                        fechaPrefinal = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Time horaPrefinal = null;
                if (horaprestamo != null && !horaprestamo.isEmpty()) {
                    try {
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                        java.util.Date utilTime = sdfTime.parse(horaprestamo);
                        horaPrefinal = new java.sql.Time(utilTime.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Date fechaDevfinal = null;
                if (fechadevolucion != null && !fechadevolucion.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date utilDate = sdf.parse(fechadevolucion);
                        fechaDevfinal = new java.sql.Date(utilDate.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Time horaDevfinal = null;
                if (horadevolucion != null && !horadevolucion.isEmpty()) {
                    try {
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                        java.util.Date utilTime = sdfTime.parse(horadevolucion);
                        horaDevfinal = new java.sql.Time(utilTime.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                prestamo.setFechaPrestamo(fechaPrefinal);
                prestamo.setHoraPrestamo(horaPrefinal);
                prestamo.setFechaDevolucion(fechaDevfinal);
                prestamo.setHoraDevolucion(horaDevfinal);

                listaPrestamos.add(prestamo);
            }

            // Pasa la lista de usuarios al JSP
            request.setAttribute("prestamos", listaPrestamos);
            request.getRequestDispatcher("/views/mostrar_prestamos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los libros" + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();
        // Obtener los parámetros del formulario 
        String idLibro = request.getParameter("id_libro");
        String idUsuario = request.getParameter("id_usuario");
        String fechaprestamo = request.getParameter("fecha_prestamo");
        String horaprestamo = request.getParameter("hora_prestamo");
        String fechadevolucion = request.getParameter("fecha_devolucion");
        String horadevolucion = request.getParameter("hora_devolucion");

        Date fechaPrefinal = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(fechaprestamo);
            fechaPrefinal = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Time horaPrefinal = null;
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            java.util.Date utilTime = sdfTime.parse(horaprestamo);
            horaPrefinal = new java.sql.Time(utilTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date fechaDevfinal = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(fechadevolucion);
            fechaDevfinal = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Time horaDevfinal = null;
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            java.util.Date utilTime = sdfTime.parse(horadevolucion);
            horaDevfinal = new java.sql.Time(utilTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            // Crear la consulta SQL para insertar el usuario 
            String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo, hora_prestamo, fecha_devolucion, hora_devolucion)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, idLibro);
            ps.setString(2, idUsuario);
            ps.setDate(3, fechaPrefinal);
            ps.setTime(4, horaPrefinal);
            ps.setDate(5, fechaDevfinal);
            ps.setTime(6, horaDevfinal);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, redirigir al usuario a una página de éxito 
                request.setAttribute("mensaje", "Prestamo guardado con éxito!");
            } else {
                // Si falló, redirigir a una página de error 
                request.setAttribute("mensaje", "Error al guardar prestamo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
        } finally {
            // Cerrar los recursos 
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("views/registro_prestamo.jsp").forward(request, response);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String id = request.getParameter("id");
        // Validate input
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid request
            return;
        }

        String sql = "DELETE FROM prestamos WHERE id_prestamo like ?";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // Eliminar exitoso 
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // No se encontró el usuario 
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Error del servidor 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();
        String id_prestamo = "";
        String id_libro = "";
        String id_usuario = "";
        String fecha_prestamo = "";
        String hora_prestamo = "";
        String fecha_devolucion = "";
        String hora_devolucion = "";

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String requestBody = sb.toString();
            //System.out.println(requestBody);
            requestBody = requestBody.replaceAll("[{}\"]", "");
            String[] pairs = requestBody.split(",");

            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);

                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "id_prestamo":
                        id_prestamo = value;
                        break;
                    case "id_libro":
                        id_libro = value;
                        break;
                    case "id_usuario":
                        id_usuario = value;
                        break;
                    case "fecha_prestamo":
                        fecha_prestamo = value;
                        break;
                    case "hora_prestamo":
                        hora_prestamo = value;
                        break;
                    case "fecha_devolucion":
                        fecha_devolucion = value;
                        break;
                    case "hora_devolucion":
                        hora_devolucion = value;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error parsing JSON request body.");
            return;
        }

        if (id_prestamo == null || id_prestamo.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Id not found in request body");
            return;
        }

        Date fechaPrefinal = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(fecha_prestamo);
            fechaPrefinal = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Time horaPrefinal = null;

        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            java.util.Date utilTime = sdfTime.parse(hora_prestamo);
            horaPrefinal = new java.sql.Time(utilTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date fechaDevfinal = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(fecha_devolucion);
            fechaDevfinal = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Time horaDevfinal = null;
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            java.util.Date utilTime = sdfTime.parse(hora_devolucion);
            horaDevfinal = new java.sql.Time(utilTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            conn = conexion.getConnectionBD();

            String sql = "UPDATE prestamos SET id_libro = ?, id_usuario = ?, fecha_prestamo = ?, hora_prestamo = ?, "
                    + "fecha_devolucion = ?, hora_devolucion = ? WHERE id_prestamo = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, id_libro);
            ps.setString(2, id_usuario);
            ps.setDate(3, fechaPrefinal);
            ps.setTime(4, horaPrefinal);
            ps.setDate(5, fechaDevfinal);
            ps.setTime(6, horaDevfinal);
            ps.setString(7, id_prestamo);

            ps.executeUpdate();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Prestamo actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al actualizar el prestamo: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
