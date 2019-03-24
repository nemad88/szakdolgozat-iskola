package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.services.common.CrudService;


import java.util.Date;
import java.util.List;


public interface MarkService extends CrudService<Mark, Long> {

    List<Mark> findAllByStudent(Student student);

    List<Mark> findAllBySubject(Subject subject);

    List<Mark> findAllBySubjectAndStudent(Subject subject, Student student);

    List<Mark> findAllBySubjectAndStudentOrderByDateDesc(Subject subject, Student student);

    List<Mark>findAllBySubjectAndStudentAndDateGreaterThan(Subject subject, Student student, Date date);

    List<Mark>findAllBySubjectAndStudentAndDateGreaterThanAndDateLessThan(Subject subject, Student student, Date dateStart, Date dateEnd);


    List<Mark> findAllByStudentAndDateGreaterThan(Student student, Date date);



}
