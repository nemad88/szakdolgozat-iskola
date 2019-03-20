package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.repositories.MarkRepository;
import hu.adam.nemeth.services.MarkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
