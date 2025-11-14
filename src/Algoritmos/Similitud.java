package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;

public class Similitud {


    public static double similitudLevenshtein(Empresa e, Postulante p) {
        
        //Se estandariza la distancai de levenshtein => poder analizarlo mejor junto a Jaro
        String a = e.getTexto();
        String b = p.getTexto();

        int dist = DistanciaLevenshtein.CalcularDistancia(a, b);
        int max = Math.max(a.length(), b.length());

        if (max == 0) return 1.0;

        return 1.0 - ((double) dist / max);
    }

    public static double similitudJaroWinkler(Empresa e, Postulante p) {

        String a = e.getTexto();
        String b = p.getTexto();

        return Jaro.jaro(a, b);
    }

    public static double puntajeFinal(Empresa e, Postulante p) {
        double jw = similitudJaroWinkler(e, p);
        double lv = similitudLevenshtein(e, p);

        double ponderado = (0.7 * jw) + (0.3 * lv); //se consideran ambos valores

        System.out.println("Empresa: " + e.getNombre() + " - Postulante: " + p.getNombre());
        System.out.println("  -> Jaro-Winkler: " + jw);
        System.out.println("  -> Levenshtein:  " + lv);
        System.out.println("  -> Puntaje final (0.7/0.3): " + ponderado);
        System.out.println();


        return ponderado;
    }
}
