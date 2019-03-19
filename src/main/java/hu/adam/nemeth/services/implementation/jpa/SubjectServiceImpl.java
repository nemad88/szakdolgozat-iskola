package hu.adam.nemeth.services.implementation.jpa;


import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.repositories.SubjectRepository;
import hu.adam.nemeth.services.SubjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    public final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Set<Subject> findAll() {
        Set<Subject> subjects = new HashSet<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public Subject findById(Long aLong) {
        return subjectRepository.findById(aLong).orElse(null);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void delete(Subject subject) {
        subjectRepository.delete(subject);
    }

    @Override
    public void deleteById(Long aLong) {
        subjectRepository.deleteById(aLong);
    }
}
