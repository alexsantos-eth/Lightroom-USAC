package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JPEGImageHandlerRotator extends ImageHandler {
  final String resPath;
  final String imageName;
  int width, height;
  byte[] bitmap;

  // CONSTRUCTOR
  public JPEGImageHandlerRotator(String path) {
    // ENVIAR handledFileName
    super(path);

    // NOMBRE DE IMAGEN TEMPORAL
    imageName = Image.getName(path);
    resPath = imageName + ".bmp";
  }

  // ROTAR IMAGENES
  private byte[] rotateBitmap(int mode) {
    // COPIA DE BITMAP
    byte[] backupBitmap = bitmap.clone();
    byte[] newBitmap = bitmap.clone();
    int length = newBitmap.length - 4;
    int maxSize = bitmap.length - 54;

    if (newBitmap.length > 0) {
      // ROTAR HORIZONTAL
      if (mode == 0)
        for (int i = 54; i < length; i += 3) {
          // OBTENER COLORES
          byte B = backupBitmap[length - ((54 - i) * -1) + 1];
          byte G = backupBitmap[length - ((54 - i) * -1) + 2];
          byte R = backupBitmap[length - ((54 - i) * -1) + 3];

          // ASIGNAR COLORES
          newBitmap[i] = B;
          newBitmap[i + 1] = G;
          newBitmap[i + 2] = R;
        }

      // ROTAR VERTICAL
      else
        for (int i = 1; i <= height; i++) {
          // DIMENSION DE FILAS
          int rowLength = 3 * width;
          int count = 54 + (rowLength * i);

          // RECORRER COLUMNAS
          for (int j = 0; j < width * 3; j += 3) {
            // DIMENSION MAXIMA
            if (count - j < maxSize) {
              // OBTENER COLORES
              byte B = backupBitmap[count - j];
              byte G = backupBitmap[count - j + 1];
              byte R = backupBitmap[count - j + 2];

              // ASIGNAR COLORES
              newBitmap[count - rowLength + j] = B;
              newBitmap[count - rowLength + j + 1] = G;
              newBitmap[count - rowLength + j + 2] = R;
            }
          }
        }
    }

    return newBitmap;
  }

  // GENERAR ARCHIVO
  private void genFile(byte[] newBitmap, String name) throws IOException {
    // MENSAJE EN CONSOLA
    System.out.println("Generando rotada " + (name == "HRotation" ? "Horizont" : "Vertic") + "almente");

    // CREAR IMAGEN
    String tmpPath = "tmp/rotations/" + name + "-" + imageName + ".bmp";
    FileOutputStream output = new FileOutputStream(tmpPath);
    output.write(newBitmap);
    output.close();

    // CONVERTIRLA DE BMP A JPEG
    new BMPtoJPEGImage(tmpPath, false);

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
    new JPEGtoBMPImage(this.handledFileName, "tmp/rotations/");

    // OBTENER ARCHIVO RESULTANTE
    File newBMP = new File("tmp/rotations/" + imageName + ".bmp");

    // OBTENER MAPA, ANCHO Y ALTO
    bitmap = Image.toByteArray(newBMP);
    width = Image.getWidth(bitmap);
    height = Image.getHeight(bitmap);
  }

  @Override
  public void generateFiles() throws Exception {
    // NOMBRES PARA BITMAPS
    String[] imageNames = { "HRotation", "VRotation" };

    // GENERAR ARCHIVOS
    for (int i = 0; i < imageNames.length; i++)
      genFile(rotateBitmap(i), imageNames[i]);
  }
}