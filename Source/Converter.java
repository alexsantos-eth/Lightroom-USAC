package Source;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Converter extends FrameCommon {
  private static final long serialVersionUID = 1L;

  public Converter(Controller usersControllers) {
    // LISTAS
    LinkedList<User> users = usersControllers.getUsers();

    // PANELS
    JPanel categoryPanel = new JPanel();
    categoryPanel.setLayout(new FlowLayout());
    categoryPanel.setPreferredSize(new Dimension(400, 70));

    // COMPONENTES
    Label userLabel = new Label("Usuario:");
    Label catLabel = new Label("Categorias");
    Button addCatBtn = new Button("Agregar", 130, 35);
    addCatBtn.setLabelFont(addCatBtn.btnText.getFont().deriveFont(13f));

    // COMBO BOX
    JComboBox<String> userComboBox = new JComboBox<String>();
    for (int i = 0; i < users.getSize(); i++)
      userComboBox.addItem(users.get(i).name);

    JComboBox<String> catComboBox = new JComboBox<String>();
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

    // AGREGAR
    categoryPanel.add(userLabel);
    categoryPanel.add(userComboBox);
    categoryPanel.add(catLabel);
    categoryPanel.add(catComboBox);
    categoryPanel.add(addCatBtn);

    // ASIGNAR
    add(categoryPanel);
  }
}