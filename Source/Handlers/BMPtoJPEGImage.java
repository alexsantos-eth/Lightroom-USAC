package Source.Handlers;

import Source.Utils.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BMPtoJPEGImage extends ImageHandler {
  private BufferedImage image;
  private String outputName;
  private Boolean temporal;

  public BMPtoJPEGImage(String path, String relPath) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + Image.getName(path) + ".jpg";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public BMPtoJPEGImage(String path, String relPath, Boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + Image.getName(path) + ".jpg";

    // GENERAR ARCHIVOS
    this.temporal = temporal;
  }

  public BMPtoJPEGImage(String path, String relPath, String prefix) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + prefix + Image.getName(path) + ".jpg";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public BMPtoJPEGImage(String path, String relPath, String prefix, Boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = relPath + prefix + Image.getName(path) + ".jpg";

    // GENERAR ARCHIVOS
    this.temporal = temporal;
  }

  public BMPtoJPEGImage(String path) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = path.substring(0, path.lastIndexOf(".")) + ".jpg";

    // GENERAR ARCHIVOS
    this.temporal = true;
  }

  public BMPtoJPEGImage(String path, Boolean temporal) {
    // HANDLER
    super(path);

    // IMAGEN DE SALIDA
    this.outputName = path.substring(0, path.lastIndexOf(".")) + ".jpg";

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
}