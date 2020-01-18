package com.prototipados.expressfood;

public class Pedido {

    int id;
    String nombreC;
    String direccion;
    double valor;

    public Pedido(int id, String nombreC, String direccion, double valor) {
        this.id = id;
        this.nombreC = nombreC;
        this.direccion = direccion;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getNombreC() {
        return nombreC;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getValor() {
        return valor;
    }
}
