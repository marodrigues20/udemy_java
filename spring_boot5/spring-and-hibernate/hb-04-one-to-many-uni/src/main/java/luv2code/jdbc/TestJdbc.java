package luv2code.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

    public static void main(String[] args) {


        String jdbcUrl = "jdbc:postgresql://localhost/hb-04-one-to-many-uni";
        String user = "hbstudent";
        String pass = "hbstudent";



        try{

            System.out.println("Connection to database: " + jdbcUrl);
            Connection myConn = DriverManager.getConnection(jdbcUrl, user,pass);

            System.out.println("We have a successful!!!");

        }catch (Exception e){

            e.printStackTrace();

        }


    }

}
