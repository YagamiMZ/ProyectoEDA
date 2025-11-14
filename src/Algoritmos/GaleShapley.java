package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class GaleShapley {

    public static int[] emparejar(Empresa[] empresas, Postulante[] postulantes, String metodo) {

        int nP = postulantes.length;
        int nE = empresas.length;

        // Resultado final: empresa asignada a cada postulante
        int[] asignacion = new int[nP];
        Arrays.fill(asignacion, -1);

        // Cupos por empresa (vacantes restantes)
        int[] vacantes = new int[nE];
        int totalVacantes = 0;
        for (int i = 0; i < nE; i++) {
            vacantes[i] = empresas[i].getCapacidad();
            totalVacantes += vacantes[i];
        }

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
                    case "JaroWinkler" -> score = Similitud.similitudJaroWinkler(empresas[e], postulantes[p]);
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
                        case "JaroWinkler" -> score = Similitud.similitudJaroWinkler(empresas[e], postulantes[p]);
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

        return asignacion;
    }
}
