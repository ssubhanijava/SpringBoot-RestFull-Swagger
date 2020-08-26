package com.subbu.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subbu.modal.Student;
import com.subbu.service.StudentService;

@RestController
@RequestMapping(name = "student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping(value = "/save")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {

		Student stu = studentService.saveStudent(student);

		return new ResponseEntity<Student>(stu, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity deleteStudent(@PathVariable Integer id) {

		studentService.deleteStudent(id);

		return new ResponseEntity("Student Delete Successfully", HttpStatus.OK);
	}

	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getStudentById(@PathVariable Integer id) {

		Student student = studentService.findStudentById(id);

		if (student != null) {
			return new ResponseEntity<Object>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Object Not Found In Db", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateStudnet(@PathVariable Integer id, @RequestBody Student student) {

		Student stud = studentService.updateStudnet(student, id);

		if (stud != null) {
			return new ResponseEntity<Object>(stud, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Object Not Found In Db", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping(value = "/partialUpdate/{id}")
	public ResponseEntity<Object> partialUpdate(@PathVariable Integer id, @RequestBody Map<Object, Object> fields) {

		Student student = studentService.findStudentById(id);
		fields.forEach((k, v) -> {

			Field field = ReflectionUtils.findField(Student.class, (String) k);
			field.setAccessible(true);
			ReflectionUtils.setField(field, student, v);
		});

		Student updatestStudent = studentService.saveStudent(student);

		if (updatestStudent != null) {
			return new ResponseEntity<Object>(updatestStudent, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Student Not Found In Db", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/allStudents")
	public ResponseEntity<List<Student>> fetchingAllStudnet() {

		List<Student> allStudnet = studentService.fetchingAllStudnet();

		return new ResponseEntity<List<Student>>(allStudnet, HttpStatus.OK);
	}

}
