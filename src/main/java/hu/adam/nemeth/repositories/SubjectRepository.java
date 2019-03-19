package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findAll();
}
