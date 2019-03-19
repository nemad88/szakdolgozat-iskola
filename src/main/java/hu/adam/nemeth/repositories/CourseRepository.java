package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
