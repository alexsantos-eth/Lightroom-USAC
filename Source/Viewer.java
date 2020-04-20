package Source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.border.EmptyBorder;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Viewer extends FrameCommon {
  // VARIABLES GLOBALES
  private static final long serialVersionUID = 1L;
  private String defSrc, defInfo;
  private JPanel categoryPanel;
  private JPanel imagePanel;
  private Button[] navBtns;
  private int sliderCount;
  private Label fileName;
  private User tempList;
  private Image image;
  private String currentCategory;
  private Controller userController;
  private Boolean saveState;

  // AGREGAR CATEGORIA A PANEL
  public void addCategoryPane(JPanel pane, String category) {
    // ACTUALIZAR TEXTO
    Button nBtn = new Button(category, Theme.colorList[(int) (Math.random() * 17) + 1]);

    nBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        updateCategoryView(category);
      }
    });

    nBtn.setPreferredSize(new Dimension(200, 50));
    pane.add(nBtn);
  }

  public void updateCategoryView(String category) {
    currentCategory = category;

    String path = defSrc;
    if (tempList.getCategory(currentCategory).images.getSize() > 0)
      path = tempList.getCategory(currentCategory).images.get(0);

    updateImage(path);
  }

  // GUARDAR LISTA DE CATEGORIAS
  public void saveCategory() {
    // MOSTRAR DIALOGO
    String categoryStr = JOptionPane.showInputDialog(null, "Escribe el nombre de la categoria que deseas agregar");
    currentCategory = categoryStr;

    tempList.addCategory(new Category(currentCategory));

    updateWholeView(defSrc);
  }

  public void updateWholeView(String path) {
    updateImage(path);
    addCategoryPane(categoryPanel, currentCategory);
  }

  // ACTUALIZAR CONTROLES DE NAVEGACION
  public void setControls(int mode) {
    // VARIABLES POR DEFECTO
    int tempWidth = 400;
    imagePanel.removeAll();

    // CONDICIONES DE NAVEGACION
    if (mode == 0) {
      imagePanel.add(navBtns[0]);
      imagePanel.add(image.getCPane());
      imagePanel.add(navBtns[1]);
    } else if (mode == 1) {
      tempWidth = 440;
      imagePanel.add(image.getCPane());
      imagePanel.add(navBtns[1]);
    } else if (mode == 2) {
      tempWidth = 440;
      imagePanel.add(navBtns[0]);
      imagePanel.add(image.getCPane());
    } else if (mode == 3) {
      tempWidth = 480;
      imagePanel.add(image.getCPane());
    }

    // ACTUALIZAR DIMENSION
    image.getCPane().setPreferredSize(new Dimension(tempWidth, 400));

    // RE RENDERIZAR
    image.revalidate();
    image.repaint();
    imagePanel.revalidate();
    imagePanel.repaint();
  }

  public void removeCategory() {
    tempList.removeCategory(tempList.getCategory(currentCategory));

    currentCategory = tempList.getCategoryList().get(tempList.getCategoryList().getSize() - 1).name;
    updateImage(tempList.getCategory(currentCategory).images.get(0));

    categoryPanel.remove(categoryPanel.getComponent(categoryPanel.getComponentCount() - 1));
    categoryPanel.repaint();
  }

  public void convertToBMP(String path) {
    try {
      if (!path.contains(".bmp")) {
        File input = new File(path);
        BufferedImage image = ImageIO.read(input);
        String outputName = path + ".bmp";
        File output = new File(outputName);
        ImageIO.write(image, "bmp", output);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (IOException e) {
      System.out.println("Error:" + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // LEER ARCHIVOS
  public void readFile(String catName) {
    // ABRIR DIALOGO
    FileDialog dialog = new FileDialog((Frame) null, "Selecciona tu foto");

    // CONFIGURAR DIALOGO
    dialog.setMode(FileDialog.LOAD);
    dialog.setMultipleMode(true);
    dialog.setVisible(true);

    // LISTA DE ARCHIVOS
    File[] paths = dialog.getFiles();

    if (tempList.getCategoryList().getSize() == 0) {
      addCategoryPane(categoryPanel, catName);
      tempList.addCategory(new Category(catName));
    }

    for (int i = 0; i < paths.length; i++) {
      convertToBMP(paths[i].getAbsolutePath());
      tempList.getCategory(currentCategory).addImages(paths[i].getAbsolutePath());
    }

    // ACTUALIZAR CONTROLES
    if (tempList.getCategory(currentCategory) != null && tempList.getCategory(currentCategory).images.getSize() > 1)
      setControls(0);

    // ACTUALIZAR IMAGEN
    sliderCount = 0;
    updateImage(tempList.getCategory(currentCategory).images.get(0));

    dialog.setEnabled(false);
  }

  // ACTUALIZAR IMAGE
  public void updateImage(String path) {
    // ACTUALIZAR
    fileName.setText(path != defSrc ? path : defInfo);
    image.updateSrc(path);

    // ACTUALIZAR CONTROLES
    if (tempList.getCategory(currentCategory).images.getSize() > 1) {
      if (sliderCount == 0)
        setControls(1);
      else if (sliderCount == tempList.getCategory(currentCategory).images.getSize() - 1)
        setControls(2);
      else
        setControls(0);
    } else
      setControls(3);

    if (path == defSrc)
      setControls(3);
  }

  // SIGUIENTE IMAGEN
  public void goNext() {
    if (sliderCount < tempList.getCategory(currentCategory).images.getSize() - 1) {
      sliderCount++;
      updateImage(tempList.getCategory(currentCategory).images.get(sliderCount));
    }
  }

  // IMAGEN ANTERIOR
  public void goBack() {
    if (sliderCount > 0) {
      sliderCount--;
      updateImage(tempList.getCategory(currentCategory).images.get(sliderCount));
    }
  }

  public void openImage() {
    if (tempList.getCategoryList().getSize() == 0) {
      currentCategory = "General";
      readFile("General");
    } else
      readFile(currentCategory);
  }

  public void deleteImage() {
    tempList.getCategory(currentCategory).deleteImage(tempList.getCategory(currentCategory).images.get(sliderCount));

    if (sliderCount > 0)
      sliderCount--;

    updateImage(tempList.getCategory(currentCategory).images.getSize() == 0 ? defSrc
        : tempList.getCategory(currentCategory).images.get(sliderCount));
  }

  public void loadUserState() {
    DoublyLinkedList<Category> catList = tempList.getCategoryList();
    for (int i = 0; i < catList.getSize(); i++) {
      String path = defSrc;
      currentCategory = catList.get(i).name;

      if (catList.get(i).images.getSize() > 0)
        path = catList.get(i).images.get(0);

      updateWholeView(path);
    }
  }

  public void saveUser() {
    saveState = true;
    userController.saveUser(tempList);
  }

  public Viewer(String userName) {
    // CONFIG
    setLayout(new GridBagLayout());
    setFocusable(true);
    setSize(850, 650);
    setResizable(true);

    // EVENTOS
    ActionListener addCategoryListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        saveCategory();
      }
    };

    ActionListener removeCategoryListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        removeCategory();
      }
    };

    ActionListener openImageListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        openImage();
      }
    };

    ActionListener goPreviousBtnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goBack();
      }
    };

    ActionListener goNextBtnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goNext();
      }
    };

    ActionListener deleteImageListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        deleteImage();
      }
    };

    ActionListener saveUserListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        saveUser();
      }
    };

    // LOCALES
    GridBagConstraints mainC = new GridBagConstraints();
    GridBagConstraints c = new GridBagConstraints();
    LayoutManager flow = new FlowLayout();
    GridLayout grid = new GridLayout(2, 1);
    ViewerMenu menubar = new ViewerMenu();

    // INICIALES
    defSrc = "../Source/assets/imageBackground.jpg";
    defInfo = "Ruta en los archivos de las fotos";
    fileName = new Label(defInfo, Color.white);
    image = new Image(defSrc, 480, 400);
    userController = new Controller(userName);
    tempList = userController.getData();
    navBtns = new Button[2];
    sliderCount = 0;
    saveState = true;

    // PROPIEDADES LOCALES
    grid.setVgap(5);

    // PANEL DE CATEGORIA
    categoryPanel = new JPanel();
    categoryPanel.setBackground(new Color(60, 60, 60));
    categoryPanel.setFont(Theme.font);
    categoryPanel.setAlignmentY(SwingConstants.CENTER);
    categoryPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
    categoryPanel.setPreferredSize(new Dimension(250, 556));

    // PANEL DE SCROLL
    JScrollPane scrollPane = new JScrollPane(categoryPanel);
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);

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

    // TITULO
    Label nameTitle = new Label(userName.toUpperCase() + " WORKSPACE", Color.white);
    nameTitle.setFont(Theme.font.deriveFont(18f).deriveFont(Font.BOLD));

    // COMPONENTES PRIMITIVOS
    Button openImage = new Button("Abrir imagen", 238, 50);
    Button addCategory = new Button("Agregar categoria", 238, 5);
    Button deleteImage = new Button("Eliminar imagen", 238, 50, Theme.grayBlue);
    Button removeCategory = new Button("Eliminar categoria", 238, 50, Theme.grayBlue);

    // EVENTOS
    menubar.newCategory.addActionListener(addCategoryListener);
    addCategory.addActionListener(addCategoryListener);

    menubar.removeCategory.addActionListener(removeCategoryListener);
    removeCategory.addActionListener(removeCategoryListener);

    menubar.openFile.addActionListener(openImageListener);
    openImage.addActionListener(openImageListener);

    menubar.closeFile.addActionListener(deleteImageListener);
    deleteImage.addActionListener(deleteImageListener);

    menubar.saveWorkspace.addActionListener(saveUserListener);
    goPreviousBtn.addActionListener(goPreviousBtnListener);
    goNextBtn.addActionListener(goNextBtnListener);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        if (saveState == false & JOptionPane.showConfirmDialog(null, "Deseas guardar antes de salir?", "Cerrar Ventana",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
          saveUser();
        }
      }
    });

    // ASIGNAR PANEL DE IMAGEN
    // imagePanel.add(goPrevious);
    imagePanel.add(image.getCPane());
    // imagePanel.add(goNext);

    // ASIGNAR PANEL DE RUTA
    fileNamePanel.add(fileName);

    // ASIGNAR FILA DE BOTONES
    fileBtnPanel.add(openImage);
    fileBtnPanel.add(addCategory);

    // ASIGNAR FILA DE BOTONES
    categoryBtnPanel.add(deleteImage);
    categoryBtnPanel.add(removeCategory);
    categoryPanel.add(nameTitle);

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

    setJMenuBar(menubar);
    loadUserState();
  }
}
