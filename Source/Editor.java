package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Editor extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public Editor(String userName, Controller userController) {
    // CONFIGURAR VENTANA
    setSize(708, 480);
    setLayout(new GridBagLayout());

    // INICIALES
    User currentUser = userController.getData();
    DoublyLinkedList<Category> categoryList = currentUser.getCategoryList();

    Image image = new Image(categoryList.get(0).images.get(0), 400, 400);

    // PANELES
    GridBagConstraints imgC = new GridBagConstraints();
    JPanel pathPanel = new JPanel();
    pathPanel.setPreferredSize(new Dimension(400, 30));
    pathPanel.setLayout(new FlowLayout());

    // COMBO BOXES
    JComboBox<String> categoryBox = new JComboBox<String>();
    categoryBox.setPreferredSize(new Dimension(70, 25));
    for (int i = 0; i < categoryList.getSize(); i++)
      categoryBox.addItem(categoryList.get(i).name);

    JComboBox<String> imageBox = new JComboBox<String>();
    imageBox.setPreferredSize(new Dimension(320, 25));
    for (int i = 0; i < categoryList.get(0).images.getSize(); i++) {
      final int index = i;

      imageBox.addItem(categoryList.get(0).images.get(i));
      imageBox.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          image.updateSrc(categoryList.get(0).images.get(index));
        }
      });
    }

    // PANEL DE BOTONES
    GridBagConstraints btnC = new GridBagConstraints();
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new GridBagLayout());

    // BOTON DE COPIAR
    Button copyBtn = new Button("Copiar", 300, 70, new Color(80, 80, 80));
    copyBtn.setLabelFont(copyBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));

    // BOTON DE CONVERTIR
    Button reverseBtn = new Button("Convertir", 300, 70, new Color(80, 80, 80));
    reverseBtn.setLabelFont(reverseBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));

    // BOTON DE FILTROS
    Button colorsBtn = new Button("Filtros", 300, 70, new Color(80, 80, 80));
    colorsBtn.setLabelFont(colorsBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));

    // BOTON DE ROTAR
    Button rotationBtn = new Button("Rotar", 300, 70, new Color(80, 80, 80));
    rotationBtn.setLabelFont(rotationBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));

    // BOTON DE BLANCO Y NEGRO
    Button bnBtn = new Button("Blanco y negro", 300, 70, new Color(80, 80, 80));
    bnBtn.setLabelFont(bnBtn.getFont().deriveFont(25f).deriveFont(Font.BOLD));

    // BOTON DE EDITAR
    Button convertBtn = new Button("Editar", 300, 79);
    convertBtn.setLabelFont(convertBtn.getFont().deriveFont(30f).deriveFont(Font.BOLD));

    // MENU
    EditorMenu menubar = new EditorMenu();

    // AGREGAR
    btnC.weighty = 7;
    btnC.gridheight = 1;
    btnC.gridy = 0;
    btnPanel.add(copyBtn, btnC);

    btnC.gridy = 1;
    btnPanel.add(reverseBtn, btnC);

    btnC.gridy = 2;
    btnPanel.add(colorsBtn, btnC);

    btnC.gridy = 3;
    btnPanel.add(rotationBtn, btnC);

    btnC.gridy = 4;
    btnPanel.add(bnBtn, btnC);

    btnC.gridy = 5;
    btnC.gridheight = 2;
    btnPanel.add(convertBtn, btnC);

    pathPanel.add(categoryBox);
    pathPanel.add(imageBox);

    // AGREGAR MENU
    setJMenuBar(menubar);

    imgC.weightx = 2;
    imgC.weighty = 10;
    imgC.gridheight = 1;
    imgC.gridy = 0;
    imgC.gridx = 0;
    imgC.gridwidth = 1;
    add(pathPanel, imgC);

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