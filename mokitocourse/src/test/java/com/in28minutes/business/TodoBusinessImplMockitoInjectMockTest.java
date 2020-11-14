package com.in28minutes.business;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.in28minutes.data.api.TodoService;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockitoInjectMockTest {

  // Rules run before and after test.
  @Rule // Prefer @Ruler over @Runwith
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  TodoService todoServiceMock;

  @InjectMocks
  TodoBusinessImpl todoBusinessImpl;
  //TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

  @Captor
  ArgumentCaptor<String> stringArgumentCaptor;


  @Test
  public void retrieveTodosRelatedToSpring_usingAMock(){



    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);



    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    assertEquals(2,filteredTodos.size());

  }

  @Test
  public void retrieveTodosRelatedToSpring_withEmptyList(){



    List<String> todos = Arrays.asList();

    when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);

    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    assertEquals(0,filteredTodos.size());

  }

  @Test
  public void retrieveTodosRelatedToSpring_usingBDD(){

    //Given


    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    //When

    List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

    //Then
    assertThat(filteredTodos.size(), is(2));
  }


  @Test
  public void deleteTodosNotRelatedToSpring_usingBDD(){

    //Given


    List<String> todos = Arrays.asList("Learn Spring MVC","Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    //When

    todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

    //Then


    then(todoServiceMock).should().deleteTodo("Learn to Dance"); //BDD Style

    verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");

  }

  @Test
  public void deleteTodosNotRelatedToSpring_usingBDD_argumentCapture(){

    //Declare Argument Captor
    //Define Argument captor on specific method call
    //Capture the argument

    //Given


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


    List<String> todos = Arrays.asList("Learn to Rock and Roll", "Learn Spring","Learn to Dance");

    given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

    //When

    todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

    //Then

    //You might want to check more than just the size.
    //Write asserts to check complete content of stringArgumentsCaptor as an exercise.
    then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());



    assertThat(stringArgumentCaptor.getAllValues().size(),is(2));


  }



}
