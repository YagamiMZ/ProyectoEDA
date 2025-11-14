package Modelo;

public class Empresa {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String requisitos;
    private String ubicacion;
    private String rubro;
    private String[] tags;
    private int capacidad; // Vacantes disponibles

    public Empresa(String codigo, String nombre, String descripcion, String requisitos,
                   String ubicacion, String rubro, String[] tags, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.ubicacion = ubicacion;
        this.rubro = rubro;
        this.tags = tags;
        this.capacidad = capacidad;
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

    public String getRequisitos() {
        return requisitos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getRubro() {
        return rubro;
    }

    public String[] getTags() {
        return tags;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
