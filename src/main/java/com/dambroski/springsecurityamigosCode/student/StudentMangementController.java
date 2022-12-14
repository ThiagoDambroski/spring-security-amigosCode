package com.dambroski.springsecurityamigosCode.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
	
// hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents(){
		return students;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void newStudent(@RequestBody Student student) {
		System.out.println("newStudent");
		System.out.println(student);
	}
	
	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentID) {
		System.out.println("deleteStudent");
		System.out.println(studentID);
	}
	
	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student) {
		System.out.println("updateStudent");
		System.out.println(String.format("%s %s", studentId,student ));
	}

}
