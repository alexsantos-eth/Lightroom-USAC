package Source.Views;

import Source.Handlers.*;
import Source.Menus.EditorMenu;
import Source.Utils.*;
import Source.Utils.Image;
import Source.Controllers.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.*;
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
  private JButton removeBtn;
  private Image image;
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

  // ABRIR ARCHIVOS
  private void openFiles() {
    // ABRIR DIALOGO
    FileDialog dialog = new FileDialog((Frame) null, "Selecciona tu foto");

    // CONFIGURAR DIALOGO
    dialog.setMode(FileDialog.LOAD);
    dialog.setMultipleMode(true);
    dialog.setVisible(true);
    dialog.setIconImage(new ImageIcon("../Source/assets/icon.png").getImage());

    // LISTA DE ARCHIVOS Y MODELOS
    File[] paths = dialog.getFiles();
    DefaultListModel<String> model = (DefaultListModel<String>) pathList.getModel();

    // ASIGNAR A LISTA
    for (File path : paths) {
      model.addElement(path.getAbsolutePath());
      Controller.createImage(path.getAbsolutePath());
    }

    // REINICIAR IMAGEN
    String npath = paths[0].getAbsolutePath();
    image.updateSrc(npath);
    currentPath = npath;
  }

  // ASIGNAR LISTENER DE BOTONES
  public void setListeners() {
    ActionListener addListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        openFiles();
      }
    };

    ActionListener removeListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ev) {
        DefaultListModel<String> model = (DefaultListModel<String>) pathList.getModel();
        String path = pathList.getSelectedValue();

        // QUITAR RUTA
        if (path != null) {
          if (model.getSize() >= 1)
            pathList.setSelectedIndex(pathList.getSelectedIndex() - 1);

          model.removeElement(path);
        }
      }
    };

    ListSelectionListener selectListener = new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent ev) {
        if (!ev.getValueIsAdjusting()) {
          DefaultListModel<String> model = (DefaultListModel<String>) pathList.getModel();
          String path = pathList.getSelectedValue();

          // ACTUALIZAR IMAGEN
          if (model.getSize() == 0)
            image.updateSrc("../Source/assets/imageBackground.jpg");

          else
            image.updateSrc(path == null ? model.get(0) : path);

          currentPath = path;
        }
      }
    };

    // LISTENER DE COPIAR
    ActionListener copyListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runCopy();
        showMessage("copiado");
      }
    };

    // LISTENER DE FILTROS
    ActionListener filterListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runFilter();
        showMessage("filtrado");
      }
    };

    // LISTENER DE ROTAR
    ActionListener rotateListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runRotate();
        showMessage("rotado");
      }
    };

    // LISTENER DE BLANCO Y NEGRO
    ActionListener bnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runBN();
        showMessage("filtrado con blanco y negro");
      }
    };

    // LISTENER DE CONVERTIR
    ActionListener convertListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runConvert();
        showMessage("convertido");
      }
    };

    // ASIGNAR A BOTONES
    selectBtn.addActionListener(addListener);
    removeBtn.addActionListener(removeListener);
    pathList.addListSelectionListener(selectListener);
    convertBtn.addActionListener(convertListener);
    filerBtn.addActionListener(filterListener);
    copyBtn.addActionListener(copyListener);
    rotateBtn.addActionListener(rotateListener);
    bnBtn.addActionListener(bnListener);
  }

  public Editor() {
    // construct components
    selectBtn = new JButton("Agregar +");
    removeBtn = new JButton("Eliminar -  ");
    convertBtn = new JButton("Convertir JPEG/BMP");
    filerBtn = new JButton("Aplicar filtros");
    copyBtn = new JButton("Copiar imagen");
    rotateBtn = new JButton("Rotar V/H");
    bnBtn = new JButton("Blanco y Negro");
    image = new Image("../Source/assets/imageBackground.jpg", 400, 400);
    pathList = new JList<String>(new DefaultListModel<String>());
    pathListScroll = new JScrollPane(pathList);
    menubar = new EditorMenu(true);

    // adjust size and set layout
    setSize(682, 466);
    setJMenuBar(menubar);
    setLayout(null);
    setListeners();

    // add components
    add(selectBtn);
    add(removeBtn);
    add(convertBtn);
    add(filerBtn);
    add(copyBtn);
    add(rotateBtn);
    add(bnBtn);
    add(image);
    add(pathListScroll);

    // set component bounds (only needed by Absolute Positioning)
    selectBtn.setBounds(10, 130, 82, 45);
    removeBtn.setBounds(92, 130, 82, 45);
    convertBtn.setBounds(10, 205, 165, 40);
    filerBtn.setBounds(10, 285, 165, 40);
    copyBtn.setBounds(10, 245, 165, 40);
    rotateBtn.setBounds(10, 325, 165, 40);
    bnBtn.setBounds(10, 365, 165, 40);
    image.setBounds(185, 10, 480, 393);
    image.getCPane().setBounds(185, 10, 480, 393);
    pathListScroll.setBounds(10, 10, 165, 115);
  }
}