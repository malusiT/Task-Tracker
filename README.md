# Task Tracker

### CLI app to track tasks and manage to do list
================================================

## What it does

> Application runs from the command line

- Add, Update, and Delete tasks
- Marks tasks in progress or does
- Lists all tasks
- Filter and list tasks as done, not done and in progress
- Saves tasks and updates locally

## Example


  `# Adding a new task
  task-cli add "Buy groceries"
  # Output: Task added successfully (ID: 1)
  
  # Updating and deleting tasks
  task-cli update 1 "Buy groceries and cook dinner"
  task-cli delete 1
  
  # Marking a task as in progress or done
  task-cli mark-in-progress 1
  task-cli mark-done 1

  # Listing all tasks
  task-cli list

  # Listing tasks by status
  task-cli list done
  task-cli list todo
  task-cli list in-progress
  



