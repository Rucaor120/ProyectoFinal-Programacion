package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.ConexionDB;
import model.Compra;
import dto.CompraDTO;

public class CompraDAOImpl implements CompraDAO {

    @Override
    public boolean insertar(Compra compra) {
        String sql = "INSERT INTO compras (cliente_id, pintura_id, fecha, cantidad, precio_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, compra.getClienteId());
            ps.setInt(2, compra.getPinturaId());
            ps.setDate(3, compra.getFecha());
            ps.setInt(4, compra.getCantidad());
            ps.setDouble(5, compra.getPrecioTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CompraDTO> listarTodos() {
        List<CompraDTO> lista = new ArrayList<>();
        String sql = "SELECT c.id, u.nombre AS nombreCliente, u.apellidos AS apellidosCliente, " +
                     "cl.tipo_cliente, " +
                     "p.nombre AS nombrePintura, c.fecha, c.cantidad, c.precio_total " +
                     "FROM compras c " +
                     "JOIN clientes cl ON c.cliente_id = cl.usuario_id " +
                     "JOIN usuarios u ON cl.usuario_id = u.id " +
                     "JOIN pinturas p ON c.pintura_id = p.id";
                     
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombreCompleto = rs.getString("nombreCliente") + " " + rs.getString("apellidosCliente");
                lista.add(new CompraDTO(
                    rs.getInt("id"),
                    nombreCompleto,
                    rs.getString("tipo_cliente"),
                    rs.getString("nombrePintura"),
                    rs.getDate("fecha"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_total")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM compras WHERE id = ?";
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
