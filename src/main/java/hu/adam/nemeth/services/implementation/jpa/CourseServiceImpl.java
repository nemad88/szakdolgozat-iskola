package hu.adam.nemeth.services.implementation.jpa;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.repositories.CourseRepository;
import hu.adam.nemeth.services.CourseService;
import hu.adam.nemeth.services.StudentService;
import hu.adam.nemeth.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    StudentService studentService;
    TeacherService teacherService;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long aLong) {
        return courseRepository.findById(aLong).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public void deleteById(Long aLong) {
        courseRepository.deleteById(aLong);
    }

    @Override
    public List<LocalDate> getAllCourseDayByStudentId(Long id) {
        Student student = studentService.findById(id);
        List<Course> courses = student.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime).reversed()).collect(Collectors.toList());
        LocalDate oldestCourseDate = courses.get(courses.size() - 1).getStartTime().toLocalDate();
        LocalDate newestCourseDate = courses.get(0).getStartTime().toLocalDate();
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = oldestCourseDate; date.isBefore(newestCourseDate) || date.isEqual(newestCourseDate); date = date.plusDays(1)) {
            dates.add(date);

        }
        return dates;
    }

    @Override
    public List<LocalDate> getAllCourseDayByTeacherId(Long id) {
        Teacher teacher = teacherService.findById(id);
        List<Course> courses = teacher.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime).reversed()).collect(Collectors.toList());
        LocalDate oldestCourseDate = courses.get(courses.size() - 1).getStartTime().toLocalDate();
        LocalDate newestCourseDate = courses.get(0).getStartTime().toLocalDate();
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = oldestCourseDate; date.isBefore(newestCourseDate) || date.isEqual(newestCourseDate); date = date.plusDays(1)) {
            dates.add(date);

        }
        return dates;
    }

    @Override
    public List<Course> filterCoursesByDay(List<Course> courses, String day) {
        List<Course> specificDayCourses = courses.stream()
                .filter(course -> course.getStartTime().toLocalDate().equals(LocalDate.parse(day)))
                .collect(Collectors.toList());
        return specificDayCourses;
    }

    @Override
    public List<Course> filterCoursesByTeacherId(List<Course> courses, String teacherId) {
        List<Course> coursesByTeacher = courses.stream()
                .filter(course -> course.getTeacher().getId().equals(Long.valueOf(teacherId)))
                .collect(Collectors.toList());
        return coursesByTeacher;
    }

    @Override
    public List<Course> filterCourseAfterNow(List<Course> courses) {
        return courses.stream()
                .filter(course -> course.getStartTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Course> filterCourseBeforeNow(List<Course> courses) {
        return courses.stream()
                .filter(course -> course.getStartTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

}
