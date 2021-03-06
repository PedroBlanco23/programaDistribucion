package com.company;

import TDA.Camino;
import TDA.Grafo;
import TDA.NodoGrafo;
import TDA.NodoVivo;
import api.GrafoTDA;
import TDA.ColaPrioridad;
import java.util.ArrayList;

public class AlgoritmoDistribucion {
    GrafoTDA grafo;
    NodoGrafo central;
    float cotaGeneral;
    public NodoVivo mejorSolucion = null;

    public AlgoritmoDistribucion(GrafoTDA grafo) {
        this.grafo = grafo;
        this.central = grafo.obtenerOrigen();
        this.cotaGeneral = Float.MAX_VALUE;
        this.central.horarioInicio= mejorHorarioInicio();
    }

    public int mejorHorarioInicio() {
        int mejorHorario =0;
        ArrayList<NodoGrafo> nodos = grafo.vertices();
        for (NodoGrafo nodo : nodos) {
            if(nodo.horarioInicio!=0 && nodo.horarioInicio>mejorHorario) {
                mejorHorario= nodo.horarioInicio;
            }
        }
        return mejorHorario;
    }


    public ArrayList<Camino> calcularRecorrido() {
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarColaPrioridad();
        NodoVivo raiz = crearRaiz();
        cola.acolar(raiz, raiz.etapa, raiz.cotaInferior);
        while (!cola.colaVacia()) {
            NodoVivo candidato = cola.primero();
            cola.desacolar();
            if (!podar(candidato, cotaGeneral)) {
                ArrayList<NodoVivo> hijos = generarHijos(candidato);
                for (NodoVivo hijo : hijos) {
                    if (!podar(hijo, cotaGeneral)) {
                        if (esSolucion(hijo)) {
                            hijo.solucionParcial.add(caminoAOrigen(hijo));
                            hijo.kmParcial+=hijo.solucionParcial.get(hijo.solucionParcial.size()-1).distanciaTotal;
                            hijo.tiempoParcial+=hijo.solucionParcial.get(hijo.solucionParcial.size()-1).tiempoTotal;
                            if (esMejorSolucion(mejorSolucion, hijo)) {
                                mejorSolucion = hijo;
                                cotaGeneral = actualizarCota(hijo, cotaGeneral);
                            }
                        } else {
                            cola.acolar(hijo, hijo.etapa, hijo.cotaInferior);
                        }
                    }
                }
            }
        }
        return mejorSolucion.solucionParcial;

    }


    public boolean llegaATiempo(NodoVivo origen) {
        if(origen.solucionParcial.size()==0) {
            return true;
        } else {
            boolean correcto = true;
            Camino ultimoCamino = origen.solucionParcial.get(origen.solucionParcial.size()-1);
            if ((origen.tiempoParcial) > ultimoCamino.destino.horarioFinal ||
                    (origen.tiempoParcial) < ultimoCamino.destino.horarioInicio) {
                correcto=false;
            } else {
                ArrayList<NodoGrafo> noVisitados = new ArrayList<NodoGrafo>(grafo.vertices());
                for (NodoGrafo nodoGrafo: origen.visitados) {
                    noVisitados.remove(nodoGrafo);
                }

                for (NodoGrafo nodoGrafo: noVisitados) {
                    if(origen.tiempoParcial>nodoGrafo.horarioFinal){
                        correcto =false;
                    }
                }
            }
            return correcto;
        }


    }


    public ArrayList<NodoVivo> generarHijos(NodoVivo nodoVivo) {
        ArrayList<NodoVivo> hijos = new ArrayList<NodoVivo>();
        ArrayList<Camino> caminosAdyacentes = nodoVivo.visitados.get(nodoVivo.visitados.size()-1).caminos;
        for(Camino adyacente: caminosAdyacentes) {
                if(!nodoVivo.visitados.contains(adyacente.destino)) {
                    NodoVivo hijo = new NodoVivo();
                    hijo.solucionParcial = new ArrayList<Camino>(nodoVivo.solucionParcial);
                    hijo.visitados = new ArrayList<NodoGrafo>(nodoVivo.visitados);
                    hijo.etapa = nodoVivo.etapa + 1;
                    hijo.visitados.add(adyacente.destino);
                    hijo.solucionParcial.add(adyacente);
                    hijo.kmParcial = nodoVivo.kmParcial + adyacente.distanciaTotal;
                    hijo.tiempoParcial = nodoVivo.tiempoParcial + adyacente.tiempoTotal;
                    hijo.cotaInferior = calcularCotaInferior(hijo);
                    hijos.add(hijo);
            }
        }
        return hijos;




    }

