package Source;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class JPEGtoBMPImage extends ImageHandler {
  private BufferedImage image;
  private String outputName;
  private Boolean temporal;

  public JPEGtoBMPImage(String path, String relPath) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public JPEGtoBMPImage(String path, String relPath, Boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = temporal;
  }

  public JPEGtoBMPImage(String path, String relPath, String prefix) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + prefix + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public JPEGtoBMPImage(String path, String relPath, String prefix, boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + prefix + Image.getName(path) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = temporal;
  }

  public JPEGtoBMPImage(String path) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = path.substring(0, path.lastIndexOf(".")) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public JPEGtoBMPImage(String path, Boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = path.substring(0, path.lastIndexOf(".")) + ".bmp";

    // GENERAR ARCHIVOS
    this.temporal = temporal;
  }

  @Override
  public void readFile() throws Exception {
    // LEER IMAGEN
    File input = new File(this.handledFileName);
    image = ImageIO.read(input);
  }

  @Override
  public void generateFiles() throws Exception {
    try {
      // IMAGEN DE SALIDA
      File output = new File(outputName);

      // ELIMINAR LA IMAGEN
      if (temporal)
        output.deleteOnExit();

      // CREAR IMAGEN
      ImageIO.write(image, "bmp", output);
      image.flush();
    } catch (FileNotFoundException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}