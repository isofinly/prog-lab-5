package src.command.simplecommands;

import src.command.Executable;

import java.util.HashMap;

public record HelpCommand() implements Executable {
    @Override
    public void execute(){
        HashMap <String, String> manual = new HashMap <>();
        manual.put("\u001B[32m help: \u001B[0m", "output help for available commands");
        manual.put("\u001B[32m info: \u001B[0m", "output information about the collection (type, initialization date, number of items, etc.) to the standard output stream.");
        manual.put("\u001B[32m show: \u001B[0m", "output to the standard output stream all the elements of the collection in a string representation");
        manual.put("\u001B[32m add {element}: \u001B[0m", "add a new item to the collection");
        manual.put("\u001B[32m update id {element}: \u001B[0m", "update the value of a collection item whose id is equal to the specified one");
        manual.put("\u001B[32m remove_by_id id: \u001B[0m", "delete an item from the collection by its id");
        manual.put("\u001B[32m clear: \u001B[0m", "clear the collection");
        manual.put("\u001B[32m save: \u001B[0m", "save the collection to a file");
        manual.put("\u001B[32m execute_script file_name: \u001B[0m", "read and execute the script from the specified file.");
        manual.put("\u001B[32m exit: \u001B[0m", "terminate the program (without saving to a file)");
        manual.put("\u001B[32m add_if_max {element}: \u001B[0m", "add a new item to the collection if its value is greater than that of the largest item in this collection");
        manual.put("\u001B[32m add_if_min {element}: \u001B[0m", "add a new item to the collection if its value is less than that of the smallest item in this collection");
        manual.put("\u001B[32m remove_lower {element}: \u001B[0m", "remove all items smaller than the specified one from the collection");
        manual.put("\u001B[32m max_by_creation_date: \u001B[0m", "output the average value of the meters Above Sea Level field for all elements of the collection");
        manual.put("\u001B[32m group_counting_by_area: \u001B[0m", "group the elements of the collection by the value of the area field, output the number of elements in each group");
        manual.put("\u001B[32m filter_greater_than_personal_qualities_maximum: \u001B[0m", "output the unique values of the meters Above Sea Level field of all elements in the collection");
        for (String key : manual.keySet()) {
            System.out.println(key + manual.get(key));
        }
    }
}
