package hu.adam.nemeth.controllers.teacher;

import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher")
public class TeacherDetailsController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    TeacherService teacherService;
    MarkService markService;

    @RequestMapping({"/details", "/details.html"})
    public String details(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());
        model.addAttribute("user", teacher);
        return "teacher/details";
    }

}
