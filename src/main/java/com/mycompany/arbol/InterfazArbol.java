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
        panelSuperior.add(new JLabel("Ingrese la función:"));
        inputExpresion = new JTextField(30);
        panelSuperior.add(inputExpresion);
        

        // Panel de resultados Arbol
        JPanel panelResultados = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
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
        panelResultados.add(new JScrollPane(outputResultado), gbc);

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
    
        // Eliminar el panel gráfico anterior
        remove(panelGrafico);
        
        // Crear un nuevo panel gráfico con el árbol actualizado
        panelGrafico = new PanelGrafico(arbol.getRaiz());
        
        // Agregar el nuevo panel gráfico
        add(panelGrafico, BorderLayout.EAST);
        
        // Actualizar la interfaz
        panelGrafico.revalidate();
        panelGrafico.repaint();
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
