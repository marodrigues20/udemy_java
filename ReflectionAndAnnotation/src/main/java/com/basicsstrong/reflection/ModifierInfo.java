package com.basicsstrong.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierInfo {

    public static void main(String[] args) throws Exception {

        Entity e = new Entity(10, "id");
        Class<? extends Entity> clss = e.getClass();
        int modifiersInt = clss.getModifiers();
        //int i = modifiersInt & Modifier.PUBLIC;
        //boolean isPublicClass = Modifier.isPublic(modifiersInt);

        System.out.println(Modifier.toString(modifiersInt));

        //System.out.println(isPublicClass);

        Method method = clss.getMethod("getVal");
        int methodModifiers = method.getModifiers();

        //int j = methodModifiers & Modifier.PRIVATE;
        //boolean isPubMethod = Modifier.isPublic(methodModifiers);

        //System.out.println(methodModifiers);
        System.out.println(Modifier.toString(methodModifiers));


    }
}
