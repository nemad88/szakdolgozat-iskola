package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.model.Teacher;

import java.util.Set;

public interface MessageService extends CrudService<Message, Long>{

    Set<Message> findAllByTeacher(Teacher teacher);

    Set<Message> findAllByStudent(Student student);

}
