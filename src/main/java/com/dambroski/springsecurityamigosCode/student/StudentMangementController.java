package com.dambroski.springsecurityamigosCode.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentMangementController {
	
	
	private static final List<Student> students = Arrays.asList(
			Student.builder().studentId((long) 1).name("thiago").build(), 
			Student.builder().studentId((long) 2).name("leticia").build(),
			Student.builder().studentId((long) 3).name("denji").build()
			);
	
	@GetMapping
	public List<Student> getAllStudents(){
		return students;
	}
	
	@PostMapping
	public void newStudent(@RequestBody Student student) {
		System.out.println(student);
	}
	
	@DeleteMapping(path = "{studentId}")
	public void deleteStudent(@PathVariable("studentId") Integer studentID) {
		System.out.println(studentID);
	}
	
	@PutMapping(path = "{studentId}")
	public void updateStudent(@PathVariable("studentId") Integer studentId, Student student) {
		System.out.println(String.format("%s %s", studentId,student ));
	}

}
