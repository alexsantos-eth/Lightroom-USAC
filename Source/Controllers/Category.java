package Source.Controllers;

import Source.Structure.*;
import java.io.Serializable;
import java.awt.*;

public class Category implements Serializable {
  private static final long serialVersionUID = 1L;

  // PROPIEDADES
  public DoublyLinkedList<String> images;
  public String name;
  public Color color;

  public Category(String name) {
    // VALORES INICIALES
    images = new DoublyLinkedList<String>();
    this.name = name;
  }

  // AGREGAR IMAGENES
  public void addImages(String path) {
    images.add(path);
  }

  // BORRAR IMAGENES
  public void deleteImage(String path) {
    images.delete(path);
  }

  // ASIGNAR COLOR
  public void setColor(Color newColor) {
    this.color = newColor;
  }
}