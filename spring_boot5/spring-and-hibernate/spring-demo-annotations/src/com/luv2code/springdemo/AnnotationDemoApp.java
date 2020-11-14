package com.luv2code.aopdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationDemoApp {

  public static void main(String[] args) {

    // read spring config file
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "applicationContext.xml");

    // get the bean from spring container
    //Coach theCoach = context.getBean("ThatSillyCoach",Coach.class);
    Coach theCoach = context.getBean("tennisCoach",Coach.class);

    // call a method on the bean
    System.out.println(theCoach.getDailyWorkout());

    // call a method on the bean
    System.out.println(theCoach.getDailyFortune());

    // close the context
    context.close();

  }

}
