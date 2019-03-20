package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
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
    public String student(Model model) {
        Student student = studentService.findById(1L);
        model.addAttribute("student", student);
        return "student/index";
    }

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timetable(Model model) {
        Student student = studentService.findById(1L);
        model.addAttribute("student", student);
        return "student/timetable";
    }

    @RequestMapping({"/details", "/details.html"})
    public String studentDetails(Model model) {
        Student student = studentService.findById(1L);
        model.addAttribute("student", student);
        return "student/details";
    }

    @RequestMapping({"/marks", "/marks.html"})
    public String studentMarks(Model model) {
        Student student = studentService.findById(1L);
        List<Mark> marks = markService.findAllByStudent(student);
        model.addAttribute("student", student);
        model.addAttribute("marks", marks);
        return "student/marks";
    }

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model) {
        Student student = studentService.findById(1L);
        model.addAttribute("student", student);
        model.addAttribute("messages", messageService.findAllByStudent(student));
        return "student/messages";
    }
}
