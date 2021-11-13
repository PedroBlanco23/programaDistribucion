package api;

import TDA.NodoVivo;

public interface ColaPrioridadTDA {
    void inicializarColaPrioridad();
    void acolar(NodoVivo nodo, float cota);
    void desacolar();
    NodoVivo primero();
    boolean colaVacia();
}
