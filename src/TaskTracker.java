import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class TaskTracker {
  public static boolean isRunning = true;
  public static ArrayList<Task> loadedTasks = getTasks("./data/tasks.json");
  public static ArrayList<Task> tasks = (loadedTasks != null) ? loadedTasks : new ArrayList<>();

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    while (isRunning) {
      System.out.print("task-tracker: ");
      String input = scanner.nextLine().trim();

      String[] parsedInput = parser(input);

      if (parsedInput.length < 1) {
        System.out.println("Command too short, please try again \n" + input);
        continue;
      }

      String firstCommand = parsedInput[0].toLowerCase();
      switch (firstCommand) {
        case "add" -> {
          addTask(parsedInput, tasks);
        }
        case "update" -> {
          updateTask(parsedInput, tasks);
        }

        case "delete" -> {
          deleteTask(parsedInput, tasks);
        }

        case "mark-in-progress" -> {
          mark("in-progress", parsedInput, tasks);
        }
        case "mark-done" -> {
          mark("done", parsedInput, tasks);

        }

        case "list" -> {
          list(parsedInput, tasks);
        }
        case "exit" -> {
          isRunning = false;
          return;
        }
        default -> {
          System.out.println("Command not found: " + input);
          System.out.println("Please try again");
        }
      }
    }
    scanner.close();
  }

  // }

  public static void list(String[] parsedInput, ArrayList<Task> tasks) {
    // -> list format
    // id taskName status createrAtDate
    if (parsedInput.length == 1) {
      listAll(tasks);
    }
    if (parsedInput.length > 1) {

      String filterCommand = parsedInput[1];
      switch (filterCommand) {
        case "to-do" -> {
          listToDo(tasks);
        }
        case "in-progress" -> {
          listInProgress(tasks);
        }
        case "done" -> {
          listDone(tasks);
        }
        default -> {
          System.out.println("Wrong command, pls try again");
          System.out.println("""
              eg. -> list
                  -> list done
                  -> list to-do
                  -> list in-progress
              """);
        }
      }
    }

  }

  public static void listAll(ArrayList<Task> tasks) {
    System.out.println("");
    for (Task task : tasks) {

      int id = task.getId();
      String description = task.getDescription();
      String status = task.getStatus();
      String[] date = task.getCreateAt().split("-");

      System.out.println("=".repeat(70));
      System.out.printf("(%d) - \"%s\", status: %s  date: %s-%s-%s\n", id, description, status, date[0], date[1],
          date[2]);
    }
    System.out.println("");
  }

  public static void listToDo(ArrayList<Task> tasks) {
    System.out.println("");
    for (Task task : tasks) {
      if (task.getStatus().equalsIgnoreCase("to-do")) {
        int id = task.getId();
        String description = task.getDescription();
        String status = task.getStatus();
        String[] date = task.getCreateAt().split("-");

        System.out.println("=".repeat(60));
        System.out.printf("(%d) - \"%s\", status: %s  date: %s-%s-%s\n", id, description, status, date[0], date[1],
            date[2]);
      }
    }
    System.out.println("");
  }

  public static void listInProgress(ArrayList<Task> tasks) {
    System.out.println("");
    for (Task task : tasks) {
      if (task.getStatus().equalsIgnoreCase("in-progress")) {
        int id = task.getId();
        String description = task.getDescription();
        String status = task.getStatus();
        String[] date = task.getCreateAt().split("-");

        System.out.println("=".repeat(60));
        System.out.printf("(%d) - \"%s\", status: %s  date: %s-%s-%s\n", id, description, status, date[0], date[1],
            date[2]);
      }
    }
    System.out.println("");
  }

  public static void listDone(ArrayList<Task> tasks) {
    System.out.println("");
    for (Task task : tasks) {
      if (task.getStatus().equalsIgnoreCase("done")) {
        int id = task.getId();
        String description = task.getDescription();
        String status = task.getStatus();
        String[] date = task.getCreateAt().split("-");

        System.out.println("=".repeat(60));
        System.out.printf("(%d) - \"%s\", status: %s  date: %s-%s-%s\n", id, description, status, date[0], date[1],
            date[2]);
      }
    }
    System.out.println("");
  }

  public static ArrayList<Task> getTasks(String filePath) {

    ArrayList<Task> tasks = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      StringBuilder getLines = new StringBuilder();
      String line;

      while (((line = reader.readLine()) != null)) {
        getLines.append(line);
      }

      JsonObject tasksObject = JsonParser.parseString(getLines.toString()).getAsJsonObject();

      JsonArray tasksArray = tasksObject.getAsJsonArray("tasks");

      Gson gson = new Gson();
      Type tasksListType = new TypeToken<ArrayList<Task>>() {
      }.getType();
      ArrayList<Task> tasksList = gson.fromJson(tasksArray, tasksListType);

      for (Task task : tasksList) {
        tasks.add(task);
      }

    } catch (FileNotFoundException e) {
      System.out.println("File Not Found Error");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return tasks;
  }

  public static void deleteTask(String[] parsedInput, ArrayList<Task> tasks) {
    // delete 1

    if (parsedInput.length < 2) {
      System.out.println("Invalid Command: Missing Arguments");
      System.out.println("Usage: delete <id>");
      return;
    }

    int id = Integer.parseInt(parsedInput[1]);
    boolean isDeleted = false;

    if (tasks.removeIf(i -> (i.getId() == id))) {
      isDeleted = true;
      save(tasks);
    }

    if (isDeleted) {
      System.out.println("Task has been deleted");
    } else {
      System.out.println("Task not deleted");
    }

  }

  public static void updateTask(String[] parsedInput, ArrayList<Task> tasks) {
    // update 22 "wash car with soap"

    if (parsedInput.length < 3) {
      System.out.println("Invalid Command: Missing Arguments");
      System.out.println("Usage:  update <id> \"new description\"");
      return;
    }

    int id = Integer.parseInt(parsedInput[1]);
    String updatedDescription = parsedInput[2].replaceAll("^\"|\"$", ""); // getting the secondCommand
    boolean isUpdated = false;

    for (int i = 0; i < tasks.size(); i++) {
      Task taskFound = tasks.get(i);
      if (taskFound.getId() == id) {
        System.out.println("Found Match!! at index: " + i);
        taskFound.setDescription(updatedDescription);
        isUpdated = true;
        break;
      }
    }
    if (isUpdated) {
      save(tasks);
      System.out.println("== Task updated ==");
    } else {
      System.out.println("Id Match Not Found");
    }
  }

  public static void addTask(String[] parsedInput, ArrayList<Task> tasks) {
    if (parsedInput.length < 2) {
      System.out.println("Command to short to add Task.");
      return;
    }
    int maxId = 0;
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).getId() > maxId) {
        maxId = tasks.get(i).getId();
      }
    }

    String secondCommand = parsedInput[1].replaceAll("^\"|\"$", ""); // getting the secondCommand

    Task newTask = new Task(secondCommand);
    newTask.setId(++maxId);
    tasks.add(newTask);

    // save tasks in json format
    save(tasks);
    System.out.println("=== Task added successfully ===");
  }

  public static void mark(String status, String[] parsedInput, ArrayList<Task> tasks) {
    // mark-in-progress id

    if (parsedInput.length < 2) {
      System.out.println("Missing Arguments try again!!");
      System.out.println("Usage: mark-in-progress <id>");
      return;
    }

    int id = Integer.parseInt(parsedInput[1]);
    String updatedStatus = "";

    switch (status) {
      case "in-progress", "done" -> {
        updatedStatus = status;
      }
      default -> {
        System.out.println("Wrong status marking");
        return;
      }
    }

    boolean isUpdated = false;
    for (int i = 0; i < tasks.size(); i++) {
      Task taskFound = tasks.get(i);
      if (taskFound.getId() == id) {
        if (taskFound.getStatus().equals("done")) {
          System.out.println("Task is already done *note: Make a new task");
          return;
        }
        taskFound.setStatus(updatedStatus);
        isUpdated = true;
        break;
      }
    }
    if (isUpdated) {
      save(tasks);
      System.out.println("== Task updated ==");
    } else {
      System.out.println("Id Match Not Found");
    }

  }

  public static void save(ArrayList<Task> tasks) {

    String filePathAndName = "./data/tasks.json";

    Gson gson = new Gson();
    JsonObject root = new JsonObject();
    root.add("tasks", gson.toJsonTree(tasks));

    String json = gson.toJson(root);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathAndName))) {
      writer.write(json);
    } catch (IOException e) {
      System.out.println("An error occured while writing to file!!!");
      e.printStackTrace();
    }
  }

  public static String[] parser(String userInput) {
    return userInput.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
  }
}
