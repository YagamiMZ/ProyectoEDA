/*
 * Controlador principal
 */
package Controlador;

import Modelo.Empresa;
import Modelo.Postulante;
import java.util.Arrays;
import java.util.Comparator;
import Algoritmos.GaleShapley;
import static Algoritmos.JaroWinkler.jaroWinkler;
import static Algoritmos.distanciaLevenshtein.CalcularDistancia;

public class controlador {
    
    private static Empresa[] empresas;
    private static Postulante[] postulantes;
    
    public static void main(String[] args) {
        inicializarDatosDemo();
        
        System.out.println("=== MATCHING CON LEVENSHTEIN ===");
        construirRankings(true); // true = Levenshtein
        int[] resultadoLev = GaleShapley.emparejar(empresas, postulantes);
        mostrarResultado(resultadoLev, "Levenshtein");
        
        System.out.println("\n=== MATCHING CON JARO-WINKLER ===");
        construirRankings(false); // false = Jaro-Winkler
        int[] resultadoJW = GaleShapley.emparejar(empresas, postulantes);
        mostrarResultado(resultadoJW, "Jaro-Winkler");
    }
    
    // Datos de prueba
    private static void inicializarDatosDemo() {
        empresas = new Empresa[3];
        postulantes = new Postulante[5];
        
        empresas[0] = new Empresa(
                "E123",
                "TechSecurity S.A.",
                "Compañía líder en servicios de ciberseguridad y análisis de datos.",
                "Se requieren analistas de datos y ciberseguridad, con interés en Python y cloud.",
                "Lima, Perú",
                "Servicios TI",
                new String[]{"ciberseguridad", "analítica de datos", "cloud", "automatización"}
        );
        
        empresas[1] = new Empresa(
                "E456",
                "DataInsights Consulting",
                "Consultora especializada en proyectos de analítica de datos para diversas industrias.",
                "Buscamos practicantes con interés en BI, SQL y modelos de machine learning básicos.",
                "Lima, Perú",
                "Consultoría",
                new String[]{"analítica de datos", "business intelligence", "machine learning"}
        );
        
        empresas[2] = new Empresa(
                "E789",
                "CloudSolutions Peru",
                "Empresa dedicada a migración y gestión de infraestructura en la nube.",
                "Se requiere interés en DevOps, automatización y servicios cloud.",
                "Lima, Perú",
                "Servicios TI",
                new String[]{"cloud", "devops", "infraestructura", "automatización"}
        );
        
        postulantes[0] = new Postulante(
                "P001",
                "Juan Pérez",
                "Estudiante de ingeniería de sistemas, proactivo, con gusto por la ciberseguridad y aprendizaje automático.",
                "Me interesa la programación, ciberseguridad, análisis de datos y trabajar en cloud.",
                new String[]{"Python", "SQL", "Linux", "ciberseguridad"},
                "Lima, Perú",
                "Pregrado – Ingeniería de Sistemas"
        );
        
        postulantes[1] = new Postulante(
                "P002",
                "María López",
                "Estudiante de ingeniería industrial con interés en inteligencia de negocios.",
                "Me gusta el análisis de datos, dashboards y reporting.",
                new String[]{"Power BI", "Excel", "SQL"},
                "Lima, Perú",
                "Pregrado – Ingeniería Industrial"
        );
        
        postulantes[2] = new Postulante(
                "P003",
                "Carlos Díaz",
                "Estudiante de ingeniería de sistemas con interés en cloud y DevOps.",
                "Me interesa automatizar despliegues y trabajar con contenedores.",
                new String[]{"Linux", "Docker", "Python"},
                "Lima, Perú",
                "Pregrado – Ingeniería de Sistemas"
        );
        
        postulantes[3] = new Postulante(
                "P004",
                "Ana Torres",
                "Estudiante de computación científica con interés en machine learning.",
                "Me gusta experimentar con modelos de clasificación y regresión.",
                new String[]{"Python", "Pandas", "Scikit-learn"},
                "Lima, Perú",
                "Pregrado – Computación Científica"
        );
        
        postulantes[4] = new Postulante(
                "P005",
                "Luis Gómez",
                "Estudiante de informática con interés en soporte y redes.",
                "Me interesa la administración de sistemas y mantenimiento de redes.",
                new String[]{"Redes", "Soporte técnico", "Windows Server"},
                "Lima, Perú",
                "Pregrado – Informática"
        );
    }
    
    // Construir rankings para empresas y postulantes usando Levenshtein (true) o Jaro-Winkler (false)
    private static void construirRankings(boolean usarLevenshtein) {
        int nEmp = empresas.length;
        int nPost = postulantes.length;
        
        double[][] matrizSim = new double[nEmp][nPost];
        
        // calcular similitudes empresa–postulante
        for (int i = 0; i < nEmp; i++) {
            String textoEmp = empresas[i].getTexto();
            for (int j = 0; j < nPost; j++) {
                String textoPost = postulantes[j].getTexto();
                double sim;
                if (usarLevenshtein) {
                    sim = CalcularDistancia(textoEmp, textoPost);
                } else {
                    sim = jaroWinkler(textoEmp, textoPost);
                }
                matrizSim[i][j] = sim;
            }
        }
        
        // ranking de postulantes para cada empresa (ordenado de mayor a menor similitud)
        for (int i = 0; i < nEmp; i++) {
            Integer[] indices = new Integer[nPost];
            for (int j = 0; j < nPost; j++) indices[j] = j;
            
            final int empIndex = i;
            Arrays.sort(indices, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return -Double.compare(matrizSim[empIndex][a], matrizSim[empIndex][b]);
                }
            });
            
            int[] rankingPostulantes = new int[nPost];
            for (int k = 0; k < nPost; k++) {
                rankingPostulantes[k] = indices[k];
            }
            empresas[i].setRankingPostulantes(rankingPostulantes);
        }
        
        // ranking de empresas para cada postulante (usando misma matriz)
        for (int j = 0; j < nPost; j++) {
            Integer[] indicesEmp = new Integer[nEmp];
            for (int i = 0; i < nEmp; i++) indicesEmp[i] = i;
            
            final int postIndex = j;
            Arrays.sort(indicesEmp, new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return -Double.compare(matrizSim[a][postIndex], matrizSim[b][postIndex]);
                }
            });
            
            int[] rankingEmpresas = new int[nEmp];
            for (int k = 0; k < nEmp; k++) {
                rankingEmpresas[k] = indicesEmp[k];
            }
            postulantes[j].setRankingEmpresas(rankingEmpresas);
        }
    }
    
    // Mostrar resultado como: Postulante → Empresa
    private static void mostrarResultado(int[] parejaPostulante, String metodo) {
        System.out.println("Emparejamientos usando " + metodo + ":");
        for (int j = 0; j < postulantes.length; j++) {
            int e = parejaPostulante[j];
            if (e == -1) {
                System.out.println("Postulante " + postulantes[j].getNombre()
                        + " → (sin empresa asignada)");
            } else {
                System.out.println("Postulante " + postulantes[j].getNombre()
                        + " → Empresa " + empresas[e].getNombre());
            }
        }
    }
}
