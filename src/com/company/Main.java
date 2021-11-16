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


        AlgoritmoDistribucion algoritmoDistribucion = new AlgoritmoDistribucion(grafo);
        ImprimirPantalla.imprimirHorarios(grafo);

        ArrayList<Camino> mejorCamino= algoritmoDistribucion.calcularRecorrido();
        System.out.println("-----------------------------------------");
        System.out.println("Trayecto que debe hacer el camionero: ");
        ImprimirPantalla.mostrarCamino(mejorCamino, grafo);


    }
}
