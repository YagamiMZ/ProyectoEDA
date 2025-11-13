/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author LENOVO
 */
public class Postulante {
    private String id;
    private String nombre;
    private String descripcion;
    private String intereses;
    private String[] habilidades;
    private String ubicacion;
    private String nivelEstudios;
    
    // Preferencias (índices de empresas)
    private int[] rankingEmpresas;
    
    // Pareja actual en el matching (índice de empresa, -1 si libre)
    private int pareja = -1;

    public Postulante(String id, String nombre, String descripcion,
                      String intereses, String[] habilidades,
                      String ubicacion, String nivelEstudios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.intereses = intereses;
        this.habilidades = habilidades;
        this.ubicacion = ubicacion;
        this.nivelEstudios = nivelEstudios;
    }

    // Texto combinado para cálculo de similitud
    public String getTexto() {
        String hab = "";
        if (habilidades != null) {
            for (String s : habilidades) {
                hab += " " + s;
            }
        }
        return (descripcion + " " + intereses + " " + hab).trim();
    }

    // Getters / setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getIntereses() { return intereses; }
    public String[] getHabilidades() { return habilidades; }
    public String getUbicacion() { return ubicacion; }
    public String getNivelEstudios() { return nivelEstudios; }

    public void setRankingEmpresas(int[] rankingEmpresas) {
        this.rankingEmpresas = rankingEmpresas;
    }

    public int[] getRankingEmpresas() {
        return rankingEmpresas;
    }

    public int getPareja() {
        return pareja;
    }

    public void setPareja(int pareja) {
        this.pareja = pareja;
    }
}