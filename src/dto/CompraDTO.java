package dto;

import java.sql.Date;

public class CompraDTO {
    private int id;
    private String nombreCliente;
    private String tipoCliente;
    private String nombrePintura;
    private Date fecha;
    private int cantidad;
    private double precioTotal;

    public CompraDTO(int id, String nombreCliente, String tipoCliente, String nombrePintura, Date fecha, int cantidad, double precioTotal) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.tipoCliente = tipoCliente;
        this.nombrePintura = nombrePintura;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public int getId() { return id; }
    public String getNombreCliente() { return nombreCliente; }
    public String getTipoCliente() { return tipoCliente; }
    public String getNombrePintura() { return nombrePintura; }
    public Date getFecha() { return fecha; }
    public int getCantidad() { return cantidad; }
    public double getPrecioTotal() { return precioTotal; }
}
