package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    List<Teacher> findAll();

    Teacher findByUserName(String userName);
}
