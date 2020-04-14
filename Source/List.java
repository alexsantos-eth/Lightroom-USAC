package Source;

public class List {
  String[] list;

  public List() {
    list = new String[0];
  }

  public void add(String item) {
    String[] copy = new String[list.length + 1];
    for (int i = 0; i < list.length; i++) {
      copy[i] = list[i];
    }
    copy[copy.length - 1] = item;
    list = copy;
  }

  public void remove(int index) {
    String[] temp = new String[list.length > 0 ? list.length - 1 : 0];

    for (int i = 0; i < list.length; i++) {
      if (i + 1 < list.length)
        temp[i] = list[i >= index ? i + 1 : i];
    }

    list = temp;
  }

  public String get(int index) {
    return list[index];
  }

  public int size() {
    return list.length;
  }
}