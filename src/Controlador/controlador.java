/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
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
    
    public static int DistanciaLevenshtein (String cad1, String cad2){
        int[][] distancia = new int [cad1.length()+1][cad2.length()+1]; //+1 paraobtener los datos iniciales
        
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
    
    public static void main(String[] args) {
        //prueba de levenshtein
        System.out.println(DistanciaLevenshtein("gato","cata"));
    }
    
}
