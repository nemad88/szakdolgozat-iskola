package hu.adam.nemeth.controllers.teacher;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher")
public class TeacherMessagesController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    TeacherService teacherService;
    MarkService markService;
    StudentService studentService;

    @RequestMapping({"/messages", "/messages.html"})
    public String listAllMessage(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        List<Message> messages = messageService.findAllByTeacher(teacher);
        Filter filter = new Filter();
        if (messages.size() > 0) {
            filter.setDateStart(messages.get(messages.size() - 1).getDate().truncatedTo(ChronoUnit.MINUTES).toString());
        }
        model.addAttribute("user", teacher);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "teacher/messages";
    }

    @PostMapping("/messages")
    public String filterMessages(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        List<Message> messages = messageService.findAllByTeacher(teacher);


        if (!filter.getDateStart().equals("")) {
            LocalDateTime start = LocalDateTime.parse(filter.getDateStart());
            messages = messageService.filterByStartDate(messages, start);
        }

        if (!filter.getDateEnd().equals("")) {
            LocalDateTime end = LocalDateTime.parse(filter.getDateEnd());
            messages = messageService.filterByEndDate(messages, end);
        }

        if (!filter.studentId.equals("-1")) {
            messages = messageService.filterByStudentId(messages, Long.valueOf(filter.studentId));
        }

        model.addAttribute("user", teacher);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "teacher/messages";
    }

    @RequestMapping("/newMessage")
    public String createNewMessage(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        ActualMessage actualMessage = new ActualMessage();
        actualMessage.studentId = teacher.getId().toString();
        actualMessage.studentId = "-1";
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("user", teacher);
        model.addAttribute("message", actualMessage);
        return "teacher/newMessage";
    }

    @PostMapping("/saveMessage")
    public String saveMessage(Model model, @ModelAttribute ActualMessage message, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        Message messageToSave = new Message();
        if (!message.messageId.equals("")) {
            messageToSave = messageService.findById(Long.valueOf(message.messageId));
        }

        messageToSave.setTeacher(teacherService.findByUserName(user.getUsername()));
        messageToSave.setStudent(studentService.findById(Long.valueOf(message.studentId)));
        messageToSave.setDate(LocalDateTime.now());
        messageToSave.setMessageTitle(message.title);
        messageToSave.setDescription(message.description);

        if (messageToSave.getStudent() == null) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("user", teacher);
            model.addAttribute("message", message);
            return "teacher/newMessage";
        }

        messageService.save(messageToSave);
        return "redirect:/teacher/messages";
    }

    @RequestMapping(value = "/deletemessage", params = "delete")
    public String deleteMessage(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("delete") String delete) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        Message message = messageService.findById(Long.valueOf(delete));
        if (message.getTeacher().equals(teacher)) {
            messageService.delete(message);
        }
        return "redirect:/teacher/messages";
    }

    @RequestMapping("/message/{id}")
    public String messageDetails(Model model, @AuthenticationPrincipal UserDetails user, @PathVariable("id") String id) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        Message message = messageService.findById(Long.valueOf(id));

        if (!message.getTeacher().getId().equals(teacher.getId())) {
            return "redirect:/teacher/messages";
        }

        model.addAttribute("user", teacher);
        model.addAttribute("message", message);
        return "teacher/messagedetails";
    }

    @Getter
    @Setter
    class Filter {
        private String studentId;
        private String dateStart;
        private String dateEnd;

        public Filter() {
            this.dateStart = LocalDateTime.now().minusDays(7).truncatedTo(ChronoUnit.MINUTES).toString();
            this.dateEnd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class ActualMessage {
        String teacherId;
        String studentId;
        String title;
        String description;
        String messageId;
    }
}
