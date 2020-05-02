package Source.Menus;

import javax.swing.*;
import java.awt.event.*;

public class EditorMenu extends JMenuBar {
  private static final long serialVersionUID = 1L;
  private JMenu toolsMenu, fileMenu;
  public JMenuItem openFile, copyItem, convertItem, rotateItem, filterItem, bnItem;

  public void setProperties() {
    // MENUS PRINCIPALES
    toolsMenu = new JMenu("Imagen");
    fileMenu = new JMenu("Archivo");

    // MENU DE ABRIR
    copyItem = new JMenuItem("Copiar");
    copyItem.setMnemonic(KeyEvent.VK_C);
    copyItem.setToolTipText("Copiar jpeg");
    copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

    // MENU DE CERRAR
    convertItem = new JMenuItem("Convertir");
    convertItem.setMnemonic(KeyEvent.VK_T);
    convertItem.setToolTipText("Convertir de JPEG a BMP y viceversa");
    convertItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));

    // CREAR CATEGORIA
    rotateItem = new JMenuItem("Rotar");
    rotateItem.setMnemonic(KeyEvent.VK_R);
    rotateItem.setToolTipText("Rotar horizontal y vertical");
    rotateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));

    // QUITAR CATEGORIA
    filterItem = new JMenuItem("Aplicar filtros");
    filterItem.setMnemonic(KeyEvent.VK_F);
    filterItem.setToolTipText("Filtros Rojo, Verde, Azul y Sepia");
    filterItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));

    // GUARDAR ESPACIO DE TRABAJO
    bnItem = new JMenuItem("Blanco y Negro");
    bnItem.setMnemonic(KeyEvent.VK_B);
    bnItem.setToolTipText("Filtro Blanco y Negro");
    bnItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));

    // GUARDAR ESPACIO DE TRABAJO
    openFile = new JMenuItem("Abrir");
    openFile.setMnemonic(KeyEvent.VK_O);
    openFile.setToolTipText("Aplica todos los filtros");
    openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
  }

  public void addItems() {
    // AGREGAR A MENU DE ARCHIVO
    toolsMenu.add(copyItem);
    toolsMenu.add(convertItem);
    toolsMenu.add(rotateItem);
    toolsMenu.add(filterItem);
    toolsMenu.add(bnItem);

  }

  public EditorMenu(Boolean openSection) {
    // CREAR MENUS
    setProperties();

    // AGREGAR SUBMENUS
    if (openSection)
      fileMenu.add(openFile);
    addItems();

    // AGREGAR A MENUBAR
    if (openSection)
      add(fileMenu);

    add(toolsMenu);
  }
}