package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Converter extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public Converter() {
    // PANELS
    JPanel categoryPanel = new JPanel();
    categoryPanel.setLayout(new FlowLayout());
    categoryPanel.setPreferredSize(new Dimension(400, 70));

    // COMPONENTES
    Label userLabel = new Label("Usuario:");
    Label catLabel = new Label("Categorias");

    // COMBO BOX
    JComboBox<String> userComboBox = new JComboBox<String>();
    JComboBox<String> catComboBox = new JComboBox<String>();

    // AGREGAR
    categoryPanel.add(userLabel);
    categoryPanel.add(userComboBox);
    categoryPanel.add(catLabel);
    categoryPanel.add(catComboBox);

    // ASIGNAR
    add(categoryPanel);
  }
}