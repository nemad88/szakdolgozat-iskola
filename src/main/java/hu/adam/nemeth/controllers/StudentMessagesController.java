package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentMessagesController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Message> messages = messageService.findAllByStudent(student);

        Filter filter = new Filter();

        if (messages.size() > 0) {
            filter.setDateStart(messages.get(messages.size() - 1).getDate().truncatedTo(ChronoUnit.MINUTES).toString());
        }

        model.addAttribute("user", student);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "student/messages";
    }

    @RequestMapping(value = "/message", params = "id")
    public String studentMessage(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("id") String id) {
        Message message = messageService.findById(Long.valueOf(id));
        Student student = studentService.findByUserName(user.getUsername());

        if (message.getStudent().getId().equals(student.getId())) {
            System.out.println(message.getDescription());
        } else {
            return "redirect:/student/messages";
        }

        model.addAttribute("user", student);
        model.addAttribute("message", message);
        return "student/messagedetails";
    }

    @PostMapping("/messages")
    public String filterMessage(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) throws ParseException {
        Student student = studentService.findByUserName(user.getUsername());
        List<Message> messages = messageService.findAllByStudent(student);

        if (!filter.getDateStart().equals("")) {
            LocalDateTime start = LocalDateTime.parse(filter.getDateStart());
            messages = messageService.filterByStartDate(messages, start);
        }

        if (!filter.getDateEnd().equals("")) {
            LocalDateTime end = LocalDateTime.parse(filter.getDateEnd());
            messages = messageService.filterByEndDate(messages, end);
        }

        if (!filter.teacherId.equals("-1")) {
            messages = messageService.filterByTeacherId(messages, Long.valueOf(filter.getTeacherId()));
        }

        model.addAttribute("user", student);
        model.addAttribute("messages", messages);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("filter", filter);

        return "student/messages";
    }

    @Getter
    @Setter
    class Filter {
        private String teacherId;
        private String dateStart;
        private String dateEnd;

        public Filter() {
            this.dateStart = LocalDateTime.now().minusDays(7).truncatedTo(ChronoUnit.MINUTES).toString();
            this.dateEnd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
        }
    }

}


