package Source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class Viewer extends FrameCommon {
  // VARIABLES GLOBALES
  private static final long serialVersionUID = 1L;
  List tempList;
  String header = "<html><body><font face='Arial' color='white' size='5' align='center'>Categorias: </font><hr/>";
  JEditorPane categoryPanel;
  JPanel imagePanel;
  Button[] navBtns;
  Image image;
  int sliderCount;

  // AGREGAR CATEGORIA A PANEL
  public void addCategoryPane(JEditorPane pane, String category) {
    String catString = header + "<font face='Arial' color='white' size='7' align='center'>" + category + "</font><br/>";
    header = catString;
    pane.setText(header + "</body></html>");
  }

  // GUARDAR LISTA DE CATEGORIAS
  public void saveCategory() {
    String categoryStr = JOptionPane.showInputDialog(null, "Escribe el nombre de la categoria que deseas agregar");
    addCategoryPane(categoryPanel, categoryStr);
  }

  public void setControls(int mode) {
    int tempWidth = 400;
    imagePanel.removeAll();

    if (mode == 0) {
      imagePanel.add(navBtns[0]);
      imagePanel.add(image);
      imagePanel.add(navBtns[1]);
    } else if (mode == 1) {
      tempWidth = 440;
      imagePanel.add(image);
      imagePanel.add(navBtns[1]);
    } else if (mode == 2) {
      tempWidth = 440;
      imagePanel.add(navBtns[0]);
      imagePanel.add(image);
    } else if (mode == 3) {
      tempWidth = 480;
      imagePanel.add(image);
    }

    image.setPreferredSize(new Dimension(tempWidth, 400));
    image.revalidate();
    image.repaint();
    imagePanel.revalidate();
    imagePanel.repaint();
  }

  // LEER ARCHIVOS
  public void readFile() {
    FileDialog dialog = new FileDialog((Frame) null, "Selecciona tu foto");
    dialog.setMode(FileDialog.LOAD);
    dialog.setMultipleMode(true);
    dialog.setVisible(true);
    File[] paths = dialog.getFiles();

    for (int i = 0; i < paths.length; i++) {
      tempList.add(paths[i].getAbsolutePath());
    }

    if (tempList.size() > 1)
      setControls(0);

    updateImage(tempList.get(sliderCount));
  }

  // ACTUALIZAR IMAGE
  public void updateImage(String path) {
    image.updateSrc(path);

    if (tempList.size() > 1) {
      if (sliderCount == 0)
        setControls(1);
      else if (sliderCount == tempList.size() - 1)
        setControls(2);
      else
        setControls(0);
    } else
      setControls(3);
  }

  public void goNext() {
    if (sliderCount < tempList.size() - 1) {
      sliderCount++;
      updateImage(tempList.get(sliderCount));
    }
  }

  public void goBack() {
    if (sliderCount > 0) {
      sliderCount--;
      updateImage(tempList.get(sliderCount));
    }
  }

  public Viewer() {
    // CONFIG
    setLayout(new GridBagLayout());
    setFocusable(true);
    setSize(749, 585);

    // LOCALES
    GridBagConstraints mainC = new GridBagConstraints();
    GridBagConstraints c = new GridBagConstraints();
    LayoutManager flow = new FlowLayout();
    GridLayout grid = new GridLayout(2, 1);
    String src = "../Source/assets/imageBackground.jpg";

    // INICIALES
    image = new Image(src, 480, 400);
    tempList = new List();
    navBtns = new Button[2];
    sliderCount = 0;

    // PROPIEDADES LOCALES
    grid.setVgap(5);

    // PANEL DE CATEGORIA
    categoryPanel = new JEditorPane();
    categoryPanel.setBackground(new Color(60, 60, 60));
    categoryPanel.setFont(Theme.font);
    categoryPanel.setAlignmentY(SwingConstants.CENTER);
    categoryPanel.setEditable(false);
    categoryPanel.setContentType("text/html");
    categoryPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
    categoryPanel.setPreferredSize(new Dimension(250, 556));

    // PANEL DE SCROLL
    JScrollPane scrollPane = new JScrollPane(categoryPanel);
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);
    addCategoryPane(categoryPanel, "General");

    // PANEL DE VISOR
    JPanel viewerPanel = new JPanel();
    viewerPanel.setLayout(new GridBagLayout());

    // PANEL DE IMAGEN Y NAVEGACION
    imagePanel = new JPanel();
    imagePanel.setLayout(flow);

    // PANEL DE RUTA
    JPanel fileNamePanel = new JPanel();
    fileNamePanel.setLayout(flow);
    fileNamePanel.setPreferredSize(new Dimension(480, 30));
    fileNamePanel.setBackground(new Color(190, 190, 190));

    // PANEL DE BOTONES
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(flow);

    // PANEL DE BOTONES PARA ARCHIVOS
    JPanel fileBtnPanel = new JPanel();
    fileBtnPanel.setLayout(grid);

    // PANEL DE BOTONES DE CATEGORIAS
    JPanel categoryBtnPanel = new JPanel();
    categoryBtnPanel.setLayout(grid);

    // ==== NAVEGACION ====
    // KEY LISTENERS
    KeyListener keyListener = new KeyListener() {
      public void keyTyped(KeyEvent e) {

      }

      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
          goNext();
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
          goBack();

        System.out.println(e.getKeyCode());
      }

      public void keyReleased(KeyEvent e) {
      }
    };
    addKeyListener(keyListener);

    // BOTON DE ATRAS
    Button goPreviousBtn = new Button("", new Color(240, 240, 240));
    goPreviousBtn.setLayout(new GridLayout(2, 1));
    goPreviousBtn.setPreferredSize(new Dimension(35, 200));
    goPreviousBtn.setBorder(new EmptyBorder(0, 0, 75, 0));

    // ICONO DE ATRAS
    JLabel prevLabel = new JLabel(new ImageIcon("../Source/assets/prev.png"));
    goPreviousBtn.add(prevLabel);
    navBtns[0] = goPreviousBtn;

    // BOTON DE SIGUIENTE
    Button goNextBtn = new Button("", new Color(240, 240, 240));
    goNextBtn.setLayout(new GridLayout(2, 1));
    goNextBtn.setPreferredSize(new Dimension(35, 200));
    goNextBtn.setBorder(new EmptyBorder(0, 0, 75, 0));

    // ICONO DE SIGUIENTE
    JLabel nextLabel = new JLabel(new ImageIcon("../Source/assets/next.png"));
    goNextBtn.add(nextLabel);
    navBtns[1] = goNextBtn;

    // COMPONENTES PRIMITIVOS
    Label fileName = new Label("Ruta en los archivos de las fotos", Color.white);
    Button openImage = new Button("Abrir imagen", 238, 50);
    Button addCategory = new Button("Agregar categoria", 238, 5);
    Button deleteImage = new Button("Eliminar imagen", 238, 50, Theme.grayBlue);
    Button removeCategory = new Button("Eliminar categoria", 238, 50, Theme.grayBlue);

    // EVENTOS
    addCategory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        saveCategory();
      }
    });

    openImage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        readFile();
      }
    });

    goPreviousBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goBack();
      }
    });

    goNextBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goNext();
      }
    });

    deleteImage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        tempList.remove(sliderCount);
        if (sliderCount != 0)
          sliderCount--;

        updateImage(tempList.size() == 0 ? src : tempList.get(sliderCount));
      }
    });

    // ASIGNAR PANEL DE IMAGEN
    // imagePanel.add(goPrevious);
    imagePanel.add(image);
    // imagePanel.add(goNext);

    // ASIGNAR PANEL DE RUTA
    fileNamePanel.add(fileName);

    // ASIGNAR FILA DE BOTONES
    fileBtnPanel.add(openImage);
    fileBtnPanel.add(addCategory);

    // ASIGNAR FILA DE BOTONES
    categoryBtnPanel.add(deleteImage);
    categoryBtnPanel.add(removeCategory);

    // ASIGNAR PANEL DE BOTONES
    btnPanel.add(fileBtnPanel);
    btnPanel.add(categoryBtnPanel);

    // AGREGAR ESPACIO DE PANEL DE RUTA
    c.weighty = 10;
    c.gridheight = 1;
    c.gridy = 0;
    viewerPanel.add(fileNamePanel, c);

    // ASIGNAR ESPACIO DE PANEL DE IMAGEN
    c.gridy = 2;
    c.gridheight = 7;
    viewerPanel.add(imagePanel, c);

    // ASIGNAR ESPACIO DE PANEL DE BOTONES
    c.gridy = 9;
    c.gridheight = 1;
    viewerPanel.add(btnPanel, c);

    // AGREGAR PANEL PRINCIPAL
    mainC.weightx = 5;
    mainC.gridwidth = 1;
    mainC.gridx = 0;
    add(scrollPane, mainC);

    // AGREGAR PANEL DE VISOR
    mainC.gridx = 1;
    mainC.gridwidth = 5;
    add(viewerPanel, mainC);
  }
}
