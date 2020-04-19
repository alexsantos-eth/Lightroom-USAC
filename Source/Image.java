package Source;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Image extends JEditorPane {
  private static final long serialVersionUID = 1L;
  int width, height;
  JScrollPane scrollPane;
  String src;

  public Image(String src, int width, int height) {
    this.src = src;
    this.width = width;
    this.height = height;

    setEditable(false);

    scrollPane = new JScrollPane(this);
    scrollPane.setPreferredSize(new Dimension(width, height));
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);
  }

  public void updateSrc(String path) {
    this.src = path + ".bmp";
    repaint();
  }

  public JScrollPane getCPane() {
    return scrollPane;
  }

  public void paint(Graphics g) {
    try {
      File imgFile = new File(src);
      byte[] byteArry = new byte[(int) imgFile.length()];

      FileInputStream fileStream = new FileInputStream(imgFile);
      fileStream.read(byteArry);
      fileStream.close();

      int width = *(int*) &byteArry[18];
      System.out.println(width);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
