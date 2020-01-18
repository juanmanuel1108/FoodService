package com.prototipados.expressfood;

public class Repartidor {

    private int id;

    private String  documento;

    private String telefono;

    private int estado;

    private String  Email;

    private String nombre;

    public Repartidor(int id, String documento, String telefono, int estado, String email, String nombre) {
        this.id = id;
        this.documento = documento;
        this.telefono = telefono;
        this.estado = estado;
        Email = email;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEstado() {
        return estado;
    }

    public String getEmail() {
        return Email;
    }

    public String getNombre() {
        return nombre;
    }
}
