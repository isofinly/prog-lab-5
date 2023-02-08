package src.command.simplecommands;

import src.collection.CollectionManager;
import src.command.CollectionBased;

public final class ShowCommand extends CollectionBased {
    public ShowCommand(CollectionManager manager) {
        super(manager);
    }

    @Override
    public void execute() {
        getCollectionManager().show();
    }
}
