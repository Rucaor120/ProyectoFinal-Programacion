package dao;

import java.util.List;
import model.Compra;
import dto.CompraDTO;

public interface CompraDAO {
    boolean insertar(Compra compra);
    List<CompraDTO> listarTodos();
    boolean eliminar(int id);
}
