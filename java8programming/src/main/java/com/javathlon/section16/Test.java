package com.javathlon.section16;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Test {

    /*public static void main(String[] args) {
        String x = "Set-Cookie: nova-auth=MTgwNmE5NzItNzNmNy00NDZhLTk0MGUtMzJiZWE2YzMzMDU4; Max-Age=604800; Expires=Tue, 14-Jul-2020 21:34:57 GMT; Domain=novafutur.com; Path=/";
        String[] result = x.split(";");
        for(int i = 0; i < result.length; i ++){
            if(result[i].contains("Expires")){
                String data = result[i].substring(9);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss z");
                LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
                System.out.println(dateTime);
            }
        }

    }*/

    //public static void main(String[] args) {
    //    //String data = "Tue, 14-Jul-2020 21:34:57 GMT";
    //    String data = "Tue, 14-Jul-2020 21:34:57 GMT";
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss z");
    //    LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
    //    System.out.println(dateTime);
    //}

    public static void main(String[] args) {
        String x = "Set-Cookie: nova-auth=MTgwNmE5NzItNzNmNy00NDZhLTk0MGUtMzJiZWE2YzMzMDU4; Max-Age=604800; Expires=Tue, 14-Jul-2020 21:34:57 GMT; Domain=novafutur.com; Path=/";
        String[] result = x.split(";");
        for(int i = 0; i < result.length; i ++){
            if(result[i].contains("nova-auth")){
                String jwt = result[i].substring(22);
                System.out.println(jwt);
            }
        }

    }
}
