package Source.Menus;

import javax.swing.*;
import java.awt.event.*;

public class LoginMenuBar extends JMenuBar {
  private static final long serialVersionUID = 1L;
  public JMenuItem editorMenu, converterMenu;

  public LoginMenuBar() {
    JMenu toolsMenu = new JMenu("Herramientas");

    // MENU DE EDITOR
    editorMenu = new JMenuItem("Editor", new ImageIcon("../Source/assets/editor-icon.png"));
    editorMenu.setMnemonic(KeyEvent.VK_E);
    editorMenu.setToolTipText("Ingresar al editor");
    editorMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));

    // MENU DE CONVERTIDOR
    converterMenu = new JMenuItem("Convertidor", new ImageIcon("../Source/assets/converter-icon.png"));
    converterMenu.setMnemonic(KeyEvent.VK_T);
    converterMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    converterMenu.setToolTipText("Ingresar al convertidor");

    // ASIGNAR BARRA DE MENU
    toolsMenu.add(editorMenu);
    toolsMenu.add(converterMenu);
    add(toolsMenu);
  }
}