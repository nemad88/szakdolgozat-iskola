package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.common.CrudService;

import java.time.LocalDateTime;
import java.util.List;


public interface MessageService extends CrudService<Message, Long> {

    List<Message> findAllByTeacher(Teacher teacher);

    List<Message> findAllByStudent(Student student);

    List<Message> filterByStartDate(List<Message> messages, LocalDateTime startDate);

    List<Message> filterByEndDate(List<Message> messages, LocalDateTime endDate);

    List<Message> filterByTeacherId(List<Message> messages, Long teacherId);

    List<Message> filterByStudentId(List<Message> messages, Long teacherId);

}
