package hu.adam.nemeth.controllers.teacher;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
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

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/teacher")
public class TeacherMarksController {

    SubjectService subjectService;
    MessageService messageService;
    CourseService courseService;
    StudentService studentService;
    MarkService markService;
    TeacherService teacherService;

    @RequestMapping({"/marks", "/marks.html"})
    public String listMarks(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByTeacher(teacher);

        Filter filter = new Filter();
        filter.setDateStart(marks.get(marks.size() - 1).getDate().truncatedTo(ChronoUnit.MINUTES).toString());

        model.addAttribute("user", teacher);
        model.addAttribute("marks", marks);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", filter);

        return "teacher/marks";
    }

    @GetMapping({"/newMark"})
    public String newMark(Model model, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        ActualMark actualMark = new ActualMark();
        model.addAttribute("user", teacher);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("actualmark", actualMark);
        return "teacher/newMark";
    }

    @PostMapping("/saveMark")
    public String savaMark(Model model, @ModelAttribute ActualMark actualMark, @AuthenticationPrincipal UserDetails user) throws ParseException {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        Student student = studentService.findById(Long.valueOf(actualMark.studentId));

        if (student == null){
            model.addAttribute("user", teacher);
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("subjects", subjectService.findAll());
            model.addAttribute("actualmark", actualMark);
            return "redirect:/newMark";
        }

        List<Mark> marks = markService.findAllByTeacher(teacher);
        Mark mark = new Mark();
        String[] markDetails = actualMark.getMark().split("-");
        mark.setMarkValue(Integer.valueOf(markDetails[0]));
        mark.setMarkName(markDetails[1]);
        mark.setDate(LocalDateTime.now());
        mark.setStudent(student);
        mark.setTeacher(teacher);
        mark.setSubject(subjectService.findById(Long.valueOf(actualMark.getSubjectId())));
        markService.save(mark);
        return "redirect:/teacher/newMark?success";
    }

    @PostMapping("/marks")
    public String filterMarks(Model model, @ModelAttribute Filter filter, @AuthenticationPrincipal UserDetails user) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        List<Mark> marks = markService.findAllByTeacher(teacher);

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

        if (!filter.studentId.equals("-1")) {
            marks = markService.filterByStudentId(marks, Long.valueOf(filter.studentId));
        }

        if (!filter.mark.equals("-1")) {
            marks = markService.filterByMark(marks, filter.getMark());
        }
        model.addAttribute("user", teacher);
        model.addAttribute("marks", marks);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("filter", filter);
        return "teacher/marks";
    }

    @GetMapping(value = "/deletemark", params = "delete")
    public String deleteMark(Model model, @AuthenticationPrincipal UserDetails user, @RequestParam("delete") String delete) {
        Teacher teacher = teacherService.findByUserName(user.getUsername());
        Mark mark = markService.findById(Long.valueOf(delete));
        if (mark.getTeacher().equals(teacher)) {
            markService.delete(mark);
        }
        return "redirect:/teacher/marks";
    }

    @Getter
    @Setter
    class Filter {
        private String subjectId;
        private String studentId;
        private String dateStart;
        private String dateEnd;
        private String description;
        private String mark;

        public Filter() {
            this.dateStart = LocalDateTime.now().minusDays(7).truncatedTo(ChronoUnit.MINUTES).toString();
            this.dateEnd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class ActualMark {
        String mark;
        String studentId;
        String subjectId;
    }
}
