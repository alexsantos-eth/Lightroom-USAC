package Source;

public class Node<T> {
  // PROPIEDADES DE NODO SIMPLRE
  LinkedList<T> list;
  Node<T> next;
  T value;

  // VALORES SIMPLES
  public Node(T item) {
    this.value = item;
    list = new LinkedList<T>();
  }
}