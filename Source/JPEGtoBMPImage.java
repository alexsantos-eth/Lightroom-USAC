package Source;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class JPEGtoBMPImage {
  private void genFiles(String path, String outputName, Boolean temporal) {
    try {
      // VERIFICAR QUE NO SEA UNA BMP
      // LEER IMAGEN
      File input = new File(path);
      BufferedImage image = ImageIO.read(input);

      // IMAGEN DE SALIDA
      File output = new File(outputName);

      // ELIMINAR LA IMAGEN
      if (temporal)
        output.deleteOnExit();

      // CREAR IMAGEN
      ImageIO.write(image, "bmp", output);
      image.flush();
    } catch (

    FileNotFoundException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public JPEGtoBMPImage(String path, String relPath) {
    // IMAGEN DE SALIDA
    String outputName = relPath + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, true);
  }

  public JPEGtoBMPImage(String path) {
    // IMAGEN DE SALIDA
    String outputName = path.substring(0, path.lastIndexOf(".")) + ".bmp";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, true);
  }

  public JPEGtoBMPImage(String path, Boolean temporal) {
    // IMAGEN DE SALIDA
    String outputName = path.substring(0, path.lastIndexOf(".")) + ".bmp";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, temporal);
  }

  public JPEGtoBMPImage(String path, String relPath, String prefix) {
    // IMAGEN DE SALIDA
    String outputName = relPath + prefix + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, true);
  }

}