package com.mycompany.arbol;

import java.util.*;

public class Arbol {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce una expresión matemática: ");
        String expresion = scanner.nextLine();

        if (!ValidadorExpresion.validarExpresion(expresion)) {
            System.out.println("La expresión contiene caracteres no permitidos.");
            return;
        }

        if (!ValidadorExpresion.verificarParentesis(expresion)) {
            System.out.println("Los paréntesis están desbalanceados o mal posicionados.");
            return;
        }

        String postfija = EvaluadorExpresion.convertirApostfija(expresion);
        System.out.println("Notación postfija: " + postfija);

        ArbolExpresion arbol = new ArbolExpresion(postfija);
        System.out.println("Recorrido inorden: " + arbol.getInorden());
        System.out.println("Recorrido preorden: " + arbol.getPreorden());
        System.out.println("Recorrido postorden: " + arbol.getPostorden());

        Set<Character> variables = new HashSet<>();
        for (char c : expresion.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(c);
            }
        }

        Map<Character, Double> valores = new HashMap<>();
        for (char variable : variables) {
            System.out.print("Introduce el valor para la variable " + variable + ": ");
            valores.put(variable, scanner.nextDouble());
        }

        try {
            double resultado = EvaluadorExpresion.evaluarPostfija(postfija, valores);
            System.out.println("El resultado de la expresión es: " + resultado);
        } catch (Exception e) {
            System.out.println("Error en la evaluación: " + e.getMessage());
        }
        scanner.close();
    }
}