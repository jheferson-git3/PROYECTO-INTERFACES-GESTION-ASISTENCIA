
package com.instituto.model;

public class Usuario {
    private String id;
    private String nombre;
    private String contrasena;
    private Rol rol;

    public Usuario(String id, String nombre, String contrasena, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // g
    public String getId() { return id; } // devuelve
    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }
    public Rol getRol() { return rol; }
}
