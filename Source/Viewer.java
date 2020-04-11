package Source;

import java.awt.*;
import javax.swing.*;

public class Viewer extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public Viewer() {
    setLayout(new GridBagLayout());
    setResizable(true);
    setSize(1000, 600);

    // LOCALES
    LayoutManager flow = new FlowLayout();
    String src = "../Source/assets/icon.jpg";

    // COMPONENTES
    JPanel viewerPanel = new JPanel();
    viewerPanel.setLayout(new GridLayout(2, 2));
    viewerPanel.setBackground(Theme.red);
    viewerPanel.setSize(100, 100);

    JPanel fileNamePanel = new JPanel();
    fileNamePanel.setLayout(flow);

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(flow);

    JPanel image = new Image(src, 200, 200);

    // PRIMITIVOS
    Label fileName = new Label(src);
    Button openImage = new Button("Abrir imagen");
    Button addCategory = new Button("Agregar categoria");
    Button deleteImage = new Button("Eliminar imagen", Theme.red);
    Button removeCategory = new Button("Eliminar categoria", Theme.red);

    fileNamePanel.add(fileName);

    btnPanel.add(openImage);
    btnPanel.add(addCategory);
    btnPanel.add(deleteImage);
    btnPanel.add(removeCategory);

    viewerPanel.add(fileNamePanel);
    viewerPanel.add(image);

    add(viewerPanel);
    add(btnPanel);
  }

}
