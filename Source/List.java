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
}