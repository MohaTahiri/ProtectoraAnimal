package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.util.Collections;
import java.util.List;

public class AdoptanteDaoJson extends DaoJson<Adoptante> implements AdoptanteDao {

    public AdoptanteDaoJson() {
        super("datos/adoptantes.json", Adoptante.class, Adoptante::getId);
    }

    @Override
    public List<Adoptante> buscarPorEmail(String email) throws ProtectoraException {
        if (email == null || email.isBlank()) {
            return List.of();
        }

        return listarTodos().orElseGet(List::of).stream().filter(a -> email.equalsIgnoreCase(a.setEmail())).toList();
    }

    /*@Override
    public List<Adoptante> buscarPorEmail(String email) throws ProtectoraException {
        if (email == null) return null;

        return listarTodos().orElseGet(Collections::emptyList).stream().filter(a -> email.equalsIgnoreCase(a.getEmail())).findFirst().orElse(null);
    }*/
}


/*package dao;

import excepciones.ProtectoraException;
import modelo.Adoptante;

import java.util.List;
import java.util.Optional;

public class AdoptanteDaoJson extends DaoJson<Adoptante> implements AdoptanteDao {

    public AdoptanteDaoJson() {
        super("datos/adoptantes.json", Adoptante.class, Adoptante::getId);
    }

    @Override
    public Optional<Adoptante> buscarPorEmail(String email) throws ProtectoraException {
        return listarTodos().stream().filter(a -> a.getEmail() != null && a.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}*/
