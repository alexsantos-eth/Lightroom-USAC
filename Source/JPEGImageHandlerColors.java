package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JPEGImageHandlerColors extends ImageHandler {
  final String resPath;
  final String imageName;
  byte[] bitmap;
  int width, height;

  public JPEGImageHandlerColors(String path) {
    super(path);
    imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    resPath = imageName + ".bmp";
    width = Image.getWidth(bitmap);
    height = Image.getHeight(bitmap);
  }

  private byte[] rotateBitMap(int mode) {
    byte[] backupBitmap = bitmap.clone();
    byte[] newBitmap = bitmap.clone();
    int length = newBitmap.length - 4;

    // VALOR INICIAL
    int y = 0, x = 0;

    if (newBitmap.length > 0) {

      // RECORRER BITMAP
      if (mode == 0)
        for (int i = 54; i < length; i += 3) {
          // OBTENER COLORES
          byte B = backupBitmap[length - ((54 - i) * -1) + 1];
          byte G = backupBitmap[length - ((54 - i) * -1) + 2];
          byte R = backupBitmap[length - ((54 - i) * -1) + 3];

          newBitmap[i] = B;
          newBitmap[i + 1] = G;
          newBitmap[i + 2] = R;
        }
      else
        for (int i = 54; i < length; i += 3) {
          // OBTENER COLORES
          byte B = backupBitmap[x - ((54 - i) * -1) + 1];
          byte G = backupBitmap[x - ((54 - i) * -1) + 2];
          byte R = backupBitmap[x - ((54 - i) * -1) + 3];

          newBitmap[i] = B;
          newBitmap[i + 1] = G;
          newBitmap[i + 2] = R;
        }

      // REINICIAR CONTADORES
      x = y + 1 == width ? x + 1 : x;
      y = y + 1 == width ? 0 : y + 1;
    }

    // RETORNAR NUEVO BITMAP
    return newBitmap;
  }

  private byte[] filteredBitMap(int mode) {
    byte[] newBitmap = bitmap.clone();

    if (newBitmap.length > 0) {
      // RECORRER BITMAP
      for (int i = newBitmap.length - 4; i > 54; i -= 3) {
        // OBTENER COLORES
        byte B = newBitmap[i + 1];
        byte G = newBitmap[i + 2];
        byte R = newBitmap[i + 3];

        if (mode == 4) {
          // FILTRO GRAYSCALE
          newBitmap[i + 1] = G;
          newBitmap[i + 2] = G;
          newBitmap[i + 3] = G;
        }

        else if (mode == 3) {
          // FILTRO SEPIA
          newBitmap[i + 1] = (byte) Math.min((0.272 * R) + (0.534 * G) + (0.131 * B), 255.0);
          newBitmap[i + 2] = (byte) Math.min((0.349 * R) + (0.686 * G) + (0.168 * B), 255.0);
          newBitmap[i + 3] = (byte) Math.min((0.393 * R) + (0.769 * G) + (0.189 * B), 255.0);

        }

        else if (mode != 5) {
          // REMPLAZAR VALORES
          newBitmap[i + 1] = mode == 2 ? B : 0;
          newBitmap[i + 2] = mode == 1 ? G : 0;
          newBitmap[i + 3] = mode == 0 ? R : 0;
        }
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
  }

  @Override
  public void generateFiles() throws Exception {
    // CREAR BITMAPS
    String[] colorNames = { "Red", "Green", "Blue", "Sepia", "GrayScale" };
    for (int i = 0; i < colorNames.length; i++)
      genColorFile(filteredBitMap(i), colorNames[i]);

    genColorFile(rotateBitMap(0), "HRotation");
  }

}