package Algoritmos;

import static Algoritmos.Similitud.similitudJaro;
import Modelo.Empresa;
import Modelo.Postulante;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class GaleShapley {

    //HACER QUE REGRESE SOLO LAS LISTAS X SEPARADO PARA PODER HACER PRINT MAS FACIL EN CONTROLADOR
    public static int[][] emparejar(Empresa[] empresas, Postulante[] postulantes, int[][] rankingPost) {
        Postulante[][]empPref = calcularPreferenciasEmpresas(empresas, postulantes);
        
        int nP = postulantes.length;
        int nE = empresas.length;
        
        Stack<Integer> empresaLibres = new Stack<>();
        boolean[] postulantesLibres = new boolean[nP];
        
        int[] sgnPropuesta = new int[nP];
        
        int[] postulDeEmpresa = new int[nE];
        int[] empresDePost = new int[nP];
        
        for (int p = 0; p < nP; p++) {
            postulantesLibres[p] = true;
            sgnPropuesta[p] = 0;
            empresDePost[p] = -1;
        }
        for (int e = 0; e < nE; e++) {
            empresaLibres.push(e);
            postulDeEmpresa[e] = -1;
        }
            
        
        
        while(!empresaLibres.empty()){
            int emp = empresaLibres.peek(); //practicante se postula
            int post = empPref[emp][sgnPropuesta[emp]].getOrden();
            sgnPropuesta[emp]++;
            
            if(postulantesLibres[post]){
                postulDeEmpresa[emp] = post;
                empresDePost[post] = emp;   
                postulantesLibres[post] = false;
                empresaLibres.pop();
            }
            else{
                if(rankingPost[post][emp] < rankingPost[post][empresDePost[post]]){
                    empresaLibres.pop();
                    empresaLibres.push(empresDePost[post]);
                    postulDeEmpresa[empresDePost[post]] = -1;
                    postulDeEmpresa[emp] = post;
                    empresDePost[post] = emp;
                }
            }
            }
            
            int [][] parejasPorEmpresas = new int[nE][1];
            for (int i = 0; i < nE; i++) {
                for (int j = 0; j < 1; j++) {
                    parejasPorEmpresas[i][j] = postulDeEmpresa[i];
                    
                }
            
        }
            return parejasPorEmpresas;       
    }
    
    
    public static Postulante[][] calcularPreferenciasEmpresas(Empresa[] empresas, Postulante[] postulantes){
        int nP = postulantes.length;
        int nE = empresas.length;
        
        Postulante[][] prefEmpresa = new Postulante[nE][nP];
        
        prefEmpresa = Similitud.puntajeFinalIni(empresas, postulantes);
        
        return prefEmpresa;
    }
}
