package luv2code.hibernate.demo;


import luv2code.hibernate.demo.entity.Course;
import luv2code.hibernate.demo.entity.Instructor;
import luv2code.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructorDemo {

    public static void main(String[] args) {

         // create session factory

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try{

            // create the objects
            Instructor tempInstructor = new Instructor("Susan", "Public", "susan.public@luv2code.com");

            InstructorDetail tempInstructorDetail =
                    new InstructorDetail("http://www.youtube.com", "Video Games");

            // associate the objects together
            tempInstructor.setInstructorDetail(tempInstructorDetail);

            // start a transaction
            session.beginTransaction();

            // save the instructor
            //
            // Note: this will SAVE the details object
            // because of CascadeType.ALL
            //
            System.out.println("Saving instructor: " + tempInstructor);
            session.save(tempInstructor);

            // commit transaction
            session.getTransaction().commit();

            System.out.println("Done!");

        }finally {
            //add  clean up code
            session.close();
            factory.close();
        }
    }
}
