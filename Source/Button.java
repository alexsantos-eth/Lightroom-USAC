package Source;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class Button extends JButton {
  private static final long serialVersionUID = 1L;

  private void setProperties(String text) {
    // LAYOUT
    setLayout(new GridLayout(1, 1));

    // TEXTO
    JLabel btnText = new JLabel(text);
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

  public Button(String text) {
    setProperties(text);
    setBackground(Theme.blue);
  }

  public Button(String text, Color background) {
    setProperties(text);
    setBackground(background);
  }

  public Button(String text, int width, int height) {
    setProperties(text);
    setBackground(Theme.blue);
    setPreferredSize(new Dimension(width, height));
  }

  public Button(String text, int width, int height, Color background) {
    setProperties(text);
    setBackground(background);
    setPreferredSize(new Dimension(width, height));
  }
}