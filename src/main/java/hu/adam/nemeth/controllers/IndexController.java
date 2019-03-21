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
public class IndexController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;

    @RequestMapping({"", "/", "/index"})
    public String student(Model model, @AuthenticationPrincipal UserDetails user) {

        String role = studentService.findByUserName(user.getUsername()).getRole();
        if (role.equals("ROLE_STUDENT")){
            return "redirect:/student";
        } else {
            return "redirect:/teacher";
        }
    }
}
