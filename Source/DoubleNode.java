package Source;

public class DoubleNode<T> {
  // PROPIEDADES DE NODO DOBLE
  DoublyLinkedList<T> list;
  DoubleNode<T> next;
  DoubleNode<T> prev;
  T value;

  // VALORES INICIALES
  public DoubleNode(T item) {
    this.value = item;
    list = new DoublyLinkedList<T>();
  }
}