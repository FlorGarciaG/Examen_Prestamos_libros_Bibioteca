/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Configuration.ConnectionBD;
import Model.UsuarioModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "usuario_servlet", urlPatterns = {"/usuario_servlet"})
public class usuario_servlet extends HttpServlet {

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
            out.println("<title>Servlet usuario_servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet usuario_servlet at " + request.getContextPath() + "</h1>");
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
        List<UsuarioModel> listaUsers = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellidoP, apellidoM, "
                + "correo, telefono FROM usuario";

        try {
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Itera sobre los resultados y crea objetos UsuarioModel
            while (rs.next()) {
                UsuarioModel user = new UsuarioModel();
                int idFinal = Integer.parseInt(rs.getString("id_usuario"));
                user.setIdUsuario(idFinal);
                user.setNombre(rs.getString("nombre"));
                user.setApellidoP(rs.getString("apellidoP"));
                user.setApellidoM(rs.getString("apellidoM"));
                user.setCorreo(rs.getString("correo"));
                user.setTelefono(rs.getString("telefono"));
                listaUsers.add(user);
            }

            // Pasa la lista de usuarios al JSP
            request.setAttribute("libros", listaUsers);
            request.getRequestDispatcher("/views/mostrar_usuarios.jsp").forward(request, response);

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
        String nombre = request.getParameter("nombre");
        String apellidoP = request.getParameter("apellidoP");
        String apellidoM = request.getParameter("apellidoM");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        try {
            // Crear la consulta SQL para insertar el usuario 
            String sql = "INSERT INTO usuario (nombre, apellidoP, apellidoM, correo, telefono) VALUES (?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidoP);
            ps.setString(3, apellidoM);
            ps.setString(4, correo);
            ps.setString(5, telefono);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, redirigir al usuario a una página de éxito 
                request.setAttribute("mensaje", "Usuario registrado con éxito!");
            } else {
                // Si falló, redirigir a una página de error 
                request.setAttribute("mensaje", "Error al registrar usuario.");
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
            request.getRequestDispatcher("views/registro_usuario.jsp").forward(request, response);

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

        String sql = "DELETE FROM usuario WHERE id_usuario like ?";

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
    
}
