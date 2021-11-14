package com.company;

import TDA.Camino;
import TDA.NodoGrafo;
import api.GrafoTDA;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImprimirPantalla {
    public static void mostrarCamino(ArrayList<Camino> recorrido) {
        float total = 0;
        float totalTiempo = recorrido.get(0).origen.horarioInicio;
        for(Camino camino:recorrido){
            total=total + camino.distanciaTotal;
            totalTiempo = totalTiempo + camino.tiempoTotal;
            System.out.println(camino.origen.nombre + "-"+ camino.distanciaTotal +"km-> " + camino.destino.nombre + " " + (int) totalTiempo /60 + ":" + (int) totalTiempo%60 );
        }
        System.out.println();
        System.out.println(total);
    }

    public static void imprimirHorarios (GrafoTDA grafo) {
        ArrayList<NodoGrafo> nodos = grafo.vertices();
        for (NodoGrafo nodo : nodos) {
            System.out.println(nodo.nombre + " " + nodo.horarioInicio/60 + ":" + nodo.horarioInicio%60 + "-" + nodo.horarioFinal/60 + ":" + nodo.horarioFinal%60);
        }
    }

}
