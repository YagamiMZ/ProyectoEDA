package Algoritmos;

import Modelo.Empresa;
import Modelo.Postulante;
import java.util.*;

public class GaleShapley {

    public static HashTable emparejar(Empresa[] empresas, Postulante[] postulantes) {

        HashTable aceptados = new HashTable();

        // Inicializar: cada empresa tiene lista vacía
        for (Empresa e : empresas) {
            aceptados.insert(e.getCodigo(), new ArrayList<Postulante>());
        }

        Queue<Postulante> libres = new LinkedList<>();
        for (Postulante p : postulantes) libres.add(p);

        HashTable idxPropuesta = new HashTable(); // índice de propuesta para cada postulante
        for (Postulante p : postulantes) idxPropuesta.insert(p.getCodigo(), 0);

        while (!libres.isEmpty()) {
            Postulante p = libres.poll();
            int idx = (int) idxPropuesta.buscar(p.getCodigo());

            if (idx >= empresas.length) continue;

            Empresa e = empresas[idx];
            idxPropuesta.insert(p.getCodigo(), idx + 1);

            @SuppressWarnings("unchecked")
            List<Postulante> lista = (List<Postulante>) aceptados.buscar(e.getCodigo());

            if (lista.size() < e.getCapacidad()) {
                lista.add(p);
            } else {
                // buscar el peor
                Postulante peor = lista.get(0);
                double sPeor = Similitud.similitudFinal(e, peor);
                for (Postulante pp : lista) {
                    double s = Similitud.similitudFinal(e, pp);
                    if (s < sPeor) {
                        sPeor = s;
                        peor = pp;
                    }
                }
                double sNuevo = Similitud.similitudFinal(e, p);
                if (sNuevo > sPeor) {
                    lista.remove(peor);
                    lista.add(p);
                    libres.add(peor);
                } else {
                    libres.add(p);
                }
            }

            aceptados.insert(e.getCodigo(), lista);
        }

        return aceptados;
    }
}
