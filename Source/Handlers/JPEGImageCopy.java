package Source.Handlers;

import Source.Utils.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JPEGImageCopy extends ImageHandler {
  String resPath;
  String imageName;
  BmpHandlerCopy bmpCopy;

  public JPEGImageCopy(String path) {
    super(path);
    imageName = Image.getName(path);
    resPath = imageName + ".bmp";
  }

  @Override
  public void readFile() throws Exception {
    // HANDLER
    ImageHandler toBMP = new JPEGtoBMPImage(this.handledFileName, "");
    JPEGHandler.runHandler(toBMP);

    bmpCopy = new BmpHandlerCopy(resPath);
    bmpCopy.readFile();
  }

  @Override
  public void generateFiles() throws Exception {
    bmpCopy.generateFiles();
    ImageHandler toJPEG = new BMPtoJPEGImage("copy-" + resPath, "");
    JPEGHandler.runHandler(toJPEG);

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