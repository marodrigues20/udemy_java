package com.javathlon.test;

import java.util.ArrayList;

public class Test {

  public static void main(String[] args) {
    fibonnaci(98);
  }


  private static int[] fibonnaci(int value){
    int[] result = new int[100];
    result[0] = 1;
    result[1] = 1;
    for(int i = 0; i < value; i++){
      result[i+2] = result[i] + result[i+1];
      System.out.print(result[i] + ",");
      if(result[i] == value){
        return result;
      }
    }
    return result;
  }

}
