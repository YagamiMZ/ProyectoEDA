package Controlador;

import Algoritmos.GaleShapley;
import Algoritmos.Similitud;
import Modelo.Empresa;
import Modelo.Postulante;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Controlador1 {
    
    public static void main(String[] args) {

        // Definir empresas
        Empresa[] empresas = new Empresa[3];
        empresas[0] = new Empresa(
                "E123",
                "TechSecurity S.A.",
                "Compania lider en servicios de ciberseguridad y analisis de datos.",
                "Se requieren analistas de datos y ciberseguridad, con interes en Python y cloud.",
                "Lima, Peru",
                "Servicios TI",
                new String[]{"ciberseguridad", "analitica de datos", "cloud", "automatizacion"},
                2
        );

        empresas[1] = new Empresa(
                "E456",
                "DataInsights Consulting",
                "Consultora especializada en proyectos de analitica de datos.",
                "Buscamos practicantes con interes en BI, SQL y machine learning basico.",
                "Lima, Peru",
                "Consultoria",
                new String[]{"analitica de datos", "business intelligence", "machine learning"},
                1
        );

        empresas[2] = new Empresa(
                "E789",
                "CloudSolutions Peru",
                "Empresa dedicada a migracion y gestion de infraestructura cloud.",
                "Se requiere interes en DevOps, automatizacion y servicios cloud.",
                "Lima, Peru",
                "Servicios TI",
                new String[]{"cloud", "devops", "infraestructura", "automatizacion"},
                1
        );

        // Definir postulantes
        Postulante[] postulantes = new Postulante[5];
        postulantes[0] = new Postulante("P001", "Juan Perez",
                "Estudiante de ingenieria de sistemas.",
                "Programacion, ciberseguridad, analisis de datos y cloud.",
                new String[]{"Python", "SQL", "Linux", "ciberseguridad"},
                "Lima, Peru",
                "Ingenieria de Sistemas",0);

        postulantes[1] = new Postulante("P002", "Maria Lopez",
                "Estudiante de ingenieria industrial.",
                "Analisis de datos, dashboards y reporting.",
                new String[]{"Power BI", "Excel", "SQL"},
                "Lima, Peru",
                "Ingenieria Industrial",1);

        postulantes[2] = new Postulante("P003", "Carlos Diaz",
                "Interes en cloud y DevOps.",
                "Automatizar despliegues y trabajar con contenedores.",
                new String[]{"Linux", "Docker", "Python"},
                "Lima, Peru",
                "Ingenieria de Sistemas",2);

        postulantes[3] = new Postulante("P004", "Ana Torres",
                "Interes en machine learning.",
                "Experimentar con modelos de clasificacion y regresion.",
                new String[]{"Python", "Pandas", "Scikit-learn"},
                "Lima, Peru",
                "Computacion Cientifica",3);

        postulantes[4] = new Postulante("P005", "Luis Gomez",
                "Interes en soporte y redes.",
                "Administracion de sistemas y mantenimiento de redes.",
                new String[]{"Redes", "Soporte tecnico", "Windows Server"},
                "Lima, Peru",
                "Informatica",4);
        
        
        // Matriz de preferencias de los postulantes
        int[][] rankingPost = {
            {2,1,3},
            {1,3,2},
            {3,1,2},
            {2,3,1},
            {1,2,3},
        };

        System.out.println("PROGRAMA DE ASIGNACION OPTIMA ENTRE PRACTICANTES - EMPRESAS\n");

        System.out.println("Convocatoria de practicas");
        System.out.println("===================================================");
        for (int i = 0; i < empresas.length; i++) {
            System.out.println("\n" + empresas[i].getNombre());
            System.out.println("Requisitos: " + empresas[i].getRequisitos());
            System.out.println("Tags: " + empresas[i].TagsString());

        }
        System.out.println("\n===================================================\n");

        System.out.println("Postulantes");
        System.out.println("===================================================");
        for (int i = 0; i < postulantes.length; i++) {
            System.out.println("\n" + postulantes[i].getNombre());
            System.out.println("Interes en " + postulantes[i].getIntereses());

        }
        System.out.println("\n===================================================");

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPresione Enter para comenzar la simulacion...");
        sc.nextLine();

        System.out.println("=====================================");
        System.out.println("              PUNTAJES               ");
        System.out.println("=====================================\n");

        Similitud.imprimirPuntajes(empresas, Similitud.puntajeFinalIni(empresas, postulantes));


        System.out.println("=====================================");
        System.out.println("            EMPAREJAMIENTO               ");
        System.out.println("=====================================\n");
            
            int[][] asign = GaleShapley.emparejar(empresas, postulantes, rankingPost);
            System.out.println("Postulante -> Empresa:");
            
            for (int j = 0; j < empresas.length; j++) { 
                int capacidad = asign[j].length;
            for (int i = 0; i < capacidad; i++) {
                int eIdx = asign[j][i]; //indice del postulante asignado a la empresa
                String eName = empresas[j].getNombre();
                System.out.println("  " + postulantes[eIdx].getNombre() + " -> " + eName);
                
            }}
            
            // reconstruir lista por empresa
            List<List<String>> porEmpresa = new ArrayList<>();
            for (int i = 0; i < empresas.length; i++) {
                porEmpresa.add(new ArrayList<>());
            }
            
            for (int j = 0; j < empresas.length; j++) {  
                int capacidad = asign[j].length;
            for (int i = 0; i < capacidad; i++) {
                int eIdx = asign[j][i];
                if (eIdx >= 0 && eIdx < postulantes.length) {
                    porEmpresa.get(j).add(postulantes[eIdx].getNombre());
                }
            }}

            // imprimir empresa -> lista
            System.out.println("\nEmpresa -> Postulantes:");
            for (int i = 0; i < empresas.length; i++) {
                System.out.print("  " + empresas[i].getNombre() + " (capacidad:" + empresas[i].getCapacidad() + ") -> ");
                if (porEmpresa.get(i).isEmpty()) {
                    System.out.println("Sin asignados");
                } else {
                    for (String nombre : porEmpresa.get(i)) {
                        System.out.print(nombre + " ");

                    }
                    System.out.println();
                }
            }

        }
    }
