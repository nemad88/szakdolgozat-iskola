package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;

    @RequestMapping({"", "/", "/index"})
    public String student(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/index";
    }

    @RequestMapping({"/timetable", "/timetable.html"})
    public String timetable(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/timetable";
    }

    @RequestMapping({"/details", "/details.html"})
    public String studentDetails(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        return "student/details";
    }

    @RequestMapping({"/marks", "/marks.html"})
    public String studentMarks(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByStudent(student);
        model.addAttribute("user", student);
        model.addAttribute("marks", marks);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", new Filter());
        for (Subject subject : subjectService.findAll()) {
            System.out.println(subject.getDescription());
        }
        return "student/marks";
    }

    @PostMapping("/marks")
    public String greetingSubmit(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) throws ParseException {
        System.out.println("UJ USER");
        System.out.println(filter.getId());
        Subject subject = subjectService.findById(Long.valueOf(filter.getId()));
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks;


        try {
            String[] dateStart = filter.getDateStart().split("-");
            String[] dateEnd= filter.getDateEnd().split("-");

            DateFormat formatStart = new SimpleDateFormat("yyyy-MM-dd");
            Date start = formatStart.parse(dateStart[0] + "-" + dateStart[1]  + "-" + dateStart[2]);

            DateFormat formatEnd = new SimpleDateFormat("yyyy-MM-dd");
            Date end = formatEnd.parse(dateEnd[0] + "-" + dateEnd[1]  + "-" + dateEnd[2]);

            if(Integer.valueOf(filter.getId()) > 0){
//                marks = markService.findAllBySubjectAndStudentAndDateGreaterThan(subject,student,start);

                marks = markService.findAllBySubjectAndStudentAndDateGreaterThanAndDateLessThan(subject, student, start, end);

            } else {
                marks = markService.findAllByStudentAndDateGreaterThan(student, start);
            }


        } catch (Exception e){
            marks = markService.findAllBySubjectAndStudentOrderByDateDesc(subject, student);
        }


        model.addAttribute("user", student);
        model.addAttribute("marks", marks);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", new Filter());

        return "student/marks";
    }

    @RequestMapping({"/messages", "/messages.html"})
    public String studentMessages(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        model.addAttribute("user", student);
        model.addAttribute("messages", messageService.findAllByStudent(student));
        return "student/messages";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class Filter {
        private String id;
        private String dateStart;
        private String dateEnd;
        private String description;
    }

}


