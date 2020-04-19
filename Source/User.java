package Source;

public class User {
  public String name;
  public DoublyLinkedList<Category> categories;

  public User(String name) {
    this.name = name;
    categories = new DoublyLinkedList<Category>();
  }

  public void addCategory(Category newCat) {
    categories.add(newCat);
  }

  public void removeCategory(Category remCat) {
    categories.delete(remCat);
  }

  public String getName() {
    return this.name;
  }

  public DoublyLinkedList<Category> getCategoryList() {
    return this.categories;
  }

  public Category getCategory(String catName) {
    Category out = null;
    for (int i = 0; i < getCategoryList().getSize(); i++) {
      if (getCategoryList().get(i).name == catName)
        out = getCategoryList().get(i);
    }
    return out;
  }
}