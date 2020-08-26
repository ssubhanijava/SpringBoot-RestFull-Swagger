package com.subbu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subbu.modal.Student;

@Repository
public interface StudentJpa extends JpaRepository<Student, Integer> {

}
