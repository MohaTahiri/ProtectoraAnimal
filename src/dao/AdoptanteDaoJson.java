package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.nio.file.Path;
import java.util.Optional;

public class AdoptanteDaoJson extends DaoJson<Adoptante> implements AdoptanteDao {

    public AdoptanteDaoJson() {
        this(DaoJson.DIRECTORIO_POR_DEFECTO);
    }

    public AdoptanteDaoJson(Path directorio) {
        super(directorio.resolve("adoptantes.json"), Adoptante.class, Adoptante::getId);
    }

    @Override
    public Optional<Adoptante> buscarPorEmail(String email) throws ProtectoraException {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return listarTodos().stream()
                .filter(a -> email.equalsIgnoreCase(a.getEmail()))
                .findFirst();
    }
}
