package Algoritmos;

/**
 *
 * @author melis
 */
public class DistanciaLevenshtein { // mide las ediciones que se necesitan realizar para transformar una cadena en otra
    
    public static int CalcularDistancia (String cad1, String cad2){
        // matriz para calcular la distancia final
        int[][] distancia = new int [cad1.length()+1][cad2.length()+1]; //+1 para tener d[i][0] y [0][j] como datos iniciales
        
        // Obtener datos iniciales
        /* La matriz inicial quedará así:
           0 | 1 | 2 |..| i 
           1 | 
           2 |
           : |
           j |
        */
        // columna - i eliminaciones
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
        return distancia[cad1.length()][cad2.length()]; //numero de cambios = ultima celda de la matriz (distancia[n][m])
    }
    
}
