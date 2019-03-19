package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
