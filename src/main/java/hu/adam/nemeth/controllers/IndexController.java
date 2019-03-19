package hu.adam.nemeth.controllers;

import hu.adam.nemeth.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    SubjectService subjectService;

    public IndexController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "index";
    }


}
