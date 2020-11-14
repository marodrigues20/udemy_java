package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import java.util.ArrayList;
import java.util.List;

//TodoBusinessImpl SUT
// TodoService Dependency
public class TodoBusinessImpl {

  TodoService todoService;

  //Constructor enables us to test TodoBusinessImpl with any implementation
  //of TodoService.
  public TodoBusinessImpl(TodoService todoService) {
    this.todoService = todoService;
  }

  //This is the method we want to write tests for - in future.
  //Lets write logic now to filter the todos and only return a
  //list of todos containing Spring.
  public List<String> retrieveTodosRelatedToSpring(String user){

      List<String> filteredTodos = new ArrayList<String>();
      List<String> allTodos = todoService.retrieveTodos(user);
      for (String todo : allTodos) {
        if (todo.contains("Spring")) {
          filteredTodos.add(todo);
        }
      }
      return filteredTodos;
    }

  public List<String> deleteTodosNotRelatedToSpring(String user){

    List<String> filteredTodos = new ArrayList<String>();
    List<String> allTodos = todoService.retrieveTodos(user);
    for (String todo : allTodos) {
      if (!todo.contains("Spring")) {
        todoService.deleteTodo(todo);
      }
    }
    return filteredTodos;
  }



}
