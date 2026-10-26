
package dao;

import excepciones.ProtectoraException;
import modelo.Animal;
import modelo.Especie;
import modelo.EstadoSalud;

import java.util.List;

public class AnimalDaoJson
        extends DaoJson<Animal>
        implements AnimalDao {

    public AnimalDaoJson() {
        super("datos/animales.json",
                Animal.class, Animal::getId);
    }

    @Override
    public List<Animal> buscarPorEspecie(Especie especie) throws ProtectoraException {
        return listarTodos().stream().filter(a -> a.getEspecie() == especie).toList();
    }

    @Override
    public List<Animal> listarDisponibles() throws ProtectoraException {
        return listarTodos().stream().filter(Animal::isDisponible).toList();
    }

    @Override
    public List<Animal> buscarPorEstadoSalud(EstadoSalud estado) throws ProtectoraException {
        return listarTodos().stream().filter(a -> a.getEstadoSalud() == estado).toList();
    }
}

