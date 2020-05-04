package Source.Views;

import Source.Handlers.*;
import Source.Menus.ConverterMenu;
import Source.Utils.*;
import Source.Structure.*;
import Source.Controllers.*;

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
  private JRadioButton lifo;
  private JRadioButton fifo;
  private ConverterMenu menubar;
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
    int mode = thread.isSelected() ? 0 : lifo.isSelected() ? 1 : fifo.isSelected() ? 2 : -1;
    Queue<ImageHandler> hQueue = new Queue<ImageHandler>();
    Stack<ImageHandler> hStack = new Stack<ImageHandler>();

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

        else if (mode == 1)
          // ASIGNAR PILA
          hStack.push(handler);

        else if (mode == 2)
          // ASIGNAR COLA
          hQueue.push(handler);
      }

      // EJECUTAR COLAS/PILAS
      if (mode == 1)
        executeStack(hStack);

      else if (mode == 2)
        executeQueue(hQueue);

      // REINICIAR VALORES
      model.removeAllElements();
      categoryNum = 0;
    }

  }

  // COLAS
  private void executeQueue(Queue<ImageHandler> hQueue) {
    // LONGITUD
    for (int i = 0, length = hQueue.getSize(); i < length; i++) {
      try {
        // CORRER HANDLER
        JPEGHandler.runHandler(hQueue.pop());

        // AUMENTAR BARRA
        progress.setValue(progress.getValue() + 1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // PILAS
  private void executeStack(Stack<ImageHandler> hStack) {
    // LONGITUD
    for (int i = 0, length = hStack.getSize(); i < length; i++) {
      try {
        // CORRER HANDLER
        JPEGHandler.runHandler(hStack.pop());

        // AUMENTAR BARRA
        progress.setValue(progress.getValue() + 1);
      } catch (Exception e) {
        e.printStackTrace();
      }
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
    menubar = new ConverterMenu();

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
    lifo = new JRadioButton("Ejecutar secuencial LIFO");
    fifo = new JRadioButton("Ejecutar en Secualcial FIFO");

    // AGREGAR SUB COMPONENTES
    radios.add(thread);
    radios.add(lifo);
    radios.add(fifo);

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
    setSize(525, 445);
    setLayout(null);

    // MENU
    setJMenuBar(menubar);

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
    add(lifo);
    add(fifo);
    add(progress);
    add(consoleScroll);

    // PROPIEDADES
    thread.setSelected(true);

    // AGREGAR POSICIONES DE COMPONENTES
    usersLabel.setBounds(10, 10, 60, 25);
    usersBox.setBounds(80, 10, 100, 25);
    categoryLabel.setBounds(200, 10, 70, 25);
    categoryBox.setBounds(285, 10, 100, 25);
    categoryList.setBounds(10, 45, 500, 60);
    categoryScroll.setBounds(10, 45, 500, 60);
    bnBtn.setBounds(110, 140, 100, 35);
    copyBtn.setBounds(10, 105, 100, 35);
    filterBtn.setBounds(110, 105, 100, 35);
    convertBtn.setBounds(210, 105, 115, 70);
    rotateBtn.setBounds(10, 140, 100, 35);
    addBtn.setBounds(405, 10, 105, 25);
    thread.setBounds(330, 105, 140, 25);
    fifo.setBounds(330, 150, 180, 25);
    lifo.setBounds(330, 125, 165, 30);
    console.setBounds(10, 215, 498, 180);
    consoleScroll.setBounds(10, 205, 498, 180);
    progress.setBounds(10, 185, 498, 25);

    // EVENTOS
    setAddListener();
    setProcessListeners();
    setConsole();
    changeCategories();
  }
}