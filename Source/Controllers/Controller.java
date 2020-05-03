package Source.Controllers;

import Source.Structure.*;
import Source.Handlers.*;
import java.io.*;

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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // LEER ARCHIVO BIN
  @SuppressWarnings("unchecked")
  public LinkedList<User> readSerializable() {
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
        return (LinkedList<User>) inputStream.readObject();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // CREAR LISTA NUEVA
  private void setData(LinkedList<User> userList, String name) {
    // VERIFICAR SI EXISTEN USUARIOS
    if (userList != null && userList.getSize() > 0) {
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

      // CREAR ARCHIVO
      createSerializable(users);
    }

    // CREAR IMAGENES
    DoublyLinkedList<Category> catList = currentUser.getCategoryList();
    for (int i = 0; i < catList.getSize(); i++) {
      for (int j = 0; j < catList.get(i).images.getSize(); j++) {
        String path = catList.get(i).images.get(i);
        String imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        File tmpFile = new File("tmp/view/" + imageName);

        if (!tmpFile.exists()) {
          ImageHandler convert = new JPEGtoBMPImage(catList.get(i).images.get(j), "tmp/view/");
          try {
            JPEGHandler.runHandler(convert);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  // CREAR IMAGENES
  public static void createImage(String path) {
    String imageName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
    File tmpFile = new File("tmp/view/" + imageName);

    if (!tmpFile.exists()) {
      ImageHandler convert = new JPEGtoBMPImage(path, "tmp/view/");
      try {
        JPEGHandler.runHandler(convert);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // LISTA CON USUARIO
  public Controller(String name) {
    // OBTENER LISTA
    LinkedList<User> userList = readSerializable();

    // ASIGNAR DATOS
    setData(userList, name);
  }

  // LISTA NORMAL
  public Controller() {
    // OBTENER LISTA
    LinkedList<User> userList = readSerializable();

    // ASIGNAR LISTA
    this.users = userList;
  }

  // OBTENER LISTA
  public Boolean getUser(String name) {
    // USUARIO NUEVO
    Boolean newUser = true;
    currentUser = new User(name);

    // RECORRER LISTA DE USUARIOS
    for (int i = 0; i < users.getSize(); i++) {
      // VERIFICAR SI TIENEN EL MISMO NOMBRE

      if (name.equals(users.get(i).name)) {
        // ASIGNAR NUEVO USUARIO
        currentUser = users.get(i);
        newUser = false;
        break;
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

  // OBTENER LISTA DE USUARIOS
  public LinkedList<User> getUsers() {
    return users;
  }
}