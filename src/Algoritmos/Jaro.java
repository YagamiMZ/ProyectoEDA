package Algoritmos;

/**
 *
 * @author aUreLi0
 */

public class Jaro {

    public static double jaro(String s1, String s2) { //mide q tan parecidos son las cadenas 
        
        //limpiar texto para evitar errores (eliminando espacios al inicio y final) 
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        s1 = s1.toLowerCase().trim();
        s2 = s2.toLowerCase().trim();

        int len1 = s1.length();
        int len2 = s2.length();
        
        //si ambas estan vacias son iguales y si no, una vacia y la otra no, son completamente diferenctes
        if (len1 == 0 && len2 == 0) return 1.0;
        if (len1 == 0 || len2 == 0) return 0.0;

        //distancia máxima para considerar un match 
        int matchDistance = Math.max(len1, len2) / 2 - 1;
        if (matchDistance < 0) matchDistance = 0;

        //arreglos para marcar coincidencias
        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];
        
        //contador coincidencias y transposiciones
        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < len1; i++) {
            //intervalor donde un caracter = coincidencia
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, len2);

            for (int j = start; j < end; j++) {
                if (s2Matches[j]) continue;
                if (s1.charAt(i) != s2.charAt(j)) continue;
                
                //coiciden
                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break; //se da pase al siguiente caracter
            }
        }

        if (matches == 0) return 0.0;
        
        //Calculo transposiciones
        int k = 0;
        for (int i = 0; i < len1; i++) {
            if (!s1Matches[i]) continue;
            while (!s2Matches[k]) k++;
            if (s1.charAt(i) != s2.charAt(k)) transpositions++;
            k++;
        }

        double m = matches;
        //Formula general de la similitud de Jaro
        return ((m / len1) + (m / len2) + ((m - transpositions / 2.0) / m)) / 3.0;
    }

    
}
