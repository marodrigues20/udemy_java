package com.example.repository;

import com.example.entity.Student;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByName(String name);

    List<Student> findByNameAndEmail(String email, String name);

    List<Student> findByNameOrEmail(String email, String name);

    //it just works when we have embedded Department in our Student Collection
    List<Student> findByDepartmentDepartmentName(String deptName);
    //it just works when we have embedded Subject in our Student Collection
    List<Student> findBySubjectsSubjectName(String subName);

    List<Student> findByEmailIsLike(String email);

    List<Student> findByNameStartsWith(String name);

    // Uset just this method when we are using DBRef annotation in our entity.
    // This retrieve like foreing key in relation db.
    List<Student> findByDepartmentId (String deptId);
    @Query ("{ \"name\" : \"?0\" }")
    List<Student> getByName(String name);
}
