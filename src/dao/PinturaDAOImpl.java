package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.ConexionDB;
import model.Pintura;

public class PinturaDAOImpl implements PinturaDAO {

    @Override
    public boolean insertar(Pintura pintura) {
        String sql = "INSERT INTO pinturas (nombre, color, tipo, precio, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pintura.getNombre());
            ps.setString(2, pintura.getColor());
            ps.setString(3, pintura.getTipo());
            ps.setDouble(4, pintura.getPrecio());
            ps.setInt(5, pintura.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Pintura pintura) {
        String sql = "UPDATE pinturas SET nombre = ?, color = ?, tipo = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pintura.getNombre());
            ps.setString(2, pintura.getColor());
            ps.setString(3, pintura.getTipo());
            ps.setDouble(4, pintura.getPrecio());
            ps.setInt(5, pintura.getStock());
            ps.setInt(6, pintura.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Pintura> listarTodos() {
        List<Pintura> lista = new ArrayList<>();
        String sql = "SELECT * FROM pinturas";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Pintura(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("color"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pinturas WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarStock(int id, int cantidadComprada) {
        String sql = "UPDATE pinturas SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidadComprada);
            ps.setInt(2, id);
            ps.setInt(3, cantidadComprada);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
