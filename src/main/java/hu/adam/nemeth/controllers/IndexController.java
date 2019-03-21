package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.CourseService;
import hu.adam.nemeth.services.MessageService;
import hu.adam.nemeth.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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

    @RequestMapping({"test"})
    public String test(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("messages", messageService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "test";
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        return "index";
    }

}
