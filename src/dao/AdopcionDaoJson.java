package dao;

import excepciones.ProtectoraException;
import modelo.Adopcion;

import java.nio.file.Path;
import java.util.List;

public class AdopcionDaoJson extends DaoJson<Adopcion> implements AdopcionDao {

    public AdopcionDaoJson() {
        this(DaoJson.DIRECTORIO_POR_DEFECTO);
    }

    public AdopcionDaoJson(Path directorio) {
        super(directorio.resolve("adopciones.json"), Adopcion.class, Adopcion::getId);
    }

    @Override
    public List<Adopcion> listarPorAdoptante(String adoptanteId) throws ProtectoraException {
        return listarTodos().stream()
                .filter(a -> adoptanteId != null && adoptanteId.equals(a.getAdoptanteId()))
                .toList();
    }

    @Override
    public List<Adopcion> listarPorAnimal(String animalId) throws ProtectoraException {
        return listarTodos().stream()
                .filter(a -> animalId != null && animalId.equals(a.getAnimalId()))
                .toList();
    }
}
