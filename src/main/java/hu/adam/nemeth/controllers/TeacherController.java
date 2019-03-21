package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Teacher;
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
@RequestMapping("/teacher")
public class TeacherController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    TeacherService teacherService;
    MarkService markService;

    @RequestMapping({"", "/", "/index"})
    public String teacher(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/index";
    }

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/timetable";
    }

    @RequestMapping({"/details", "/details.html"})
    public String details(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/details";
    }

    @RequestMapping({"/marks", "/marks.html"})
    public String marks(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());

        return "teacher/marks";
    }

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher= teacherService.findByUserName(user.getUsername());

        return "teacher/messages";
    }

}
