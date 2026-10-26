
package dao;

import excepciones.ProtectoraException;
import modelo.Adopcion;

import java.util.List;

public class AdopcionDaoJson extends DaoJson<Adopcion> implements AdopcionDao {

    public AdopcionDaoJson() {
        super("datos/adopciones.json", Adopcion.class, Adopcion::getId);
    }

    @Override
    public List<Adopcion> listarPorAdoptante(String adoptanteId) throws ProtectoraException {
        return listarTodos().stream().filter(a -> adoptanteId.equals(a.getAdoptanteId())).toList();
    }

    @Override
    public List<Adopcion> listarPorAnimal(String animalId) throws ProtectoraException {
        return listarTodos().stream().filter(a -> animalId.equals(a.getAnimalId())).toList();
    }
}
