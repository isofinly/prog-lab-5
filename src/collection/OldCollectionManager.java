package src.collection;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import src.utils.*;


/*
    help : вывести справку по доступным командам
    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
    add {element} : добавить новый элемент в коллекцию
    update id {element} : обновить значение элемента коллекции, id которого равен заданному
    remove_by_id id : удалить элемент из коллекции по его id
    clear : очистить коллекцию
    save : сохранить коллекцию в файл
    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
    exit : завершить программу (без сохранения в файл)
    add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
    add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
    remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
    max_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является максимальным
    group_counting_by_id : сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе
    filter_greater_than_personal_qualities_maximum personalQualitiesMaximum : вывести элементы, значение поля personalQualitiesMaximum которых больше заданного
 */


public class OldCollectionManager{

    //    private LinkedHashSet<LabWork> works;
    private LinkedHashSet <LabWork> labWork = new LinkedHashSet <>();
    private File jsonCollection;
    private Date initDate;
    private File outPut;
    Gson gson = new Gson();
    protected static HashMap <String, String> manual;
    private List <String> scriptStack = new ArrayList <>();

    {
        Gson gson = new Gson();
        labWork = new LinkedHashSet <>();
        manual = new HashMap <>();

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
    }


    public OldCollectionManager(String inPath, String outPath) throws Exception, IOException {
        this.jsonCollection = new File(inPath);
        this.outPut = new File(outPath);
        this.initDate = new Date();
        this.load();
        for (LabWork p : labWork) {
            if (p.getMinimalPoint() < 0.0) {
                throw new Exception();
            }
        }
    }

    public void load() throws IOException {

        int fileSize = labWork.size();

        if (!jsonCollection.exists()) {
            System.out.println("\u001B[31m File not found.");
            System.exit(1);
        }

        if (!jsonCollection.canRead() || !jsonCollection.canWrite()) {
            System.out.println("\u001B[31m No access to file. Check file permissions.");
            System.exit(1);
        }

        if (jsonCollection.length() == 0) {
            System.out.println("\u001B[31m File is empty.");
            System.exit(1);
        }
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonCollection)))) {
            System.out.println("\u001B[34m Collection is loading " + jsonCollection.getAbsolutePath());
            String nextLine;
            StringBuilder result = new StringBuilder();
            while ((nextLine = inputStreamReader.readLine()) != null) {
                result.append(nextLine);
            }

            Type collectionTypeObject = new TypeToken <LinkedHashSet <Object>>() {
            }.getType();
            LinkedHashSet <Object> itemsObj = gson.fromJson(result.toString(), collectionTypeObject);
            ArrayList <String> args2 = new ArrayList <>();
            while (itemsObj.iterator().hasNext()) {
                String[] args = itemsObj.iterator().next().toString().split("\\w*=");
                for (String arg : args) {
                    String cleanedArg = arg.replace("{", "").replace("}", "").replace(",", "").trim();
                    if (!cleanedArg.isEmpty()) {
                        args2.add(cleanedArg);
                    }
                }

                itemsObj.remove(itemsObj.iterator().next());

                try {
                    labWork.add(new LabWork(
                            args2.get(0),
                            new Coordinates(
                                    Double.parseDouble(args2.get(1)),
                                    Float.parseFloat(args2.get(2))
                            ),
                            (int) Double.parseDouble(args2.get(3)),
                            (int) Double.parseDouble(args2.get(4)),
                            (long) Double.parseDouble(args2.get(5)),
                            Difficulty.valueOf(args2.get(6)),
                            new Discipline(args2.get(7), (long) Double.parseDouble(args2.get(8)))
                    ));
                    args2.clear();
                } catch (JsonSyntaxException ex) {
                    System.out.println("\u001B[31m JSON syntax erorr.");
                    System.exit(1);
                }
            }
            System.out.println(labWork);
            System.out.println("Collection of type " + labWork.getClass().getName() + " successfully loaded in size of " + (labWork.size() - fileSize) + " elements.");
        }
    }

    public void add(List <String> args) {

    }

    public HashMap <String, String> getManual() {
        return manual;
    }

    public void help() {
        for (Map.Entry <String, String> entry : manual.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void info() {
        System.out.println("\u001B[34m Collection type: " + labWork.getClass().getName());
        System.out.println("Collection size: " + labWork.size());
        System.out.println("Initialization date: " + initDate);
    }

    public void show() {
        if (labWork.isEmpty()) {
            System.out.println("\u001B[31m Collection is empty!");
            return;
        }
        ;
        for (LabWork work : labWork) {
            System.out.println(work);
        }
    }

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
        public void updateId ( int id, LabWork labWork){
            for (LabWork work : this.labWork) {
                if (work.getId() == id) {
                    this.labWork.remove(work);
                    this.labWork.add(labWork);
                    System.out.println("\u001B[34m Element with id " + id + " updated.");
                    return;
                }
            }
            System.out.println("\u001B[31m Element with id " + id + " not found.");
        }


        public void remove_by_id(int id){
            for (LabWork work : labWork) {
                if (work.getId() == id) {
                    labWork.remove(work);
                    System.out.println("\u001B[34m Element with id " + id + " removed.");
                    return;
                }
            }
            System.out.println("\u001B[31m Element with id " + id + " not found.");
        }


        public void clear () {
            labWork.remove(labWork);
            System.out.println("\u001B[34m Collection cleared.");
        }

        public void save () throws IOException {
            // TODO
            try (BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsonCollection)))) {
                outputStreamWriter.write(gson.toJson(labWork));
                System.out.println("\u001B[34m Collection saved to " + jsonCollection.getAbsolutePath());
            }
        }

        public void execute_scrpit () {
            // TODO
        }

        public void exit () {
            System.out.println("\u001B[34m Bye!");
            System.exit(0);
        }

        public void add_if_max (LabWork labWork){
            if (labWork.compareTo(Collections.max(this.labWork)) > 0) {
                this.labWork.add(labWork);
                System.out.println("\u001B[34m Element added.");
            } else {
                System.out.println("\u001B[31m Element not added.");
            }
        }

        public void add_if_min (LabWork labWork){
            if (labWork.compareTo(Collections.min(this.labWork)) < 0) {
                this.labWork.add(labWork);
                System.out.println("\u001B[34m Element added.");
            } else {
                System.out.println("\u001B[31m Element not added.");
            }
        }

        public void remove_lower (LabWork labWork){
            for (LabWork work : this.labWork) {
                if (work.compareTo(labWork) < 0) {
                    this.labWork.remove(work);
                }
            }
            System.out.println("\u001B[34m Elements removed.");
        }

        public void max_by_creation_date () {
            // TODO
        }

        public void group_conting_by_id () {
            // TODO
        }

        public void filter_greater_than_personal_qualities_maximun () {
            // TODO
        }

        @Override
        public String toString () {
            if (labWork.isEmpty()) {
                return "Collection is empty!";
            }
            ;

            String info = "";
            for (LabWork work : labWork) {
                info += work;
                info += "\n\n";
            }
            System.out.println(info);
            return info;

        }

    }

