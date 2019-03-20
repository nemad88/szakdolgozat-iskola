package hu.adam.nemeth.controllers;

import hu.adam.nemeth.services.CourseService;
import hu.adam.nemeth.services.MessageService;
import hu.adam.nemeth.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;

    public IndexController(SubjectService subjectService, MessageService messageService, CourseService courseService) {
        this.subjectService = subjectService;
        this.messageService = messageService;
        this.courseService = courseService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("messages", messageService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "index";
    }


}
