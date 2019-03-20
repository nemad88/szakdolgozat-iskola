package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark, Long> {
}
