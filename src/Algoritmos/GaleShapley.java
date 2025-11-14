package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;
import java.util.*;

public class GaleShapley {

    // método principal de Gale-Shapley estilo C++
    public static int[][] emparejar(Empresa[] empresas, Postulante[] postulantes, String metodo) {

        int nEmp = empresas.length;
        int nPost = postulantes.length;

        // Arrays para almacenar el índice del postulante asignado a cada empresa y viceversa
        int[] parejaPostulante = new int[nPost]; // parejaPostulante[i] = índice de la empresa asignada
        int[] parejaEmpresa = new int[nEmp];     // parejaEmpresa[i] = índice del postulante asignado

        for (int i = 0; i < parejaPostulante.length; i++) {
            parejaPostulante[i] = -1;
        }

        for (int i = 0; i < parejaEmpresa.length; i++) {
            parejaEmpresa[i] = -1;
        }
        
   
        boolean[] empresasLibres = new boolean[nEmp]; // true = empresa tiene vacantes
        for (int i = 0; i < empresasLibres.length; i++) {
            empresasLibres[i] = true;
        }

        int[] sigPropuesta = new int[nPost]; // siguiente empresa a la que propone cada postulante

        Stack<Integer> postulantesLibres = new Stack<>();
        for (int i = 0; i < nPost; i++) {
            postulantesLibres.push(i);
        }

        while (!postulantesLibres.isEmpty()) {
            int pIdx = postulantesLibres.peek();
            Postulante p = postulantes[pIdx];

            if (sigPropuesta[pIdx] >= nEmp) {
                postulantesLibres.pop(); // ya propuso a todas
                continue;
            }

            Empresa e = empresas[sigPropuesta[pIdx]];
            int eIdx = sigPropuesta[pIdx];
            sigPropuesta[pIdx]++;

            // Calcular similitud según el método
            double sNuevo;
            if (metodo.equals("JaroWinkler")) {
                sNuevo = Similitud.similitudJaroWinkler(e, p);
            } else if (metodo.equals("Levenshtein")) {
                sNuevo = Similitud.similitudLevenshtein(e, p);
            } else {
                sNuevo = Similitud.puntajeFinal(e, p);
            }

            if (parejaEmpresa[eIdx] == -1) {
                // Empresa libre
                parejaEmpresa[eIdx] = pIdx;
                parejaPostulante[pIdx] = eIdx;
                postulantesLibres.pop();
            } else {
                // Empresa ya tiene pareja, comparar
                Postulante actual = postulantes[parejaEmpresa[eIdx]];
                double sActual;
                if (metodo.equals("JaroWinkler")) {
                    sActual = Similitud.similitudJaroWinkler(e, actual);
                } else if (metodo.equals("Levenshtein")) {
                    sActual = Similitud.similitudLevenshtein(e, actual);
                } else {
                    sActual = Similitud.puntajeFinal(e, actual);
                }

                if (sNuevo > sActual) {
                    // Reemplazar al actual
                    postulantesLibres.pop();
                    postulantesLibres.push(parejaEmpresa[eIdx]);
                    parejaPostulante[parejaEmpresa[eIdx]] = -1;

                    parejaEmpresa[eIdx] = pIdx;
                    parejaPostulante[pIdx] = eIdx;
                }
                // si no es mejor, sigue libre
            }
        }

        // Devolver matriz con parejas: [0] = empresa -> postulante, [1] = postulante -> empresa
        int[][] resultado = new int[2][Math.max(nEmp, nPost)];
        System.arraycopy(parejaEmpresa, 0, resultado[0], 0, nEmp);
        System.arraycopy(parejaPostulante, 0, resultado[1], 0, nPost);
        return resultado;
    }
}
