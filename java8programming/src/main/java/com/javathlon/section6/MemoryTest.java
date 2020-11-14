package com.javathlon.section6;

public class MemoryTest {

    private static int i = 0;

    public static void main(String[] args){
        MemoryTest test = new MemoryTest();
        test.doSth();

    }

    public void doSth(){
        try{
            i++;
            System.out.println("5");
            doSth();
        }catch (Exception e){
            System.out.println(i);
        }finally {
            System.out.println(i);
        }

    }
}
