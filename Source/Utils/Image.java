package Source.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Image extends JPanel {
  // PROPIEDADES
  private static final long serialVersionUID = 1L;
  JScrollPane scrollPane;
  int width, height;
  byte[] byteArry;
  File imgFile;
  String src, imageName;

  public Image(String src, int width, int height) {
    // VALORES INICIALES
    this.src = src.contains(".bmp") ? src : src.substring(0, src.lastIndexOf(".")) + ".bmp";

    // CONFIGURAR PANEL
    setOpaque(true);

    // AGREGAR SCROLL
    scrollPane = new JScrollPane(this);
    scrollPane.setPreferredSize(new Dimension(width, height));
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);

    // PINTAR POR PRIMERA
    updateSrc(src);
  }

  // ACTUALIZAR IMAGEN
  public void updateSrc(String path) {
    // OBTENER RUTA
    imageName = getName(path);
    this.src = path.contains(".bmp") ? path : "tmp/view/" + imageName + ".bmp";

    try {
      // LEER ARCHIVO Y OBTENER BYTES
      imgFile = new File(src);
      byteArry = toByteArray(imgFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // OBTENER ANCHO DEL BYTE 18-21
    width = getWidth(byteArry);

    // OBTENER ALTO DEL BYTE 22-25
    height = getHeight(byteArry);

    // ASIGNAR DIMENSION
    setPreferredSize(new Dimension(width, height));

    // ACTUALIZAR
    repaint();
  }

  // OBTENER PANEL
  public JScrollPane getCPane() {
    return scrollPane;
  }

  // CONVERTIR A BYTES[]
  public static byte[] toByteArray(File file) throws FileNotFoundException {
    // ABRIR STREAM
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];

    // CREAR BYTES CON STREAM
    try {
      for (int readNum; (readNum = fis.read(buf)) != -1;)
        bos.write(buf, 0, readNum);

      // CERRAR STREAMS
      bos.close();
      fis.close();
    } catch (IOException ex) {
      System.out.print(ex);
    }

    // RETORNAR BYTES[]
    return bos.toByteArray();
  }

  // OBTENER ALTO DE BITMAP
  public static int getWidth(byte[] bitmap) {
    return toInt(bitmap, 18);
  }

  // OBTENER NOMBRE DE IMAGEN
  public static String getName(String nPath) {
    String replaced = nPath.replaceAll("\\\\", "/");
    return replaced.substring(replaced.lastIndexOf("/") + 1, replaced.lastIndexOf("."));
  }

  // OBTENER ANCHO DE BITMAP
  public static int getHeight(byte[] bitmap) {
    return toInt(bitmap, 22);
  }

  // OBTENER ENTERO ORDENADO LITTLE-ENDIAN
  private static int toInt(byte[] a, int offs) {
    return (a[offs + 3] & 0xFF) << 24 | (a[offs + 2] & 0xFF) << 16 | (a[offs + 1] & 0xFF) << 8 | a[offs] & 0xFF;
  }

  // RENDERIZAR IMAGEN
  public void paint(Graphics g) {
    if (byteArry.length > 0) {
      // VALOR INICIAL
      int x = width, y = 0;

      // RECORRER BITMAP
      for (int i = byteArry.length - 4; i > 54; i -= 3) {
        // OBTENER COLORES
        int B = (byteArry[i + 1] & 0xFF);
        int G = (byteArry[i + 2] & 0xFF);
        int R = (byteArry[i + 3] & 0xFF);

        // AGREGAR COLOR Y DIBUJAR
        g.setColor(new Color(R, G, B));
        g.drawLine(x, y, x, y);

        // REINICIAR CONTADORES
        y = x - 1 == 0 ? y + 1 : y;
        x = x - 1 == 0 ? width : x - 1;
      }
    }
  }
}
