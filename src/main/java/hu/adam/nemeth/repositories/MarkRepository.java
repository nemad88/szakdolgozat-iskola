package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarkRepository extends CrudRepository<Mark, Long> {

    List<Mark> findAll();

    List<Mark> findAllByStudent(Student student);

}
