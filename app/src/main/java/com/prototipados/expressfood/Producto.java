package com.prototipados.expressfood;

public class Producto {

    String nombre;
    int cantidad;
    double precio;
    double subtotal;

    public Producto( String nombre, int cantidad, double precio, double subtotal) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
