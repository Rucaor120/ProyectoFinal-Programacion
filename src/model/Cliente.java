package model;

public class Cliente extends Usuario {
    private String tipoCliente;

    public Cliente() {
        super();
        this.setRol("cliente");
    }

    public Cliente(int id, String username, String password, String email, String nombre, String apellidos, String dni, String tipoCliente) {
        super(id, username, password, email, nombre, apellidos, dni, "cliente");
        this.tipoCliente = tipoCliente;
    }

    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
}
