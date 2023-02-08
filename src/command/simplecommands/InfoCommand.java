package src.command.simplecommands;

import src.collection.CollectionManager;
import src.command.CollectionBased;

public final class InfoCommand extends CollectionBased {

    public InfoCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        getCollectionManager().getInfo();
    }
}
