package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findAll();

    Student findByUserName(String userName);

}
