package TDA;

import api.ColaPrioridadTDA;

public class ColaPrioridad implements ColaPrioridadTDA {

    class NodoPrioridad {
        NodoVivo nodoVivo;
        int etapa;
        float cota;
        NodoPrioridad siguiente;
    }

    NodoPrioridad mayorPrioridad;
    public int cant;
    @Override
    public void inicializarColaPrioridad() {
        mayorPrioridad = null;
        cant =0;
    }

    public void acolar(NodoVivo nodo, int etapa, float cota) {
        // Se crea el nodo a agregar
        NodoPrioridad nuevo = new NodoPrioridad();
        nuevo.nodoVivo = nodo;
        nuevo.etapa = etapa;
        cant+=1;

        // Si la cola esta vacia o el nuevo nodo tiene una menor cota, se lo agrega al principio
        if(mayorPrioridad == null || etapa > mayorPrioridad.etapa || (nodo.cotaInferior < mayorPrioridad.cota && nodo.etapa == mayorPrioridad.etapa)){
            nuevo.siguiente = mayorPrioridad;
            mayorPrioridad = nuevo;
        } else {
            NodoPrioridad aux = mayorPrioridad;
            while(aux.siguiente != null && (aux.siguiente.etapa > etapa || (aux.siguiente.etapa== etapa && nodo.cotaInferior>aux.siguiente.cota ))) {
                aux = aux.siguiente;
            }
            nuevo.siguiente = aux.siguiente;
            aux.siguiente = nuevo;
        }
    }

    @Override
    public void desacolar() {
        mayorPrioridad = mayorPrioridad.siguiente;
    }

    @Override
    public NodoVivo primero() {
        return mayorPrioridad.nodoVivo;
    }

    @Override
    public boolean colaVacia() {
        return (mayorPrioridad == null);
    }
}
