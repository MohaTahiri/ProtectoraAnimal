package servicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Operaciones de "llamadas al sistema" sobre ficheros y directorios.
 * No forman parte de la interfaz DAO.
 */
public class SistemaFicheros {

    private SistemaFicheros() {
    }

    /**
     * Operación 1: renombra un directorio (por ejemplo "datos" -> "datos_2025").
     *
     * @return la nueva ruta del directorio
     */
    public static Path renombrarDirectorio(Path directorio, String nuevoNombre) throws IOException {
        if (!Files.isDirectory(directorio)) {
            throw new IOException("No existe el directorio: " + directorio);
        }
        Path destino = directorio.resolveSibling(nuevoNombre);
        if (Files.exists(destino)) {
            throw new IOException("Ya existe: " + destino);
        }
        return Files.move(directorio, destino);
    }

    /**
     * Operación 2: mueve un fichero a otro directorio (lo crea si no existe)
     * conservando su nombre.
     *
     * @return la nueva ruta del fichero
     */
    public static Path moverFichero(Path fichero, Path directorioDestino) throws IOException {
        if (!Files.isRegularFile(fichero)) {
            throw new IOException("No existe el fichero: " + fichero);
        }
        Files.createDirectories(directorioDestino);
        return Files.move(fichero, directorioDestino.resolve(fichero.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
