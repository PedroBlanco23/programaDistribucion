package com.company;

import TDA.Camino;
import TDA.NodoGrafo;
import api.GrafoTDA;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImprimirPantalla {
    public static void mostrarCamino(ArrayList<Camino> recorrido, GrafoTDA g) {
        float total = 0;
        float totalTiempo = 0;
        float tiempoActual = 0;
        int aux = 0;
        float aux2;
        for(Camino camino:recorrido){
            String separadorO = camino.origen.nombre.replaceAll("(.)([A-Z])", "$1 $2");
            String separadorD = camino.destino.nombre.replaceAll("(.)([A-Z])", "$1 $2");


            if(camino.origen.cliente.equals("A")) {
                tiempoActual = camino.origen.horarioInicio/60 + camino.tiempoTotal/100;
                System.out.println("Del "+separadorO+" voy al cliente ubicado en "
                        + separadorD + " tomando la ruta de "+ camino.distanciaTotal
                        + " kms " + "llegando a las " + (double) Math.round(tiempoActual*100)/100 + " hs " + "\n");

            }



            else {
                tiempoActual += camino.tiempoTotal/100;

                //float test = tiempoActual/60;
                aux = (int) tiempoActual;

                aux2 = (float) (aux + 0.60);
                if(tiempoActual-aux2 > 0) {
                    aux += 1;
                    aux2 = aux + (tiempoActual-aux2);
                    tiempoActual = aux2;
                }

                if(camino.destino.cliente.equals("A")) {
                    System.out.println("Del cliente ubicado en "+separadorO+" vuelvo al "
                            + separadorD + " tomando la ruta de "+ camino.distanciaTotal
                            + " kms "+ "llegando a las " + (double) Math.round(tiempoActual*100)/100 + " hs " + "\n");
                }

                else {
                    System.out.println("Del cliente ubicado en "+separadorO+" voy al cliente ubicado en "
                            + separadorD + " tomando la ruta de "+ camino.distanciaTotal
                            + " kms "+ "llegando a las " + (double) Math.round(tiempoActual*100)/100 + " hs " + "\n");
                }

            }

            total=total + camino.distanciaTotal;
            totalTiempo += camino.tiempoTotal/60;
        }
        System.out.println();
        System.out.println("Kilometros total recorridos: "+(double) Math.round(total*100)/100 + "kms");
        System.out.println("Tiempo total empleado: " + (double) Math.round(totalTiempo*100)/100 + " horas");
    }

    public static void imprimirHorarios (GrafoTDA grafo) {
        ArrayList<NodoGrafo> nodos = grafo.vertices();
        for (NodoGrafo nodo : nodos) {
            System.out.println(nodo.nombre + " " + nodo.horarioInicio/60 + ":" + nodo.horarioInicio%60 + "-" + nodo.horarioFinal/60 + ":" + nodo.horarioFinal%60);
        }
    }

}
