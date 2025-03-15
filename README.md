# PROYECTO I

![enter image description here](https://i.postimg.cc/HLgBYrgM/Java-Logo.png)


# Supercalculadora 

Esta es una aplicación desarrollada en Java con Swing, diseñada para interpretar y evaluar expresiones matemáticas ingresadas por el usuario. La aplicación permite validar la sintaxis de la expresión, transformarla a notación postfija (RPN), construir un árbol binario para su representación estructurada y evaluar su resultado mostrando el proceso paso a paso.

Cuenta con una interfaz gráfica intuitiva que facilita la interacción con el usuario, permitiendo visualizar tanto la conversión de la expresión como sus diferentes recorridos en el árbol (Inorden, Preorden y Postorden). Además, la evaluación de la expresión se muestra detalladamente, incluyendo los cambios en la pila durante el cálculo.

![enter image description here](https://i.postimg.cc/wjZg33Yx/imagen-2025-03-14-054751227.png)

# Características Principales

 - Validación de expresiones matemáticas con operadores (+, -, *, /, ^, √) y variables.
 - Conversión automática de notación infija a notación postfija.
 - Construcción y representación de un árbol de expresión.
 - Visualización de recorridos: inorden, preorden y postorden.
 - Evaluación paso a paso, mostrando cambios en la pila.
 - Interfaz gráfica interactiva para una mejor experiencia de usuario.

# Herramientas Utilizadas

 - Lenguaje de Programación: **Java**
 - Interfaz Gráfica: **Swing**
 - IDE: **NetBeans**
 - Gestión de Datos: **Estructuras de datos en Java** (pilas, árboles)
 - Librerías de Java Estándar: **javax.swing**, **java.util**, **java.math**

# Clases utilizadas

A continuación se presenta todo el código de manera ordenada y una breve descripción del mismo:

## Arbol.java

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

Esta clase actúa como el punto de entrada principal para la ejecución de la aplicación en modo consola. Su objetivo principal es permitir al usuario ingresar una expresión matemática, validarla, convertirla a notación postfija, construir el árbol de expresión y evaluar su resultado.

## ArbolExpresion.java

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
                    if (nodo.esOpera
    
    dor()) {
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


Esta clase construye y manipula un árbol de expresión a partir de una notación postfija. Permite recorrer el árbol en diferentes órdenes.

## ConvertidorPostfija.java


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

Esta clase convierte una expresión en notación infija (convencional) a notación postfija (notación polaca inversa, RPN).

## EvaluadorExpresion.java

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

Esta clase evalúa una expresión matemática escrita en notación postfija.

## InterfazArbol.java

    package com.mycompany.arbol;
    
    import javax.swing.*;
    import java.awt.*;
    import java.util.HashMap;
    import java.util.Map;
    
    public class InterfazArbol extends JFrame {
        private JTextField inputExpresion;
        private JTextArea outputPostfija, outputProceso, outputResultado, outputInorden, outputPreorden, outputPostorden;
        private JButton btnOperar, btnBorrar, btnGraficar;
        private PanelGrafico panelGrafico;
    
        public InterfazArbol() {
            setTitle("Calculadora de Expresiones");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 700);
            setLayout(new BorderLayout(10, 10));
    
            // Panel de entrada
            JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ‎src/main/java/com/mycompany/arbol/InterfazArbol.java
+25
Original file line number	Diff line number	Diff line change
@@ -22,6 +22,7 @@
        panelSuperior.add(new JLabel("Ingrese la función:"));
        inputExpresion = new JTextField(30);
        panelSuperior.add(inputExpresion);
        

        // Panel de resultados Arbol
        JPanel panelResultados = new JPanel(new GridBagLayout());
@@ -31,81 +32,105 @@
        gbc.gridx = 0;
        gbc.gridy = 0;

        
        panelResultados.add(new JLabel("Notación Postfija:"), gbc);
        gbc.gridy++;
        outputPostfija = new JTextArea(2, 40);
        outputPostfija.setLineWrap(true);
        outputPostfija.setWrapStyleWord(true);
        outputPostfija.setMinimumSize(new Dimension(400, 50));
        panelResultados.add(new JScrollPane(outputPostfija), gbc);
        
        
        

        gbc.gridy++;
        panelResultados.add(new JLabel("Recorrido Inorden:"), gbc);
        gbc.gridy++;
        outputInorden = new JTextArea(2, 40);
        outputInorden.setLineWrap(true);
        outputInorden.setWrapStyleWord(true);
        outputInorden.setMinimumSize(new Dimension(400, 50));
        panelResultados.add(new JScrollPane(outputInorden), gbc);

        gbc.gridy++;
        panelResultados.add(new JLabel("Recorrido Preorden:"), gbc);
        gbc.gridy++;
        outputPreorden = new JTextArea(2, 40);
        outputPreorden.setLineWrap(true);
        outputPreorden.setWrapStyleWord(true);
        outputPreorden.setMinimumSize(new Dimension(400, 50));
        panelResultados.add(new JScrollPane(outputPreorden), gbc);

        gbc.gridy++;
        panelResultados.add(new JLabel("Recorrido Postorden:"), gbc);
        gbc.gridy++;
        outputPostorden = new JTextArea(2, 40);
        outputPostorden.setLineWrap(true);
        outputPostorden.setWrapStyleWord(true);
        outputPostorden.setMinimumSize(new Dimension(400, 50));
        panelResultados.add(new JScrollPane(outputPostorden), gbc);

        gbc.gridy++;
        panelResultados.add(new JLabel("Proceso de Evaluación:"), gbc);
        gbc.gridy++;
        outputProceso = new JTextArea(6, 40);
        outputProceso.setLineWrap(true);
        outputProceso.setWrapStyleWord(true);
        outputProceso.setMinimumSize(new Dimension(4000, 2000));
        panelResultados.add(new JScrollPane(outputProceso), gbc);

        gbc.gridy++;
        panelResultados.add(new JLabel("Resultado Final:"), gbc);
        gbc.gridy++;
        outputResultado = new JTextArea(2, 40);
        outputResultado.setLineWrap(true);
        outputResultado.setWrapStyleWord(true);
        outputResultado.setMinimumSize(new Dimension(4000, 2000));
    
            // Botones
            JPanel panelBotones = new JPanel();
            btnOperar = new JButton("Operar");
            btnBorrar = new JButton("Borrar");
            btnGraficar = new JButton("Graficar");
            panelBotones.add(btnOperar);
            panelBotones.add(btnBorrar);
            panelBotones.add(btnGraficar);
    
            add(panelSuperior, BorderLayout.NORTH);
            add(panelResultados, BorderLayout.CENTER);
            add(panelBotones, BorderLayout.SOUTH);
    
            // Panel gráfico
            panelGrafico = new PanelGrafico(null);
            add(panelGrafico, BorderLayout.EAST); // Colocamos el panel a la derecha para graficar
    
            // Acciones de los botones
            btnOperar.addActionListener(e -> procesarExpresion());
            btnBorrar.addActionListener(e -> limpiarCampos());
            btnGraficar.addActionListener(e -> graficarArbol());
    
            setLocationRelativeTo(null);
            setVisible(true);
        }
    
        private void procesarExpresion() {
            String expresion = inputExpresion.getText();
            if (!ValidadorExpresion.validarExpresion(expresion)) {
                outputPostfija.setText("Expresión inválida");
                return;
            }
            if (!ValidadorExpresion.verificarParentesis(expresion)) {
                outputPostfija.setText("Paréntesis desbalanceados");
                return;
            }
    
            String postfija = EvaluadorExpresion.convertirApostfija(expresion);
            outputPostfija.setText(postfija);
    
            ArbolExpresion arbol = new ArbolExpresion(postfija);
            outputInorden.setText(arbol.getInorden());
            outputPreorden.setText(arbol.getPreorden());
            outputPostorden.setText(arbol.getPostorden());
    
            Map<Character, Double> valores = new HashMap<>();
            for (char c : expresion.toCharArray()) {
                if (Character.isLetter(c) && !valores.containsKey(c)) {
                    String valorStr = JOptionPane.showInputDialog("Ingrese el valor para " + c + ":");
                    if (valorStr == null || valorStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor para " + c);
                        return;
                    }
                    try {
                        valores.put(c, Double.parseDouble(valorStr));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Valor inválido para " + c);
                        return;
                    }
                }
            }
    
            try {
                outputProceso.setText("");
                double resultado = EvaluadorExpresion.evaluarPostfija(postfija, valores, outputProceso);
                outputResultado.setText(String.valueOf(resultado));
            } catch (Exception ex) {
                outputResultado.setText("Error: " + ex.getMessage());
            }
        }
    
        private void graficarArbol() {
            String expresion = inputExpresion.getText();
            if (!ValidadorExpresion.validarExpresion(expresion)) {
                JOptionPane.showMessageDialog(this, "Expresión inválida para graficar");
                return;
            }
    
            String postfija = EvaluadorExpresion.convertirApostfija(expresion);
            ArbolExpresion arbol = new ArbolExpresion(postfija);
    
            panelGrafico.removeAll();
            panelGrafico = new PanelGrafico(arbol.getRaiz());
            add(panelGrafico, BorderLayout.EAST);
            revalidate();
            repaint();
        }
    
        private void limpiarCampos() {
            inputExpresion.setText("");
            outputPostfija.setText("");
            outputProceso.setText("");
            outputResultado.setText("");
            outputInorden.setText("");
            outputPreorden.setText("");
            outputPostorden.setText("");
            panelGrafico.limpiar();
        }
    
        public static void main(String[] args) {
            new InterfazArbol();
        }
    }

Esta es la interfaz gráfica (GUI) de la aplicación. Permite al usuario ingresar una expresión matemática, visualizar su conversión a notación postfija, los recorridos del árbol de expresión y el resultado de la evaluación.

## Nodo.java

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

Esta clase representa un nodo dentro del árbol de expresión. Puede ser un operador o un operando.

## PanelGrafico.java

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


Esta clase se encarga de visualizar gráficamente un árbol de expresión matemática. Dibuja los nodos y sus conexiones de manera estructurada utilizando Graphics.


## ValidadorExpresion.java

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

Esta clase valida la sintaxis de la expresión ingresada por el usuario. Verifica que solo contenga caracteres permitidos y que los paréntesis estén correctamente balanceados.



