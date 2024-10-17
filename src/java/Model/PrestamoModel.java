/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author florc
 */
public class PrestamoModel {
    private int idPrestamo;
    private int idLibro;
    private int idUsuario;
    private Date fechaPrestamo;
    private Time horaPrestamo;
    private Date fechaDevolucion;
    private Time horaDevolucion;

    public PrestamoModel() {
    }

    public PrestamoModel(int idPrestamo, int idLibro, int idUsuario, Date fechaPrestamo, Time horaPrestamo, Date fechaDevolucion, Time horaDevolucion) {
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.horaPrestamo = horaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.horaDevolucion = horaDevolucion;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Time getHoraPrestamo() {
        return horaPrestamo;
    }

    public void setHoraPrestamo(Time horaPrestamo) {
        this.horaPrestamo = horaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Time getHoraDevolucion() {
        return horaDevolucion;
    }

    public void setHoraDevolucion(Time horaDevolucion) {
        this.horaDevolucion = horaDevolucion;
    }
    
    
}
