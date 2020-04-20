package Source;

import javax.swing.*;

public class LightRoom {
  public static void main(String[] args) {
    // LinkedList<String> prueba = new LinkedList<String>();

    // prueba.add("Hola");
    // prueba.add("Mundo");
    // prueba.add("Nueva");

    // prueba.delete("Nueva");

    // prueba.add("Ves");
    // // // prueba.replace("Mundo", "Nuevo Mundo");

    // // // // // // System.out.println(prueba.getIndex("Hasds"));

    // for (int i = 0; i < prueba.getSize(); i++) {
    // System.out.println(prueba.get(i));
    // }

    // // for (int i = -1; i >= (prueba.getSize() * -2); i--) {
    // // System.out.println(prueba.get(i));
    // // }
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    new LoginMenu();

  }
}