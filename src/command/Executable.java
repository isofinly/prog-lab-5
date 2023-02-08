package src.command;

public interface Executable {
    default void execute() {
        System.out.println("\u001B[31m This command is not implemented yet. \u001B[32m help to get available commands.");
    }
}
