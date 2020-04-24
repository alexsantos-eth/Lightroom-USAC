package Source;

import javax.swing.*;

public class LightRoom {
  public static void main(String[] args) {
    String path = "C:/Users/ernesto/Desktop/RandomImages/image0.jpg";
    ImageHandler[] handlers = { new JPEGImageCopy(path), new JPEGImageHandlerColors(path),
        new JPEGImageHandlerRotator(path), new JPEGImageHandlerBN(path) };

    try {
      for (int i = 0; i < handlers.length; i++)
        JPEGHandler.runHandler(handlers[i]);

    } catch (Exception e) {
      e.printStackTrace();
    }

    // try {
    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    // } catch (ClassNotFoundException | InstantiationException |
    // IllegalAccessException
    // | UnsupportedLookAndFeelException e) {
    // e.printStackTrace();
    // }

    // new LoginMenu();
  }
}