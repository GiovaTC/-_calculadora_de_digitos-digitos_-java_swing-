import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * CalculadoraDigitosGUI
 * Aplicación Swing que permite agregar números a una lista y mostrar
 * paso a paso cada dígito multiplicado por sí mismo (n * n = resultado).
 *
 * Instrucciones:
 * 1. javac CalculadoraDigitosGUI.java
 * 2. java CalculadoraDigitosGUI
 */

public class CalculadoraDigitosGUI extends JFrame {

    private DefaultListModel<String> listaModel;
    private JList<String> listaNumeros;
    private JTextArea salida;
    private JTextField inputField;
    private final Pattern multiSplit = Pattern.compile("[,\\s]+");

    public CalculadoraDigitosGUI() {
        setTitle("✨ Calculadora de Dígitos × Dígitos ✨");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initUI();
    }
}