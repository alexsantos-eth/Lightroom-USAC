package Source;

public class DoublyLinkedList<T> extends EstructuraDeDatos {
  private static final long serialVersionUID = 1L;
  DoubleNode<T> firstNode;
  DoubleNode<T> lastNode;
  Boolean isCircular = false;

  public void makeCircular() {
    isCircular = true;
    lastNode.next = firstNode;
    firstNode.prev = lastNode;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void add(Object item) {
    if (firstNode == null) {
      firstNode = new DoubleNode<T>((T) item);
      lastNode = firstNode;
      if (isCircular) {
        lastNode.next = firstNode;
        firstNode.prev = firstNode;
      }
    } else {
      DoubleNode<T> auxNode = firstNode;

      for (int i = 0; i < this.index; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      lastNode = new DoubleNode<T>((T) item);
      auxNode.next = lastNode;
      lastNode.prev = auxNode;
    }
    this.index++;
  }

  @SuppressWarnings("unchecked")
  public void addBack(Object item) {
    if (firstNode == null) {
      firstNode = new DoubleNode<T>((T) item);
      lastNode = firstNode;
      if (isCircular) {
        lastNode.next = firstNode;
        firstNode.prev = firstNode;
      }
    } else {
      DoubleNode<T> auxNode = lastNode;

      for (int i = 0; i < this.index; i++)
        if (auxNode.prev != null)
          auxNode = auxNode.prev;

      firstNode = new DoubleNode<T>((T) item);
      auxNode.prev = firstNode;
      firstNode.next = auxNode;
    }

    this.index++;

  }

  @Override
  public T peek() {
    return lastNode.value;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T find(Object item) {
    DoubleNode<T> auxNode = firstNode;

    while (auxNode.value != (T) item)
      auxNode = auxNode.next;

    return auxNode.value;
  }

  @Override
  public Object getNext() {
    return null;
  }

  @Override
  public int getSize() {
    return this.index;
  }

  @Override
  public T get(int indexSearch) {
    if (indexSearch < 0) {
      if (!isCircular)
        if (indexSearch < (this.index * -1))
          return null;

      DoubleNode<T> auxNode = lastNode;

      for (int i = 0; i < (indexSearch * -1) - 1; i++)
        if (auxNode.prev != null)
          auxNode = auxNode.prev;

      return auxNode.value;

    } else {
      if (!isCircular)
        if (indexSearch > this.index)
          return null;

      DoubleNode<T> auxNode = firstNode;

      for (int i = 0; i < indexSearch; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      return auxNode.value;
    }
  }

  @Override
  public Object pop() {
    delete(lastNode.value);
    return null;
  }

  @SuppressWarnings("unchecked")
  public int getIndex(Object item) {
    DoubleNode<T> auxNode = firstNode;
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
    DoubleNode<T> auxNode = firstNode;

    if (getIndex(e) == 0)
      firstNode = firstNode.next;
    else if (getIndex(e) != this.index - 1) {
      for (int i = 0; i < this.index - 1; i++) {
        if (auxNode != null) {
          DoubleNode<T> tempNode = auxNode;
          auxNode = auxNode.next;

          if (auxNode.value == (T) e) {
            tempNode.next = tempNode.next.next;
            auxNode.next.prev = tempNode;
          }
        }
      }
    } else {
      for (int i = 0; i < this.index - 2; i++)
        auxNode = auxNode.next;

      lastNode = auxNode;
      lastNode.next = null;
    }

    this.index--;
  }
}