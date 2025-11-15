package Algoritmos;

import static Algoritmos.Similitud.similitudJaro;
import Modelo.Empresa;
import Modelo.Postulante;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class GaleShapley {

    
    public static int[][] emparejar(Empresa[] empresas, Postulante[] postulantes, int[][] rankingPost) {
        Postulante[][]empPref = calcularPreferenciasEmpresas(empresas, postulantes);
        
        int nP = postulantes.length;
        int nE = empresas.length;
        
        List<List<Integer>> asignados = new ArrayList<>(nE); //no puede ser matriz porque el proceso es dínamico;
        for (int i = 0; i < nE; i++) asignados.add(new ArrayList<>());
        
        int[] sgnPropuesta = new int[nE]; //lista de los siguientes postulantes a asignar para la empresa nE
        
        int[] empresDePost = new int[nP];
        Arrays.fill(empresDePost, -1);
        
        Queue<Integer> sgnEmpresa = new LinkedList<>();
        for (int e = 0; e < nE; e++) sgnEmpresa.add(e);
        
        
        while(!sgnEmpresa.isEmpty()){
            int emp = sgnEmpresa.poll(); //se agarra a la primera empresa de la cola
            int cap = empresas[emp].getCapacidad();
            
            if(asignados.get(emp).size() >= cap){
            continue;}
            
            if(sgnPropuesta[emp] >= empPref[emp].length){
            continue;}
            
            int post = empPref[emp][sgnPropuesta[emp]].getOrden(); //agarramos al postulante de la lista de propuestas
            sgnPropuesta[emp]++;
            
            
            if(empresDePost[post] == -1){
                asignados.get(emp).add(post);
                empresDePost[post] = emp;
                
                //
                if(asignados.get(emp).size() < cap) sgnEmpresa.add(emp);
            }
            else{
                int empActual = empresDePost[post];
                if(rankingPost[post][emp] < rankingPost[post][empActual]){
                    asignados.get(empActual).remove(Integer.valueOf(post));
                    asignados.get(emp).add(post);
                    empresDePost[post] = emp;
                    
                if(asignados.get(empActual).size()< empresas[empActual].getCapacidad()) sgnEmpresa.add(empActual);
                
                
                if(asignados.get(emp).size() < cap) sgnEmpresa.add(emp);}
                
                else{
                sgnEmpresa.add(emp);}
            }
            }

            int [][] postulantesPorEmpresas = new int[nE][];
            for (int i = 0; i < nE; i++) {
                List<Integer> lista = asignados.get(i);
                postulantesPorEmpresas[i] = lista.stream().mapToInt(Integer::intValue).toArray();
        }
            return postulantesPorEmpresas;       
    }
    
    
    public static Postulante[][] calcularPreferenciasEmpresas(Empresa[] empresas, Postulante[] postulantes){
        int nP = postulantes.length;
        int nE = empresas.length;
        
        Postulante[][] prefEmpresa = new Postulante[nE][nP];
        
        prefEmpresa = Similitud.puntajeFinalIni(empresas, postulantes);
        
        return prefEmpresa;
    }
}
