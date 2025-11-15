package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;
import java.util.Arrays;
import java.util.Comparator;

public class Similitud {

    public static double similitudLevenshtein(Empresa e, Postulante p) {

        //Se estandariza la distancai de levenshtein => poder analizarlo mejor junto a Jaro
        String a = e.getTexto();
        String b = p.getTexto();

        int dist = DistanciaLevenshtein.CalcularDistancia(a, b);
        int max = Math.max(a.length(), b.length());

        if (max == 0) {
            return 1.0;
        }

        return 1.0 - ((double) dist / max);
    }

    public static double similitudJaro(Empresa e, Postulante p) {

        String a = e.getTexto();
        String b = p.getTexto();

        return Jaro.jaro(a, b);
    }
    
    public static Postulante[][] puntajeFinalIni(Empresa[] em, Postulante[] post){
        Postulante[][] puntajes = new Postulante [em.length][post.length];
        double jw;
        double lv;
        double ponderado;
        for (int e = 0; e < em.length; e++) {
            for (int p = 0; p < post.length; p++) {
                jw = similitudJaro(em[e], post[p]);
                lv = similitudLevenshtein(em[e], post[p]);
                ponderado = (0.7 * jw) + (0.3*lv);
                post[p].setPuntaje(ponderado, e); //el postulante guarda el puntaje ponderado en su lista de puntajes 
                puntajes[e][p] = post[p]; 
            }
            
        }
        OrdenarPuntajes(puntajes);
        return puntajes; //postulantes ordenas de mejor a peor nota por empresa
    }
    
    public static void OrdenarPuntajes(Postulante[][] post){
        for (int emp = 0; emp < post.length; emp++) {
            final int indexEmp = emp; //el indice para obtener los puntajes de los postulantes correspondientes a la empresa 
            
            Arrays.sort(post[emp], (Postulante post1, Postulante post2) -> {
                double punt1 = post1.getPuntaje()[indexEmp];
                double punt2 = post2.getPuntaje()[indexEmp];
                return Double.compare(punt2, punt1);
            });
        }
    }
    

    public static void imprimirPuntajes(Empresa[] empresas, Postulante[][] postulantes) {
        for (int e = 0; e < empresas.length; e++ ) {
            System.out.println("Empresa: " + empresas[e].getNombre());
            for (Postulante p : postulantes[e]) {
                double punt = p.getPuntaje()[e];
                System.out.println("Postulante: " + p.getNombre() + " -> Puntaje: " + punt);
                System.out.println("--------------");
            }
            System.out.println("_________________________________________________");
        }
    }

}
