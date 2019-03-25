package hu.adam.nemeth.repositories;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface MarkRepository extends CrudRepository<Mark, Long> {

    List<Mark> findAll();

    List<Mark> findAllByStudent(Student student);

    List<Mark> findAllBySubject(Subject subject);

    List<Mark> findAllBySubjectAndStudent(Subject subject, Student student);

    List<Mark> findAllBySubjectAndStudentAndDateGreaterThan(Subject subject, Student student, Date date);

    List<Mark> findAllBySubjectAndStudentAndDateGreaterThanAndDateLessThan(Subject subject, Student student, Date dateStart, Date dateEnd);

    List<Mark> findAllByStudentAndDateGreaterThan(Student student, Date date);

    List<Mark> findAllBySubjectAndStudentOrderByDateDesc(Subject subject, Student student);
}
