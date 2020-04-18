package Source;

public class Node<T> {
  Node<T> next;
  T value;
  LinkedList<T> list;

  public Node(T item) {
    this.value = item;
    list = new LinkedList<T>();
  }

}