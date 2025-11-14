package Modelo;

public class Postulante {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String intereses;
    private String[] habilidades;
    private String ubicacion;
    private String estudio;

    public Postulante(String codigo, String nombre, String descripcion, String intereses,
                      String[] habilidades, String ubicacion, String estudio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.intereses = intereses;
        this.habilidades = habilidades;
        this.ubicacion = ubicacion;
        this.estudio = estudio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIntereses() {
        return intereses;
    }

    public String[] getHabilidades() {
        return habilidades;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getEstudio() {
        return estudio;
    }
}
