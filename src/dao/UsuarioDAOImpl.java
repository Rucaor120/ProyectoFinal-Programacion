package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.ConexionDB;
import model.Usuario;
import model.Cliente;
import model.Empleado;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario validar(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registrar(Usuario usuario) {
        String sqlUsuario = "INSERT INTO usuarios (username, password, email, nombre, apellidos, dni, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO clientes (usuario_id, tipo_cliente) VALUES (?, ?)";
        
        Connection con = null;
        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false); // Transacción manual

            int usuarioId = 0;
            try (PreparedStatement ps = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, usuario.getUsername());
                ps.setString(2, usuario.getPassword());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getNombre());
                ps.setString(5, usuario.getApellidos());
                ps.setString(6, usuario.getDni());
                ps.setString(7, usuario.getRol());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuarioId = rs.getInt(1);
                    }
                }
            }

            if (usuario instanceof Cliente && usuarioId > 0) {
                try (PreparedStatement ps2 = con.prepareStatement(sqlCliente)) {
                    ps2.setInt(1, usuarioId);
                    ps2.setString(2, ((Cliente)usuario).getTipoCliente());
                    ps2.executeUpdate();
                }
            } else if (usuario instanceof Empleado && usuarioId > 0) {
                Empleado emp = (Empleado) usuario;
                String sqlEmpleado = "INSERT INTO empleados (usuario_id, turno, salario) VALUES (?, ?, ?)";
                try (PreparedStatement ps3 = con.prepareStatement(sqlEmpleado)) {
                    ps3.setInt(1, usuarioId);
                    ps3.setString(2, emp.getTurno());
                    ps3.setDouble(3, emp.getSalario());
                    ps3.executeUpdate();
                }
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, c.tipo_cliente, e.turno, e.salario " +
                     "FROM usuarios u " +
                     "LEFT JOIN clientes c ON u.id = c.usuario_id " +
                     "LEFT JOIN empleados e ON u.id = e.usuario_id";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String rol = rs.getString("rol");
                if ("cliente".equalsIgnoreCase(rol)) {
                    lista.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("tipo_cliente")
                    ));
                } else if ("empleado".equalsIgnoreCase(rol)) {
                    lista.add(new Empleado(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("turno"),
                        rs.getDouble("salario")
                    ));
                } else {
                    lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rol
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, email = ?, rol = ? WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
