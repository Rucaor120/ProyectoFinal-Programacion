package dao;

import java.util.List;
import model.Usuario;

public interface UsuarioDAO {
    Usuario validar(String username, String password);
    boolean registrar(Usuario usuario);
    List<Usuario> listarTodos();
    boolean actualizar(Usuario usuario);
    boolean eliminar(int id);
}
