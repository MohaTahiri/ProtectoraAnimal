
package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import excepciones.DatosInvalidosException;
import excepciones.EntidadNoEncontradaException;
import excepciones.ProtectoraException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

public class DaoJson<T> implements Dao<T> {

    protected final Path archivo;
    private final Function<T, String> obtenerId;
    private final Type tipoLista;

    private final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    public DaoJson(String ruta, Class<T> clase,
                   Function<T, String> obtenerId) {
        this.archivo = Paths.get(ruta);
        this.obtenerId = obtenerId;

        this.tipoLista = TypeToken
                .getParameterized(List.class, clase)
                .getType();
    }

    protected List<T> leer() throws ProtectoraException {
        try {
            if (!Files.exists(archivo)) {
                return new ArrayList<>();
            }

            String json = Files.readString(
                    archivo, StandardCharsets.UTF_8);

            if (json.isBlank()) {
                return new ArrayList<>();
            }

            List<T> lista = gson.fromJson(json, tipoLista);

            return lista == null
                    ? new ArrayList<>()
                    : lista;

        } catch (IOException | RuntimeException e) {
            throw new ProtectoraException(
                    "Error al leer el archivo JSON", e);
        }
    }

    protected void guardar(List<T> lista)
            throws ProtectoraException {
        try {
            Path carpeta = archivo.getParent();

            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }

            Files.writeString(
                    archivo,
                    gson.toJson(lista, tipoLista),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            throw new ProtectoraException(
                    "Error al guardar el archivo JSON", e);
        }
    }

    private void validar(T entidad)
            throws DatosInvalidosException {
        if (entidad == null
                || obtenerId.apply(entidad) == null
                || obtenerId.apply(entidad).isBlank()) {
            throw new DatosInvalidosException(
                    "La entidad y su ID son obligatorios");
        }
    }

    @Override
    public void crear(T entidad) throws ProtectoraException {
        validar(entidad);

        List<T> lista = leer();
        String id = obtenerId.apply(entidad);

        for (T elemento : lista) {
            if (id.equals(obtenerId.apply(elemento))) {
                throw new DatosInvalidosException(
                        "Ya existe una entidad con ese ID");
            }
        }

        lista.add(entidad);
        guardar(lista);
    }

    @Override
    public Optional<T> buscarPorId(String id)
            throws ProtectoraException {
        return leer().stream()
                .filter(e -> Objects.equals(
                        obtenerId.apply(e), id))
                .findFirst();
    }

    @Override
    public List<T> listarTodos()
            throws ProtectoraException {
        return leer();
    }

    @Override
    public void actualizar(T entidad)
            throws ProtectoraException {
        validar(entidad);

        List<T> lista = leer();
        String id = obtenerId.apply(entidad);

        for (int i = 0; i < lista.size(); i++) {
            if (Objects.equals(
                    obtenerId.apply(lista.get(i)), id)) {
                lista.set(i, entidad);
                guardar(lista);
                return;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe una entidad con ese ID");
    }

    @Override
    public void eliminar(String id)
            throws ProtectoraException {
        List<T> lista = leer();

        boolean eliminado = lista.removeIf(
                e -> Objects.equals(obtenerId.apply(e), id));

        if (!eliminado) {
            throw new EntidadNoEncontradaException(
                    "No existe una entidad con ese ID");
        }

        guardar(lista);
    }
}
