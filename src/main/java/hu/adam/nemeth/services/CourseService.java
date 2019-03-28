package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.services.common.CrudService;

import java.time.LocalDate;
import java.util.List;

public interface CourseService extends CrudService<Course, Long> {


    List<LocalDate> getAllCourseDayByStudentId(Long id);

    List<LocalDate> getAllCourseDayByTeacherId(Long id);

    List<Course> filterCoursesByDay(List<Course> courses, String day);

    List<Course> filterCoursesByTeacherId(List<Course> courses, String teacherId);

    List<Course> filterCourseAfterNow(List<Course> courses);

    List<Course> filterCourseBeforeNow(List<Course> courses);

}
