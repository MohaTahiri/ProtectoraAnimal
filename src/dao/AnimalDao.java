package dao;

import excepciones.ProtectoraException;
import modelo.Animal;
import modelo.Especie;
import modelo.EstadoSalud;

import java.util.List;

public interface AnimalDao extends Dao<Animal> {

    /** Operación no CRUD: animales de una especie concreta. */
    List<Animal> buscarPorEspecie(Especie especie) throws ProtectoraException;

    /** Operación no CRUD: animales que se pueden adoptar ahora mismo. */
    List<Animal> listarDisponibles() throws ProtectoraException;

    /** Operación no CRUD: animales con un estado de salud concreto. */
    List<Animal> buscarPorEstadoSalud(EstadoSalud estado) throws ProtectoraException;
}
