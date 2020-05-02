package Source;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class Button extends JButton {
  private static final long serialVersionUID = 1L;
  public JLabel btnText;

  private void setProperties(String text) {
    // LAYOUT
    setLayout(new GridLayout(1, 1));

    // TEXTO
    btnText = new JLabel(text);
    btnText.setFont(new Font("Roboto", Font.BOLD, 15));
    btnText.setForeground(Color.white);
    btnText.setHorizontalAlignment(SwingConstants.CENTER);

    // AGREGAR PROPIEDADES
    setFocusPainted(false);
    setBorder(new EmptyBorder(10, 30, 10, 30));
    setMargin(new Insets(0, 0, 0, 0));
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setContentAreaFilled(false);
    setOpaque(true);

    // AGREGAR TEXTO
    add(btnText);
  }

  // CAMBIAR FUENTE
  public void setLabelFont(Font lblFont) {
    btnText.setFont(lblFont);
  }

  // OBTENER FUENTE
  public Font getLabelFont() {
    return btnText.getFont();
  }

  // CONSTRUCTORES
  public Button(String text) {
    setProperties(text);
    setBackground(Globals.blue);
  }

  // CONSTRUCTORES
  public Button(String text, Font font) {
    setProperties(text);
    setBackground(Globals.blue);
    setLabelFont(font);
  }

  // CONSTRUCTORES
  public Button(String text, Color background) {
    setProperties(text);
    setBackground(background);
  }

  // CONSTRUCTORES
  public Button(String text, int width, int height) {
    setProperties(text);
    setBackground(Globals.blue);
    setPreferredSize(new Dimension(width, height));
  }

  // CONSTRUCTORES
  public Button(String text, int width, int height, Color background) {
    setProperties(text);
    setBackground(background);
    setPreferredSize(new Dimension(width, height));
  }
}