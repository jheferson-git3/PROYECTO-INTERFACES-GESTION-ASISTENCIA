package com.instituto.model;

public class Alumno {
    private int id;
    private String nombre;


    private int totalClases;     // Total de clases (asistencias + faltas)
    private int asistencias;     // Clases asistidas

    //  c
    public Alumno() {
    }


    public Alumno(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.totalClases = 0;
        this.asistencias = 0;
    }

    // g
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotal() {
        return totalClases;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public double getPorcentaje() {
        if (totalClases == 0) return 0;
        return ((double) asistencias / totalClases) * 100;
    }

    // S
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTotal(int totalClases) {
        this.totalClases = totalClases;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    // met log negcio
    public void incrementarClases() {
        totalClases++;
    }

    public void incrementarAsistencia() {
        asistencias++;
    }
}
