package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Viewer extends FrameCommon {
  // VARIABLES GLOBALES
  private static final long serialVersionUID = 1L;
  private Controller userController;
  private String currentCategory;
  private String defSrc, defInfo;
  private JPanel categoryPanel;
  private Boolean saveState;
  private JPanel imagePanel;
  private Button[] navBtns;
  private int sliderCount;
  private Label fileName;
  private User tempList;
  private Image image;

  // AGREGAR CATEGORIA A PANEL
  public void addCategoryPane(JPanel pane, String category, Color randomColor) {
    // ACTUALIZAR TEXTO
    Button nBtn = new Button(category, randomColor);

    // EVENTO PARA CAMBIAR DE CATEGORIA
    nBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        updateCategoryView(category);
      }
    });

    // AGREGAR BOTON AL PANEL
    nBtn.setPreferredSize(new Dimension(200, 50));
    pane.add(nBtn);
  }

  // ACTUALIZAR LISTA DE CATEGORIAS
  public void updateCategoryView(String category) {
    // ASIGNAR NUEVA CATEGORIA
    currentCategory = category;

    // AGREGAR CATEGORIA AL USUARIO
    String path = defSrc;
    if (tempList.getCategory(currentCategory).images.getSize() > 0)
      path = tempList.getCategory(currentCategory).images.get(0);

    // ACTUALIZAR VISTA
    updateImage(path);
  }

  // GUARDAR LISTA DE CATEGORIAS
  public void saveCategory() {
    // ACTUALIZAR CAMBIOS
    saveState = false;

    // NUEVO COLOR
    Color randomColor = newRandomColor();

    // MOSTRAR DIALOGO
    String categoryStr = JOptionPane.showInputDialog(null, "Escribe el nombre de la categoria que deseas agregar");

    // VERIFICAR CATEGORIA
    if (tempList.verifyCategory(categoryStr)) {
      currentCategory = categoryStr;

      // CREAR CATEGORIA
      Category category = newCategory();
      category.setColor(randomColor);

      // AGREGAR CATEGORIA AL USUARIO Y
      tempList.addCategory(category);
      updateWholeView(defSrc, randomColor);
    }

    // MOSTRAR PANEL DE ERROR SI NO SE PUEDE AGREGAR
    else
      JOptionPane.showMessageDialog(null, "Ocurrio un error al intentar agregar una categoria, intenta de nuevo",
          "Error al agregar", JOptionPane.ERROR_MESSAGE);
  }

  // ACTUALIZAR IMAGEN Y PANEL DE CATEGORIAS
  public void updateWholeView(String path, Color randomColor) {
    updateImage(path);
    addCategoryPane(categoryPanel, currentCategory, randomColor);
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

  // QUITAR CATEGORIA
  public void removeCategory() {
    // ACTUALIZAR CAMBIOS
    saveState = false;

    // VERIFICAR SI NO ESTA VACIA
    if (tempList.getCategoryList().getSize() > 0) {

      // QUITAR DEL USUARIO
      int removeCategoryIndex = tempList.getCategoryIndex(currentCategory);
      tempList.removeCategory(tempList.getCategory(currentCategory));

      // OBTENER NUEVA LONGITUD
      Boolean filledList = tempList.getCategoryList().getSize() > 0;

      // ASIGNAR ULTIMA CATEGORIA Y ACTUALIZAR IMAGEN
      if (filledList)
        currentCategory = tempList.getLastCategory().name;
      else
        currentCategory = "";

      // ACTUALIZAR LISTA
      updateImage(filledList ? tempList.getCategory(currentCategory).images.get(0) : defSrc);

      // QUITAR DEL PANEL DE CATEGORIAS
      categoryPanel.remove(categoryPanel.getComponent(1 + removeCategoryIndex));
      categoryPanel.repaint();
    }
  }

  // CREAR COLOR RANDOM
  public Color newRandomColor() {
    return Globals.colorList[(int) (Math.random() * 17) + 1];
  }

  // CREAR NUEVA CATEGORIA
  public Category newCategory() {
    // CREAR OBJETO Y COLOR
    return new Category(currentCategory);
  }

  // LEER ARCHIVOS
  public void readFile(String catName) {
    // ABRIR DIALOGO
    FileDialog dialog = new FileDialog((Frame) null, "Selecciona tu foto");

    // CONFIGURAR DIALOGO
    dialog.setMode(FileDialog.LOAD);
    dialog.setMultipleMode(true);
    dialog.setVisible(true);
    dialog.setIconImage(new ImageIcon("../Source/assets/icon.png").getImage());

    // LISTA DE ARCHIVOS
    File[] paths = dialog.getFiles();

    // ASIGNAR NUEVA CATEGORIA SI NO HAY
    if (tempList.getCategoryList().getSize() == 0) {
      // CREAR NUEVA CATEGORIA
      Color randomColor = newRandomColor();
      Category category = newCategory();
      category.setColor(randomColor);

      // AGREGAR NUEVA CATEGORIA
      addCategoryPane(categoryPanel, catName, randomColor);
      tempList.addCategory(category);
    }

    // AGREGAR IMAGENES A LA CATEGORIA
    for (int i = 0; i < paths.length; i++) {
      new JPEGtoBMPImage(paths[i].getAbsolutePath(), "tmp/view/");
      tempList.getCategory(currentCategory).addImages(paths[i].getAbsolutePath());
    }

    // ACTUALIZAR CONTROLES
    if (tempList.getCategory(currentCategory) != null && tempList.getCategory(currentCategory).images.getSize() > 1)
      setControls(0);

    // ACTUALIZAR IMAGEN
    sliderCount = 0;
    updateImage(tempList.getCategory(currentCategory).images.get(0));
  }

  // ACTUALIZAR IMAGE
  public void updateImage(String path) {
    // ACTUALIZAR
    fileName.setText(path != defSrc ? path : defInfo);
    image.updateSrc(path);

    // ACTUALIZAR CONTROLES
    if (currentCategory.length() > 0) {
      if (tempList.getCategory(currentCategory).images.getSize() > 1) {
        if (sliderCount == 0)
          setControls(1);
        else if (sliderCount == tempList.getCategory(currentCategory).images.getSize() - 1)
          setControls(2);
        else
          setControls(0);
      } else
        setControls(3);
    }

    // VALOR POR DEFECTO
    if (path == defSrc)
      setControls(3);
  }

  // SIGUIENTE IMAGEN
  public void goNext() {
    // AGREGAR LIMITE
    if (sliderCount < tempList.getCategory(currentCategory).images.getSize() - 1) {
      sliderCount++;
      updateImage(tempList.getCategory(currentCategory).images.get(sliderCount));
    }
  }

  // IMAGEN ANTERIOR
  public void goBack() {
    // AGREGAR LIMITE
    if (sliderCount > 0) {
      sliderCount--;
      updateImage(tempList.getCategory(currentCategory).images.get(sliderCount));
    }
  }

  // ABRIR IMAGEN
  public void openImage() {
    // ACTUALIZAR CAMBIOS
    saveState = false;

    // CREAR NUEVA CATEGORIA GENERAL
    if (tempList.getCategoryList().getSize() == 0) {
      currentCategory = "General";
      readFile("General");
    }

    // SINO ASIGNAR A CATEGORIA ACTUAL
    else
      readFile(currentCategory);
  }

  // BORRAR IMAGEN
  public void deleteImage() {
    // ACTUALIZAR CAMBIOS
    saveState = false;

    // VERIFICAR SI HAY IMAGENES
    if (tempList.getCategory(currentCategory) != null && tempList.getCategory(currentCategory).images != null
        && tempList.getCategory(currentCategory).images.getSize() > 0) {
      // QUITAR DEL USUARIO
      tempList.getCategory(currentCategory).deleteImage(tempList.getCategory(currentCategory).images.get(sliderCount));

      // REDUCIR POSICION
      if (sliderCount > 0)
        sliderCount--;

      // ACTUALIZAR IMAGEN
      updateImage(tempList.getCategory(currentCategory).images.getSize() == 0 ? defSrc
          : tempList.getCategory(currentCategory).images.get(sliderCount));
    }
  }

  // CARGAR DATOS GUARDADOS
  public void loadUserState() {
    // OBTENER LISTA DE CATEGORIAS
    DoublyLinkedList<Category> catList = tempList.getCategoryList();

    // RECORRER LISTA
    for (int i = 0; i < catList.getSize(); i++) {
      // CREAR IMAGENES
      for (int j = 0; j < catList.get(i).images.getSize(); j++) {
        String path = catList.get(i).images.get(i);
        String imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        File tmpFile = new File("tmp/view/" + imageName);

        if (!tmpFile.exists()) {
          new JPEGtoBMPImage(catList.get(i).images.get(j), "tmp/view/");
        }
      }

      // CREAR DIRECCION Y COLOR
      Color color = newRandomColor();
      String path = defSrc;
      currentCategory = catList.get(i).name;

      // ASIGNAR DIRECCION
      if (catList.get(i).images.getSize() > 0) {
        path = catList.get(i).images.get(0);
        color = catList.get(i).color;
      }

      // ACTUALIZAR PANELES
      updateWholeView(path, color);
    }
  }

  // GUARDAR DATOS
  public void saveUser() {
    // CAMBIAR ESTADO Y ENVIAR AL CONTROLADOR
    saveState = true;
    this.userController.saveUser(tempList);
  }

  public Viewer(String userName, Controller userController) {
    // CONFIGURAR VENTANA
    setLayout(new GridBagLayout());
    setFocusable(true);
    setSize(750, 606);

    // ====== EVENTOS =======
    // AGREGAR CATEGORIA
    ActionListener addCategoryListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        saveCategory();
      }
    };

    // REMOVER CATEGORIA
    ActionListener removeCategoryListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        removeCategory();
      }
    };

    // ABRIR IMAGEN
    ActionListener openImageListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        openImage();
      }
    };

    // BORRAR IMAGEN
    ActionListener deleteImageListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        deleteImage();
      }
    };

    // IR ATRAS
    ActionListener goPreviousBtnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goBack();
      }
    };

    // IR ADELANTE
    ActionListener goNextBtnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        goNext();
      }
    };

    // GUARDAR
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
    this.userController = userController;
    image = new Image(defSrc, 480, 400);
    tempList = this.userController.getData();
    navBtns = new Button[2];
    saveState = true;
    sliderCount = 0;

    // PROPIEDADES LOCALES
    setJMenuBar(menubar);
    grid.setVgap(5);

    // PANEL DE CATEGORIA
    categoryPanel = new JPanel();
    categoryPanel.setBackground(new Color(60, 60, 60));
    categoryPanel.setFont(Globals.font);
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
    nameTitle.setFont(Globals.font.deriveFont(18f).deriveFont(Font.BOLD));

    // COMPONENTES PRIMITIVOS
    Button openImage = new Button("Abrir imagen", 238, 50);
    Button addCategory = new Button("Agregar categoria", 238, 5);
    Button deleteImage = new Button("Eliminar imagen", 238, 50, Globals.grayBlue);
    Button removeCategory = new Button("Eliminar categoria", 238, 50, Globals.grayBlue);

    // ============= ASIGNAR EVENTOS ==============
    // EVENTO DE AGREGAR CATEGORIA
    menubar.newCategory.addActionListener(addCategoryListener);
    addCategory.addActionListener(addCategoryListener);

    // EVENTO DE REMOVE CATEGORIA
    menubar.removeCategory.addActionListener(removeCategoryListener);
    removeCategory.addActionListener(removeCategoryListener);

    // EVENTO DE ABRIR IMAGEN
    menubar.openFile.addActionListener(openImageListener);
    openImage.addActionListener(openImageListener);

    // EVENTO DE CERRAR IMAGEN
    menubar.closeFile.addActionListener(deleteImageListener);
    deleteImage.addActionListener(deleteImageListener);

    // NAVEGACION Y GUARDAR
    menubar.saveWorkspace.addActionListener(saveUserListener);
    goPreviousBtn.addActionListener(goPreviousBtnListener);
    goNextBtn.addActionListener(goNextBtnListener);

    // EVENTO AL CERRAR VENTANA
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        // VERIFICAR SI SE GUARDO
        if (saveState == false && JOptionPane.showConfirmDialog(null, "Deseas guardar antes de salir?",
            "Cerrar Ventana", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
          saveUser();
      }
    });

    // ASIGNAR PANEL DE IMAGEN
    imagePanel.add(image.getCPane());

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

    // CARGAR DATOS
    loadUserState();
  }
}
