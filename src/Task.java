import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;

public class Task {

  private static AtomicInteger nextId = new AtomicInteger(0);
  private int id;
  private String description;
  private String status;
  private String createdAt;
  private String updatedAt;

  // main constructor for creation of Task
  public Task(String description) {
    this.id = nextId.incrementAndGet();
    this.status = "to-do";
    this.description = description;
    LocalDateTime currentDateAndTime = LocalDateTime.now();
    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    this.createdAt = currentDateAndTime.format(myFormat);
  }

  // For updating you need id and description
  public Task(int id, String description) {
    this.id = id;
    this.description = description;
    LocalDateTime currentDateAndTime = LocalDateTime.now();
    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    this.updatedAt = currentDateAndTime.format(myFormat);
    // this.createdAt =

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public String getCreateAt() {
    return createdAt;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
