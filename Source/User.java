package Source;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;

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
      if (catName.equals(getCategoryList().get(i).name))
        out = getCategoryList().get(i);
    }
    return out;
  }

  // OBTENER POSICION DE LA CATEGORIA
  public int getCategoryIndex(String catName) {
    // VALOR POR DEFECTO
    int out = 0;

    for (int i = 0; i < getCategoryList().getSize(); i++)
      // ENCONTRAR POR NOMBRE Y ASIGNAR
      if (catName.equals(getCategoryList().get(i).name))
        out = i;

    // RETORNAR INDICE
    return out;
  }

  // VERIFICAR SI SE PUEDE AGREGAR CATEGORIA
  public Boolean verifyCategory(String catName) {
    // VALOR POR DEFECTO
    Boolean out = true;

    // RECORRER LISTA
    for (int i = 0; i < getCategoryList().getSize(); i++)
      // ENCONTRAR POR NOMBRE Y ASIGNAR
      if (catName.equals(getCategoryList().get(i).name))
        out = false;

    // VERIFICAR LONGITUD
    if (catName != null) {
      if (catName.length() == 0)
        out = false;
    } else
      out = false;

    // RETORNAR SALIDA
    return out;
  }

  // ELIMINAR LA ULTIMA CATEGORIA
  public void popCategory() {
    getCategoryList().pop();
  }
}