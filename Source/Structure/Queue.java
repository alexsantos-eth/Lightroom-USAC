package Source.Structure;

public class Queue<T> {
  // NODOS Y LONGITUD
  Node<T> firstNode, lastNode;
  int index;

  // CONSTRUCTOR
  public Queue() {
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
    Node<T> auxNode = firstNode;

    // VERIFICAR SI ESTA VACIA
    if (isEmpty())
      return null;

    // SINO QUITAR REFERENCIA
    else {
      firstNode = firstNode.next;

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