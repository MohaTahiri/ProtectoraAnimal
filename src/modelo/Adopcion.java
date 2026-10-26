
package modelo;

public class Adopcion {

    private String id;
    private String animalId;
    private String adoptanteId;
    private String fecha;
    private String observaciones;

    public Adopcion() {
    }

    public Adopcion(String id, String animalId, String adoptanteId, String fecha, String observaciones) {
        this.id = id;
        this.animalId = animalId;
        this.adoptanteId = adoptanteId;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getAdoptanteId() {
        return adoptanteId;
    }

    public void setAdoptanteId(String adoptanteId) {
        this.adoptanteId = adoptanteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Adopción " + id + ", animal: " + animalId + ", adoptante: " + adoptanteId + ", fecha: " + fecha;
    }
}
