package Source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Editor extends FrameCommon {
  private static final long serialVersionUID = 1L;
  private String currentPath;

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

  // HANDLER PARA TODOS
  private void runAll() {
    runCopy();
    runConvert();
    runFilter();
    runRotate();
    runBN();
  }

  public Editor(String userName, Controller userController) {
    // CONFIGURAR VENTANA
    setSize(708, 480);
    setLayout(new GridBagLayout());

    // INICIALES
    User currentUser = userController.getData();
    DoublyLinkedList<Category> categoryList = currentUser.getCategoryList();
    currentPath = categoryList.get(0).images.get(0);

    // IMAGEN
    Image image = new Image(currentPath, 400, 400);

    // PANELES
    GridBagConstraints imgC = new GridBagConstraints();
    JPanel pathPanel = new JPanel();
    pathPanel.setPreferredSize(new Dimension(400, 30));
    pathPanel.setLayout(new FlowLayout());

    // COMBO BOX DE CATEGORIAS
    JComboBox<String> categoryBox = new JComboBox<String>();
    categoryBox.setPreferredSize(new Dimension(70, 25));

    // AGREGAR CATEGORIAS
    for (int i = 0; i < categoryList.getSize(); i++)
      categoryBox.addItem(categoryList.get(i).name);

    // COMBO BOX DE RUTAS
    JComboBox<String> imageBox = new JComboBox<String>();
    imageBox.setPreferredSize(new Dimension(320, 25));

    // AGREGAR RUTAS
    for (int i = 0; i < categoryList.get(0).images.getSize(); i++)
      imageBox.addItem(categoryList.get(0).images.get(i));

    // LISTENER DE RUTAS
    imageBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        // OBTENER RUTA
        String localPath = (String) imageBox.getSelectedItem();

        if (localPath != null) {
          // ACTUALIZAR IMAGEN
          currentPath = localPath;
          image.updateSrc(localPath);
        }
      }
    });

    // LISTENER DE CATEGORIAS
    categoryBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        // OBTENER POSICION DE CATEGORIA
        int index = categoryBox.getSelectedIndex();

        // REMOVER TODAS LAS RUTAS
        imageBox.removeAllItems();

        // AGREGAR RUTAS NUEVAS
        for (int i = 0; i < categoryList.get(index).images.getSize(); i++)
          imageBox.addItem(categoryList.get(index).images.get(i));
      }
    });

    // PANEL DE BOTONES
    GridBagConstraints btnC = new GridBagConstraints();
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new GridBagLayout());

    // LISTENERS
    // LISTENER DE COPIAR
    ActionListener runCopyListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runCopy();
        showMessage("copiado");
      }
    };

    // LISTENER DE CONVERTIR
    ActionListener runConvertListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runConvert();
        showMessage("convertido");
      }
    };

    // LISTENER DE FILTROS
    ActionListener runFilterListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runFilter();
        showMessage("filtrado");
      }
    };

    // LISTENER DE ROTAR
    ActionListener runRotateListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runRotate();
        showMessage("rotado");
      }
    };

    // LISTENER DE BLANCO Y NEGRO
    ActionListener runBNListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runBN();
        showMessage("filtrado con blanco y negro");
      }
    };

    // LISTENER DE EDITAR
    ActionListener runAllListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        runAll();
        showMessage("procesado");
      }
    };

    // BOTON DE COPIAR
    Button copyBtn = new Button("Copiar", 300, 70, new Color(80, 80, 80));
    copyBtn.setLabelFont(copyBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));
    copyBtn.addActionListener(runCopyListener);

    // BOTON DE CONVERTIR
    Button reverseBtn = new Button("Convertir", 300, 70, new Color(80, 80, 80));
    reverseBtn.setLabelFont(reverseBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));
    reverseBtn.addActionListener(runConvertListener);

    // BOTON DE FILTROS
    Button colorsBtn = new Button("Filtros", 300, 70, new Color(80, 80, 80));
    colorsBtn.setLabelFont(colorsBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));
    colorsBtn.addActionListener(runFilterListener);

    // BOTON DE ROTAR
    Button rotationBtn = new Button("Rotar", 300, 70, new Color(80, 80, 80));
    rotationBtn.setLabelFont(rotationBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));
    rotationBtn.addActionListener(runRotateListener);

    // BOTON DE BLANCO Y NEGRO
    Button bnBtn = new Button("Blanco y negro", 300, 70, new Color(80, 80, 80));
    bnBtn.setLabelFont(bnBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));
    bnBtn.addActionListener(runBNListener);

    // BOTON DE EDITAR
    Button convertBtn = new Button("Generar Todo", 300, 79);
    convertBtn.setLabelFont(convertBtn.getFont().deriveFont(30f).deriveFont(Font.BOLD));
    convertBtn.addActionListener(runAllListener);

    // MENU
    EditorMenu menubar = new EditorMenu();

    // AGREGAR LISTENERS
    menubar.copyItem.addActionListener(runCopyListener);
    menubar.convertItem.addActionListener(runConvertListener);
    menubar.filterItem.addActionListener(runFilterListener);
    menubar.rotateItem.addActionListener(runRotateListener);
    menubar.bnItem.addActionListener(runBNListener);
    menubar.allItem.addActionListener(runAllListener);

    // AGREGAR BOTON DE COPIAR
    btnC.weighty = 7;
    btnC.gridheight = 1;
    btnC.gridy = 0;
    btnPanel.add(copyBtn, btnC);

    // AGREGAR BOTON DE CONVERTIR
    btnC.gridy = 1;
    btnPanel.add(reverseBtn, btnC);

    // AGREGAR BOTON DE COLORES
    btnC.gridy = 2;
    btnPanel.add(colorsBtn, btnC);

    // AGREGAR BOTON DE ROTAR
    btnC.gridy = 3;
    btnPanel.add(rotationBtn, btnC);

    // AGREGAR BOTON DE BLANCO Y NEGRO
    btnC.gridy = 4;
    btnPanel.add(bnBtn, btnC);

    // AGREGAR BOTON PRINCIPAL
    btnC.gridy = 5;
    btnC.gridheight = 2;
    btnPanel.add(convertBtn, btnC);

    // AGREGAR PANEL DE CATEGORIA E IMAGENES
    pathPanel.add(categoryBox);
    pathPanel.add(imageBox);

    // AGREGAR MENU
    setJMenuBar(menubar);

    // AGREGAR PANEL DE RUTAS
    imgC.weightx = 2;
    imgC.weighty = 10;
    imgC.gridheight = 1;
    imgC.gridy = 0;
    imgC.gridx = 0;
    imgC.gridwidth = 1;
    add(pathPanel, imgC);

    // AGREGAR PANEL DE IMAGENES
    imgC.gridx = 1;
    imgC.gridheight = 10;
    imgC.gridy = 0;
    add(btnPanel, imgC);

    // ASIGNAR ESPACIO DE PANEL DE IMAGEN
    imgC.gridy = 2;
    imgC.gridheight = 7;
    imgC.gridx = 0;
    imgC.gridwidth = 1;
    add(image.getCPane(), imgC);
  }
}