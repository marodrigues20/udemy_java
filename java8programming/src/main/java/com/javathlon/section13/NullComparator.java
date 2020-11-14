package com.javathlon.section13;

import java.util.Comparator;

public class NullComparator implements Comparator<Integer> {


  @Override
  public int compare(Integer t0, Integer t1) {

    if(t0 == null) t0 = 0;
    if(t1 == null) t1 = 0;
    return t0-t1;
  }
}
