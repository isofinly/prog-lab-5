package src.collection;

public record CollectionManager(MyLinkedHashSet collection) {

    public void clear() {
        collection.clear();
    }
    public void exit() {
        System.exit(0);
    }
    public void getInfo() {
        collection.getInfo();
    }

    public void show() {
        collection.forEach(System.out::println);
    }

    public void removeById(int id) {
        collection.removeById(id);
    }

}
