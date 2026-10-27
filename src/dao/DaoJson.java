package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import excepciones.DatosInvalidosException;
import excepciones.EntidadNoEncontradaException;
import excepciones.ProtectoraException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Implementación genérica del DAO que persiste cada entidad en un fichero JSON
 * (un array JSON por fichero) usando la librería Gson.
 */
public class DaoJson<T> implements Dao<T> {

    public static final Path DIRECTORIO_POR_DEFECTO = Paths.get("datos");

    protected final Path archivo;
    private final Function<T, String> obtenerId;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public DaoJson(Path archivo, Class<T> clase, Function<T, String> obtenerId) {
        this.archivo = archivo;
        this.obtenerId = obtenerId;
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }

    protected List<T> leer() throws ProtectoraException {
        try {
            if (!Files.exists(archivo)) {
                return new ArrayList<>();
            }
            String json = Files.readString(archivo, StandardCharsets.UTF_8);
            if (json.isBlank()) {
                return new ArrayList<>();
            }
            List<T> lista = gson.fromJson(json, tipoLista);
            return lista == null ? new ArrayList<>() : new ArrayList<>(lista);
        } catch (IOException | JsonParseException e) {
            throw new ProtectoraException("Error al leer el archivo JSON: " + archivo, e);
        }
    }

    protected void guardar(List<T> lista) throws ProtectoraException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            // Se escribe primero en un temporal y se mueve después, para no
            // dejar el fichero a medias si algo falla durante la escritura.
            Path temporal = archivo.resolveSibling(archivo.getFileName() + ".tmp");
            Files.writeString(temporal, gson.toJson(lista, tipoLista), StandardCharsets.UTF_8);
            Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ProtectoraException("Error al guardar el archivo JSON: " + archivo, e);
        }
    }

    private void validar(T entidad) throws DatosInvalidosException {
        if (entidad == null) {
            throw new DatosInvalidosException("La entidad es obligatoria");
        }
        String id = obtenerId.apply(entidad);
        if (id == null || id.isBlank()) {
            throw new DatosInvalidosException("El ID de la entidad es obligatorio");
        }
    }

    @Override
    public void crear(T entidad) throws ProtectoraException {
        validar(entidad);
        List<T> lista = leer();
        String id = obtenerId.apply(entidad);

        for (T elemento : lista) {
            if (id.equals(obtenerId.apply(elemento))) {
                throw new DatosInvalidosException("Ya existe una entidad con el ID " + id);
            }
        }
        lista.add(entidad);
        guardar(lista);
    }

    @Override
    public Optional<T> buscarPorId(String id) throws ProtectoraException {
        return leer().stream()
                .filter(e -> Objects.equals(obtenerId.apply(e), id))
                .findFirst();
    }

    @Override
    public List<T> listarTodos() throws ProtectoraException {
        return leer();
    }

    @Override
    public void actualizar(T entidad) throws ProtectoraException {
        validar(entidad);
        List<T> lista = leer();
        String id = obtenerId.apply(entidad);

        for (int i = 0; i < lista.size(); i++) {
            if (Objects.equals(obtenerId.apply(lista.get(i)), id)) {
                lista.set(i, entidad);
                guardar(lista);
                return;
            }
        }
        throw new EntidadNoEncontradaException("No existe una entidad con el ID " + id);
    }

    @Override
    public void eliminar(String id) throws ProtectoraException {
        List<T> lista = leer();
        boolean eliminado = lista.removeIf(e -> Objects.equals(obtenerId.apply(e), id));
        if (!eliminado) {
            throw new EntidadNoEncontradaException("No existe una entidad con el ID " + id);
        }
        guardar(lista);
    }
}
