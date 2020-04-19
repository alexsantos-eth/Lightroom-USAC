package Source;

import java.io.*;
import java.io.IOException;

public class Controller {
  LinkedList<User> users;
  User currentUser;

  public void createSerializable(Object user) {
    try {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();

      // CONVIERTE EL OBJETO A BYTES
      try (ObjectOutputStream outputStream = new ObjectOutputStream(stream)) {
        outputStream.writeObject(user);
      }

      byte[] bytes = stream.toByteArray();

      // CREA O MODIFICA EL ARCHIVO ANTERIOR
      File archivo = new File("users.bin");
      try (FileOutputStream outputStream = new FileOutputStream(archivo)) {
        outputStream.write(bytes);
      }
    } catch (IOException e) {

    }
  }

  public Object readSerializable() {

    try {

      File archivo = new File("users.bin");
      byte[] bytes = new byte[(int) archivo.length()];

      // INTENTA LEER EL ARCHIVO
      try (FileInputStream fis = new FileInputStream(archivo)) {
        fis.read(bytes);
      }

      ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

      // INTENTA LEER LOS BYTES
      try (ObjectInputStream inputStream = new ObjectInputStream(stream)) {
        Object o = inputStream.readObject();
        return o;
      }
    } catch (IOException | ClassNotFoundException e) {
      return null;
    }

  }

  @SuppressWarnings("unchecked")
  public Controller(String name) {
    LinkedList<User> userList = (LinkedList<User>) readSerializable();
    if (userList.getSize() > 0) {
      users = userList;
      Boolean isNew = getUser(name);

      if (isNew)
        users.add(currentUser);
    } else {
      User nUser = new User(name);
      users = new LinkedList<User>();

      users.add(nUser);
      currentUser = nUser;
    }
  }

  public Boolean getUser(String name) {
    Boolean newUser = true;
    currentUser = new User(name);

    for (int i = 0; i < users.getSize(); i++) {
      if (users.get(i).name == name) {
        currentUser = users.get(i);
        newUser = false;
      }
    }

    return newUser;
  }

  public void saveUser(User user) {
    users.replace(currentUser, user);

    System.out.println(users.getSize());
    currentUser = user;
    createSerializable(users);
  }

  public User getData() {
    return currentUser;
  }
}