package hu.adam.nemeth.controllers;

import hu.adam.nemeth.model.Mark;
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

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentMarksController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/marks", "/marks.html"})
    public String studentMarks(Model model, @AuthenticationPrincipal UserDetails user) {
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByStudent(student);

        Filter filter = new Filter();
        filter.setDateStart(marks.get(marks.size() - 1).getDate().truncatedTo(ChronoUnit.MINUTES).toString());

        model.addAttribute("user", student);
        model.addAttribute("marks", marks);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", filter);

        return "student/marks";
    }

    @PostMapping("/marks")
    public String greetingSubmit(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) throws ParseException {
        Student student = studentService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByStudent(student);

        System.out.println(filter.getDateStart());


        if (!filter.getDateStart().equals("")) {
            LocalDateTime start = LocalDateTime.parse(filter.getDateStart());
            marks = markService.filterByStartDate(marks, start);
        }

        if (!filter.getDateEnd().equals("")) {
            LocalDateTime end = LocalDateTime.parse(filter.getDateEnd());
            marks = markService.filterByEndDate(marks, end);
        }

        if (!filter.subjectId.equals("-1")) {
            marks = markService.filterBySubjectId(marks, Long.valueOf(filter.getSubjectId()));
        }

        if (!filter.teacherId.equals("-1")) {
            marks = markService.filterByTeacherId(marks, Long.valueOf(filter.getTeacherId()));
        }

        if (!filter.mark.equals("-1")) {
            marks = markService.filterByMark(marks, filter.getMark());
        }

        model.addAttribute("user", student);
        model.addAttribute("marks", marks);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", filter);

        return "student/marks";
    }


    @Getter
    @Setter
    class Filter {
        private String subjectId;
        private String teacherId;
        private String dateStart;
        private String dateEnd;
        private String description;
        private String mark;

        public Filter() {
            this.dateStart = LocalDateTime.now().minusDays(7).truncatedTo(ChronoUnit.MINUTES).toString();
            this.dateEnd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
        }
    }

}


