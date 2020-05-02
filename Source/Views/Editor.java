package Source.Views;

import Source.Handlers.*;
import Source.Menus.EditorMenu;
import Source.Utils.*;
import Source.Utils.Button;
import Source.Utils.Image;
import Source.Structure.*;
import Source.Controllers.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Editor extends FrameCommon {
  private static final long serialVersionUID = 1L;
  private String currentPath;
  private JButton selectBtn;
  private JButton convertBtn;
  private JButton filerBtn;
  private JButton copyBtn;
  private JButton rotateBtn;
  private JButton bnBtn;
  private JTextArea jcomp7;
  private JList<String> pathList;
  private JScrollPane pathListScroll;
  private EditorMenu menubar;

  // MENSAJE DE SALIDA
  private void showMessage(String action) {
    JOptionPane.showMessageDialog(null, "Tu imagen se ha " + action + " exitosamente.", "Proceso terminado",
        JOptionPane.INFORMATION_MESSAGE);
  }

  // HANDLER DE COPIA
  private void runCopy() {
    if (currentPath != null) {
      ImageHandler copy = new JPEGImageCopy(currentPath);
      try {
        JPEGHandler.runHandler(copy);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // CONVERTIR IMAGENES
  private void runConvert() {
    // OBTENER TIPO DE ARCHIVO
    String imageExt = currentPath.substring(currentPath.lastIndexOf(".") + 1).toLowerCase();

    try {
      // CONVERTIR A BMP
      if (imageExt.equals("jpeg") || imageExt.equals("jpg")) {
        ImageHandler toBMP = new JPEGtoBMPImage(currentPath, "tmp/converted/", "converted-", false);
        JPEGHandler.runHandler(toBMP);
      }

      // CONVERTIR A JPEG
      else {
        ImageHandler toJPEG = new BMPtoJPEGImage(currentPath, "tmp/converted/", "converted-", false);
        JPEGHandler.runHandler(toJPEG);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // HANDLER DE FILTROS
  private void runFilter() {
    ImageHandler filter = new JPEGImageHandlerColors(currentPath);
    try {
      JPEGHandler.runHandler(filter);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // HANDLER DE ROTAR
  private void runRotate() {
    ImageHandler rotate = new JPEGImageHandlerRotator(currentPath);
    try {
      JPEGHandler.runHandler(rotate);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // HANDLER DE BLANCO Y NEGRO
  private void runBN() {
    ImageHandler bn = new JPEGImageHandlerBN(currentPath);
    try {
      JPEGHandler.runHandler(bn);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Editor() {
    // construct components
    selectBtn = new JButton("Seleccionar Imagen");
    convertBtn = new JButton("Convertir JPEG/BMP");
    filerBtn = new JButton("Aplicar filtros");
    copyBtn = new JButton("Copiar imagen");
    rotateBtn = new JButton("Rotar V/H");
    bnBtn = new JButton("Blanco y Negro");
    jcomp7 = new JTextArea(5, 5);
    pathList = new JList<String>(new DefaultListModel<String>());
    pathListScroll = new JScrollPane(pathList);
    menubar = new EditorMenu(true);

    // adjust size and set layout
    setSize(602, 466);
    setJMenuBar(menubar);
    setLayout(null);

    // add components
    add(selectBtn);
    add(convertBtn);
    add(filerBtn);
    add(copyBtn);
    add(rotateBtn);
    add(bnBtn);
    add(jcomp7);
    add(pathListScroll);

    // set component bounds (only needed by Absolute Positioning)
    selectBtn.setBounds(10, 130, 165, 45);
    convertBtn.setBounds(10, 205, 165, 40);
    filerBtn.setBounds(10, 285, 165, 40);
    copyBtn.setBounds(10, 245, 165, 40);
    rotateBtn.setBounds(10, 325, 165, 40);
    bnBtn.setBounds(10, 365, 165, 40);
    jcomp7.setBounds(185, 10, 400, 400);
    pathListScroll.setBounds(10, 10, 165, 115);
  }
}