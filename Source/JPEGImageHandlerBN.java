package Source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JPEGImageHandlerBN extends ImageHandler {
  // GLOBALES
  final String resPath;
  final String imageName;
  byte[] bitmap;

  // CONSTRUCTOR
  public JPEGImageHandlerBN(String path) {
    // ENVIAR handledFileName
    super(path);

    // NOMBRE DE IMAGEN TEMPORAL
    imageName = Image.getName(path);
    resPath = imageName + ".bmp";
  }

  private byte[] filteredBitMap() {
    // COPIA DE BITMAP
    byte[] newBitmap = bitmap.clone();

    if (newBitmap.length > 0) {
      // RECORRER BITMAP
      for (int i = newBitmap.length - 4; i > 54; i -= 3) {
        // OBTENER COLORES
        byte G = newBitmap[i + 2];

        // FILTRO GRAYSCALE
        newBitmap[i + 1] = G;
        newBitmap[i + 3] = G;
      }
    }

    // RETORNAR NUEVO BITMAP
    return newBitmap;
  }

  // GENERAR ARCHIVO
  private void genColorFile(byte[] colorBitmap, String color) throws IOException {
    // MENSAJE EN CONSOLA
    System.out.println("Generando imagen en blanco y negro ...");

    // CREAR IMAGEN
    String tmpPath = "tmp/bn/" + color + "-" + imageName + ".bmp";
    FileOutputStream output = new FileOutputStream(tmpPath);
    output.write(colorBitmap);
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
    new JPEGtoBMPImage(this.handledFileName, "tmp/bn/");

    // OBTENER ARCHIVO RESULTANTE
    File newBMP = new File("tmp/bn/" + imageName + ".bmp");

    // OBTENER MAPA
    bitmap = Image.toByteArray(newBMP);
  }

  @Override
  public void generateFiles() throws Exception {
    // NOMBRES PARA BITMAPS
    byte[] bnBitmap = filteredBitMap();

    // GENERAR ARCHIVOS
    genColorFile(bnBitmap, "BN");
  }
}