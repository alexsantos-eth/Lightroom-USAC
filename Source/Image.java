package Source;

import javax.swing.*;
import java.awt.*;

public class Image extends JPanel {
  private static final long serialVersionUID = 1L;
  int width, height;
  String src;

  public Image(String src, int width, int height) {
    this.src = src;
    this.width = width;
    this.height = height;

    setSize(width, height);
    setPreferredSize(new Dimension(width, height));
  }

  public void paint(Graphics g) {
    ImageIcon image = new ImageIcon(src);
    g.drawImage(image.getImage(), 0, 0, width, height, null);
    setOpaque(false);
    super.paintComponent(g);
  }

  public void updateSrc(String path) {
    this.src = path;
    repaint();
  }
}
