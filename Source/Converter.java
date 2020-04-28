package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Converter extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public Converter(Controller usersControllers) {
    setLayout(new BoxLayout(this.getContentPane(), 3));
    setSize(750, 600);

    // LISTAS
    LinkedList<User> users = usersControllers.getUsers();

    // PANELS
    JPanel progressPanel = new JPanel();
    progressPanel.setLayout(new FlowLayout());

    JPanel categoryPanel = new JPanel();
    categoryPanel.setLayout(new FlowLayout());
    categoryPanel.setPreferredSize(new Dimension(400, 70));

    JPanel processSelection = new JPanel();
    processSelection.setLayout(new FlowLayout());

    JPanel btnsPanel = new JPanel();
    btnsPanel.setLayout(new FlowLayout());

    JEditorPane processPane = new JEditorPane();
    processPane.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(processPane);
    scrollPane.setPreferredSize(new Dimension(400, 100));
    scrollPane.setBorder(new EmptyBorder(0, 50, 0, 50));
    scrollPane.setViewportBorder(null);

    // BOTON DE COPIAR
    Button copyBtn = new Button("Copiar", 125, 50, new Color(80, 80, 80));
    copyBtn.setLabelFont(copyBtn.getFont().deriveFont(13f).deriveFont(Font.BOLD));

    // BOTON DE CONVERTIR
    Button reverseBtn = new Button("Convertir", 125, 50, new Color(80, 80, 80));
    reverseBtn.setLabelFont(reverseBtn.getFont().deriveFont(13f).deriveFont(Font.BOLD));

    // BOTON DE FILTROS
    Button colorsBtn = new Button("Filtros", 125, 50, new Color(80, 80, 80));
    colorsBtn.setLabelFont(colorsBtn.getFont().deriveFont(13f).deriveFont(Font.BOLD));

    // BOTON DE ROTAR
    Button rotationBtn = new Button("Rotar", 125, 50, new Color(80, 80, 80));
    rotationBtn.setLabelFont(rotationBtn.getFont().deriveFont(13f).deriveFont(Font.BOLD));

    // BOTON DE BLANCO Y NEGRO
    Button bnBtn = new Button("B/N", 125, 50, new Color(80, 80, 80));
    bnBtn.setLabelFont(bnBtn.getFont().deriveFont(15f).deriveFont(Font.BOLD));

    // COMPONENTES
    Label userLabel = new Label("Usuario:");
    Label catLabel = new Label("   Categorias:");
    Button addCatBtn = new Button("Agregar", 130, 35);
    addCatBtn.setLabelFont(addCatBtn.btnText.getFont().deriveFont(13f));

    // RADIO
    ButtonGroup radios = new ButtonGroup();
    JRadioButton multiBtn = new JRadioButton("Ejecutar Paralelo");
    multiBtn.setFont(multiBtn.getFont().deriveFont(20f));

    JRadioButton lifoBtn = new JRadioButton("Secuencial LIFO");
    lifoBtn.setFont(lifoBtn.getFont().deriveFont(20f));

    JRadioButton fifoBtn = new JRadioButton("Secualcial FIFO");
    fifoBtn.setFont(fifoBtn.getFont().deriveFont(20f));

    radios.add(multiBtn);
    radios.add(lifoBtn);
    radios.add(fifoBtn);

    processSelection.add(multiBtn);
    processSelection.add(lifoBtn);
    processSelection.add(fifoBtn);

    // COMBO BOX
    JComboBox<String> userComboBox = new JComboBox<String>();
    userComboBox.setFont(userComboBox.getFont().deriveFont(15f));
    for (int i = 0; i < users.getSize(); i++)
      userComboBox.addItem(users.get(i).name);

    JComboBox<String> catComboBox = new JComboBox<String>();
    catComboBox.setFont(catComboBox.getFont().deriveFont(15f));
    for (int i = 0; i < users.get(0).getCategoryList().getSize(); i++)
      catComboBox.addItem(users.get(0).getCategoryList().get(i).name);

    // EVENTOS
    userComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        int userIndex = userComboBox.getSelectedIndex();
        catComboBox.removeAllItems();

        for (int i = 0; i < users.get(userIndex).getCategoryList().getSize(); i++)
          catComboBox.addItem(users.get(userIndex).getCategoryList().get(i).name);
      }
    });

    // CONSOLE
    JTextArea textArea = new JTextArea();
    JScrollPane consoleScroll = new JScrollPane(textArea);
    textArea.setForeground(Color.white);
    textArea.setEditable(false);
    textArea.setBorder(new EmptyBorder(10, 50, 10, 50));
    textArea.setBackground(new Color(50, 50, 50));

    // System.setOut(new PrintStream(new OutputStream() {
    // @Override
    // public void write(int b) throws IOException {
    // textArea.append(String.valueOf((char) b));
    // }
    // }));

    // AGREGAR
    categoryPanel.add(userLabel);
    categoryPanel.add(userComboBox);
    categoryPanel.add(catLabel);
    categoryPanel.add(catComboBox);
    categoryPanel.add(addCatBtn);

    btnsPanel.add(copyBtn);
    btnsPanel.add(reverseBtn);
    btnsPanel.add(colorsBtn);
    btnsPanel.add(rotationBtn);
    btnsPanel.add(bnBtn);

    // ASIGNAR
    add(categoryPanel);
    add(scrollPane);
    add(btnsPanel);
    add(processSelection);
    add(progressPanel);
    add(consoleScroll);
  }
}