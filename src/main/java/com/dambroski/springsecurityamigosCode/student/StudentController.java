package com.dambroski.springsecurityamigosCode.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	private static final List<Student> students = Arrays.asList(
			Student.builder().studentId((long) 1).name("thiago").build(), 
			Student.builder().studentId((long) 2).name("leticia").build(),
			Student.builder().studentId((long) 3).name("denji").build()
			);
	
	
	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Long studentId) {
		System.out.println(students);
		return students.stream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Student " + studentId + " not found"));
	}
	

}
