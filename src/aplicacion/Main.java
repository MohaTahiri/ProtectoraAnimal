package aplicacion;

import dao.AdopcionDaoJson;
import dao.AdoptanteDaoJson;
import dao.AnimalDaoJson;
import excepciones.ProtectoraException;
import modelo.Adopcion;
import modelo.Adoptante;
import modelo.Animal;
import modelo.Especie;
import modelo.EstadoSalud;
import modelo.TipoRegistro;
import servicio.SistemaFicheros;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Demostración de la práctica: DAO (CRUD + operaciones propias) con JSON (Gson)
 * y dos operaciones de sistema de ficheros fuera de la interfaz DAO.
 */
public class Main {

    public static void main(String[] args) {
        Path datos = Path.of("datos");

        try {
            AnimalDaoJson animales = new AnimalDaoJson(datos);
            AdoptanteDaoJson adoptantes = new AdoptanteDaoJson(datos);
            AdopcionDaoJson adopciones = new AdopcionDaoJson(datos);

            // Partimos siempre de datos limpios para que la demo se pueda repetir.
            for (Animal a : animales.listarTodos()) animales.eliminar(a.getId());
            for (Adoptante a : adoptantes.listarTodos()) adoptantes.eliminar(a.getId());
            for (Adopcion a : adopciones.listarTodos()) adopciones.eliminar(a.getId());

            System.out.println("=== CREATE ===");
            animales.crear(new Animal("A1", "Toby", Especie.PERRO, 3, EstadoSalud.SANO, TipoRegistro.RESCATADO, true));
            animales.crear(new Animal("A2", "Misi", Especie.GATO, 2, EstadoSalud.EN_TRATAMIENTO, TipoRegistro.ENTREGADO_POR_DUENO, true));
            animales.crear(new Animal("A3", "Luna", Especie.PERRO, 5, EstadoSalud.SANO, TipoRegistro.NACIDO_EN_PROTECTORA, true));
            adoptantes.crear(new Adoptante("P1", "Ana Pérez", "ana@correo.com", "600111222"));
            adoptantes.crear(new Adoptante("P2", "Luis Gómez", "luis@correo.com", "600333444"));
            adopciones.crear(new Adopcion("D1", "A1", "P1", "2026-10-26", "Primera adopción"));
            adopciones.crear(new Adopcion("D2", "A3", "P1", "2026-10-27", "Segunda adopción"));
            animales.listarTodos().forEach(System.out::println);

            System.out.println("\n=== READ / UPDATE ===");
            Animal misi = animales.buscarPorId("A2").orElseThrow();
            misi.setEstadoSalud(EstadoSalud.SANO);
            animales.actualizar(misi);
            System.out.println("Actualizado: " + animales.buscarPorId("A2").orElseThrow());

            System.out.println("\n=== OPERACIONES NO CRUD ===");
            System.out.println("Perros: " + animales.buscarPorEspecie(Especie.PERRO));
            System.out.println("Disponibles: " + animales.listarDisponibles().size());
            System.out.println("Sanos: " + animales.buscarPorEstadoSalud(EstadoSalud.SANO).size());
            System.out.println("Email luis@correo.com: " + adoptantes.buscarPorEmail("LUIS@correo.com").orElse(null));
            System.out.println("Adopciones de P1: " + adopciones.listarPorAdoptante("P1").size());
            System.out.println("Adopciones de A1: " + adopciones.listarPorAnimal("A1").size());

            System.out.println("\n=== DELETE ===");
            animales.eliminar("A3");
            System.out.println("Animales restantes: " + animales.listarTodos().size());

            // ---- Operaciones de sistema (fuera de la interfaz DAO) ----
            System.out.println("\n=== SISTEMA DE FICHEROS ===");
            Path movido = SistemaFicheros.moverFichero(datos.resolve("adopciones.json"), Path.of("archivo"));
            System.out.println("Fichero movido a: " + movido);

            String nuevoNombre = "carpeta_adopciones";
            Path renombrado = SistemaFicheros.renombrarDirectorio(Path.of("archivo"), nuevoNombre);
            System.out.println("Directorio renombrado a: " + renombrado);

        } catch (ProtectoraException | IOException e) {
            System.err.println("Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa: " + e.getCause());
            }
        }
    }
}
