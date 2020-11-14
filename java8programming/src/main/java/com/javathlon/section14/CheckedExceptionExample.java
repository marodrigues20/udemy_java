package com.javathlon.section14;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CheckedExceptionExample {

    public static void main(String[] args) {

        try {
            FileReader reader = new FileReader("C:\\talha");
            CheckedExceptionExample checkedExceptionExample = new CheckedExceptionExample();
            checkedExceptionExample.readFile();
        } catch (FileNotFoundException e) { //checked exception
            e.printStackTrace();
        }

        System.out.println("Program finished");


    }

    private void readFile() throws FileNotFoundException{
        FileReader reader = new FileReader("C:\\talha");

    }
}
