package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Set<Message> findAllByTeacher(Teacher teacher);

    Set<Message> findAllByStudent(Student student);

}
