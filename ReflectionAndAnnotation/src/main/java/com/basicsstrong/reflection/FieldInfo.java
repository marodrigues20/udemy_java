package com.basicsstrong.reflection;

import java.lang.reflect.Field;

public class FieldInfo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Entity e = new Entity(10,"id");
        Class<? extends Entity> clss = e.getClass();

        Field[] fields = clss.getFields();


        // Return just public fields.
        for (Field field: fields) {
            System.out.println(field.getName());
        }

        System.out.println("*********************************************");

        // Return all fields independent of modifiers.
        Field[] declaredField = clss.getDeclaredFields();
        for(Field field: declaredField){
            System.out.println(field.getName());
        }

        System.out.println("*********************************************");


        //non-declared : all the public elements in that class and in its super class
        //declared : all the elements present in that class only.

        Field field = clss.getField("type");
        //Field field2 = clss.getField("val"); --> java.lang.NoSuchFieldException (private field)
        Field field2 = clss.getDeclaredField("val");
        field2.setAccessible(true);
        field2.set(e,19);
        field.set(e,"rollNo.");

        System.out.println(e.getType());
        System.out.println(e.getVal());





    }
}
