package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.*;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentTimetableController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/timetable";
    }
}


