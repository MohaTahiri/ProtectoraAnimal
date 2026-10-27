package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.util.Optional;

public interface AdoptanteDao extends Dao<Adoptante> {

    /** Operación no CRUD: busca un adoptante por su email (sin distinguir mayúsculas). */
    Optional<Adoptante> buscarPorEmail(String email) throws ProtectoraException;
}
