package com.company;

import TDA.Camino;

import java.util.ArrayList;

public class ImprimirPantalla {
    public static void mostrarCamino(ArrayList<Camino> recorrido) {
        float total = 0;
        for(Camino camino:recorrido){
            System.out.println(camino.origen.cliente + "-"+ camino.distanciaTotal +"-> " + camino.destino.cliente);
            total=total + camino.distanciaTotal;
        }
        System.out.println();
        System.out.println(total);
    }
}
