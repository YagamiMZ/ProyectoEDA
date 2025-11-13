/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Stack;
import Modelo.Postulante;
/*
 * Clase Empresa
 */

public class Empresa {
    private String id;
    private String nombre;
    private String descripcion;
    private String requerimientos;
    private String ubicacion;
    private String tipoEmpresa;
    private String[] interesesEmpresariales;
    
    // Preferencias (índices de postulantes)
    private int[] rankingPostulantes;
    
    // Pareja actual en el matching (índice de postulante, -1 si libre)
    private int pareja = -1;

    public Empresa(String id, String nombre, String descripcion,
                   String requerimientos, String ubicacion,
                   String tipoEmpresa, String[] interesesEmpresariales) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requerimientos = requerimientos;
        this.ubicacion = ubicacion;
        this.tipoEmpresa = tipoEmpresa;
        this.interesesEmpresariales = interesesEmpresariales;
    }

    // Texto combinado para cálculo de similitu
    public String getTexto() {
        String intereses = "";
        if (interesesEmpresariales != null) {
            for (String s : interesesEmpresariales) {
                intereses += " " + s;
            }
        }
        return (descripcion + " " + requerimientos + " " + intereses).trim();
    }

    // Getters / setters básicos
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getRequerimientos() { return requerimientos; }
    public String getUbicacion() { return ubicacion; }
    public String getTipoEmpresa() { return tipoEmpresa; }

    public String[] getInteresesEmpresariales() { return interesesEmpresariales; }

    public void setRankingPostulantes(int[] rankingPostulantes) {
        this.rankingPostulantes = rankingPostulantes;
    }

    public int[] getRankingPostulantes() {
        return rankingPostulantes;
    }
    
    public int getPareja() {
        return pareja;
    }

    public void setPareja(int pareja) {
        this.pareja = pareja;
    }

    
}