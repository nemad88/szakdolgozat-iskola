package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.repositories.MarkRepository;
import hu.adam.nemeth.services.MarkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class MarkServiceImpl implements MarkService {

    MarkRepository markRepository;

    @Override
    public List<Mark> findAll() {
        return markRepository.findAll();
    }

    @Override
    public Mark findById(Long aLong) {
        return markRepository.findById(aLong).orElse(null);
    }

    @Override
    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public void delete(Mark mark) {
        markRepository.delete(mark);
    }

    @Override
    public void deleteById(Long aLong) {
        markRepository.deleteById(aLong);
    }

    @Override
    public List<Mark> findAllByStudent(Student student) {
        return markRepository.findAllByStudent(student);
    }

    @Override
    public List<Mark> findAllBySubject(Subject subject) {
        return markRepository.findAllBySubject(subject);
    }

    public List<Mark> findAllBySubjectAndStudent(Subject subject, Student student){
        return markRepository.findAllBySubjectAndStudent(subject, student);
    }

    public List<Mark> findAllBySubjectAndStudentOrderByDateDesc(Subject subject, Student student){
        return markRepository.findAllBySubjectAndStudentOrderByDateDesc(subject, student);
    }


    public List<Mark> findAllBySubjectAndStudentAndDateGreaterThan(Subject subject, Student student, Date dateStart){
        return markRepository.findAllBySubjectAndStudentAndDateGreaterThan(subject, student, dateStart);
    }

    public List<Mark> findAllBySubjectAndStudentAndDateGreaterThanAndDateLessThan(Subject subject, Student student, Date dateStart, Date dateEnd){
        return markRepository.findAllBySubjectAndStudentAndDateGreaterThanAndDateLessThan(subject, student, dateStart, dateEnd);
    }


    public List<Mark> findAllByStudentAndDateGreaterThan(Student student, Date date){
        return markRepository.findAllByStudentAndDateGreaterThan(student, date);
    }

}
