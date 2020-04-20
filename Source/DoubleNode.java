package Source;

import java.io.Serializable;

public class DoubleNode<T> implements Serializable {
  private static final long serialVersionUID = 1L;

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