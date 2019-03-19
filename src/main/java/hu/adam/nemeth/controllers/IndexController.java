package hu.adam.nemeth.controllers;

import hu.adam.nemeth.services.MessageService;
import hu.adam.nemeth.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    SubjectService subjectService;
    MessageService messageService;

    public IndexController(SubjectService subjectService, MessageService messageService) {
        this.subjectService = subjectService;
        this.messageService = messageService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("messages", messageService.findAll());
        return "index";
    }


}
