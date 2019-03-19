package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.repositories.StudentRepository;
import hu.adam.nemeth.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Set<Student> findAll() {
        return null;
    }

    @Override
    public Student findById(Long aLong) {
        return null;
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
