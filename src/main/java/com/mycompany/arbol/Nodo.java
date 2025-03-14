package com.mycompany.arbol;

public class Nodo {
    private String valor;
    private Nodo izquierdo;
    private Nodo derecho;
    private boolean esOperador;

    public Nodo(String valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
        this.esOperador = "+-*/^√".contains(valor);
    }

    public boolean esOperador() {
        return esOperador;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    public String getValor() {
        return valor;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }
}