package Source;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BMPtoJPEGImage {
  private void genFiles(String path, String outputName, boolean temporal) {
    try {
      // LEER IMAGEN
      File input = new File(path);
      BufferedImage image = ImageIO.read(input);

      // IMAGEN DE SALIDA
      File output = new File(outputName);

      // ELIMINAR LA IMAGEN
      if (temporal)
        output.deleteOnExit();

      // CREAR IMAGEN
      ImageIO.write(image, "jpg", output);
      image.flush();
    } catch (FileNotFoundException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public BMPtoJPEGImage(String path, String relPath) {
    // IMAGEN DE SALIDA
    String originalPath = path.replaceAll("\\\\", "/");
    String outputName = relPath
        + originalPath.substring(originalPath.lastIndexOf("/") + 1, originalPath.lastIndexOf(".")) + ".jpg";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, true);
  }

  public BMPtoJPEGImage(String path) {
    // IMAGEN DE SALIDA
    String outputName = path.substring(0, path.lastIndexOf(".")) + ".jpg";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, true);
  }

  public BMPtoJPEGImage(String path, Boolean temporal) {
    // IMAGEN DE SALIDA
    String outputName = path.substring(0, path.lastIndexOf(".")) + ".jpg";

    // GENERAR ARCHIVOS
    genFiles(path, outputName, temporal);
  }
}