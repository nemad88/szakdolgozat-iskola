package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepositorz extends CrudRepository<Message, Long> {
}
