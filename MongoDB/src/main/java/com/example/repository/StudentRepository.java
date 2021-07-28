package com.example.repository;

import com.example.entity.Student;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findByName(String name);

    List<Student> findByNameAndEmail(String email, String name);

    List<Student> findByNameOrEmail(String email, String name);

    List<Student> findByDepartmentDepartmentName(String deptName);

    List<Student> findBySubjectsSubjectName(String subName);

    List<Student> findByEmailIsLike(String email);

    List<Student> findByNameStartsWith(String name);

}
