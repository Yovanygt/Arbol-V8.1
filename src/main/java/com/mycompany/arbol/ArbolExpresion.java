package com.mycompany.arbol;

import java.util.Stack;

public class ArbolExpresion {
    private Nodo raiz;

    public ArbolExpresion(String postfija) {
        this.raiz = construirArbol(postfija);
    }

    private Nodo construirArbol(String postfija) {
        Stack<Nodo> pila = new Stack<>();
        String[] tokens = postfija.split("\\s+"); // Separar por espacios
        
        for (String token : tokens) {
            Nodo nodo = new Nodo(token);
            if (nodo.esOperador()) {
                Nodo nodoDerecho = pila.pop();
                Nodo nodoIzquierdo = null;
                if (!token.equals("√")) {
                    nodoIzquierdo = pila.pop();
                }
                nodo.setIzquierdo(nodoIzquierdo);
                nodo.setDerecho(nodoDerecho);
                pila.push(nodo);
            } else {
                pila.push(nodo);
            }
        }
        return pila.pop(); // El último nodo es la raíz
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public String getInorden() {
        StringBuilder sb = new StringBuilder();
        recorridoInorden(raiz, sb);
        return sb.toString().trim();
    }

    public String getPreorden() {
        StringBuilder sb = new StringBuilder();
        recorridoPreorden(raiz, sb);
        return sb.toString().trim();
    }

    public String getPostorden() {
        StringBuilder sb = new StringBuilder();
        recorridoPostorden(raiz, sb);
        return sb.toString().trim();
    }

    private void recorridoInorden(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            recorridoInorden(nodo.getIzquierdo(), sb);
            sb.append(nodo.getValor()).append(" ");
            recorridoInorden(nodo.getDerecho(), sb);
        }
    }

    private void recorridoPreorden(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.getValor()).append(" ");
            recorridoPreorden(nodo.getIzquierdo(), sb);
            recorridoPreorden(nodo.getDerecho(), sb);
        }
    }

    private void recorridoPostorden(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            recorridoPostorden(nodo.getIzquierdo(), sb);
            recorridoPostorden(nodo.getDerecho(), sb);
            sb.append(nodo.getValor()).append(" ");
        }
    }
}
