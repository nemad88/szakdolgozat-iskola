package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Override
    List<Message> findAll();

    List<Message> findAllByTeacher(Teacher teacher);

    List<Message> findAllByStudent(Student student);

}
