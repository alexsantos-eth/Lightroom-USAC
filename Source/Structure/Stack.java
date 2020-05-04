package Source.Structure;

public class Stack<T> {
  // NODOS Y LONGITUD
  Node<T> firstNode, lastNode;
  int index;

  // CONSTRUCTOR
  public Stack() {
    index = 0;
  }

  // AGREGAR
  @SuppressWarnings("unchecked")
  public void push(Object item) {
    // NUEVO NODO
    Node<T> newNode = new Node<T>((T) item);

    // VERIFICAR
    if (index == 0)
      firstNode = newNode;
    else
      lastNode.next = newNode;

    // AUMENTAR Y ASIGNAR
    lastNode = newNode;
    index++;
  }

  // ESTA VACIA
  public boolean isEmpty() {
    return firstNode == null;
  }

  public T pop() {
    // NODO AUXILIAR
    Node<T> auxNode = lastNode;

    // VERIFICAR SI ESTA VACIA
    if (isEmpty())
      return null;

    // SINO QUITAR REFERENCIA
    else {
      Node<T> iterNode = firstNode;

      for (int i = 0; i < index - 2; i++)
        iterNode = iterNode.next;

      lastNode = iterNode;

      // DISMINUIR LONGITUD
      index--;

      // SI ESTA VACIA ELIMINAR REFERENCIA
      if (index == 0)
        lastNode = null;

      // RETORNAR VALOR ELIMINADO
      return auxNode.value;
    }

  }

  // OBTENER LONGITUD
  public int getSize() {
    return index;
  }
}