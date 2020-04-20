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
    this.src = src.contains(".bmp") ? src : src + ".bmp";
    this.width = width;
    this.height = height;

    setEditable(false);

    scrollPane = new JScrollPane(this);
    scrollPane.setPreferredSize(new Dimension(width, height));
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);
  }

  public void updateSrc(String path) {
    this.src = path.contains(".bmp") ? path : path + ".bmp";
    repaint();
  }

  public JScrollPane getCPane() {
    return scrollPane;
  }

  private static byte[] toByteArray(File file) throws FileNotFoundException {
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    try {
      for (int readNum; (readNum = fis.read(buf)) != -1;)
        bos.write(buf, 0, readNum);
    } catch (IOException ex) {
      System.out.print(ex);
    }
    return bos.toByteArray();
  }

  private int toInt(byte[] a, int offs) {
    return (a[offs + 3] & 0xFF) << 24 | (a[offs + 2] & 0xFF) << 16 | (a[offs + 1] & 0xFF) << 8 | a[offs] & 0xFF;
  }

  public void paint(Graphics g) {
    try {
      File imgFile = new File(src);
      byte[] byteArry = toByteArray(imgFile);

      int width = toInt(byteArry, 18);
      int height = toInt(byteArry, 22);

      setSize(width, height);

      int x = width, y = 0;
      for (int i = byteArry.length - 4; i > 54; i -= 3) {
        int B = (byteArry[i + 1] & 0xFF);
        int G = (byteArry[i + 2] & 0xFF);
        int R = (byteArry[i + 3] & 0xFF);

        // LIMITAR VALORES PARA LOS FILTROS EN PROXIMA FASE
        if (B > 255)
          B = 255;
        if (G > 255)
          G = 255;
        if (R > 255)
          R = 255;
        if (B < 0)
          B = 0;
        if (G < 0)
          G = 0;
        if (R < 0)
          R = 0;

        g.setColor(new Color(R, G, B));
        g.drawLine(x, y, x, y);

        x--;

        if (x == 0) {
          x = width;
          y++;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
