/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;
import Modelo.Empresa;
import Modelo.Postulante;

public class GaleShapley {
    
    public static int[] emparejar(Empresa[] empresas, Postulante[] postulantes) {
        int nEmp = empresas.length;
        int nPost = postulantes.length;
        
        // parejaPostulante[j] = índice de empresa que empareja a j, o -1
        int[] parejaPostulante = new int[nPost];
        for (int j = 0; j < nPost; j++) {
            parejaPostulante[j] = -1;
        }
        
        boolean[] empresaLibre = new boolean[nEmp];
        int[] siguientePropuesta = new int[nEmp]; // posición en rankingPostulantes
        for (int i = 0; i < nEmp; i++) {
            empresaLibre[i] = true;
            siguientePropuesta[i] = 0;
            empresas[i].setPareja(-1);
        }
        for (int j = 0; j < nPost; j++) {
            postulantes[j].setPareja(-1);
        }
        
        // matriz de ranking de empresas para cada postulante:
        // rankEmpresa[post][emp] = posición en la lista del postulante
        int[][] rankEmpresa = construirMatrizRankingPostulantes(postulantes, nEmp);
        
        int libres = nEmp;
        
        while (libres > 0) {
            int e = encontrarEmpresaLibre(empresaLibre);
            if (e == -1) break; // seguridad
            
            int[] prefs = empresas[e].getRankingPostulantes();
            if (siguientePropuesta[e] >= prefs.length) {
                // ya propuso a todos, no puede emparejar
                empresaLibre[e] = false;
                libres--;
                continue;
            }
            
            int p = prefs[siguientePropuesta[e]];
            siguientePropuesta[e]++;
            
            if (parejaPostulante[p] == -1) {
                // postulante libre → aceptar
                parejaPostulante[p] = e;
                empresaLibre[e] = false;
                empresas[e].setPareja(p);
                postulantes[p].setPareja(e);
                libres--;
            } else {
                int eActual = parejaPostulante[p];
                // ¿postulante prefiere a e en lugar de eActual?
                if (rankEmpresa[p][e] < rankEmpresa[p][eActual]) {
                    // cambiar de pareja
                    parejaPostulante[p] = e;
                    empresas[e].setPareja(p);
                    postulantes[p].setPareja(e);
                    
                    empresaLibre[e] = false;
                    empresaLibre[eActual] = true;
                }
            }
        }
        
        return parejaPostulante;
    }
    
    private static int encontrarEmpresaLibre(boolean[] empresaLibre) {
        for (int i = 0; i < empresaLibre.length; i++) {
            if (empresaLibre[i]) return i;
        }
        return -1;
    }
    
    private static int[][] construirMatrizRankingPostulantes(Postulante[] postulantes, int nEmp) {
        int nPost = postulantes.length;
        int[][] rank = new int[nPost][nEmp];
        
        for (int j = 0; j < nPost; j++) {
            int[] prefs = postulantes[j].getRankingEmpresas();
            if (prefs == null) continue;
            for (int pos = 0; pos < prefs.length; pos++) {
                int empIndex = prefs[pos];
                rank[j][empIndex] = pos;
            }
        }
        return rank;
    }
}