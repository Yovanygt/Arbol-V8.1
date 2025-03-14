package com.mycompany.arbol;

import java.util.Stack;

public class ValidadorExpresion {

    // Método para validar la expresión
    public static boolean validarExpresion(String expresion) {
        // Eliminar espacios al inicio y al final de la expresión
        expresion = expresion.trim();

        // Verificar que no haya operadores consecutivos o en los extremos
        if (expresion.matches(".*[\\+\\-*/^√]{2,}.*") || 
            expresion.startsWith("+") || expresion.startsWith("-") || 
            expresion.endsWith("+") || expresion.endsWith("-")) {
            return false;
        }
        
        // Verificar que solo haya caracteres permitidos
        return expresion.matches("[a-zA-Z0-9+\\-*/^√()\\s]+");
    }

    // Método para verificar que los paréntesis estén balanceados
    public static boolean verificarParentesis(String expresion) {
        Stack<Character> pila = new Stack<>();
        for (char c : expresion.toCharArray()) {
            if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                if (pila.isEmpty()) return false; // Si no hay paréntesis de apertura
                pila.pop();
            }
        }
        return pila.isEmpty(); // Si la pila está vacía, los paréntesis están balanceados
    }
}
