package hu.adam.nemeth.controllers.teacher;

import hu.adam.nemeth.controllers.student.StudentTimetableController;
import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher")
public class TeacherTimetableController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timeTableWithoutParameter() {
        return "redirect:/teacher/timetable?show=today";
    }

    @RequestMapping(value = "/timetable", params = "show")
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("show") String show) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());
        List<Course> courses = teacher.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime)).collect(Collectors.toList());
        Filter filter = new Filter();

        if (show.equals("today")) {
            courses = courseService.filterCoursesByDay(courses, LocalDate.now().toString());
            filter.setDay(LocalDate.now().toString());
        } else if (show.equals("all")) {

        } else {
            return "redirect:/teacher/timetable";
        }
        model.addAttribute("user", teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teacherService.findAll());
        //TODO refakt service method
        model.addAttribute("dates", courseService.getAllCourseDayByTeacherId(teacher.getId()));
        model.addAttribute("filter", filter);
        return "teacher/timetable";
    }

    @PostMapping(value = "/timetable", params = "show")
    public String filterTimeTable(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        List<Course> courses = teacher.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime).reversed()).collect(Collectors.toList());

        if (!filter.day.equals("-1")) {
            courses = courseService.filterCoursesByDay(courses, filter.day);
        }

        model.addAttribute("user", teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("dates", courseService.getAllCourseDayByStudentId(teacher.getId()));
        model.addAttribute("filter", filter);
        return "teacher/timetable";
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
