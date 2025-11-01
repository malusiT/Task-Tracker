import java.util.Scanner;

import java.util.Arrays;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;

public class TaskTracker{
  public static boolean isRunning = true;
  
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Task> tasks = new ArrayList<>();
/*
    //For BufferedReader
    ArrayList<String> new ArrayList<>();
    
    try(BufferedReader reader = new BufferedReader(new FileReader("../data/tasks.json")){
      while(true){
        String line = reader.readLine();
        if(line == null){
          break;
        }
        list.add(line);
        reader.close();
      }
    }catch(FileNotFoundException e){
      e.printStackTrace;
      System.out.println("File is not Found!!");
    }

    for(String line : list){
      System.out.println(line);
    }
  */  

    
    while(isRunning){
      System.out.print("task-tracker: ");
      String input = scanner.nextLine().trim();


      String[] parsedInput = parser(input);

      if(parsedInput.length < 1){
        System.out.println("Command too short, please try again \n" + input);
        return;
      }
      
      String firstCommand = parsedInput[0].toLowerCase();
      switch(firstCommand){
        case "add" -> {
          addTask(parsedInput, tasks);
        }
/*
        case "update" -> {

        }
        case "delete" -> {

        }
        case "mark-in-progress" ->{

        }
        case "mark-done" -> {
      
        }
        case "list" ->{

        }*/
        case "exit" ->{
          isRunning = false;
          System.out.println("Exitiing program...");
          return;
        }
        default -> {
          System.out.println("Command not found: " + input);
          System.out.println("Please try again");
        }
      }
    }

 }



  
  public static void addTask(String[] parsedInput, ArrayList<Task> tasks){
    if(parsedInput.length < 2){
      System.out.println("Command to short to add Task.");
      return;
    }

    String secondCommand = parsedInput[1].replaceAll("^\"|\"$", ""); // getting the secondCommand
    Task newTask = new Task(secondCommand);
    tasks.add(newTask); 

    //save tasks in json format
    save(tasks);
  }



 public static void save(ArrayList<Task> tasks){
  
    String filePathAndName = "./data/tasks.json";

    Gson gson = new GsonBuilder().setPrettyPrinting().create(); //gson with pretty printing for formatting, better readability.

    JsonObject root = new JsonObject();
    root.add("tasks", gson.toJsonTree(tasks));

    String json = gson.toJson(root);

    
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePathAndName))){
      writer.write(json);
    }
    catch(IOException e){
      System.out.println("An error occured while writing to file!!!");
      e.printStackTrace();
    }
 
 }
  
  public static String[] parser(String userInput){
    return userInput.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
  }
}  

