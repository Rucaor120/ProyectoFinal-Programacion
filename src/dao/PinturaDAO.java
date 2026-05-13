package dao;

import java.util.List;
import model.Pintura;

public interface PinturaDAO {
    boolean insertar(Pintura pintura);
    boolean actualizar(Pintura pintura);
    List<Pintura> listarTodos();
    boolean eliminar(int id);
}
