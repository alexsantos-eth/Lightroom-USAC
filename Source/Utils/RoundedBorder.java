package Source.Utils;

import java.awt.*;
import javax.swing.border.Border;

public class RoundedBorder implements Border {
  // RADIO DEL BORDE
  private int radius;

  public RoundedBorder(int radius) {
    this.radius = radius;
  }

  // OBTENER POSICIONES
  public Insets getBorderInsets(Component c) {
    return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
  }

  // OBTENER VALOR DE OPACIDAD
  public boolean isBorderOpaque() {
    return true;
  }

  // DIBUJAR BORDE
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.setColor(Color.white);
    g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
  }
}