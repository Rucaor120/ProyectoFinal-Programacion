package model;

import java.sql.Date;

public class Compra {
    private int id;
    private int clienteId;
    private int pinturaId;
    private Date fecha;
    private int cantidad;
    private double precioTotal;

    public Compra() {}

    public Compra(int id, int clienteId, int pinturaId, Date fecha, int cantidad, double precioTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.pinturaId = pinturaId;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public int getPinturaId() { return pinturaId; }
    public void setPinturaId(int pinturaId) { this.pinturaId = pinturaId; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
}
