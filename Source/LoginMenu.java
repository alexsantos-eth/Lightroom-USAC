package Source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// EVENTO DE ABRIR EDITOR
class editorListener implements ActionListener {
  public void actionPerformed(ActionEvent actionEvent) {
    System.out.println("Selected: " + actionEvent.getActionCommand());
  }
}

// ABRIR CONVERTIDOR
class converterListener implements ActionListener {
  public void actionPerformed(ActionEvent actionEvent) {
    System.out.println("Selected: " + actionEvent.getActionCommand());
  }
}

public class LoginMenu extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public LoginMenu() {
    // CONFIGURAR VENTANA
    setSize(670, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    // LOCALES
    GridBagConstraints c = new GridBagConstraints();
    ActionListener editorAction = new editorListener();
    ActionListener converterAction = new editorListener();

    // POSICION
    c.insets = new Insets(12, 0, 0, 0);
    int margin = 70;

    // BARRA DE MENU
    JMenuBar menuBar = new JMenuBar();
    JMenu toolsMenu = new JMenu("Herramientas");

    // MENU DE EDITOR
    JMenuItem editorMenu = new JMenuItem("Editor", new ImageIcon("../Source/assets/editor-icon.png"));
    editorMenu.setMnemonic(KeyEvent.VK_E);
    editorMenu.setToolTipText("Ingresar al editor");
    editorMenu.addActionListener(editorAction);
    editorMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));

    // MENU DE CONVERTIDOR
    JMenuItem converterMenu = new JMenuItem("Convertidor", new ImageIcon("../Source/assets/converter-icon.png"));
    converterMenu.setMnemonic(KeyEvent.VK_T);
    converterMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    converterMenu.addActionListener(converterAction);
    converterMenu.setToolTipText("Ingresar al convertidor");

    // BOTON DE INGRESAR
    JPanel btnContainer = new JPanel();
    btnContainer.setLayout(new FlowLayout());

    // PANEL DE LOGIN
    JPanel loginContainer = new JPanel();
    loginContainer.setLayout(new GridLayout(2, 1));
    loginContainer.setPreferredSize(new Dimension(300, 160));

    // PANEL DE MENU
    JPanel menuContainer = new JPanel();
    menuContainer.setLayout(new GridLayout(2, 1));
    menuContainer.setPreferredSize(new Dimension(220, 160));

    // PANEL DE TITULO
    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new GridLayout(2, 1));
    titlePanel.setPreferredSize(new Dimension(220, 80));
    titlePanel.setBorder(new EmptyBorder(0, margin / 2, 0, 0));

    // PANEL DE EDITOR
    JPanel btnMenuPanel = new JPanel();
    btnMenuPanel.setLayout(new FlowLayout());
    btnMenuPanel.setBorder(new EmptyBorder(0, (margin / 2), 0, 0));

    // COMPONENTES PRIMITIVOS
    Input userInput = new Input("BIBLIOTECA (USAC VIEWER)", "Nombre de usuario", 300, 20);
    Label title = new Label("MENU PRINCIPAL");

    // BOTON DE EDITOR
    Button editorBtn = new Button("EDITOR", 220, 50, Globals.grayBlue);
    editorBtn.addActionListener(editorAction);

    // BOTON DE CONVERTIDOR
    Button converterBtn = new Button("CONVERTIDOR", 220 - (margin / 2), 50, Globals.grayBlue);
    converterBtn.addActionListener(converterAction);

    // BOTON DE INGRESAR
    Button login = new Button("INGRESAR", 300, 50);
    login.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        String name = userInput.getTextField().getText();
        Controller userController = new Controller(name);
        new Viewer(name, userController);
      }
    });

    // ASIGNAR PANEL DE BOTONES
    btnMenuPanel.add(converterBtn);

    // ASIGNAR PANEL DE EDITOR
    titlePanel.add(title);
    titlePanel.add(editorBtn);

    // ASIGNAR PANEL DE LOGIN
    btnContainer.add(login);
    loginContainer.add(userInput);
    loginContainer.add(btnContainer);

    // ASIGNAR PANELES DE MENU
    menuContainer.add(titlePanel);
    menuContainer.add(btnMenuPanel);

    // ASIGNAR BARRA DE MENU
    toolsMenu.add(editorMenu);
    toolsMenu.add(converterMenu);
    menuBar.add(toolsMenu);

    // AGREGAR PANELES
    add(loginContainer, c);
    add(menuContainer, c);
    setJMenuBar(menuBar);
  }
}