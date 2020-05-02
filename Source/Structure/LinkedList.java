package Source.Structure;

public class LinkedList<T> extends EstructuraDeDatos {
  // PROPIEDADES
  private static final long serialVersionUID = 1L;
  Node<T> firstNode;

  // AGREGAR
  @SuppressWarnings("unchecked")
  @Override
  public void add(Object item) {
    // VERIFICAR SI ES EL PRIMERO
    if (firstNode == null)

      // CREAR PRIMERO
      firstNode = new Node<>((T) item);

    // SINO RECORRER
    else {
      // NODO BASE
      Node<T> auxNode = firstNode;

      // RECORRER HASTA EL ULTIMO
      for (int i = 0; i < this.index; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      // CREAR NUEVO NODO
      auxNode.next = new Node<T>((T) item);
    }

    this.index++;
  }

  // BUSCAR NODO
  @SuppressWarnings("unchecked")
  @Override
  public Node<T> find(Object item) {
    // NODO BASE
    Node<T> auxNode = firstNode;

    // BUSCAR POR VALOR
    while (auxNode.value != (T) item)
      auxNode = auxNode.next;

    // RETORNAR NODO
    return auxNode;
  }

  // REMPLAZAR NODO
  @SuppressWarnings("unchecked")
  public void replace(Object item, Object newItem) {
    // BUSCAR NODO
    Node<T> auxNode = find(item);

    // REMPLAZAR VALOR
    auxNode.value = (T) newItem;
  }

  // OBTENER POSICION
  @SuppressWarnings("unchecked")
  public int getIndex(Object item) {
    // NODO BASE
    Node<T> auxNode = firstNode;
    int out = 0;

    // RECORRER HASTA POSICION
    while (auxNode.value != (T) item && auxNode.next != null) {
      auxNode = auxNode.next;
      out++;
    }

    // RETORNAR INDIC
    return out;
  }

  // BORRAR NODO
  @SuppressWarnings("unchecked")
  @Override
  public void delete(Object e) {
    // NODO BASE
    Node<T> auxNode = firstNode;

    // SI ES EL PRIMERO CORRER UNA POSICION
    if (getIndex(e) == 0)
      firstNode = firstNode.next;

    // SINO ES EL ULTIMO
    else if (getIndex(e) != this.index - 1) {
      // RECORRER HASTA EL INDICE
      for (int i = 0; i < this.index - 1; i++) {
        // EVITAR NULL POINTERS
        if (auxNode != null) {
          // NODO TEMPORAL
          Node<T> tempNode = auxNode;
          auxNode = auxNode.next;

          // ELIMINAR REFERENCIA
          if (auxNode.value == (T) e) {
            tempNode.next = tempNode.next.next;
            auxNode = null;
          }
        }
      }
    }

    // SI ES EL ULTIMO
    else {
      // RECORRER HASTA EL PENULTIMO
      for (int i = 0; i < this.index - 2; i++)
        auxNode = auxNode.next;

      // QUITAR REFERENCIA
      auxNode.next = null;
    }

    // REDUCIR DIMENSION
    this.index--;
  }

  // PARA QUE ES ESTE METODO SIN PARAMETROS !!!????
  @Override
  public T getNext() {
    return null;
  }

  // ELIMINAR EL ULTIMO
  @Override
  public T pop() {
    // NODO BASE
    Node<T> auxNode = firstNode;
    int position = 0;

    // RECORRER HASTA EL ULTIMO
    while (position != this.index) {
      auxNode = auxNode.next;
      position++;
    }

    // BORRAR ULTIMO
    delete(auxNode);
    return null;
  }

  // OBTENER EL ULTIMO
  @Override
  public T peek() {
    // NODO BASE
    Node<T> auxNode = firstNode;
    int position = 0;

    // RECORRER HASTA EL ULTIMO
    while (position != this.index) {
      auxNode = auxNode.next;
      position++;
    }

    // RETORNAR VALOR DEL NODO
    return auxNode.value;
  }

  // OBTENER DIMENSION
  @Override
  public int getSize() {
    return this.index;
  }

  // OBTENER NODO
  @Override
  public T get(int index) {
    // AGREGAR LIMITES
    if (index < 0 || index > this.index)
      return null;

    // NODO BASE
    Node<T> auxNode = firstNode;

    // RECORRER HACIA ADELANTE
    for (int i = 0; i < index; i++)
      if (auxNode.next != null)
        auxNode = auxNode.next;

    // RETORNAR VALOR DEL NODO
    return auxNode.value;
  }
}

// NOTA LAS REFERENCIAS VACIAS O NULAS SE LIMPIAN CON EL GARBAGE COLLECTOR