package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Override
    List<Course> findAll();

}
