/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author florc
 */
public class LibroModel {
    private int idLibro;
    private String titulo;
    private String autor;
    private int paginas;
    private Date fecha_publicacion;
    private String isbn;
    private double precio;

    public LibroModel() {
    }

    public LibroModel(int idLibro, String titulo, String autor, int paginas, Date fecha_publicacion, String isbn, double precio) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.fecha_publicacion = fecha_publicacion;
        this.isbn = isbn;
        this.precio = precio;
    }

    public LibroModel(int idLibro, String titulo) {
        this.idLibro = idLibro;
        this.titulo = titulo;
    }
    
    

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
    
}
