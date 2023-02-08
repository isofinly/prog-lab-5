package src.collection;

import java.util.LinkedHashSet;
import java.util.TreeSet;

public class MyLinkedHashSet extends LinkedHashSet <LabWork> implements StandardCollection, SortableCollection {
    public MyLinkedHashSet() {
        super();
    }

    @Override
    public void getInfo() {
        System.out.println("\u001B[32m Class: \u001B[0m" + this.getClass().getSimpleName());
        System.out.println("\u001B[32m Type: \u001B[0m" + this.getClass().arrayType().descriptorString());
        System.out.println("\u001B[32m Elements: \u001B[0m" + this);
        System.out.println("\u001B[32m Size: \u001B[0m" + this.size());
    }

    @Override
    public void sort() {
        // TODO
        var treeSet = new TreeSet <LabWork>(this);
        this.clear();
        this.addAll(treeSet);
    }

    @Override
    public void clear() {
        if (this.size() == 0) {
            System.out.println("\u001B[31m Collection is already empty!");
            return;
        }
        this.clear();
    }

    public void removeById(int id) {
        for (LabWork labWork : this) {
            if (labWork.getId() == id) {
                this.remove(labWork);
                return;
            }
        }
        System.out.println("\u001B[31m No element with such id!");
    }

}
