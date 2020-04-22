package Source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Input extends JPanel {
  private static final long serialVersionUID = 1L;
  JTextField inputField;

  private void setProperties(String label, String placeholder) {
    // PROPIEDADES
    setLayout(new GridLayout(2, 1));

    // COMPONENTES
    inputField = new JTextField();
    inputField.setForeground(new Color(100, 100, 100));
    inputField.setFont(Globals.font);
    inputField.setBorder(new RoundedBorder(10));
    inputField.setText(placeholder);

    // EVENTOS
    inputField.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        inputField.setForeground(new Color(50, 50, 50));
        inputField.setText("");
      }
    });

    // LABEL
    JLabel inputLabel = new JLabel(label);
    inputLabel.setForeground(new Color(100, 100, 100));
    inputLabel.setFont(Globals.font.deriveFont(Font.BOLD));

    // AGREGAR TEXT FIELD Y LABEL
    add(inputLabel);
    add(inputField);
  }

  // OBTENER TEXT FIELD
  public JTextField getTextField() {
    return inputField;
  }

  public Input(String label, String placeholder, int width, int height) {
    // PROPIEDADES
    setProperties(label, placeholder);
    setSize(width, height);
  }

  public Input() {
    setProperties("", "");
  }

  public Input(String label, int width, int height) {
    setProperties(label, "");
    setSize(width, height);
  }

}
