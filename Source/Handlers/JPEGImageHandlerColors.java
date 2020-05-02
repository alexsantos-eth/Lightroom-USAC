package Source.Handlers;

import Source.Utils.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JPEGImageHandlerColors extends ImageHandler {
  // GLOBALES
  final String resPath;
  final String imageName;
  int width, height;
  byte[] bitmap;

  // CONSTRUCTOR
  public JPEGImageHandlerColors(String path) {
    // ENVIAR handledFileName
    super(path);

    // NOMBRE DE IMAGEN TEMPORAL
    imageName = Image.getName(path);
    resPath = imageName + ".bmp";
  }

  private byte[] filteredBitMap(int mode) {
    // COPIA DE BITMAP
    byte[] newBitmap = bitmap.clone();

    if (newBitmap.length > 0) {
      // RECORRER BITMAP
      for (int i = newBitmap.length - 4; i > 54; i -= 3) {
        // OBTENER COLORES
        byte B = newBitmap[i + 1];
        byte G = newBitmap[i + 2];
        byte R = newBitmap[i + 3];

        // FILTRO SEPIA
        if (mode == 3) {
          newBitmap[i + 1] = (byte) Math.min((0.272 * R) + (0.534 * G) + (0.131 * B), 255.0);
          newBitmap[i + 2] = (byte) Math.min((0.349 * R) + (0.686 * G) + (0.168 * B), 255.0);
          newBitmap[i + 3] = (byte) Math.min((0.393 * R) + (0.769 * G) + (0.189 * B), 255.0);
        }

        // IMAGEN NORMAL CON UN SOLO COLOR
        else {
          newBitmap[i + 1] = mode == 2 ? B : 0;
          newBitmap[i + 2] = mode == 1 ? G : 0;
          newBitmap[i + 3] = mode == 0 ? R : 0;
        }
      }
    }

    // RETORNAR NUEVO BITMAP
    return newBitmap;
  }

  // GENERAR ARCHIVO
  private void genColorFile(byte[] colorBitmap, String color) throws IOException {
    // MENSAJE EN CONSOLA
    System.out.println("Generando imagen con filtro " + color);

    // CREAR IMAGEN
    String tmpPath = "tmp/colors/" + color + "-" + imageName + ".bmp";
    FileOutputStream output = new FileOutputStream(tmpPath);
    output.write(colorBitmap);
    output.close();

    // CONVERTIRLA DE BMP A JPEG
    ImageHandler toJPEG = new BMPtoJPEGImage(tmpPath, false);
    try {
      JPEGHandler.runHandler(toJPEG);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // BORRAR BMP
    File tmpBMP = new File(tmpPath);
    tmpBMP.delete();
  }

  // LEER ARCHIVO
  @Override
  public void readFile() throws Exception {
    // MENSAJE EN CONSOLA
    System.out.println("Generando mapa de bits ...");

    // CREAR BMP
    ImageHandler toBMP = new JPEGtoBMPImage(this.handledFileName, "tmp/colors/");
    JPEGHandler.runHandler(toBMP);

    // OBTENER ARCHIVO RESULTANTE
    File newBMP = new File("tmp/colors/" + imageName + ".bmp");

    // OBTENER MAPA, ANCHO Y ALTO
    bitmap = Image.toByteArray(newBMP);
    width = Image.getWidth(bitmap);
    height = Image.getHeight(bitmap);

    // BORRAR BMP
    newBMP.delete();
  }

  @Override
  public void generateFiles() throws Exception {
    // NOMBRES PARA BITMAPS
    String[] colorNames = { "Red", "Green", "Blue", "Sepia" };

    // GENERAR ARCHIVOS
    for (int i = 0; i < colorNames.length; i++)
      genColorFile(filteredBitMap(i), colorNames[i]);
  }

}