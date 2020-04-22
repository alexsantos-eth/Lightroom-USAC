package Source;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JPEGImageCopy extends ImageHandler {
  String resPath;
  String imageName;
  BmpHandlerCopy bmpCopy;

  public JPEGImageCopy(String path) {
    super(path);
    imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    resPath = imageName + ".bmp";
  }

  @Override
  public void readFile() throws Exception {
    System.out.println("Imagen Leida: " + this.handledFileName);
    new JPEGtoBMPImage(this.handledFileName);

    System.out.println("Imagen generada: " + resPath);

    bmpCopy = new BmpHandlerCopy(resPath);
    bmpCopy.readFile();
  }

  @Override
  public void generateFiles() throws Exception {
    bmpCopy.generateFiles();
    System.out.println("Imagen Leida: " + "copy-" + resPath);
    new BMPtoJPEGImage("copy-" + resPath, "");

    // IMAGENES RESULTANTES
    File firstBMP = new File(this.resPath);
    File originalCopy = new File("copy-" + resPath);

    // ELIMINAR IMAGENES
    firstBMP.delete();
    originalCopy.delete();

    // MOVER RESULTANTE
    String copyRes = "copy-" + imageName + ".jpg";
    File resFile = new File("tmp/copy/" + copyRes);

    if (resFile.exists())
      resFile.delete();

    Files.move(Paths.get(copyRes), Paths.get("tmp/copy/" + copyRes));

    System.out.println("Imagen Copiada: " + copyRes);
  }
}