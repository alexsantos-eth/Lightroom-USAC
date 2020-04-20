package Source;

import java.io.*;
import java.io.IOException;

public class Controller {
  // PROPIEDADES GLOBALES
  LinkedList<User> users;
  User currentUser;

  // CREAR ARCHIVO BIN
  public void createSerializable(Object user) {
    try {
      // ABRIR STREAM
      ByteArrayOutputStream stream = new ByteArrayOutputStream();

      // CONVERTIR A BYTES
      try (ObjectOutputStream outputStream = new ObjectOutputStream(stream)) {
        outputStream.writeObject(user);
      }

      // OBTENER BYTES
      byte[] bytes = stream.toByteArray();

      // CREAR ARCHIVO
      File archivo = new File("users.bin");
      try (FileOutputStream outputStream = new FileOutputStream(archivo)) {
        outputStream.write(bytes);
      }
    } catch (IOException e) {

    }
  }

  // LEER ARCHIVO BIN
  public Object readSerializable() {
    try {
      // OBTENER BYTES DE ARCHIVO
      File archivo = new File("users.bin");
      byte[] bytes = new byte[(int) archivo.length()];

      // LEER EL ARCHIVO
      try (FileInputStream fis = new FileInputStream(archivo)) {
        fis.read(bytes);
      }

      // ABRIR STREAM
      ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

      // LEET BYTES
      try (ObjectInputStream inputStream = new ObjectInputStream(stream)) {
        return inputStream.readObject();
      }
    } catch (IOException | ClassNotFoundException e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Controller(String name) {
    // OBTENER LISTA
    LinkedList<User> userList = (LinkedList<User>) readSerializable();

    // VERIFICAR SI EXISTEN USUARIOS
    if (userList.getSize() > 0) {
      // ASIGNAR LISTA
      users = userList;

      // OBTENER USUARIO O CREAR
      Boolean isNew = getUser(name);

      // SI ES NUEVO AGREGAR A LISTA
      if (isNew)
        users.add(currentUser);
    }

    // SINO CREAR NUEVA LISTA
    else {
      // CREAR USUARIO Y LISTA
      User nUser = new User(name);
      users = new LinkedList<User>();

      // AGREGAR USUARIO A LISTA
      users.add(nUser);
      currentUser = nUser;
    }
  }

  // OBTENER LISTA
  public Boolean getUser(String name) {
    // USUARIO NUEVO
    Boolean newUser = true;
    currentUser = new User(name);

    // RECORRER LISTA DE USUARIOS
    for (int i = 0; i < users.getSize(); i++) {
      // VERIFICAR SI TIENEN EL MISMO NOMBRE
      if (users.get(i).name == name) {
        // ASIGNAR NUEVO USUARIO
        currentUser = users.get(i);
        newUser = false;
      }
    }

    // RETORNAR USUARIO
    return newUser;
  }

  // GUARDAR USUARIO
  public void saveUser(User user) {
    // REMPLAZAR USUARIO
    users.replace(currentUser, user);
    currentUser = user;

    // CREAR BIN
    createSerializable(users);
  }

  // OBTENER USUARIO ACTUAL
  public User getData() {
    return currentUser;
  }
}