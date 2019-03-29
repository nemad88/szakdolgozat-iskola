package hu.adam.nemeth.controllers.student;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentTimetableController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @GetMapping(value = "/timetable")
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam(value = "show", required = false) String show) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Course> courses = student.getCourses().stream()
                .sorted(Comparator.comparing(Course::getStartTime).reversed())
                .collect(Collectors.toList());

        Filter filter = new Filter();

        if (show != null) {
        } else {
            filter.setDay(LocalDate.now().toString());
            courses = courseService.filterCoursesByDay(courses, LocalDate.now().toString());
        }

        model.addAttribute("user", student);
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("dates", courseService.getAllCourseDayByStudentId(student.getId()));
        model.addAttribute("filter", filter);
        return "student/timetable";
    }

    @PostMapping(value = "/timetable")
    public String filterTimeTable(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Course> courses = student.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime).reversed()).collect(Collectors.toList());
        if (!filter.day.equals("-1")) {
            courses = courseService.filterCoursesByDay(courses, filter.day);
        }
        if (!filter.teacherId.equals("-1")) {
            courses = courseService.filterCoursesByTeacherId(courses, filter.teacherId);
        }
        model.addAttribute("user", student);
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("dates", courseService.getAllCourseDayByStudentId(student.getId()));
        model.addAttribute("filter", filter);
        return "student/timetable";
    }

    @Getter
    @Setter
    class Filter {
        private String day;
        private String teacherId;
        public Filter() {
            this.day = "-1";
        }
    }

}


