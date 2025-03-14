
package com.mycompany.arbol;

import java.util.*;

public class ConvertidorPostfija {
    public static String convertirAPostfija(String expresion) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> operadores = new Stack<>();
        Map<Character, Integer> prioridad = Map.of(
            '+', 1, '-', 1, '*', 2, '/', 2, '^', 3, '√', 4
        );

        for (char c : expresion.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                resultado.append(c).append(" ");
            } else if (c == '(') {
                operadores.push(c);
            } else if (c == ')') {
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    resultado.append(operadores.pop()).append(" ");
                }
                operadores.pop();
            } else {
                while (!operadores.isEmpty() && prioridad.getOrDefault(operadores.peek(), 0) >= prioridad.getOrDefault(c, 0)) {
                    resultado.append(operadores.pop()).append(" ");
                }
                operadores.push(c);
            }
        }

        while (!operadores.isEmpty()) {
            resultado.append(operadores.pop()).append(" ");
        }

        return resultado.toString().trim();
    }
}
