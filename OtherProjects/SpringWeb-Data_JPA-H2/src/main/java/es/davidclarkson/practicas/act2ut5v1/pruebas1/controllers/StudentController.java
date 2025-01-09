package es.davidclarkson.practicas.act2ut5v1.pruebas1.controllers;


import es.davidclarkson.practicas.act2ut5v1.pruebas1.entities.Student;
import es.davidclarkson.practicas.act2ut5v1.pruebas1.repositories.StudentRepository;
import es.davidclarkson.practicas.act2ut5v1.pruebas1.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentRepository studentRepository;
	private final StudentService studentService;

	public StudentController(StudentRepository studentRepository, StudentService studentService) {
		this.studentRepository = studentRepository;
		this.studentService = studentService;
	}


	@RequestMapping("")
	public ResponseEntity<List<Student>> getAllStudents() {

		List<Student> students = new ArrayList<>();

/*		students.add( new Student(1, "Paco", "Perez", LocalDate.of(2001,3,6)));
		students.add( new Student(2, "Rigoberto", "Gonzalez", LocalDate.of(2011,5,16)));
		return ResponseEntity.ok(students); */

		return ResponseEntity.ok(studentRepository.findAll());


	}


//	@RequestMapping(value = "/api/students/{id}", method = RequestMethod.GET)
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id) {
//		return ResponseEntity.notFound().build();

		Optional<Student> student = studentService.findById(id);

		if (student.isPresent()) {
			return ResponseEntity.ok(student.orElseThrow());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	@RequestMapping(value = "/api/students/{id}", method = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Student> deleteStudentsById(@PathVariable("id") Integer id) {
		return ResponseEntity.notFound().build();
	}




	@GetMapping("/{page}/{pageSize}")
	public ResponseEntity<List<Student>> finadAllPaged(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
		return ResponseEntity.ok(studentService.findAllPaged(page, pageSize));
	}

}
