package com.br.restApi.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.restApi.exception.ResourceNotFoundException;
import com.br.restApi.model.Student;
import com.br.restApi.repository.StudendRepository;

@RestController 
@RequestMapping("v1")
public class StudentController {

	private final StudendRepository studentRepository;

	@Autowired
	public StudentController(StudendRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	

	@GetMapping("protected/students") //student?page=0&size=15sort=name,asc
	public ResponseEntity<?> listarContas(@PageableDefault(size = 10) Pageable pageable) {
			return new ResponseEntity<>(studentRepository.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("protected/students/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails ) {
		verifyIdStudentExists(id);
		Student student = studentRepository.studentById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@GetMapping("protected/students/name/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}

//	@transactional used to do rollback in unchecked exception
	@PostMapping("admin/students")
	@Transactional(rollbackFor = Exception.class )
	public ResponseEntity<Student> listStudent(@Valid @RequestBody Student student) {
		return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
	}

	@DeleteMapping("admin/students/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
		studentRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("admin/students")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		verifyIdStudentExists(student.getId());
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	
	private void verifyIdStudentExists(Long id) {
		if (studentRepository.studentById(id) == null)
			throw new ResourceNotFoundException("Student not Found for id" + id);
	}
	
	

	
}
