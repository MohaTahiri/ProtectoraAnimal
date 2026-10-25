
package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.util.Optional;

public class AdoptanteDaoJson
        extends DaoJson<Adoptante>
        implements AdoptanteDao {

    public AdoptanteDaoJson() {
        super("datos/adoptantes.json",
                Adoptante.class, Adoptante::getId);
    }

    @Override
    public Optional<Adoptante> buscarPorEmail(String email)
            throws ProtectoraException {
        return listarTodos().stream()
                .filter(a -> a.getEmail() != null
                        && a.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
