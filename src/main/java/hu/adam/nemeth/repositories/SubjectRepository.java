package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
