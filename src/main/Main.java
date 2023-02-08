package src.main;

import java.io.*;

import src.collection.*;
import src.utils.*;


public class Main {
    public static void main(String[] args) throws Exception {

        var env_file_input = ParserEnv.envParser("/Users/isofinly/VSC-Projects/prog-lab-5/src/.env");
        String inPath = "/Users/isofinly/VSC-Projects/prog-lab-5/src/JSON/input.json";
        String outPath = "/Users/isofinly/VSC-Projects/prog-lab-5/src/JSON/output.json";

//        var env_file_input = ParserEnv.envParser("C:\\Users\\user\\VSC-projects\\prog-lab-5\\src\\.env");
//        String inPath = "C:\\Users\\user\\VSC-projects\\prog-lab-5\\src\\JSON\\input.json";
//        String outPath = "C:\\Users\\user\\VSC-projects\\prog-lab-5\\src\\JSON\\output.json";

        System.out.println(env_file_input.get("COMMAND_1"));


        File json_file_input = new File("src/JSON/input.json");
////        CollectionManager collectionManager = new CollectionManager(inPath, outPath);
//
//        System.out.println();
//        collectionManager.help();
//        System.out.println();
//        collectionManager.info();
//        System.out.println();
//        collectionManager.show();
//        System.out.println();
//        collectionManager.clear();
//        System.out.println();
//
//        System.out.println();
//        collectionManager.exit();


    }
    
}
