package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Course;
import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentIndexController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"", "/index"})
    public String student(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByStudent(student);

        List<Course> courses = student.getCourses().stream().sorted(Comparator.comparing(Course::getStartTime).reversed()).collect(Collectors.toList());
        List<Message> messages = messageService.findAllByStudent(student);
        List<Course>beforeCourses = courseService.filterCourseBeforeNow(courses);
        List<Course>afterCourses = courseService.filterCourseAfterNow(courses);



        if(beforeCourses.size() > 5){
            beforeCourses = beforeCourses.subList(0,5);
        }

        if(afterCourses.size() > 5){
            afterCourses = afterCourses.subList(0,5);
        }

        if (marks.size() > 5) {
            marks = marks.subList(0, 5);
        }

        if (messages.size() > 5) {
            messages = messages.subList(0, 5);
        }

        model.addAttribute("marks", marks);
        model.addAttribute("beforeCourses", beforeCourses);
        model.addAttribute("afterCourses", afterCourses);
        model.addAttribute("messages", messages);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        model.addAttribute("user", student);
        return "student/index";
    }


}


