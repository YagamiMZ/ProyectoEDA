package Algoritmos;

import static Algoritmos.Similitud.similitudJaro;
import Modelo.Empresa;
import Modelo.Postulante;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

public class GaleShapley {

    //HACER QUE REGRESE SOLO LAS LISTAS X SEPARADO PARA PODER HACER PRINT MAS FACIL EN CONTROLADOR
    public static int[][] emparejar(Empresa[] empresas, Postulante[] postulantes, String metodo, int[][] PreferenciasPostulantes) {


        int nP = postulantes.length;
        int nE = empresas.length;
        
        Stack<Integer> practicantesLibres = new Stack<>();
        for (int p = 0; p < nE; p++) {
            practicantesLibres.push(p);
        }
        
        int[] sgnPropuesta = new int[nP];
        
        int[] actuales = new int[nE];
        
        // Cupos por empresa (vacantes restantes)
        int[] vacantes = new int[nE];
        int maxVacantes = 0;
        for (int i = 0; i < nE; i++) {
            vacantes[i] = empresas[i].getCapacidad();
            if(vacantes[i] > maxVacantes){
                maxVacantes = vacantes[i];
            }
        }
        
        int[][] parejasEmpresas = new int[nE][maxVacantes];
        for (int e = 0; e < nE; e++) {
            Arrays.fill(parejasEmpresas[e], -1); //no hay ningun postulante x empresa
            
        }
        double[][] ranking = calcularPreferenciasEmpresas(empresas,postulantes,metodo);
        
        
        while(!practicantesLibres.empty()){
            int p = practicantesLibres.pop(); //practicante se postula
            
            if(sgnPropuesta[p] >= nE){
                continue; //se postulo a todas las empresas
            }
            
            int e = PreferenciasPostulantes[p][sgnPropuesta[p]] -1;
            sgnPropuesta[p]++; //siguiente propuesta...
            
            //Empresa tiene vacantes disponibles
            if(actuales[e] < vacantes[e]){
                parejasEmpresas[e][actuales[e]] = p; //practicante asignado
                actuales[e]++; // -1 cupo
            }
            
            //Empresa esta llena y debe evaluar a p
            else{
                //Para encontrar al "peor" postulante de la empresa
                int peorPostulante = -1;
                int peorIndice = -1;
                double peorPuntaje = -1;
                
                //datos del practicante con el q se va a comparar p
                for (int i = 0; i < vacantes[e]; i++) {
                    System.out.println("pivote 5");
                    int indice = parejasEmpresas[e][i]; 
                    double pRank = ranking[e][peorIndice];
                    
                    if(peorIndice == -1 || pRank < peorPuntaje){
                        peorPostulante = i;
                        peorIndice = indice;
                        peorPuntaje = pRank;
                    }
                    
                }
                
                //si el ranking de p es mejor que el practicante con menor puntaje de la empresa
                if(ranking[e][p] < peorPuntaje){
                    parejasEmpresas[e][peorPostulante] = p;
                    practicantesLibres.push(peorIndice); //el de menor ranking regresa a la pila
                }else{
                    practicantesLibres.push(p); //si tiene menor puntaje, regresa a la pila
                }
            }
        }
        
        /*
        // ESTA ERA VERSION ANTERIOR XSIACA
        if (totalVacantes < nP) {
            System.out.println("ADVERTENCIA: la suma de capacidades (" + totalVacantes +
                    ") es menor que el número de postulantes (" + nP + "). Se asignarán hasta llenar cupos.");
        }

        // Para cada postulante, elegir la mejor empresa entre las que tienen vacantes > 0.
        for (int p = 0; p < nP; p++) {

            double mejorScore = -1;
            int mejorEmpresa = -1;

            // buscamos entre empresas con vacantes
            for (int e = 0; e < nE; e++) {
                if (vacantes[e] <= 0) continue; // no considerar si no hay vacantes

                double score;
                switch (metodo) {
                    case "Jaro" -> score = Similitud.similitudJaro(empresas[e], postulantes[p]);
                    case "Levenshtein" -> score = Similitud.similitudLevenshtein(empresas[e], postulantes[p]);
                    default -> score = Similitud.puntajeFinal(empresas[e], postulantes[p]);
                }

                if (score > mejorScore) {
                    mejorScore = score;
                    mejorEmpresa = e;
                }
            }

            // Si no quedó ninguna empresa con vacantes (suma capacidades < nP),
            // entonces asignamos al mejor *de todas* (aunque no tenga vacantes) — opcional.
            if (mejorEmpresa == -1) {
                // elegir la mejor entre todas
                for (int e = 0; e < nE; e++) {
                    double score;
                    switch (metodo) {
                        case "JaroWinkler" -> score = Similitud.similitudJaro(empresas[e], postulantes[p]);
                        case "Levenshtein" -> score = Similitud.similitudLevenshtein(empresas[e], postulantes[p]);
                        default -> score = Similitud.puntajeFinal(empresas[e], postulantes[p]);
                    }
                    if (score > mejorScore) {
                        mejorScore = score;
                        mejorEmpresa = e;
                    }
                }
            // no decrementamos vacantes (están agotadas)
            } else {
                // consumir una vacante real
                vacantes[mejorEmpresa]--;
            }

            asignacion[p] = mejorEmpresa;
        }

        return asignacion;*/
        return parejasEmpresas;
    }
    
    
    public static double calcularSimilitud(Empresa e, Postulante p, String metodo){
        return switch (metodo) {
                        case "JaroWinkler" -> Similitud.similitudJaro(e, p);
                        case "Levenshtein" -> Similitud.similitudLevenshtein(e, p);
                        default -> Similitud.puntajeFinal(e, p);
                    };
    }
    
    public static double[][] calcularPreferenciasEmpresas(Empresa[] empresas, Postulante[] postulantes, String metodo){
        int nP = postulantes.length;
        int nE = empresas.length;
        
        double[][] ranking = new double[nE][nP];
        
        for (int e = 0; e < nE; e++) {
            for (int p = 0; p < nP; p++) {
                ranking[e][p] = calcularSimilitud(empresas[e], postulantes[p], metodo);
                
            }
        }
        
        return ranking;
    }
}
