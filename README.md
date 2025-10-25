# -_calculadora_de_digitos-digitos_-java_swing- :
# ✨ Calculadora de Dígitos × Dígitos (Java Swing)  .

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/fc1e8160-857f-4315-bcb7-34ea00a0b9c9" />    

Aplicación Java Swing con interfaz “re coqueta” que te permite :

✅ Agregar números a una lista  
✅ Multiplicar cada dígito por sí mismo  
✅ Ver resultados paso a paso  
✅ Interfaz bonita y amigable

### 📌 Instrucciones de uso

```bash
javac CalculadoraDigitosGUI.java
java CalculadoraDigitosGUI
```

### 📂 Código completo

```java
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

    private void initUI() {
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(new EmptyBorder(12, 12, 12, 12));
        top.setBackground(new Color(40, 44, 52));

        JLabel title = new JLabel("Multiplica cada dígito por sí mismo");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        top.add(title, BorderLayout.NORTH);

        JPanel inputRow = new JPanel(new BorderLayout(8, 8));
        inputRow.setOpaque(false);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setToolTipText("Escribe un número y presiona Agregar, o pega varios separados por coma o espacio.");
        inputRow.add(inputField, BorderLayout.CENTER);

        JPanel btns = new JPanel(new GridLayout(1, 3, 8, 8));
        btns.setOpaque(false);
        JButton agregarBtn = makeStyledButton("➕ Agregar");
        JButton pegarBtn = makeStyledButton("📎 Pegar múltiples");
        JButton limpiarBtn = makeStyledButton("🧹 Limpiar lista");

        btns.add(agregarBtn);
        btns.add(pegarBtn);
        btns.add(limpiarBtn);
        inputRow.add(btns, BorderLayout.EAST);

        top.add(inputRow, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        center.setBorder(new EmptyBorder(0, 12, 12, 12));
        center.setBackground(new Color(60, 63, 69));

        JPanel leftPanel = new JPanel(new BorderLayout(8, 8));
        leftPanel.setOpaque(false);
        JLabel listLabel = new JLabel("Lista de números");
        listLabel.setForeground(Color.WHITE);
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(listLabel, BorderLayout.NORTH);

        listaModel = new DefaultListModel<>();
        listaNumeros = new JList<>(listaModel);
        listaNumeros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNumeros.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane listScroll = new JScrollPane(listaNumeros);
        leftPanel.add(listScroll, BorderLayout.CENTER);

        JPanel leftBtns = new JPanel(new GridLayout(1, 2, 8, 8));
        leftBtns.setOpaque(false);
        JButton quitarBtn = makeStyledButton("➖ Quitar seleccionado");
        JButton procesarBtn = makeStyledButton("▶ Procesar lista");
        leftBtns.add(quitarBtn);
        leftBtns.add(procesarBtn);
        leftPanel.add(leftBtns, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(8,8));
        rightPanel.setOpaque(false);
        JLabel outLabel = new JLabel("Salida (paso a paso)");
        outLabel.setForeground(Color.WHITE);
        outLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(outLabel, BorderLayout.NORTH);

        salida = new JTextArea();
        salida.setEditable(false);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setBackground(new Color(250,250,250));
        JScrollPane outScroll = new JScrollPane(salida);
        rightPanel.add(outScroll, BorderLayout.CENTER);

        center.add(leftPanel);
        center.add(rightPanel);

        add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(6, 12, 12, 12));
        footer.setBackground(new Color(40, 44, 52));
        JLabel hint = new JLabel("Tip: puedes pegar varios números separados por coma o espacios.");
        hint.setForeground(Color.LIGHT_GRAY);
        footer.add(hint, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);

        agregarBtn.addActionListener(e -> agregarDesdeCampo());
        pegarBtn.addActionListener(e -> pegarMultiplesDesdeCampo());
        limpiarBtn.addActionListener(e -> {
            listaModel.clear();
            salida.setText("");
        });

        quitarBtn.addActionListener(e -> {
            int idx = listaNumeros.getSelectedIndex();
            if (idx >= 0) listaModel.remove(idx);
        });

        procesarBtn.addActionListener(e -> procesarLista());

        inputField.addActionListener(e -> agregarDesdeCampo());

        listaNumeros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int idx = listaNumeros.locationToIndex(evt.getPoint());
                    if (idx >= 0) procesarNumero(listaModel.get(idx));
                }
            }
        });
    }

    private JButton makeStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(100, 149, 237));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        return btn;
    }

    private void agregarDesdeCampo() {
        String txt = inputField.getText().trim();
        if (txt.isEmpty()) return;
        if (multiSplit.matcher(txt).find() && (txt.contains(",") || txt.contains(" "))) {
            pegarMultiples(txt);
        } else {
            if (txt.matches(".*\\d.*")) {
                listaModel.addElement(txt);
                inputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Entrada inválida", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void pegarMultiplesDesdeCampo() {
        String txt = inputField.getText().trim();
        if (txt.isEmpty()) return;
        pegarMultiples(txt);
    }

    private void pegarMultiples(String txt) {
        String[] parts = multiSplit.split(txt);
        int added = 0;
        for (String p : parts) {
            if (p.trim().isEmpty()) continue;
            if (p.matches(".*\\d.*")) {
                listaModel.addElement(p.trim());
                added++;
            }
        }
        inputField.setText("");
        JOptionPane.showMessageDialog(this, "Se agregaron " + added + " elementos a la lista.", "Pegado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void procesarLista() {
        if (listaModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La lista está vacía. Agrega números primero.", "Lista vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listaModel.size(); i++) {
            String num = listaModel.get(i);
            sb.append(procesarTextoNumero(num));
            if (i < listaModel.size() - 1) sb.append("\n");
        }
        salida.setText(sb.toString());
        salida.setCaretPosition(0);
    }

    private void procesarNumero(String numero) {
        String resultado = procesarTextoNumero(numero);
        salida.setText(resultado);
        salida.setCaretPosition(0);
    }

    private String procesarTextoNumero(String numero) {
        StringBuilder sb = new StringBuilder();
        sb.append("Número original: ").append(numero).append("\n");

        String onlyDigits = numero.replaceAll("\\D+", "");
        if (onlyDigits.isEmpty()) {
            sb.append("  (No contiene dígitos válidos)\n");
            return sb.toString();
        }

        sb.append("Cálculos:\n");
        for (int i = 0; i < onlyDigits.length(); i++) {
            char ch = onlyDigits.charAt(i);
            int d = Character.getNumericValue(ch);
            int r = d * d;
            sb.append(String.format("  %d * %d = %d", d, d, r));
            if (i < onlyDigits.length() - 1) sb.append("\n");
        }
        sb.append("\n------------------------\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            CalculadoraDigitosGUI app = new CalculadoraDigitosGUI();
            app.setVisible(true);
        });
    }
}
```

### 🔍 Ejemplo

Entrada: `305`

Salida:

```
Número original: 305
Cálculos:
  3 * 3 = 9
  0 * 0 = 0
  5 * 5 = 25
------------------------
```

### 💡 Mejoras opcionales

- Guardar resultados a archivo
- Soporte para números negativos
- Colores en resultados
- Migrar a JavaFX    :.
