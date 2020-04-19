package Source;

import javax.swing.*;
import java.awt.event.*;

public class ViewerMenu extends JMenuBar {
  private static final long serialVersionUID = 1L;
  private JMenu fileMenu, categoryMenu, workSpaceMenu;
  public JMenuItem openFile, closeFile, newCategory, removeCategory, saveWorkspace;

  public ViewerMenu() {
    fileMenu = new JMenu("Archivo");
    categoryMenu = new JMenu("Categorias");
    workSpaceMenu = new JMenu("Biblioteca");

    // MENU DE ABRIR
    openFile = new JMenuItem("Abrir imagen");
    openFile.setMnemonic(KeyEvent.VK_O);
    openFile.setToolTipText("Abrir imagen");
    openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));

    // MENU DE CERRAR
    closeFile = new JMenuItem("Cerrar imagen");
    closeFile.setMnemonic(KeyEvent.VK_D);
    closeFile.setToolTipText("Cerrar imagen");
    closeFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

    // CREAR CATEGORIA
    newCategory = new JMenuItem("Nueva categoria");
    newCategory.setMnemonic(KeyEvent.VK_N);
    newCategory.setToolTipText("Nueva categoria");
    newCategory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

    // QUITAR CATEGORIA
    removeCategory = new JMenuItem("Eliminar categoria");
    removeCategory.setMnemonic(KeyEvent.VK_M);
    removeCategory.setToolTipText("Eliminar categoria");
    removeCategory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));

    // GUARDAR ESPACIO DE TRABAJO
    saveWorkspace = new JMenuItem("Guardar biblioteca");
    saveWorkspace.setMnemonic(KeyEvent.VK_S);
    saveWorkspace.setToolTipText("Guardar biblioteca");
    saveWorkspace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

    fileMenu.add(openFile);
    fileMenu.add(closeFile);

    categoryMenu.add(newCategory);
    categoryMenu.add(removeCategory);

    workSpaceMenu.add(saveWorkspace);

    add(workSpaceMenu);
    add(fileMenu);
    add(categoryMenu);
  }
}