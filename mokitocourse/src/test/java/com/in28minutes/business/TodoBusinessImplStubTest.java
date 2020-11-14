package com.in28minutes.business;

import static org.junit.Assert.assertEquals;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;
import java.util.List;
import org.junit.Test;

public class TodoBusinessImplStubTest {

  @Test
  public void retrieveTodosRelatedToSpring_usingAStub(){
    TodoService todoService = new TodoServiceStub();
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
    assertEquals(2,filteredTodos.size());
  }

  @Test
  public void retrieveTodosRelatedToSpring_usingAStub2(){
    TodoService todoService = new TodoServiceStub();
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
    assertEquals(0,filteredTodos.size());
  }

}
