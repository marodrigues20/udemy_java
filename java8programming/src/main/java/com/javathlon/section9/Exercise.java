package com.javathlon.section9;

public class Exercise {

    public static void main(String[] args){

        String s = "Now let's go to do something. Let me explain it first.";
        System.out.println("There are " + countWord(s,"let") + " occurrences.");
    }

    public static int countWord(String source, String searchWord){

        int index = 0;
        int count = 0;

        //for(; index >= 0; ){
        while(index >= 0){

            index = source.toLowerCase().indexOf(searchWord.toLowerCase(), ++index);

            if(index > 0 )
                count ++;
        }

         return count;

      }
}
