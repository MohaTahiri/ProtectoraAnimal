
package dao;

import excepciones.ProtectoraException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    public void crear(T entidad) throws ProtectoraException;

    public Optional<T> buscarPorId(String id) throws ProtectoraException;

    public List<T> listarTodos() throws ProtectoraException;

    public void actualizar(T entidad) throws ProtectoraException;

    public void eliminar(String id) throws ProtectoraException;
}
