package Modelo;

public class Postulante {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String intereses;
    private String[] habilidades;
    private String ubicacion;
    private String estudio;
    private double[] puntaje = new double[5];
    private int orden;

    public Postulante(String codigo, String nombre, String descripcion, String intereses,
                      String[] habilidades, String ubicacion, String estudio, int orden) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.intereses = intereses;
        this.habilidades = habilidades;
        this.ubicacion = ubicacion;
        this.estudio = estudio;
        this.orden = orden;
    }
    @Override
    public Postulante clone() {
    // Crear nueva instancia copiando los campos principales
    Postulante copia = new Postulante(
        this.codigo,
        this.nombre,
        this.descripcion,
        this.intereses,
        this.habilidades != null ? this.habilidades.clone() : null,
        this.ubicacion,
        this.estudio,
        this.orden
    );

    // Copiar el vector de puntajes
    copia.puntaje = this.puntaje.clone();

    return copia;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public double[] getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje, int emp) {
        this.puntaje[emp] = puntaje;
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
    
     // Texto combinado para cálculo de similitud
    public String getTexto() {
        String hab = HabilidadesString();
        return (descripcion + " " + intereses + " " + hab).trim();
    }
    
    public String HabilidadesString(){
        String hab = "";
        if (habilidades != null) {
            for (String s : habilidades) {
                hab += " " + s;
            }
        }
        return hab.trim();
        
    }
    

}
