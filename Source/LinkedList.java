package Source;

public class LinkedList<T> extends EstructuraDeDatos {
  private static final long serialVersionUID = 1L;
  Node<T> firstNode;

  @SuppressWarnings("unchecked")
  @Override
  public void add(Object item) {
    if (firstNode == null)
      firstNode = new Node<>((T) item);
    else {
      Node<T> auxNode = firstNode;

      for (int i = 0; i < this.index; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      auxNode.next = new Node<T>((T) item);
    }

    this.index++;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node<T> find(Object item) {
    Node<T> auxNode = firstNode;

    while (auxNode.value != (T) item)
      auxNode = auxNode.next;

    return auxNode;
  }

  @SuppressWarnings("unchecked")
  public void replace(Object item, Object newItem) {
    Node<T> auxNode = find(item);
    auxNode.value = (T) newItem;
  }

  @SuppressWarnings("unchecked")
  public int getIndex(Object item) {
    Node<T> auxNode = firstNode;
    int out = 0;

    while (auxNode.value != (T) item && auxNode.next != null) {
      auxNode = auxNode.next;
      out++;
    }

    return out;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void delete(Object e) {
    Node<T> auxNode = firstNode;

    if (getIndex(e) == 0)
      firstNode = firstNode.next;
    else if (getIndex(e) != this.index - 1) {
      for (int i = 0; i < this.index - 1; i++) {
        if (auxNode != null) {
          Node<T> tempNode = auxNode;
          auxNode = auxNode.next;

          if (auxNode.value == (T) e) {
            tempNode.next = tempNode.next.next;
            auxNode = null;
          }
        }
      }
    } else {
      for (int i = 0; i < this.index - 2; i++)
        auxNode = auxNode.next;

      auxNode.next = null;
    }

    this.index--;
  }

  @Override
  public T getNext() {
    return null;
  }

  @Override
  public T pop() {
    Node<T> auxNode = firstNode;
    int position = 0;

    while (position != this.index) {
      auxNode = auxNode.next;
      position++;
    }

    delete(auxNode);
    return null;
  }

  @Override
  public T peek() {
    Node<T> auxNode = firstNode;
    int position = 0;

    while (position != this.index) {
      auxNode = auxNode.next;
      position++;
    }

    return auxNode.value;
  }

  @Override
  public int getSize() {
    return this.index;
  }

  @Override
  public T get(int index) {
    if (index < 0 || index > this.index)
      return null;

    Node<T> auxNode = firstNode;

    for (int i = 0; i < index; i++)
      if (auxNode.next != null)
        auxNode = auxNode.next;

    return auxNode.value;
  }

}
