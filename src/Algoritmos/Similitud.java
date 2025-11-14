package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;

public class Similitud {


    public static double similitudLevenshtein(Empresa e, Postulante p) {

        // unir tags de empresa y habilidades del postulante
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

        return JaroWinkler.getJaroWinkler(a, b);
    }

    public static double puntajeFinal(Empresa e, Postulante p) {
        double jw = similitudJaroWinkler(e, p);
        double lv = similitudLevenshtein(e, p);

        // puedes cambiar los pesos luego
        double ponderado = (0.7 * jw) + (0.3 * lv);

        System.out.println("Empresa: " + e.getNombre() + " - Postulante: " + p.getNombre());
        System.out.println("  → Jaro-Winkler: " + jw);
        System.out.println("  → Levenshtein:  " + lv);
        System.out.println("  → Puntaje final (0.7/0.3): " + ponderado);
        System.out.println();


        return ponderado;
    }
}
