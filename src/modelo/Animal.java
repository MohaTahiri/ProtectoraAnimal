
package modelo;

public class Animal {

    private String id;
    private String nombre;
    private Especie especie;
    private int edad;
    private EstadoSalud estadoSalud;
    private TipoRegistro tipoRegistro;
    private boolean disponible;

    public Animal() {
    }

    public Animal(String id, String nombre, Especie especie,
                  int edad, EstadoSalud estadoSalud,
                  TipoRegistro tipoRegistro, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estadoSalud = estadoSalud;
        this.tipoRegistro = tipoRegistro;
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public EstadoSalud getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(EstadoSalud estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public TipoRegistro getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(TipoRegistro tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return id + " - " + nombre
                + " (" + especie + ")"
                + ", edad: " + edad
                + ", salud: " + estadoSalud
                + ", disponible: " + disponible;
    }
}

