import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public final class GrafoNoDirigido {
    private final boolean[][] matrizAdyacencia;

    public GrafoNoDirigido(int cantidadVertices) {
        if (cantidadVertices < 0) {
            throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa.");
        }
        matrizAdyacencia = new boolean[cantidadVertices][cantidadVertices];
    }

    public int cantidadVertices() {
        return matrizAdyacencia.length;
    }

    public void agregarArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        matrizAdyacencia[origen][destino] = true;
        matrizAdyacencia[destino][origen] = true;
    }

    public void eliminarArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        matrizAdyacencia[origen][destino] = false;
        matrizAdyacencia[destino][origen] = false;
    }

    public boolean existeArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        return matrizAdyacencia[origen][destino];
    }

    public List<Integer> obtenerVecinos(int vertice) {
        validarVertice(vertice);
        List<Integer> vecinos = new ArrayList<>();
        for (int otro = 0; otro < cantidadVertices(); otro++) {
            if (matrizAdyacencia[vertice][otro]) {
                vecinos.add(otro);
            }
        }
        return vecinos;
    }

    public List<Integer> recorridoDFS(int inicio) {
        validarVertice(inicio);
        boolean[] visitados = new boolean[cantidadVertices()];
        List<Integer> recorrido = new ArrayList<>();
        Deque<Integer> pila = new ArrayDeque<>();
        pila.push(inicio);

        while (!pila.isEmpty()) {
            int vertice = pila.pop();
            if (visitados[vertice]) {
                continue;
            }
            visitados[vertice] = true;
            recorrido.add(vertice);

            for (int vecino = cantidadVertices() - 1; vecino >= 0; vecino--) {
                if (matrizAdyacencia[vertice][vecino] && !visitados[vecino]) {
                    pila.push(vecino);
                }
            }
        }
        return recorrido;
    }

    public List<Integer> recorridoBFS(int inicio) {
        validarVertice(inicio);
        boolean[] visitados = new boolean[cantidadVertices()];
        List<Integer> recorrido = new ArrayList<>();
        Queue<Integer> cola = new ArrayDeque<>();
        visitados[inicio] = true;
        cola.add(inicio);

        while (!cola.isEmpty()) {
            int vertice = cola.remove();
            recorrido.add(vertice);

            for (int vecino = 0; vecino < cantidadVertices(); vecino++) {
                if (matrizAdyacencia[vertice][vecino] && !visitados[vecino]) {
                    visitados[vecino] = true;
                    cola.add(vecino);
                }
            }
        }
        return recorrido;
    }

    public boolean[][] obtenerMatrizAdyacencia() {
        boolean[][] copia = new boolean[cantidadVertices()][cantidadVertices()];
        for (int fila = 0; fila < cantidadVertices(); fila++) {
            copia[fila] = matrizAdyacencia[fila].clone();
        }
        return copia;
    }

    public void imprimirMatriz() {
        for (boolean[] fila : matrizAdyacencia) {
            for (boolean hayArista : fila) {
                System.out.print((hayArista ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    private void validarVertice(int vertice) {
        if (vertice < 0 || vertice >= cantidadVertices()) {
            throw new IndexOutOfBoundsException(
                    "Vertice fuera de rango: " + vertice + ". El rango valido es 0 a "
                            + (cantidadVertices() - 1) + ".");
        }
    }

    public static void main(String[] args) {
        GrafoNoDirigido grafo = new GrafoNoDirigido(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(1, 3);

        System.out.println("Matriz de adyacencia:");
        grafo.imprimirMatriz();
        System.out.println("Vecinos del vertice 0: " + grafo.obtenerVecinos(0));
        System.out.println("Recorrido DFS desde 0: " + grafo.recorridoDFS(0));
        System.out.println("Recorrido BFS desde 0: " + grafo.recorridoBFS(0));
    }
}