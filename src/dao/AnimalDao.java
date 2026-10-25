
package dao;

import excepciones.ProtectoraException;
import modelo.Animal;
import modelo.Especie;
import modelo.EstadoSalud;
import java.util.List;

public interface AnimalDao extends Dao<Animal> {

    List<Animal> buscarPorEspecie(Especie especie)
            throws ProtectoraException;

    List<Animal> listarDisponibles()
            throws ProtectoraException;

    List<Animal> buscarPorEstadoSalud(EstadoSalud estado)
            throws ProtectoraException;
}
