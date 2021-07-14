package com.basicsstrong.reflection;

import java.lang.reflect.Constructor;

class MyClass{

    public MyClass(){
        System.out.println("MyClass object created!");
    }
}


public class ReflectionDemo {

    public static void main(String[] args) throws Exception {

        //MyClass myClass = new MyClass();
        Class<?> clss = Class.forName("com.basicsstrong.reflection.MyClass");
        Constructor<?> con = clss.getDeclaredConstructor();
        con.setAccessible(true);
        MyClass newInstance = (MyClass) con.newInstance();


    }
}
