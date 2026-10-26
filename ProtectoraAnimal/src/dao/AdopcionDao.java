package dao;

import excepciones.ProtectoraException;
import modelo.Adopcion;

import java.util.List;

public interface AdopcionDao extends Dao<Adopcion> {

    /** Operación no CRUD: adopciones realizadas por un adoptante. */
    List<Adopcion> listarPorAdoptante(String adoptanteId) throws ProtectoraException;

    /** Operación no CRUD: historial de adopciones de un animal. */
    List<Adopcion> listarPorAnimal(String animalId) throws ProtectoraException;
}
