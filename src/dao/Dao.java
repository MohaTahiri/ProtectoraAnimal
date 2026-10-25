
package dao;

import excepciones.ProtectoraException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    void crear(T entidad) throws ProtectoraException;

    Optional<T> buscarPorId(String id)
            throws ProtectoraException;

    List<T> listarTodos() throws ProtectoraException;

    void actualizar(T entidad) throws ProtectoraException;

    void eliminar(String id) throws ProtectoraException;
}
