
package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.util.List;
import java.util.Optional;

public interface AdoptanteDao extends Dao<Adoptante> {

    List<Adoptante> buscarPorEmail(String email)
            throws ProtectoraException;
}
