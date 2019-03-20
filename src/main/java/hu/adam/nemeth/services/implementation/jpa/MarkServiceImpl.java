package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.repositories.MarkRepository;
import hu.adam.nemeth.services.MarkService;

import java.util.List;

public class MarkServiceImpl implements MarkService {

    MarkRepository markRepository;

    public MarkServiceImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public List<Mark> findAll() {
        return null;
    }

    @Override
    public Mark findById(Long aLong) {
        return null;
    }

    @Override
    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public void delete(Mark object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
