package Source.Utils;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label extends JLabel {
  private static final long serialVersionUID = 1L;

  // CREAR PROPIEDADES
  private void setProperties(String text) {
    setText(text);
    setFont(Globals.font.deriveFont(Font.BOLD));
    setForeground(new Color(100, 100, 100));
  }

  public Label(String text) {
    setProperties(text);
  }

  public Label(String text, Color foreground) {
    setProperties(text);
    setForeground(foreground);
  }
}