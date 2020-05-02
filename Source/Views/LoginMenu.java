package Source.Views;

import Source.Utils.*;
import Source.Utils.Button;
import Source.Utils.Label;
import Source.Controllers.*;
import Source.Menus.LoginMenuBar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginMenu extends FrameCommon {
  private static final long serialVersionUID = 1L;

  private void createController(int type, Input userInput) {
    // OBTENER NOMBRE
    String name = userInput.getTextField().getText();

    if (type == 0) {
      // VERIFICAR SI INGRESO SU NOMBRE
      if (name.equals("Nombre de usuario") || name.length() == 0)
        JOptionPane.showMessageDialog(null, "Debes ingresar primero tu nombre de usuario", "Error al autenticar",
            JOptionPane.ERROR_MESSAGE);
      else {
        // CREAR CONTROLADOR
        Controller userController = new Controller(name);
        new Viewer(name, userController);
      }
    } else if (type == 1)
      new Editor();
    else if (type == 2) {
      Controller usersController = new Controller();
      new Converter(usersController);
    }

  }

  public LoginMenu() {
    // CONFIGURAR VENTANA
    setSize(670, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridBagLayout());

    // POSICION
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(12, 0, 0, 0);
    int margin = 70;

    // COMPONENTES PRIMITIVOS
    Input userInput = new Input("BIBLIOTECA (USAC VIEWER)", "Nombre de usuario", 300, 20);
    Label title = new Label("MENU PRINCIPAL");

    // BOTON DE EDITOR
    Button editorBtn = new Button("EDITOR", 220, 50, Globals.grayBlue);

    // BOTON DE CONVERTIDOR
    Button converterBtn = new Button("CONVERTIDOR", 220 - (margin / 2), 50, Globals.grayBlue);

    // BOTON DE INGRESAR
    Button login = new Button("INGRESAR", 300, 50);

    // CREAR VISOR
    ActionListener viewerListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        createController(0, userInput);
      }
    };

    // CREAR EDITOR
    ActionListener editorListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        createController(1, userInput);
      }
    };

    // CREAR CONVERTIDOR
    ActionListener converterListener = new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        createController(2, userInput);
      }
    };

    // BARRA DE MENU
    LoginMenuBar menuBar = new LoginMenuBar();

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

    // BOTON DE INGRESAR
    login.addActionListener(viewerListener);

    // BOTON DE EDITOR
    menuBar.editorMenu.addActionListener(editorListener);
    editorBtn.addActionListener(editorListener);

    // BOTON DE CONVERTIR
    menuBar.converterMenu.addActionListener(converterListener);
    converterBtn.addActionListener(converterListener);

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

    // AGREGAR PANELES
    add(loginContainer, c);
    add(menuContainer, c);
    setJMenuBar(menuBar);
  }
}