package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    //These following jpa queries generally returns object or list of objects
    Student findByEmail(String email);

    //Select * from student where country = India //Returns list of entities
    List<Student> findByCountry(String country);
}
