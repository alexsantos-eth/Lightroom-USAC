package Source;

public class User {
  // PROPIEDADES
  public DoublyLinkedList<Category> categories;
  public String name;

  public User(String name) {
    this.name = name;
    categories = new DoublyLinkedList<Category>();
  }

  // AGREGAR CATEGORIA
  public void addCategory(Category newCat) {
    categories.add(newCat);
  }

  // REMOVER CATEGORIA
  public void removeCategory(Category remCat) {
    categories.delete(remCat);
  }

  // OBTENER NOMBRE
  public String getName() {
    return this.name;
  }

  // OBTENER LISTA DE CATEGORIAS
  public DoublyLinkedList<Category> getCategoryList() {
    return this.categories;
  }

  public Category getLastCategory() {
    return categories.peek();
  }

  // OBTENER CATEGORIA
  public Category getCategory(String catName) {
    // VALOR POR DEFECTO
    Category out = null;

    // RECORRER LISTA
    for (int i = 0; i < getCategoryList().getSize(); i++) {
      // ENCONTRAR POR NOMBRE Y ASIGNAR
      if (getCategoryList().get(i).name == catName)
        out = getCategoryList().get(i);
    }
    return out;
  }

  // ELIMINAR LA ULTIMA CATEGORIA
  public void popCategory() {
    getCategoryList().pop();
  }
}