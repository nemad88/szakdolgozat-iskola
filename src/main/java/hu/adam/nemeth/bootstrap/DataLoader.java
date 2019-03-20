//package hu.adam.nemeth.bootstrap;
//
//import hu.adam.nemeth.model.*;
//import hu.adam.nemeth.repositories.CourseRepository;
//import hu.adam.nemeth.repositories.MarkRepository;
//import hu.adam.nemeth.services.MessageService;
//import hu.adam.nemeth.services.StudentService;
//import hu.adam.nemeth.services.SubjectService;
//import hu.adam.nemeth.services.TeacherService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final SubjectService subjectService;
//    private final MessageService messageService;
//    private final StudentService studentService;
//    private final TeacherService teacherService;
//    private final CourseRepository courseRepository;
//    private final MarkRepository markRepository;
//
//
//    public DataLoader(SubjectService subjectService, MessageService messageService, StudentService studentService, TeacherService teacherService, CourseRepository courseRepository, MarkRepository markRepository) {
//        this.subjectService = subjectService;
//        this.messageService = messageService;
//        this.studentService = studentService;
//        this.teacherService = teacherService;
//        this.courseRepository = courseRepository;
//        this.markRepository = markRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        int count = subjectService.findAll().size();
//
//        if (count == 0) {
//            loadData();
//        }
//    }
//
//    private void loadData() {
//
//        //Create Subjects
//
//        Subject subject01 = new Subject("Matematika");
//        Subject savedSubject01 = subjectService.save(subject01);
//        Subject subject02 = new Subject("Testnevelés");
//        Subject savedSubject02 = subjectService.save(subject02);
//        Subject subject03 = new Subject("Irodalom");
//        Subject savedSubject03 = subjectService.save(subject03);
//        Subject subject04 = new Subject("Földrajz");
//        Subject savedSubject04 = subjectService.save(subject04);
//        Subject subject05 = new Subject("Informatika");
//        Subject savedSubject05 = subjectService.save(subject05);
//        Subject subject06 = new Subject("Ének");
//        Subject savedSubject06 = subjectService.save(subject06);
//        Subject subject07 = new Subject("Rajz");
//        Subject savedSubject07 = subjectService.save(subject07);
//
//        //Create Teachers
//
//        Teacher teacher01 = new Teacher();
//        teacher01.setLastName("Dr. Nagy");
//        teacher01.setFirstName("Ferenc");
//        teacherService.save(teacher01);
//
//        Teacher teacher02 = new Teacher();
//        teacher02.setLastName("Kovács");
//        teacher02.setFirstName("Péter");
//        teacherService.save(teacher02);
//
//        Teacher teacher03 = new Teacher();
//        teacher03.setLastName("Pusztaszery");
//        teacher03.setFirstName("Kornél");
//        teacherService.save(teacher03);
//
//        //Create Student
//
//        Student student01 = new Student();
//        student01.setLastName("Júlia");
//        student01.setFirstName("Kiss");
//        studentService.save(student01);
//
//        Student student02 = new Student();
//        student02.setLastName("Tóth");
//        student02.setFirstName("Béla");
//        studentService.save(student02);
//
//        Student student03 = new Student();
//        student03.setLastName("Dezső");
//        student03.setFirstName("Irén");
//        studentService.save(student03);
//
//        //Create Messages
//
//        Message message01 = new Message(1L, "Elbocsátás", student01, teacher02);
//        Message message02 = new Message(2L, "Igazgatói dícséret", student01, teacher01);
//        Message message03 = new Message(3L, "Kiváló tanuló", student01, teacher03);
//        Message message04 = new Message(4L, "A WC-ben cigizett", student01, teacher01);
//        Message message05 = new Message(5L, "Órán beszélgetett", student01, teacher01);
//        Message message06 = new Message(6L, "Jó sportoló", student01, teacher01);
//
//        messageService.save(message01);
//        messageService.save(message02);
//        messageService.save(message03);
//        messageService.save(message04);
//        messageService.save(message05);
//        messageService.save(message06);
//
//
//        //Create course
//        Course course01 = new Course(new Date(), new Date(), teacher01, subject01, "A terem");
//        Course course02 = new Course(new Date(), new Date(), teacher02, subject02, "V terem");
//        Course course03 = new Course(new Date(), new Date(), teacher03, subject01, "X terem");
//
//        courseRepository.save(course01);
//        courseRepository.save(course02);
//        courseRepository.save(course03);
//
//        //Create marks
//
//        Mark mark01= new Mark();
//        mark01.setMark("jeles");
//        mark01.setSubject(subject01);
//        mark01.setStudent(student01);
//
//        Mark mark02= new Mark();
//        mark02.setSubject(subject01);
//        mark02.setStudent(student01);
//        mark02.setMark("közepes");
//
//        Mark mark03= new Mark();
//        mark03.setSubject(subject03);
//        mark03.setStudent(student01);
//        mark03.setMark("jó");
//
//        Mark mark04= new Mark();
//        mark04.setStudent(student01);
//        mark04.setSubject(subject02);
//        mark04.setMark("elégtelen");
//
//        Mark mark05= new Mark();
//        mark05.setStudent(student01);
//        mark05.setSubject(subject04);
//        mark05.setMark("jeles");
//
//        Mark mark06= new Mark();
//        mark06.setStudent(student01);
//        mark06.setSubject(subject03);
//        mark06.setMark("jeles");
//
//        Mark mark07= new Mark();
//        mark07.setStudent(student01);
//        mark07.setSubject(subject02);
//        mark07.setMark("jeles");
//
//        Mark mark08= new Mark();
//        mark08.setStudent(student01);
//        mark08.setSubject(subject04);
//        mark08.setMark("elégséges");
//
//        Mark mark09= new Mark();
//        mark09.setStudent(student01);
//        mark09.setSubject(subject06);
//        mark09.setMark("közepes");
//
//        markRepository.save(mark01);
//        markRepository.save(mark02);
//        markRepository.save(mark03);
//        markRepository.save(mark04);
//        markRepository.save(mark05);
//        markRepository.save(mark06);
//        markRepository.save(mark07);
//        markRepository.save(mark08);
//        markRepository.save(mark09);
//
//
//    }
//}
