package Source.Structure;

public class DoublyLinkedList<T> extends EstructuraDeDatos {
  // PROPIEDADES
  private static final long serialVersionUID = 1L;
  Boolean isCircular = false;
  DoubleNode<T> firstNode;
  DoubleNode<T> lastNode;

  // HACER CIRCULAR
  public void makeCircular() {
    isCircular = true;
    lastNode.next = firstNode;
    firstNode.prev = lastNode;
  }

  // AGREGAR OBJETO
  @SuppressWarnings("unchecked")
  @Override
  public void add(Object item) {
    // VERIFICAR SI ES EL PRIMERO
    if (firstNode == null) {
      // CREAR NUEVO NODO
      firstNode = new DoubleNode<T>((T) item);

      // EL PRIMERO TAMBIEN ES EL ULTIMO
      lastNode = firstNode;

      // SI ES CIRCULAR REFERENCIARLOS
      if (isCircular) {
        lastNode.next = firstNode;
        firstNode.prev = firstNode;
      }
    }

    // SINO RECORRER NODOS
    else {
      // NODO BASE
      DoubleNode<T> auxNode = firstNode;

      // RECORRER HASTA EL ULTIMO
      for (int i = 0; i < this.index; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      // ASIGNAR COMO EL ULTIMO
      lastNode = new DoubleNode<T>((T) item);
      auxNode.next = lastNode;
      lastNode.prev = auxNode;
    }

    // AUMENTAR DIMENSION
    this.index++;
  }

  // AGREGAR HACIA ATRAS
  @SuppressWarnings("unchecked")
  public void addBack(Object item) {
    // VERIFICAR SI ES EL PRIMERO
    if (firstNode == null) {
      // CREAR NUEVO NODO
      firstNode = new DoubleNode<T>((T) item);

      // EL ULTIMO TAMBIEN ES EL PRIMERO
      lastNode = firstNode;

      // SI ES CIRCULAR REFERENCIAR
      if (isCircular) {
        lastNode.next = firstNode;
        firstNode.prev = firstNode;
      }
    }

    // SINO RECORRER NODOS
    else {
      // NODO BASE
      DoubleNode<T> auxNode = lastNode;

      // RECORRER HACIA ATRAS
      for (int i = 0; i < this.index; i++)
        if (auxNode.prev != null)
          auxNode = auxNode.prev;

      // ASIGNAR COMO NODO PRIMERO
      firstNode = new DoubleNode<T>((T) item);
      auxNode.prev = firstNode;
      firstNode.next = auxNode;
    }

    // AUMENTAR DIMENSION
    this.index++;
  }

  // OBTENER ULTIMO NODO
  @Override
  public T peek() {
    return lastNode.value;
  }

  // BUSCAR NODO
  @SuppressWarnings("unchecked")
  @Override
  public DoubleNode<T> find(Object item) {
    // NODO BASE
    DoubleNode<T> auxNode = firstNode;

    // BUSCAR HASTA QUE COINCIDA EL VALOR
    while (auxNode.value != (T) item)
      auxNode = auxNode.next;

    // RETORNAR VALOR DEL NODO
    return auxNode;
  }

  // PARA QUE ES ESTE METODO SIN PARAMETROS !!!????
  @Override
  public Object getNext() {
    return null;
  }

  // OBTENER DIMENSION
  @Override
  public int getSize() {
    return this.index;
  }

  // OBTENER VALOR DE NODO
  @Override
  public T get(int indexSearch) {
    // RECORRER HACIA ATRAS
    if (indexSearch < 0) {
      if (!isCircular)
        if (indexSearch < (this.index * -1))
          return null;

      // NODO BASE
      DoubleNode<T> auxNode = lastNode;

      // RECORRER HACIA ATRAS HASTA EL PRIMERO
      for (int i = 0; i < (indexSearch * -1) - 1; i++)
        if (auxNode.prev != null)
          auxNode = auxNode.prev;

      // RETORNAR NODO
      return auxNode.value;
    }

    // RECORRER HACIA ADELANTE
    else {
      // LIMITES SI NO ES CIRCULAR
      if (!isCircular)
        if (indexSearch > this.index)
          return null;

      // NODO BASE
      DoubleNode<T> auxNode = firstNode;

      // RECORRER HACIA ADELANTE
      for (int i = 0; i < indexSearch; i++)
        if (auxNode.next != null)
          auxNode = auxNode.next;

      // RETORNAR VALOR DEL NODO
      return auxNode.value;
    }
  }

  // BORRAR EL ULTIMO
  @Override
  public Object pop() {
    // BORRAR NODO
    delete(lastNode.value);
    return null;
  }

  // OBTENER INDICE DE NODO
  @SuppressWarnings("unchecked")
  public int getIndex(Object item) {
    // NODO BASE
    DoubleNode<T> auxNode = firstNode;
    int out = 0;

    // RECORRER HACIA ADELANTE
    while (auxNode.value != (T) item && auxNode.next != null) {
      auxNode = auxNode.next;
      out++;
    }

    // RETORNAR INDICE
    return out;
  }

  // BORRAR NODO
  @SuppressWarnings("unchecked")
  @Override
  public void delete(Object e) {
    // BORRAR NODO
    DoubleNode<T> auxNode = firstNode;

    // SI ES EL PRIMERO SOLO CORRER UNA POSICION
    if (getIndex(e) == 0)
      firstNode = firstNode.next;

    // SINO ES EL ULTIMO
    else if (getIndex(e) != this.index - 1) {
      // RECORRER HASTA EL ULTIMO
      for (int i = 0; i < this.index - 1; i++) {
        // VERIFICAR NULL POINTERS
        if (auxNode != null) {
          // NODO TEMPORAL
          DoubleNode<T> tempNode = auxNode;
          auxNode = auxNode.next;

          // ENCONTRAR POR VALOR
          if (auxNode.value == (T) e) {
            // ELIMINAR REFERENCIA
            tempNode.next = tempNode.next.next;
            auxNode.next.prev = tempNode;
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
      lastNode = auxNode;
      lastNode.next = null;
    }

    this.index--;
  }
}

// NOTA LAS REFERENCIAS VACIAS O NULAS SE LIMPIAN CON EL GARBAGE COLLECTOR