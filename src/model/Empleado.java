package model;

public class Empleado extends Usuario {
    private String turno;
    private double salario;

    public Empleado() {
        super();
        this.setRol("empleado");
    }

    public Empleado(int id, String username, String password, String email, String nombre, String apellidos, String dni, String turno, double salario) {
        super(id, username, password, email, nombre, apellidos, dni, "empleado");
        this.turno = turno;
        this.salario = salario;
    }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}
