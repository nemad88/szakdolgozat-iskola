package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.repositories.StudentRepository;
import hu.adam.nemeth.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long aLong) {
        return studentRepository.findById(aLong).orElse(null);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
