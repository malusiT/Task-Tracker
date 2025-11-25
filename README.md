# Task Tracker

### A lightweight CLI task management application built with java.

**Project URL:** https://roadmap.sh/projects/task-tracker

## What it does

> Application runs from the command line

- Add, Update, and Delete tasks
- Mark tasks in progress or done
- Lists all tasks
- Filter and list tasks as done, to-do and in-progress
- Saves tasks and updates locally

## Prerequisites
- Java JDK 11 or higher
- Gson library (inlcuded in ./lib)

# Installation

```bash

# Clone the repository
git clone https://github.com/malusiT/Task-Tracker.git
cd Task-Tracker

# Make the run script executable
chmod+x run.sh

# Run the application
./run.sh
```

## Example
```bash

 # Adding a new task
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
```



