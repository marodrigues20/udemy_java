package com.luv2code.aopdemo;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component("ThatSillyCoach")
@Component // use the default bean id
//@Scope("prototype")
public class TennisCoach implements Coach{

  @Autowired
  @Qualifier("randomFortuneService")
  private FortuneService fortuneService;

  // define a default constructor
  public TennisCoach(){
    // Just to check what's happening in the spring background
    System.out.println(">> TennisCoach: inside default constructor");
  }

  // define my init method
  @PostConstruct
  public void doMyStartupStuff(){
    System.out.println(">> TennisCoach: inside of doMyStartupStuff");
  }
  @PreDestroy
  public void doMyCleanupStuff(){
    System.out.println(">> TennisCoach: inside of doMyCleanupStuff");
  }

  // define my destroy method


  // define a setter method
  /*@Autowired
  public void setFortuneService(FortuneService fortuneService) {
    System.out.println(">> TennisCoach: inside setFortuneService method");
    this.fortuneService = fortuneService;
  }*/

  /*@Autowired
  public void doSomeCrazyStuff(FortuneService fortuneService) {
    System.out.println(">> TennisCoach: inside doSomeCrazyStuff method");
    this.fortuneService = fortuneService;
  }*/

  /*
  @Autowired
  public TennisCoach(FortuneService fortuneService) {
    this.fortuneService = fortuneService;
  }*/

  @Override
  public String getDailyWorkout() {
    return "Practice your backhand volley";
  }

  @Override
  public String getDailyFortune() {
    return fortuneService.getFortune();
  }
}
