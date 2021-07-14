package com.basicsstrong.reflection;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInfo {

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Entity e = new Entity(10, "id");
        Class<? extends Entity> clss = e.getClass();

        // Return all the public methods and its super class
        Method[] methods = clss.getMethods();
        for (Method method:methods) {
            System.out.println(method.getName());
        }

        System.out.println("******************************************");

        //Return all methods in the Entity class.
        Method[] declaredMethods = clss.getDeclaredMethods();
        for (Method method:declaredMethods) {
            System.out.println(method.getName());
        }

        Method method = clss.getMethod("setVal", int.class);
        method.invoke(e, 15);

        //This is for when the method is private.
        /*Method method2 = clss.getDeclaredMethod("setVal", int.class);
        method2.setAccessible(true);
        method2.invoke(e, 12);
        System.out.println(method2.invoke(e, null));*/

        Method method3 = clss.getMethod("getVal", null);
        System.out.println(method3.invoke(e, null));




    }
}
