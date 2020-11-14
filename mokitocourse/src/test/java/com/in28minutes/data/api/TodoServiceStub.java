package com.in28minutes.data.api;

import java.util.Arrays;
import java.util.List;

public class TodoServiceStub implements TodoService {
  // Problems about mocks:
  //Dynamic condition
  //Service Definition
  //They are really difficult  to maintain.

  //This is a Dummy implementation
  //Stub is a sample of implementation of TodoService.java
  //Stub is only used for Unit Testing.
  @Override
  public List<String> retrieveTodos(String user) {
    return Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");
  }

  @Override
  public void deleteTodo(String todo) {

  }
}
