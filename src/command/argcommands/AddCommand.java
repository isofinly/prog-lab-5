package src.command.argcommands;

import src.collection.Coordinates;
import src.collection.Difficulty;
import src.collection.Discipline;
import src.command.Executable;
import src.utils.IdGen;

import java.util.Scanner;

public class AddCommand implements Executable {

    public void add(Scanner read) {
        // TODO
        int id = IdGen.getNewId();

        String name = "";
        while (name.isEmpty()) {
            System.out.println("\u001B[34m Enter name: ");
            name = read.nextLine();
        }

        System.out.println("\u001B[34m Enter coordinates: ");
        double x = 0;
        while (x == 0) {
            System.out.println("\u001B[34m Enter x: ");
            try {
                x = Double.parseDouble(read.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("\u001B[31m Invalid number format.");
            }
        }
        float y = 0;
        while (y == 0) {
            System.out.println("\u001B[34m Enter y: ");
            try {
                y = Float.parseFloat(read.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("\u001B[31m Invalid number format.");
            }
        }

        Coordinates coordinates = new Coordinates(x, y);

        int minimalPoint = 0;
        while (minimalPoint == 0) {
            System.out.println("\u001B[34m Enter minimal point: ");
            try {
                minimalPoint = Integer.parseInt(read.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("\u001B[31m Invalid number format.");
            }
        }
        int personalQualitiesMinimum = 0;
        while (personalQualitiesMinimum == 0) {
            System.out.println("\u001B[34m Enter personal qualities minimum: ");
            try {
                personalQualitiesMinimum = Integer.parseInt(read.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("\u001B[31m Invalid number format.");
            }
        }
        long personalQualitiesMaximum = 0;
        while (personalQualitiesMaximum == 0) {
            System.out.println("\u001B[34m Enter personal qualities maximum: ");
            try {
                personalQualitiesMaximum = Long.parseLong(read.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("\u001B[31m Invalid number format.");
            }
        }

        Difficulty difficulty = null;
        while (difficulty == null) {
            System.out.println("\u001B[34m Enter difficulty: ");
            try {

                System.out.println("\u001B[34m Enter one of these types of difficulty:  \u001B[0m");
                System.out.println(Difficulty.nameList());
                String input = read.nextLine().trim().toUpperCase();
                difficulty = Difficulty.valueOf(input);
            } catch (IllegalArgumentException ex) {
                System.out.println("\u001B[31m Invalid difficulty type.");
            }

            Discipline discipline = null;
            while (discipline == null) {
                System.out.println("\u001B[34m Enter discipline name: ");
                try {
                    String disciplineName = read.nextLine();
                    long selfStudyHours = 0;
                    while (selfStudyHours == 0) {
                        System.out.println("\u001B[34m Enter self study hours: ");
                        try {
                            selfStudyHours = Long.parseLong(read.nextLine());
                        } catch (NumberFormatException ex) {
                            System.out.println("\u001B[31m Invalid number format.");
                        }
                    }
                    discipline = new Discipline(disciplineName, selfStudyHours);
                } catch (IllegalArgumentException ex) {
                    System.out.println("\u001B[31m Invalid discipline name.");
                }
            }

            System.out.println("\u001B[34m Element added.");
        }
    }

    @Override
    public void execute() {
    }
}
