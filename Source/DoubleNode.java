package Source;

public class DoubleNode<T> {
  DoubleNode<T> next;
  DoubleNode<T> prev;
  DoublyLinkedList<T> list;
  T value;

  public DoubleNode(T item) {
    this.value = item;
    list = new DoublyLinkedList<T>();
  }
}