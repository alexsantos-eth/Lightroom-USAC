package Source;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label extends JLabel {
  private static final long serialVersionUID = 1L;

  private void setProperties(String text) {
    setText(text);
    setFont(Theme.font.deriveFont(Font.BOLD));
    setForeground(new Color(100, 100, 100));
  }

  public Label(String text) {
    setProperties(text);
  }
}