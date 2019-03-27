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
public class TeacherMessagesController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    TeacherService teacherService;
    MarkService markService;

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());
        model.addAttribute("user", teacher);
        return "teacher/messages";
    }

}
