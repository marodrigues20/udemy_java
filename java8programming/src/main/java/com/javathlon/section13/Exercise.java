package com.javathlon.section13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Exercise {

  public static void main(String[] args) {

    List firstList = new ArrayList<Integer>();
    firstList.add(1111);
    firstList.add(2222);
    firstList.add(3333);
    firstList.add(4444);

    List secondList = new ArrayList<Integer>();
    secondList.add(2222);
    secondList.add(4444);
    secondList.add(6666);
    secondList.add(7777);

    List mergeList = Exercise.mergeList(firstList, secondList);

    System.out.println(mergeList.size());

    for(int i = 0; i < mergeList.size(); i++){
      System.out.println(mergeList.get(i));
    }
  }

  public static List mergeList(List list1, List list2) {

    Set setList1 = new TreeSet(new NullComparator());
    setList1.addAll(list1);
    setList1.addAll(list2);

    Iterator<Integer> itMerge = setList1.iterator();
    while (itMerge.hasNext()) {
      setList1.add(itMerge.next());
    }

    return Arrays.asList(setList1.toArray());

  }

}
