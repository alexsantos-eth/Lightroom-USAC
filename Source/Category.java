package Source;

public class Category {
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