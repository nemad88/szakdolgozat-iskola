package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.CourseRepository;
import hu.adam.nemeth.repositories.MessageRepository;
import hu.adam.nemeth.services.CourseService;
import hu.adam.nemeth.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long aLong) {
        return null;
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Course object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
