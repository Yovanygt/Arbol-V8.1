package com.mycompany.arbol;

import java.util.*;
import javax.swing.JTextArea;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class EvaluadorExpresion {

    // Método para convertir una expresión infija a postfija
    public static String convertirApostfija(String expresion) {
        Stack<Character> pila = new Stack<>();
        StringBuilder resultado = new StringBuilder();
        StringBuilder numero = new StringBuilder(); // Para construir números de más de un dígito
        String operadores = "+-*/^√";

        expresion = expresion.replaceAll("\\s", ""); // Eliminar todos los espacios

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (Character.isDigit(c)) {
                numero.append(c); // Acumula dígitos
            } else {
                if (numero.length() > 0) {
                    resultado.append(numero).append(" "); // Agrega el número completo
                    numero.setLength(0); // Limpia el buffer
                }

                if (Character.isLetter(c)) {
                    resultado.append(c).append(" ");
                } else if (operadores.indexOf(c) != -1) {
                    while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(c)) {
                        resultado.append(pila.pop()).append(" ");
                    }
                    pila.push(c);
                } else if (c == '(') {
                    pila.push(c);
                } else if (c == ')') {
                    while (!pila.isEmpty() && pila.peek() != '(') {
                        resultado.append(pila.pop()).append(" ");
                    }
                    pila.pop(); // Eliminar el paréntesis de apertura
                }
            }
        }

        if (numero.length() > 0) {
            resultado.append(numero).append(" "); // Agregar el último número si existe
        }

        while (!pila.isEmpty()) {
            resultado.append(pila.pop()).append(" ");
        }
        return resultado.toString().trim();
    }

    // Método para evaluar una expresión postfija
    public static double evaluarPostfija(String postfija, Map<Character, Double> valores, JTextArea proceso) {
        Stack<Double> pila = new Stack<>();
        String[] tokens = postfija.split(" ");

        for (String token : tokens) {
            proceso.append("Procesando token: " + token + "\n");
            if (token.matches("[a-zA-Z]")) {
                pila.push(valores.get(token.charAt(0)));
            } else if (token.matches("\\d+(\\.\\d+)?")) {
                pila.push(Double.parseDouble(token));
            } else {
                double b = pila.pop();
                double a = token.equals("√") ? 0 : pila.pop();
                switch (token) {
                    case "+": pila.push(a + b); break;
                    case "-": pila.push(a - b); break;
                    case "*": pila.push(a * b); break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("División por cero");
                        pila.push(a / b);
                        break;
                    case "^": pila.push(Math.pow(a, b)); break;
                    case "√": pila.push(Math.sqrt(b)); break;
                    default: throw new IllegalArgumentException("Operador no reconocido: " + token);
                }
            }
            proceso.append("Pila: " + pila + "\n");
        }

        if (pila.size() != 1) {
            throw new IllegalStateException("Expresión mal formada");
        }

        BigDecimal resultado = new BigDecimal(pila.pop()).setScale(2, RoundingMode.HALF_UP);
        return resultado.doubleValue();
    }

    // Método sobrecargado sin JTextArea para la interfaz de consola
    public static double evaluarPostfija(String postfija, Map<Character, Double> valores) {
        return evaluarPostfija(postfija, valores, new JTextArea());
    }

    // Método para obtener la prioridad de un operador
    private static int prioridad(char operador) {
        switch (operador) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            case '√': return 4;
            default: return -1;
        }
    }
}
