package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.repositories.StudentRepository;
import hu.adam.nemeth.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

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
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public void deleteById(Long aLong) {
        studentRepository.deleteById(aLong);
    }

    @Override
    public Student findByUserName(String userName) {
        return studentRepository.findByUserName(userName);
    }
}
