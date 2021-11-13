package  com.company;

import TDA.Camino;
import TDA.Grafo;
import TDA.NodoGrafo;
import TDA.NodoVivo;
import api.GrafoTDA;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        GrafoTDA grafo = new Grafo();
        grafo.inicializarGrafo();
        ExtraerUbicaciones extraerUbicaciones = new ExtraerUbicaciones("DatosClientes.txt", grafo);
        extraerUbicaciones.cargarUbicaciones();

        ExtraerCaminos extraerCaminos = new ExtraerCaminos("Caminos.txt", grafo);
        extraerCaminos.cargarCaminos();

        /*grafo.agregarVertice("A", "", 0, 0);
        grafo.agregarVertice("B", "", 2, 2);
        grafo.agregarVertice("C", "", 2, 2);
        grafo.agregarVertice("D", "", 2, 2);
        grafo.agregarVertice("E", "", 2, 2);
        grafo.agregarVertice("F", "", 2, 2);
        grafo.agregarVertice("G", "", 2, 2);
        grafo.agregarCamino(1, 0, "A", "B");
        grafo.agregarCamino(2, 0, "C", "B");
        grafo.agregarCamino(3, 0, "A", "E");
        grafo.agregarCamino(2, 0, "A", "E");
        grafo.agregarCamino(1, 0, "C", "F");
        grafo.agregarCamino(2, 0, "C", "D");
        grafo.agregarCamino(4, 0, "D", "E");
        grafo.agregarCamino(5, 0, "D", "F");
        grafo.agregarCamino(1, 0, "E", "F");
        grafo.agregarCamino(7, 0, "E", "G");
        grafo.agregarCamino(6, 0, "F", "G");

        NodoVivo nodoVivo = new NodoVivo();
        nodoVivo.visitados = new ArrayList<NodoGrafo>();
        nodoVivo.visitados.add(grafo.obtenerVertice("A"));
        nodoVivo.visitados.add(grafo.obtenerVertice("B"));
        nodoVivo.visitados.add(grafo.obtenerVertice("C"));
        nodoVivo.kmParcial=3;*/


        for(NodoGrafo nodoGrafo: grafo.vertices()) {
            for(Camino camino : nodoGrafo.caminos){
                System.out.println(camino.origen.cliente+ "->"+camino.destino.cliente + " " + camino.distanciaTotal+ "km " + camino.tiempoTotal + " mins" );
            }
        }






        AlgoritmoDistribucion algoritmoDistribucion = new AlgoritmoDistribucion(grafo);
        ArrayList<Camino> mejorCamino= algoritmoDistribucion.calcularRecorrido();
        System.out.println(mejorCamino);
        ImprimirPantalla.mostrarCamino(mejorCamino);


    }
}
