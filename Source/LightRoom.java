package Source;

import javax.swing.*;

public class LightRoom {
  public static void main(String[] args) {
    String path = "C:/Users/ernesto/Desktop/RandomImages/image0.jpg";
    ImageHandler colors = new JPEGImageHandlerColors(path);

    try {
      JPEGHandler.runHandler(colors);
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