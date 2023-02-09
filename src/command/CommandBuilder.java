package src.command;

import src.collection.CollectionManager;
import src.command.argcommands.*;
import src.command.simplecommands.*;

public class CommandBuilder {
    private CollectionManager collectionManager;

    public CommandBuilder(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Executable build(String commandName, String[] args) {
        return switch (commandName) {
            case "help" -> new HelpCommand();
            case "info" -> new InfoCommand(collectionManager);
            case "show" -> new ShowCommand(collectionManager);
//            case "add" -> new AddCommand(collectionManager, args);
//            case "update" -> new UpdateCommand(collectionManager, args);
            case "remove_by_id" -> new RemoveByIdCommand(collectionManager, args);
            case "clear" -> new ClearCommand(collectionManager);
//            case "save" -> new SaveCommand(collectionManager);
//            case "execute_script" -> new ExecuteScriptCommand(collectionManager, args);
            case "exit" -> new ExitCommand();
//            case "add_if_max" -> new AddIfMaxCommand(collectionManager, args);
//            case "add_if_min" -> new AddIfMinCommand(collectionManager, args);
//            case "remove_lower" -> new RemoveLowerCommand(collectionManager, args);
//            case "max_by_creation_date" -> new MaxByCreationDateCommand(collectionManager);
//            case "group_counting_by_id" -> new GroupCountingByIdCommand(collectionManager);
//            case "filter_greater_than_personal_qualities_maximum" -> new FilterGreaterThanPersonalQualitiesMaximumCommand(collectionManager);
            default -> new UnknownCommand();
        };
    }

    public Executable build(String line) {
        String[] args = line.split(" ");
        String commandName = args[0];
        String[] commandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, commandArgs, 0, args.length - 1);
        return this.build(commandName, commandArgs);

    }
}
