package bank;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Trainning {

    public static void main(String[] args){


        List<String> names = List.of("Ana", "Bruno", "Ana", "Carlos", "Bruno", "Ana");

        HashMap s = (HashMap) names.stream().collect(Collectors.groupingBy(name -> name));


        System.out.println(s);
    }
}
