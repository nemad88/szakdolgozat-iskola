package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentDetailsController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/details", "/details.html"})
    public String studentDetails(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/details";
    }
}


