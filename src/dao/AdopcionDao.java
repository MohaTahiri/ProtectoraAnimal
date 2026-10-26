
package dao;

import excepciones.ProtectoraException;
import modelo.Adopcion;
import java.util.List;

public interface AdopcionDao extends Dao<Adopcion> {

    List<Adopcion> listarPorAdoptante(String adoptanteId) throws ProtectoraException;

    List<Adopcion> listarPorAnimal(String animalId) throws ProtectoraException;
}

