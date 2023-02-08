package src.command;

import src.collection.CollectionManager;

public class CollectionBased implements Executable{
    private final CollectionManager collectionManager;

    public CollectionBased(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
    }
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
