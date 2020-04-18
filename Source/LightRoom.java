package Source;

import javax.swing.*;

public class LightRoom {
  public static void main(String[] args) {
    DoublyLinkedList<String> prueba = new DoublyLinkedList<String>();

    prueba.add("Hosdla");
    prueba.add("Mundso");
    prueba.add("Hasd");
    prueba.add("neyv");

    prueba.makeCircular();

    // System.out.println(prueba.getIndex("Hasds"));

    for (int i = -1; i >= (prueba.getSize() * -2); i--) {
      System.out.println(prueba.get(i));
    }

    // for (int i = -1; i >= (prueba.getSize() * -2); i--) {
    // System.out.println(prueba.get(i));
    // }
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