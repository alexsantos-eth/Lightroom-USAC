package Source;

public class Category {
  public String name;
  public DoublyLinkedList<String> images;

  public Category(String name) {
    images = new DoublyLinkedList<String>();
    this.name = name;
  }

  public void addImages(String path) {
    images.add(path);
  }

}