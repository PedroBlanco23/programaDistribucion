package api;

import TDA.NodoVivo;

public interface ColaPrioridadTDA {
    void inicializarColaPrioridad();
    void acolar(NodoVivo nodo, int etapa , float cota);
    void desacolar();
    NodoVivo primero();
    boolean colaVacia();
}
