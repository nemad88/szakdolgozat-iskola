package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.TeacherRepository;
import hu.adam.nemeth.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll().stream()
                .sorted(Comparator.comparing(Teacher::getLastName).thenComparing(Teacher::getFirstName))
                .collect(Collectors.toList());
    }

    @Override
    public Teacher findById(Long aLong) {
        return teacherRepository.findById(aLong).orElse(null);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(Teacher object) {
        teacherRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        teacherRepository.deleteById(aLong);
    }

    @Override
    public Teacher findByUserName(String userName) {
        return teacherRepository.findByUserName(userName);
    }

}
