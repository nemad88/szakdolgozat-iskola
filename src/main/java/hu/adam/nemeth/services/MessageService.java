package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.common.CrudService;

import java.util.List;


public interface MessageService extends CrudService<Message, Long> {

    List<Message> findAllByTeacher(Teacher teacher);

    List<Message> findAllByStudent(Student student);

}
