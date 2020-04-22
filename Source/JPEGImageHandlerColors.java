package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JPEGImageHandlerColors extends ImageHandler {
  final String resPath;
  final String imageName;
  int width, height;
  byte[] bitmap;

  public JPEGImageHandlerColors(String path) {
    super(path);
    imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    resPath = imageName + ".bmp";
  }

  private byte[] filteredBitMap(int mode) {
    byte[] newBitmap = bitmap.clone();

    if (newBitmap.length > 0) {
      // VALOR INICIAL
      int x = width, y = 0;

      // RECORRER BITMAP
      for (int i = newBitmap.length - 4; i > 54; i -= 3) {
        // OBTENER COLORES
        byte B = newBitmap[i + 1];
        byte G = newBitmap[i + 2];
        byte R = newBitmap[i + 3];

        if (mode == 3) {
          // FILTRO SEPIA
          newBitmap[i + 3] = (byte) Math.min((.393 * R) + (.769 * G) + (.189 * B), 255.0);
          newBitmap[i + 2] = (byte) Math.min((.349 * R) + (.686 * G) + (.168 * B), 255.0);
          newBitmap[i + 1] = (byte) Math.min((.272 * R) + (.534 * G) + (.131 * B), 255.0);
        } else {

          // REMPLAZAR VALORES
          newBitmap[i + 1] = mode == 2 ? B : 0;
          newBitmap[i + 2] = mode == 1 ? G : 0;
          newBitmap[i + 3] = mode == 0 ? R : 0;
        }

        // REINICIAR CONTADORES
        y = x - 1 == 0 ? y + 1 : y;
        x = x - 1 == 0 ? width : x - 1;
      }
    }

    // RETORNAR NUEVO BITMAP
    return newBitmap;
  }

  private void genColorFile(byte[] colorBitmap, String color) throws IOException {
    System.out.println("Generando imagen con filtro " + color);
    String tmpPath = "tmp/colors/" + color + "-" + imageName + ".bmp";
    FileOutputStream output = new FileOutputStream(tmpPath);
    output.write(colorBitmap);
    output.close();

    new BMPtoJPEGImage(tmpPath, false);
    File tmpBMP = new File(tmpPath);
    tmpBMP.delete();
  }

  @Override
  public void readFile() throws Exception {
    new JPEGtoBMPImage(this.handledFileName, "tmp/colors/");
    System.out.println("Generando mapa de bits ...");
    File newBMP = new File("tmp/colors/" + imageName + ".bmp");
    bitmap = Image.toByteArray(newBMP);
    width = Image.getWidth(bitmap);
    height = Image.getHeight(bitmap);
  }

  @Override
  public void generateFiles() throws Exception {
    // CREAR BITMAPS
    String[] colorNames = { "red", "green", "blue", "sepia" };
    for (int i = 0; i < colorNames.length; i++)
      genColorFile(filteredBitMap(i), colorNames[i]);
  }

}