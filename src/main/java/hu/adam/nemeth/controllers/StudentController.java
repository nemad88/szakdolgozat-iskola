package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;

    @RequestMapping({"", "/", "/index"})
    public String student(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/index";
    }

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/timetable";
    }

    @RequestMapping({"/details", "/details.html"})
    public String studentDetails(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/details";
    }

    @RequestMapping({"/marks", "/marks.html"})
    public String studentMarks(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByStudent(student);
        model.addAttribute("user", student);
        model.addAttribute("marks", marks);
        return "student/marks";
    }

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        model.addAttribute("messages", messageService.findAllByStudent(student));
        return "student/messages";
    }

}
