package com.javathlon.section16;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindExample {

    public static void main(String[] args) {

        List<Custormer> customerList = new ArrayList<>();

        customerList.add(new Custormer(1000,"talha"));
        customerList.add(new Custormer(2000,"marie"));
        customerList.add(new Custormer(3000,"jane"));
        customerList.add(new Custormer(4000,"talha"));
        customerList.add(new Custormer(0,"leyla"));

        Optional<Custormer> custormerOptional = customerList.stream()
                .filter(c -> c.getName().equals("bruce"))
                .findFirst();

        if(custormerOptional.isPresent()){
            System.out.println(custormerOptional.get().getName() + " " +
                    custormerOptional.get().getId());
        } else{
            System.out.println("We don't have a customer with name Bruce");
        }

        /////////////////

        custormerOptional = customerList.stream()
                .filter(c -> c.getName().equals("talha"))
                .findAny();

        if(custormerOptional.isPresent()){
            System.out.println(custormerOptional.get().getName() + " " +
                    custormerOptional.get().getId());
        } else{
            System.out.println("We don't have a customer with name talha");
        }

        ////////// allMatch

        boolean myBool = customerList.stream()
                .allMatch(c -> c.getId() > 0);

        if(myBool){
            System.out.println("All customer have id");
        } else{
            System.out.println("Some customer don't have id");
        }


        /////// anyMatch
        boolean myBoolean = customerList.stream()
                .anyMatch(c -> c.getId() > 2000);
        System.out.println(myBoolean);

    }
}
