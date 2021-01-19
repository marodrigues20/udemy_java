package com.luv2code.testdb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {

    private static final long serialVersionUID = -6652650482302662909L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // setup connection variables
        String user = "postgres";
        String pass = "springstudent";

        //String jdbcUrl = "jdbc:postgresql://localhost/web_customer_tracker";
        String jdbcUrl = "jdbc:postgresql://localhost/springcourse";
        String driver = "org.postgresql.Driver";


        // get connection to database
        try{

            PrintWriter out = response.getWriter();
            out.println("Connecting to database: " + jdbcUrl);

            Class.forName(driver);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);

            out.println("SUCCESS!!!");

            myConn.close();

        }catch (Exception ex){
            ex.printStackTrace();
            throw new ServletException(ex);
        }


    }
}
