package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.repositories.MarkRepository;
import hu.adam.nemeth.services.MarkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        return markRepository.findAllByStudent(student)
                .stream()
                .sorted(Comparator.comparing(Mark::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Mark> filterByStartDate(List<Mark> marks, LocalDateTime startDate) {
        List<Mark> filteredMarks = marks.stream()
                .filter(mark -> mark.getDate().isAfter(startDate) || mark.getDate().equals(startDate))
                .collect(Collectors.toList());
        return filteredMarks;
    }

    @Override
    public List<Mark> filterByEndDate(List<Mark> marks, LocalDateTime endDate) {
        List<Mark> filteredMarks = marks.stream()
                .filter(mark -> mark.getDate().isBefore(endDate) || mark.getDate().equals(endDate))
                .collect(Collectors.toList());
        return filteredMarks;
    }

    @Override
    public List<Mark> filterBySubjectId(List<Mark> marks, Long subjectId) {
        List<Mark> filteredMarks = marks.stream()
                .filter(mark -> mark.getSubject().getId().equals(subjectId))
                .collect(Collectors.toList());
        return filteredMarks;
    }

    @Override
    public List<Mark> filterByTeacherId(List<Mark> marks, Long teacherId) {
        List<Mark> filteredMarks = marks.stream()
                .filter(mark -> mark.getTeacher().getId().equals(teacherId))
                .collect(Collectors.toList());
        return filteredMarks;
    }

    @Override
    public List<Mark> filterByMark(List<Mark> marks, String markValue) {
        List<Mark> filteredMarks = marks.stream()
                .filter(mark -> mark.getMarkValue().equals(Integer.parseInt(markValue)))
                .collect(Collectors.toList());
        return filteredMarks;
    }
}
