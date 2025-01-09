package es.davidclarkson.practicas.act2ut5v1.pruebas1.services;

import es.davidclarkson.practicas.act2ut5v1.pruebas1.entities.Student;
import es.davidclarkson.practicas.act2ut5v1.pruebas1.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository){
		this.studentRepository = studentRepository;
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Optional<Student> findById(Integer id) {
		return studentRepository.findById(id);
	}

	@Override
	public List<Student> findAllPaged(int page, int pageSize) {
		PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("firstName", "lastName"));
		Page<Student> studentsPage = studentRepository.findAll(pageRequest);
		return studentsPage.getContent();
	}
}
