package src.command.simplecommands;

import src.collection.CollectionManager;
import src.command.CollectionBased;

public final class ClearCommand extends CollectionBased {
    public ClearCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }
    @Override
    public void execute() {
        getCollectionManager().clear();
    }
}
