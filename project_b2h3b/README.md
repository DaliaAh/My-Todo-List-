
# **My ToDo List**

For my personal project this semester, I will be designing a to-do list application, 
that can be used by clients to stay organized! This application will allow people to add tasks to their to-do list,
remove tasks from their to-do list, and mark tasks as complete. Additionally, people will have the option of organizing
their to-do lists by adding deadlines, and time needed to complete a particular task.

This project is of interest to me because it's simple and very useful for users to have. I look forward to learning 
how to design an application that can help others organize themselves, especially students like myself. It is useful to 
have an application that can make your life a little easier by ensuring that you are completing tasks efficiently. 

**Phase 4: Task 2**    
A Map Interface was used in the MyTodoList class. The keys are of type Integer, and the values are of type Task. 
This allows each task to have a unique ID.

**Phase 4: Task 3**
- Improve the cohesion of the MyTodoList class. It's currently responsible for adding and removing tasks,
 counting tasks, and for parsing the todolist to and from JSON object. I would make a new class that would be 
 responsible for parsing the todolist to and from JSON object.
 - Improve the cohesion of the Task class by making a new class that handles parsing tasks to and from JSON objects.
 - Task is currently responsible for name, description, dueDate, createDate, and Priority. I would add classes for each 
 of the properties of the task to further improve cohesion.
 - The GUI class is currently responsible for so much behavior including making frames, labels, panels, buttons, lists 
 and so on. I would designate a class for each of these behaviours.
    - Remove the playSound method in TodoGui and add it to a new class responsible for the sound effects of the GUI.
    - Remove the makeList method in TodoGUi and add it to a new class responsible for creating JLists.
    - Remove the makeLabel method in TodoGUi and add it to a new class responsible for creating JLabels.
    - Remove the makeJTextField method in TodoGUi and add it to a new class responsible for creating JTextFields.
    - Remove the makeJButton method in TodoGUi and add it to a new class responsible for creating JButtons
    - Remove the makePanel method in TodoGUi and add it to a new class responsible for creating JPanels.
    - Remove the makeFrame method in TodoGUi and add it to a new class responsible for creating JFrames.
    
- I would also designate a class for saving and retrieving todos, and another class that can create,delete,save,update,
and mark tasks as complete. This would further improve cohesion. 
- A class would be designated for adding actionListeners.
- TodoGui would only be responsible for the adding and removing the different components from the frame. 





                                               

###### _By: Dalia Ahmad_

#### **User Stories**
- As a user, I want to be able to add a Task to my to-do list.
- As a user, I want to be able to remove/delete a task from my to-do list.
- As a user, I want to be able to view tasks on my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to see the number of incomplete and complete tasks on my to-do list.
- As a user, I want to be able to save my to-do list to a file.
- As a user, I want to be able to retrieve my to-do list from a file.
- As a user, I want to be able to make a new to-do list.
- As a user, I want to be able to add multiple Xs to a Y in the application
- As a user, I want to be able to load and save the state of the application



