package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.TeacherRepository;
import hu.adam.nemeth.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {


    TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Set<Teacher> findAll() {
        return null;
    }

    @Override
    public Teacher findById(Long aLong) {
        return null;
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(Teacher object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