    public float calcularCotaInferior (NodoVivo nodoVivo) {

        NodoGrafo nodoInicio = nodoVivo.visitados.get(0);
        NodoGrafo nodoUltimo = nodoVivo.visitados.get(nodoVivo.visitados.size()-1);

        ArrayList<NodoGrafo> nodosNoVisitados = new ArrayList<NodoGrafo>();

        for(NodoGrafo nodoGrafo : grafo.vertices()) {
            if (!nodoVivo.visitados.contains(nodoGrafo)){
                nodosNoVisitados.add(nodoGrafo);
            }
        }

        float calculoPrim = prim(nodosNoVisitados);
        if(calculoPrim==-1 || (caminoAOrigen(nodoVivo) == null &&  nodoVivo.visitados.size()== grafo.vertices().size()-1)) {
            return Float.MAX_VALUE;
        } else if(calculoPrim==0 ) {
            return (nodoVivo.kmParcial + caminoAOrigen(nodoVivo).distanciaTotal);

        } else  {
            Camino caminoInicio =  menorCamino(nodoInicio, nodosNoVisitados);
            Camino caminoFin = menorCamino(nodoUltimo, nodosNoVisitados);
            if(caminoFin !=null && caminoInicio != null) {
                return nodoVivo.kmParcial + calculoPrim + caminoInicio.distanciaTotal + caminoFin.distanciaTotal;
            } else {
                return Float.MAX_VALUE;
            }
        }
    }

    public Camino caminoAOrigen(NodoVivo nodoVivo) {
        NodoGrafo ultimo = nodoVivo.visitados.get(nodoVivo.visitados.size()-1);
        Camino mejorCamino = null;
        for(Camino camino: ultimo.caminos){
            if(camino.destino== central){
                if(mejorCamino == null) {
                    mejorCamino=camino;
                } else {
                    if(camino.distanciaTotal< mejorCamino.distanciaTotal){
                        mejorCamino=camino;
                    }
                }
            }
        }
        return mejorCamino;
    }

    public Camino menorCamino(NodoGrafo nodoGrafo, ArrayList<NodoGrafo> nodosNoVisitados) {
       Camino menor = null;
        for (Camino camino : nodoGrafo.caminos) {
            if (nodosNoVisitados.contains(camino.destino)) {
                if (menor ==null) {
                    menor = camino;
                }
                if (camino.distanciaTotal< menor.distanciaTotal) {
                    menor = camino;
                }
            }
        }
        return menor;
    }




    public Camino menorCamino(ArrayList<Camino> caminos){
        Camino aux = null;
        for(Camino camino : caminos){
            if(aux ==null){
                aux = camino;
            }
            else if(camino.distanciaTotal < aux.distanciaTotal){
                aux = camino;
            }
        }
        return aux;

    }

    public NodoVivo crearRaiz(){
        NodoVivo nodo = new NodoVivo();
        nodo.kmParcial = 0;
        nodo.solucionParcial= new ArrayList<Camino>();
        nodo.visitados = new ArrayList<NodoGrafo>();
        nodo.visitados.add(grafo.obtenerOrigen());
        nodo.etapa=0;
        nodo.tiempoParcial=central.horarioInicio;
        nodo.cotaInferior=calcularCotaInferior(nodo);

        return nodo;
    }


    public boolean podar(NodoVivo nodo, float cota) {
        if (llegaATiempo(nodo)) {
            return (nodo.cotaInferior>cota);
        } else {
            return true;
        }

    }

    public float actualizarCota(NodoVivo nodo, float cota) {
        if(nodo.kmParcial<cota) {
            return nodo.kmParcial;
        } else
            return cota;
    }


    public float prim(ArrayList<NodoGrafo> nodosNoVisitados) {
        if(nodosNoVisitados.size()==0) {
            return 0;
        }
        else {
            float longitud = 0;
            Camino auxCamino;
            ArrayList<NodoGrafo> auxNoVisitados = new ArrayList<NodoGrafo>(nodosNoVisitados);
            ArrayList<Camino> caminosDisponibles = new ArrayList<Camino>();
            NodoGrafo auxNodo = auxNoVisitados.get(0);
            auxNoVisitados.remove(auxNodo);
            agregarCaminos(auxNodo, caminosDisponibles);
            while (auxNoVisitados.size() != 0) {
                auxCamino = menorCamino(caminosDisponibles);
                if(auxCamino ==null) {
                    return -1;
                } else {
                    if (auxNoVisitados.contains(auxCamino.destino)) {
                        longitud += auxCamino.distanciaTotal;
                        auxNoVisitados.remove(auxCamino.destino);
                        agregarCaminos(auxCamino.destino, caminosDisponibles);
                    }
                    caminosDisponibles.remove(auxCamino);

                }
            }
            return longitud;
        }
    }

    public void agregarCaminos(NodoGrafo nodoGrafo, ArrayList<Camino> caminosDisponibles) {
        for(Camino camino : nodoGrafo.caminos) {
            caminosDisponibles.add(camino);
        }

    }
    public boolean esSolucion(NodoVivo nodo) {
        return (nodo.etapa == grafo.vertices().size()-1);
    }

    public boolean esMejorSolucion (NodoVivo mejorSolucion, NodoVivo nodo){
        if (mejorSolucion == null)
            return true;
        else{
            return (nodo.kmParcial < mejorSolucion.kmParcial);
        }
    }
    

}
