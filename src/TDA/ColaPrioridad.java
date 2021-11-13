package TDA;

import api.ColaPrioridadTDA;

public class ColaPrioridad implements ColaPrioridadTDA {

    class NodoPrioridad {
        NodoVivo nodoVivo;
        float cota;
        NodoPrioridad siguiente;
    }

    NodoPrioridad mayorPrioridad;

    @Override
    public void inicializarColaPrioridad() {
        mayorPrioridad = null;
    }

    public void acolar(NodoVivo nodo, float cota) {
        // Se crea el nodo a agregar
        NodoPrioridad nuevo = new NodoPrioridad();
        nuevo.nodoVivo = nodo;
        nuevo.cota = cota;

        // Si la cola esta vacia o el nuevo nodo tiene una menor cota, se lo agrega al principio
        if(mayorPrioridad == null || cota < mayorPrioridad.cota){
            nuevo.siguiente = mayorPrioridad;
            mayorPrioridad = nuevo;
        } else {
            NodoPrioridad aux = mayorPrioridad;
            while(aux.siguiente != null && aux.siguiente.cota <= cota) {
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
