/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
import static Algoritmos.DistanciaLevenshtein.CalcularDistancia;
import Modelo.*;
import java.util.Random;

public class controlador {
    
    private static Empresa[] empresasPrefe;
    private static Postulante[] alumnosRanking;
    
    //para agregar postulantes a la lista de postulantes y asi tener una matriz con los ranking de los postulantes.
    private static void postulación(int mx, Postulante post, int npost){
        if( npost < mx){
        alumnosRanking[npost] = post;
        alumnosRanking[npost].setPostID(npost + 1);
        }
    }
    
    //para inicializar a las empresas, pero las empresas aun no tienen su lista de preferencia.
    private static void empresasPreferencias(int mx, int cntEmpresas, Empresa empresa){
        if(cntEmpresas < mx ){
        empresasPrefe[cntEmpresas] = empresa;}
    }
    
    //para evaluar la entrevista y habilidades blandas.
    private static void evaluación(){
        Random rc = new Random();
        for (int i = 0; i < alumnosRanking.length; i++) {
            alumnosRanking[i].setHabilidadBlanda(rc.nextFloat()*20);
            alumnosRanking[i].setHabilidadTecnica(rc.nextFloat()*20);
            
            float promedio = (float) (alumnosRanking[i].getPromedioAcademico()*0.1 + 
                    alumnosRanking[i].getHabilidadBlanda()*0.45 + alumnosRanking[i].getHabilidadTecnica()*0.45);
            
            if(promedio >= 13){
            alumnosRanking[i].setActo(true);}
        }
    }
    
    private static void crearPreferenciasEmpresas(){
    int cntAlumnos = alumnosRanking.length;
    int cntEmpresas = empresasPrefe.length;
    int postMx = cntAlumnos / cntEmpresas;
    
        for (int i = 0; i < cntEmpresas; i++) {
            int cntPost = 0; //controlamos la cantidad de postulantes max(5) para cada empresa
            for (int j = 0; j < cntAlumnos; j++) {
                if(cntPost<=5){
                    
                }
                
            }
            
        }
    }
    
    
    
    public static void main(String[] args) {
        //prueba de levenshtein
        System.out.println(CalcularDistancia("gato","cata"));
    }
    
}
