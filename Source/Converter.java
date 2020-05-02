package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// EJECUTAR POR HILOS
class ProcessThread extends Thread {
  // IMAGE HANDLER Y PROGRESS BAR
  private JProgressBar progressBar;
  private ImageHandler handler;

  // CONSTRUCTOR
  public ProcessThread(ImageHandler handler, JProgressBar progress) {
    this.handler = handler;
    this.progressBar = progress;
  }

  // EJECUTAR
  @Override
  public void run() {
    try {
      // EJECUTAR HANDLER
      JPEGHandler.runHandler(handler);

      // AUMENTAR BARRA
      progressBar.setValue(progressBar.getValue() + 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

public class Converter extends FrameCommon {
  // COMPONENTES
  private static final long serialVersionUID = 1L;
  private JLabel usersLabel;
  private JComboBox<String> usersBox;
  private JLabel categoryLabel;
  private JComboBox<String> categoryBox;
  private JList<String> categoryList;
  private JScrollPane categoryScroll;
  private JButton bnBtn;
  private JButton copyBtn;
  private JButton filterBtn;
  private JButton convertBtn;
  private JButton rotateBtn;
  private JButton addBtn;
  private JRadioButton thread;
  private JRadioButton fifo;
  private JRadioButton lifo;
  private JMenuBar menubar;
  private JTextArea console;
  private JScrollPane consoleScroll;
  private JProgressBar progress;
  private ButtonGroup radios;
  private LinkedList<User> users;
  private int categoryNum;

  // VERIFICAR SI EXISTEN ELEMENTOS
  private boolean verifySize(int size) {
    // CONDICION
    boolean out = size != 0;

    // MENSAJE DE ERROR
    if (!out)
      JOptionPane.showMessageDialog(null, "No existen imagenes agregadas a la cola de ejecucion", "Error al ejecutar",
          JOptionPane.ERROR_MESSAGE);

    // RETORNO
    return out;
  }

  // ASIGNAR USUARIOS
  private void setUsers() {
    for (int i = 0; i < users.getSize(); i++)
      usersBox.addItem(users.get(i).name);
  }

  // ASIGNAR CATEGORIAS
  private void setCategories(int index) {
    for (int i = 0; i < users.get(index).getCategoryList().getSize(); i++)
      categoryBox.addItem(users.get(index).getCategoryList().get(i).name);
  }

  // ASIGNAR LISTENER DE AGREGAR
  private void setAddListener() {
    // LISTENER
    ActionListener addListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        // CATEGORIA ACTUAL
        String currentStrCategory = (String) categoryBox.getSelectedItem();
        Category currentCategory = users.get(usersBox.getSelectedIndex()).getCategory(currentStrCategory);

        // MODELO DE LISTA
        DefaultListModel<String> model = (DefaultListModel<String>) categoryList.getModel();

        // ASIGNAR A MODELO
        for (int i = 0; i < currentCategory.images.getSize(); i++)
          model.addElement(currentCategory.images.get(i));

        // ASIGNAR DIMENSION
        int size = currentCategory.images.getSize() * (++categoryNum);
        progress.setMaximum(size);
        progress.setValue(0);

        // MOSTRAR TOTAL
        System.out.println("Add " + size + " images to task");
      }
    };

    // AGREGAR LISTENER A BOTON
    addBtn.addActionListener(addListener);
  }

  // CONSOLA
  private void setConsole() {
    /// MOSTRAR CONSOLA
    System.setOut(new PrintStream(new OutputStream() {
      public void write(int b) throws IOException {
        console.append(String.valueOf((char) b));
      }
    }));
  }

  // CAMBIAR CATEGORIAS
  private void changeCategories() {
    // LISTENER DE COMBO BOX
    usersBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        // OBTENER POSICION
        int index = usersBox.getSelectedIndex();
        categoryBox.removeAllItems();

        // CREAR CATEGORIAS
        setCategories(index);
      }
    });
  }

  // LISTENER
  private void setProcessListeners() {
    // LISTENER DE COPIAR
    ActionListener copyListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        btnsPerformed(0);
      }
    };

    // LISTENER DE FILTROS
    ActionListener filterListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        btnsPerformed(1);
      }
    };

    // LISTENER DE ROTAR
    ActionListener rotateListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        btnsPerformed(2);
      }
    };

    // LISTENER DE BLANCO Y NEGRO
    ActionListener bnListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        btnsPerformed(3);
      }
    };

    // LISTENER DE CONVERTIR
    ActionListener convertListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        btnsPerformed(4);
      }
    };

    // ASIGNAR A BOTONES
    convertBtn.addActionListener(convertListener);
    copyBtn.addActionListener(copyListener);
    filterBtn.addActionListener(filterListener);
    rotateBtn.addActionListener(rotateListener);
    bnBtn.addActionListener(bnListener);
  }

  // VERIFICACIONES Y REINICIOS
  private void btnsPerformed(int index) {
    // MODO DE EJECUCION
    int mode = thread.isSelected() ? 0 : fifo.isSelected() ? 1 : lifo.isSelected() ? 2 : -1;

    // MODELO DE LISTA
    DefaultListModel<String> model = (DefaultListModel<String>) categoryList.getModel();

    // VERIFICAR LONGITUD
    if (verifySize(model.getSize())) {
      // CREAR HILO Y AGREGAR A COLA/PILA
      for (int i = 0; i < model.getSize(); i++) {
        // CREAR HANDLER
        String path = model.getElementAt(i);

        ImageHandler handler = index == 0 ? copyHandler(path)
            : index == 1 ? filterHandler(path)
                : index == 2 ? rotateHandler(path) : index == 3 ? bnHandler(path) : convertHandler(path);

        if (mode == 0)
          // CREAR HILO
          new ProcessThread(handler, progress).start();
      }

      // EJECUTAR COLAS/PILAS

      // REINICIAR VALORES
      model.removeAllElements();
      categoryNum = 0;
    }

  }

  // ACCION DE CONVERTIR
  private ImageHandler convertHandler(String path) {
    String type = path.substring(path.lastIndexOf(".") + 1);

    // HANDLERS
    ImageHandler handler = type.equals("jpeg") || type.equals("jpg")
        ? new JPEGtoBMPImage(path, "tmp/converted/", "converted-", false)
        : new BMPtoJPEGImage(path, "tmp/converted/", "converted-", false);

    return handler;
  }

  // ACCION DE COPIAR
  private ImageHandler copyHandler(String path) {
    return new JPEGImageCopy(path);
  }

  // ACCION DE FILTROS
  private ImageHandler filterHandler(String path) {
    return new JPEGImageHandlerColors(path);
  }

  // ACCION DE ROTAR
  private ImageHandler rotateHandler(String path) {
    return new JPEGImageHandlerRotator(path);
  }

  // ACCION DE BLANCO Y NEGRO
  private ImageHandler bnHandler(String path) {
    return new JPEGImageHandlerBN(path);
  }

  public Converter(Controller useController) {
    // DATOS
    users = useController.getUsers();
    categoryNum = 0;

    // MENU
    JMenu archivoMenu = new JMenu("Archivo");
    JMenuItem copiarItem = new JMenuItem("Copiar");
    archivoMenu.add(copiarItem);
    JMenuItem filtrosItem = new JMenuItem("Filtros");
    archivoMenu.add(filtrosItem);
    JMenuItem rotarItem = new JMenuItem("Rotar");
    archivoMenu.add(rotarItem);
    JMenuItem blanco_y_negroItem = new JMenuItem("Blanco y Negro");
    archivoMenu.add(blanco_y_negroItem);
    JMenuItem convertir_jpeg_bmpItem = new JMenuItem("Convertir JPEG/BMP");
    archivoMenu.add(convertir_jpeg_bmpItem);

    // CONSTRUIR COMPONENTES
    // USUARIOS
    usersLabel = new JLabel("Usuarios: ");
    usersBox = new JComboBox<String>();

    // CATEGORIAS
    categoryLabel = new JLabel("Categorias: ");
    categoryBox = new JComboBox<String>();

    // PANEL DE CATEGORIAS
    categoryList = new JList<String>(new DefaultListModel<String>());
    categoryScroll = new JScrollPane(categoryList);

    // BOTONES
    bnBtn = new JButton("Blanco y N.");
    copyBtn = new JButton("Copiar");
    filterBtn = new JButton("Filtros");
    convertBtn = new JButton("Convertir");
    rotateBtn = new JButton("Modificar");
    addBtn = new JButton("Agregar");

    // RADIO BOTONES
    radios = new ButtonGroup();
    thread = new JRadioButton("Ejecutar en paralelo");
    fifo = new JRadioButton("Ejecutar en Secualcial FIFO");
    lifo = new JRadioButton("Ejecutar secuencial LIFO");

    // MENU
    menubar = new JMenuBar();

    // AGREGAR SUB COMPONENTES
    menubar.add(archivoMenu);
    radios.add(thread);
    radios.add(fifo);
    radios.add(lifo);

    // AGREGAR TEXTOS DE USUARIOS
    setUsers();

    // AGREGAR TEXTOS DE CATEGORIAS
    setCategories(0);

    // CONSOLA
    console = new JTextArea(5, 5);
    console.setForeground(Color.white);
    console.setEditable(false);
    console.setBackground(new Color(80, 80, 80));
    consoleScroll = new JScrollPane(console);

    // BARRA DE PROGRESO
    progress = new JProgressBar(0, 0);

    // AJUSTAR DIMENSION
    setSize(525, 450);
    setLayout(null);

    // AGREGAR COMPONENTES
    add(usersLabel);
    add(usersBox);
    add(categoryLabel);
    add(categoryBox);
    add(categoryScroll);
    add(bnBtn);
    add(copyBtn);
    add(filterBtn);
    add(convertBtn);
    add(rotateBtn);
    add(addBtn);
    add(thread);
    add(fifo);
    add(lifo);
    add(menubar);
    add(progress);
    add(consoleScroll);

    // PROPIEDADES
    thread.setSelected(true);

    // AGREGAR POSICIONES DE COMPONENTES
    usersLabel.setBounds(10, 25, 60, 25);
    usersBox.setBounds(80, 25, 100, 25);
    categoryLabel.setBounds(200, 25, 70, 25);
    categoryBox.setBounds(285, 25, 100, 25);
    categoryList.setBounds(10, 60, 500, 60);
    categoryScroll.setBounds(10, 60, 500, 60);
    bnBtn.setBounds(110, 155, 100, 35);
    copyBtn.setBounds(10, 120, 100, 35);
    filterBtn.setBounds(110, 120, 100, 35);
    convertBtn.setBounds(210, 120, 115, 70);
    rotateBtn.setBounds(10, 155, 100, 35);
    addBtn.setBounds(405, 25, 105, 25);
    thread.setBounds(330, 120, 140, 25);
    fifo.setBounds(330, 165, 180, 25);
    lifo.setBounds(330, 140, 165, 30);
    menubar.setBounds(0, 0, 520, 20);
    console.setBounds(10, 230, 498, 180);
    consoleScroll.setBounds(10, 230, 498, 180);
    progress.setBounds(10, 200, 498, 25);

    // EVENTOS
    setAddListener();
    setProcessListeners();
    setConsole();
    changeCategories();
  }
}