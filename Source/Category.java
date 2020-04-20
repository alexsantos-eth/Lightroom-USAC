package Source;

import java.io.Serializable;

public class Category implements Serializable {
  private static final long serialVersionUID = 1L;

  // PROPIEDADES
  public DoublyLinkedList<String> images;
  public String name;

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

}