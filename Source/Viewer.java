package Source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class Viewer extends FrameCommon {
  // VARIABLES GLOBALES
  private static final long serialVersionUID = 1L;
  String header = "<html><body><font face='Arial' color='white' size='5' align='center'>Categorias: </font><hr/>";
  JEditorPane categoryPanel;

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

  public Viewer() {
    // CONFIGURAR VENTANA
    setLayout(new GridBagLayout());
    setSize(749, 585);

    // LOCALES
    GridBagConstraints mainC = new GridBagConstraints();
    GridBagConstraints c = new GridBagConstraints();
    LayoutManager flow = new FlowLayout();
    GridLayout grid = new GridLayout(2, 1);
    String src = "../Source/assets/imageBackground.jpg";

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
    JPanel imagePanel = new JPanel();
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

    // BOTON DE ATRAS
    Button goPrevious = new Button("", new Color(240, 240, 240));
    goPrevious.setLayout(new GridLayout(2, 1));
    goPrevious.setPreferredSize(new Dimension(35, 200));
    goPrevious.setBorder(new EmptyBorder(0, 0, 75, 0));

    // ICONO DE ATRAS
    JLabel prevLabel = new JLabel(new ImageIcon("../Source/assets/prev.png"));
    goPrevious.add(prevLabel);

    // BOTON DE SIGUIENTE
    Button goNext = new Button("", new Color(240, 240, 240));
    goNext.setLayout(new GridLayout(2, 1));
    goNext.setPreferredSize(new Dimension(35, 200));
    goNext.setBorder(new EmptyBorder(0, 0, 75, 0));

    // ICONO DE SIGUIENTE
    JLabel nextLabel = new JLabel(new ImageIcon("../Source/assets/next.png"));
    goNext.add(nextLabel);

    // COMPONENTES PRIMITIVOS
    Image image = new Image(src, 480, 400);
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
