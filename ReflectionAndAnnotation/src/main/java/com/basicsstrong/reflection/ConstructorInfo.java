package com.basicsstrong.reflection;

import java.lang.reflect.Constructor;
import java.util.Map.Entry;

public class ConstructorInfo {

    public static void main(String[] args) throws Exception {

        Class<?> clss = Class.forName("com.basicsstrong.reflection.Entity");
        //Constructor<?>[] constructors = clss.getConstructors();
        Constructor<?>[] constructors = clss.getDeclaredConstructors();

        for (Constructor constructor: constructors ) {
            System.out.println(constructor);
        }

        Constructor<?> publicConstruct = clss.getConstructor(int.class,String.class);
        Entity entity = (Entity) publicConstruct.newInstance(10,"StudentId");
        System.out.println(entity.getVal() + " " + entity.getType());

        Constructor<?> privateConstruct = clss.getDeclaredConstructor();
        privateConstruct.setAccessible(true);
        Entity defaultE = (Entity) privateConstruct.newInstance();

        System.out.println(defaultE.getType() +  " " + defaultE.getVal());
    }


}
