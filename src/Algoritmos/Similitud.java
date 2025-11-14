package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;

public class Similitud {

    public static double similitudFinal(Empresa e, Postulante p) {
        double jaro = jaroWinkler(e, p);
        double lev = levenshteinSim(e, p);

        double w1 = 0.7;
        double w2 = 0.3;

        return w1 * jaro + w2 * lev;
    }

    public static double jaroWinkler(Empresa e, Postulante p) {
        String a = String.join(" ", e.getTags());
        String b = String.join(" ", p.getHabilidades());

        return jaro(a, b);
    }

    private static double jaro(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        int len1 = s1.length(), len2 = s2.length();
        if (len1 == 0 && len2 == 0){
            return 1.0;
        }
        if (len1 == 0 || len2 == 0) {
            return 0.0;
        }

        int matchDistance = Math.max(len1, len2) / 2 - 1;
        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];

        int matches = 0;
        for (int i = 0; i < len1; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, len2);
            for (int j = start; j < end; j++) {
                if (s2Matches[j]) continue;
                if (s1.charAt(i) != s2.charAt(j)) continue;
                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break;
            }
        }
        if (matches == 0) return 0.0;

        int t = 0, k = 0;
        for (int i = 0; i < len1; i++) {
            if (!s1Matches[i]) continue;
            while (!s2Matches[k]) k++;
            if (s1.charAt(i) != s2.charAt(k)) t++;
            k++;
        }

        double m = matches;
        return ((m / len1) + (m / len2) + ((m - t / 2.0) / m)) / 3.0;
    }

    public static double levenshteinSim(Empresa e, Postulante p) {
        String a = String.join(" ", e.getTags());
        String b = String.join(" ", p.getHabilidades());
        int dist = Levenshtein(a, b);
        int maxLen = Math.max(a.length(), b.length());
        if (maxLen == 0) return 1.0;
        return 1.0 - ((double) dist / maxLen);
    }

    public static int Levenshtein (String cad1, String cad2){
        int[][] distancia = new int [cad1.length()+1][cad2.length()+1]; //+1 para obtener los datos iniciales
        
        //  columna - i eliminaciones
        for (int i = 0; i <= cad1.length(); i++) { //recorrer todo cad1
            distancia[i][0] = i; 
        }
        // fila - j inserciones
        for (int j = 0; j <= cad2.length(); j++) { 
            distancia[0][j] = j; 
        }
        //rellenar distancia[][]
        for (int i = 1; i <= cad1.length(); i++) {
            for (int j = 1; j <= cad2.length(); j++) {
                if(cad1.charAt(i-1) == cad2.charAt(j-1)){
                    distancia[i][j] = distancia[i-1][j-1]; //Coinciden ... toma el valor de la diagonal superior izquierda
                    
                }else{
                    int eliminar = distancia[i-1][j]; // dato superior
                    int insertar = distancia[i][j-1]; // dato de la izquierda
                    int sustituir = distancia[i-1][j-1]; // dato diagonal superior izquierda
                    
                    distancia[i][j] = 1 + Math.min(eliminar, Math.min(insertar, sustituir)); //elige el menor valor
                }   
            }
        }
        return distancia[cad1.length()][cad2.length()]; //numero de cambios = ultima celda de la matriz
    }
}
