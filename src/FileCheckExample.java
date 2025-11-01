import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCheckExample {

    public static void main(String[] args) {
        String relativePathString = "../data/tasks.json";

        // Convert the string path to a Path object
        Path path = Paths.get(relativePathString);

        // Check if the file exists
        if (Files.exists(path)) {
            System.out.println("Success: The file exists at " + path.toAbsolutePath());
        } else {
            System.out.println("Error: The file does not exist at " + path.toAbsolutePath());
        }
    }
}
