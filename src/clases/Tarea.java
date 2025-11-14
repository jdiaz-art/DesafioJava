package clases;

import utilidades.anotaciones.nivelDeImportancia;

import java.time.LocalDate;

@Importancia(nivel = nivelDeImportancia.Alta)
public class Tarea {

    private int id;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private boolean completado;

    public Tarea(int id, String descripcion, LocalDate fechaVencimiento, boolean completado) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.completado = completado;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    @Override
    public String toString() {
        String estado = completado ? "[COMPLETADA]" : "[PENDIENTE]";
        return String.format("ID: %d - %s - \"%s\" (Vence: %s)", id, estado, descripcion, fechaVencimiento);
    }
}
