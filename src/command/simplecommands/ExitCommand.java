package src.command.simplecommands;

import src.command.Executable;

public record ExitCommand() implements Executable {
    @Override
    public void execute() {
        System.exit(0);
    }
}
