package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.common.CrudService;

import java.time.LocalDateTime;
import java.util.List;


public interface MarkService extends CrudService<Mark, Long> {

    List<Mark> findAllByStudent(Student student);

    List<Mark> filterByStartDate(List<Mark> marks, LocalDateTime startDate);

    List<Mark> filterByEndDate(List<Mark> marks, LocalDateTime endDate);

    List<Mark> filterBySubjectId(List<Mark> marks, Long subjectId);

    List<Mark> filterByTeacherId(List<Mark> marks, Long teacherId);

    List<Mark> filterByMark(List<Mark> marks, String markValue);

}
