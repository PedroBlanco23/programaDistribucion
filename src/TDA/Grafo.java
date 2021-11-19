package TDA;

import api.GrafoTDA;

import java.util.ArrayList;

public class Grafo implements GrafoTDA {

    ArrayList<NodoGrafo> nodos;

    @Override
    public void inicializarGrafo() {
        nodos = new ArrayList<NodoGrafo>();
    }

    @Override
    public void agregarVertice(String cliente, String nombre,  int horarioInicio, int horarioFinal) {
        NodoGrafo aux = new NodoGrafo();
        aux.nombre = nombre;
        aux.cliente = cliente;
        aux.horarioInicio = horarioInicio;
        aux.horarioFinal = horarioFinal;
        aux.caminos = new ArrayList<Camino>();
        nodos.add(aux);
    }

    @Override
    public void agregarCamino( float distancia, float tiempo, String lugar1, String lugar2 ) {
        NodoGrafo n1 = Vert2Nodo(lugar1);
        NodoGrafo n2 = Vert2Nodo(lugar2);
        Camino aux1 = new Camino();
        Camino aux2 = new Camino();
        //Si hay un camino peor en tiempo y distancia lo quita del grafo
        ArrayList<Camino> caminosPeores = new ArrayList<Camino>();
        boolean hayMejor = false;
        for(Camino camino:n1.caminos){
            if(camino.destino==n2){
                if(camino.distanciaTotal>=distancia && camino.tiempoTotal>=tiempo) {
                    caminosPeores.add(camino);
                } else if(camino.distanciaTotal<=distancia && camino.tiempoTotal<=tiempo) {
                    hayMejor = true;
                }

            }
        }



        if(!hayMejor) {
            aux1.distanciaTotal = distancia;
            aux1.tiempoTotal = tiempo;
            aux1.origen = n1;
            aux1.destino = n2;
            n1.caminos.add(aux1);


            aux2.distanciaTotal = distancia;
            aux2.tiempoTotal = tiempo;
            aux2.origen = n2;
            aux2.destino = n1;
            n2.caminos.add(aux2);
        }
        if (caminosPeores.size()>0) {


            for(Camino c1 : caminosPeores) {
                Camino c2 = null;
                for(Camino camino:n2.caminos){
                    if(camino.destino==n1 && camino.distanciaTotal ==c1.distanciaTotal && camino.tiempoTotal == c1.tiempoTotal){
                       c2= camino;
                    }
                }
                n1.caminos.remove(c1);
                n2.caminos.remove(c2);


            }

        }


    }

    @Override
    public NodoGrafo obtenerVertice(String cliente) {
        return Vert2Nodo(cliente);
    }

    @Override
    public ArrayList<Camino> adyacentes(String cliente) {
        NodoGrafo n1 = Vert2Nodo(cliente);
        return n1.caminos;
    }

    @Override
    public ArrayList<NodoGrafo> vertices() {
        return new ArrayList<NodoGrafo>(nodos);
    }

    @Override
    public NodoGrafo obtenerOrigen() {
        return nodos.get(0);
    }



    private NodoGrafo Vert2Nodo(String cliente) {
        NodoGrafo aux = null;
        for (NodoGrafo vertice : nodos){
            if (vertice.cliente.equals(cliente)) {
                aux = vertice;
            }
        }

        return aux;
    }
}
