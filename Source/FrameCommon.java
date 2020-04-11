package Source;

import java.awt.*;
import javax.swing.*;

public class FrameCommon extends JFrame {
  private static final long serialVersionUID = 1L;

  public FrameCommon() {
    super("LightRoom USAC");

    // AGREGAR ICONO Y COLOR
    setIconImage(new ImageIcon("../Source/assets/icon.png").getImage());
    setBackground(Color.white);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    // AGREGAR DIMENSIONES
    setResizable(false);
    setVisible(true);
    setSize(600, 600);
  }

}