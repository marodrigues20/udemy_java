package com.in28minutes.business;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class TodoBusinessImplMockTest {

  @Test
  public void retrieveTodosRelatedToSpring_usingAMock(){
    // What is mocking?
    // mocking is creating objects that simulate the behavior of real objects.
    // Unlike stubs, mocks can be dynamically created from code - at runtime.
    // Mocks offer more functionality than stubbing
    // You can verify method calls and a lot of other things.


    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    assertEquals(2,filteredTodos.size());

  }

  @Test
  public void retrieveTodosRelatedToSpring_withEmptyList(){

    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList();

    when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    assertEquals(0,filteredTodos.size());

  }

  @Test
  public void retrieveTodosRelatedToSpring_usingBDD(){

    //Given
    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When

    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    //Then
    assertThat(filteredTodos.size(), is(2));
  }


  @Test
  public void deleteTodosNotRelatedToSpring_usingBDD(){

    //Given
    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When

    todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

    //Then

    //verify method from mockito whether the method was called.
    //verify(todoServiceMock, times(1)).deleteTodo("Learn to Dance"); // how many times method is invoked
    //verify(todoServiceMock, atLeastOnce()).deleteTodo("Learn to Dance");
    //verify(todoServiceMock, atLeast(5)).deleteTodo("Learn to Dance");
    //verify(todoServiceMock).deleteTodo("Learn to Dance");
    then(todoServiceMock).should().deleteTodo("Learn to Dance"); //BDD Style

    verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
    //then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC"); //BDD Style
  }

  @Test
  public void deleteTodosNotRelatedToSpring_usingBDD_argumentCapture(){

    //Declare Argument Captor
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    //Define Argument captor on specific method call
    //Capture the argument

    //Given
    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When

    todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

    //Then

    then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture());

    assertThat(stringArgumentCaptor.getValue(),is("Learn to Dance"));


  }

  @Test
  public void deleteTodosNotRelatedToSpring_usingBDD_argumentCaptureMuiltipleTimes(){

    //Declare Argument Captor
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    //Define Argument captor on specific method call
    //Capture the argument

    //Given
    TodoService todoServiceMock = mock(TodoService.class);

    List<String> todos = Arrays.asList("Learn to Rock and Roll", "Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When

    todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

    //Then

    //You might want to check more than just the size.
    //Write asserts to check complete content of stringArgumentsCaptor as an exercise.
    then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());



    assertThat(stringArgumentCaptor.getAllValues().size(),is(2));


  }



}
