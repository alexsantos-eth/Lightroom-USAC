package Source.Utils;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Button extends JButton {
  private static final long serialVersionUID = 1L;
  public JLabel btnText;

  private void setProperties(final String text) {
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
  public void setLabelFont(final Font lblFont) {
    btnText.setFont(lblFont);
  }

  // OBTENER FUENTE
  public Font getLabelFont() {
    return btnText.getFont();
  }

  // CONSTRUCTORES
  public Button(final String text) {
    setProperties(text);
    setBackground(Globals.blue);
  }

  // CONSTRUCTORES
  public Button(final String text, final Font font) {
    setProperties(text);
    setBackground(Globals.blue);
    setLabelFont(font);
  }

  // CONSTRUCTORES
  public Button(final String text, final Color background) {
    setProperties(text);
    setBackground(background);
  }

  // CONSTRUCTORES
  public Button(final String text, final int width, final int height) {
    setProperties(text);
    setBackground(Globals.blue);
    setPreferredSize(new Dimension(width, height));
  }

  // CONSTRUCTORES
  public Button(final String text, final int width, final int height, final Color background) {
    setProperties(text);
    setBackground(background);
    setPreferredSize(new Dimension(width, height));
  }
}