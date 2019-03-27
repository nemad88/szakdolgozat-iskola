package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.common.Person;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Person person = studentService.findByUserName(user.getUsername());

        if (person != null) {
            return "redirect:/student";
        } else {
            return "redirect:/teacher";
        }
    }


}
