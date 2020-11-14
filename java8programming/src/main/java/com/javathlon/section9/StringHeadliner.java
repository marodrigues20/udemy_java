package com.javathlon.section9;

public class StringHeadliner {


    //all the words starts with a capital letter except the words like “for,
    //the, in”. And all the remaining letters become lowercase
    //For instance; “tHe IMPOrtance of the independency waR” becomes below
    //when you convert it to headline format:
    public static void main(String[] args){

        String text = "tHe  IMPOrtance of the independency waR";
        StringHeadliner stringHeadliner = new StringHeadliner();
        String result = stringHeadliner.makeHeadline(text);
        System.out.println(result);

    }

    public String makeHeadline(String input){

        String lowerCaseWord = input.toLowerCase();
        String[] stringArray = lowerCaseWord.split(" ");
        StringBuilder sb = new StringBuilder();
        String capitalLetter;

        for(int i = 0; i < stringArray.length; i++){
            if(stringArray[i].length() > 1){
                if(i == 0){
                    capitalLetter = stringArray[i].substring(0,1).toUpperCase().concat(stringArray[i].substring(1,stringArray[i].length()));
                    sb.append(capitalLetter.concat(" "));
                }else if(stringArray[i].equalsIgnoreCase("for") ||
                        stringArray[i].equalsIgnoreCase("the") ||
                        stringArray[i].equalsIgnoreCase("in")){
                    sb.append(stringArray[i].toLowerCase().concat(" "));
                }else{
                    capitalLetter = stringArray[i].substring(0,1).toUpperCase().concat(stringArray[i].substring(1,stringArray[i].length()));
                    sb.append(capitalLetter.concat(" "));
                }
            }

        }
        return sb.toString();
    }
}
