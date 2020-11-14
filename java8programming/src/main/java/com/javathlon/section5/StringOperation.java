package com.javathlon.section5;

public class StringOperation {

    public String concatanateStrings (String[] sArrays){

        String result = "";

        for(int i=0; i < sArrays.length; i ++){
            result += sArrays[i];
        }
        return result;
    }


    public String concatanateStringsWithVarargs (String... sArrays){

        String result = "";

        for(int i=0; i < sArrays.length; i ++){
            result += sArrays[i];
        }
        return result;
    }
}
