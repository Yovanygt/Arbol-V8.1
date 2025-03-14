package com.mycompany.arbol;

import javax.swing.*;
import java.awt.*;

public class PanelGrafico extends JPanel {
    private Nodo raiz;

    public PanelGrafico(Nodo raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            dibujarArbol(g, raiz, getWidth() / 2, 30, 100);
        }
    }

    private void dibujarArbol(Graphics g, Nodo nodo, int x, int y, int deltaX) {
        if (nodo != null) {
            g.setColor(Color.BLACK);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(nodo.getValor(), x - 5, y + 5);

            if (nodo.getIzquierdo() != null) {
                g.drawLine(x, y, x - deltaX, y + 50);
                dibujarArbol(g, nodo.getIzquierdo(), x - deltaX, y + 50, deltaX / 2);
            }

            if (nodo.getDerecho() != null) {
                g.drawLine(x, y, x + deltaX, y + 50);
                dibujarArbol(g, nodo.getDerecho(), x + deltaX, y + 50, deltaX / 2);
            }
        }
    }

    // Método para limpiar la gráfica
    public void limpiar() {
        this.raiz = null;  // Eliminar la referencia del árbol
        repaint();         // Redibujar el panel (se verá vacío)
    }
}
