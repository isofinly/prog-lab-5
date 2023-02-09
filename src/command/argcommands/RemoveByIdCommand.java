package src.command.argcommands;

import src.collection.CollectionManager;
import src.command.CollectionBased;

public final class RemoveByIdCommand extends CollectionBased {
    private int id;
    public RemoveByIdCommand(CollectionManager collectionManager, String[] args){
        super(collectionManager);
        if (args.length == 0) {
            throw new IllegalArgumentException("\u001B[31m Argument is required");
        }
        id = Integer.parseInt(args[0]);
    }
    @Override
    public void execute(){
        getCollectionManager().removeById(id);
    }
}
