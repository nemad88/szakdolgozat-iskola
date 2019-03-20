package hu.adam.nemeth.controllers;

import hu.adam.nemeth.services.CourseService;
import hu.adam.nemeth.services.MessageService;
import hu.adam.nemeth.services.StudentService;
import hu.adam.nemeth.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@AllArgsConstructor
public class StudentController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;

    public StudentController(SubjectService subjectService, MessageService messageService, CourseService courseService, StudentService studentService) {
        this.subjectService = subjectService;
        this.messageService = messageService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @RequestMapping({"student", "student.html", "student/index"})
    public String student(Model model) {
        return "student/index";
    }

    @RequestMapping({"/student/details", "/student/details.html"})
    public String studentDetails(Model model) {
        //TODO: a default erteket ki kell majd torolni
        model.addAttribute("student", studentService.findById(1L));
        return "student/details";
    }



    @RequestMapping({"/student/marks", "/student/marks.html"})
    public String studentMarks(Model model) {
        return "student/marks";
    }

    @RequestMapping({"/student/messages", "/student/messages.html"})
    public String studentMessages(Model model) {
        return "student/messages";
    }
}
