package com.javathlon.section13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RemoveDuplicates {

  public static void main(String[] args) {

    List<User> userList = new ArrayList<>();
    List<User> userList2 = new ArrayList<>();

    userList.add(new User("brad@yyy.com", "Brad Pitt"));
    userList.add(new User("milan@zzz.com", "Milan Kundera"));
    userList.add(new User("stephen@hello.com", "Stephen Zweig"));


    userList2.add(new User("brad@yyy.com", "Brad Pitt"));
    userList2.add(new User("milan@zzz.com", "Milan"));
    userList2.add(new User("orhan@ttt.com", "Orhan Pamuk"));

    Set<User> userSet = new HashSet();
    userSet.addAll(userList);
    userSet.addAll(userList2);

    Iterator<User> userIterator = userSet.iterator();
    while(userIterator.hasNext()){
      User u = userIterator.next();
      System.out.println(u.getEmail() + "-" + u.getUserName());
    }




  }

}
