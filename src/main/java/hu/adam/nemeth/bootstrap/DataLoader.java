package hu.adam.nemeth.bootstrap;

import hu.adam.nemeth.model.Message;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.model.Subject;
import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.MessageService;
import hu.adam.nemeth.services.StudentService;
import hu.adam.nemeth.services.SubjectService;
import hu.adam.nemeth.services.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final SubjectService subjectService;
    private final MessageService messageService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public DataLoader(SubjectService subjectService, MessageService messageService, StudentService studentService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.messageService = messageService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = subjectService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {

        //Create Subjects

        Subject subject01 = new Subject("Matematika");
        Subject savedSubject01 = subjectService.save(subject01);
        Subject subject02 = new Subject("Testnevelés");
        Subject savedSubject02 = subjectService.save(subject02);
        Subject subject03 = new Subject("Irodalom");
        Subject savedSubject03 = subjectService.save(subject03);
        Subject subject04 = new Subject("Földrajz");
        Subject savedSubject04 = subjectService.save(subject04);
        Subject subject05 = new Subject("Informatika");
        Subject savedSubject05 = subjectService.save(subject05);
        Subject subject06 = new Subject("Ének");
        Subject savedSubject06 = subjectService.save(subject06);
        Subject subject07 = new Subject("Rajz");
        Subject savedSubject07 = subjectService.save(subject07);


        //Create Teachers

        Teacher teacher01 = new Teacher();
        teacher01.setLastName("Dr. Nagy");
        teacher01.setFirstName("Ferenc");
        teacherService.save(teacher01);

        Teacher teacher02 = new Teacher();
        teacher02.setLastName("Kovács");
        teacher02.setFirstName("Péter");
        teacherService.save(teacher02);

        Teacher teacher03 = new Teacher();
        teacher03.setLastName("Pusztaszery");
        teacher03.setFirstName("Kornél");
        teacherService.save(teacher03);

        //Create Student

        Student student01 = new Student();
        student01.setLastName("Júlia");
        student01.setFirstName("Kiss");
        studentService.save(student01);

        Student student02 = new Student();
        student02.setLastName("Tóth");
        student02.setFirstName("Béla");
        studentService.save(student02);

        Student student03 = new Student();
        student03.setLastName("Dezső");
        student03.setFirstName("Irén");
        studentService.save(student03);

        //Create Messages

        Message message01 = new Message(1L, "Elbocsátás", student02, teacher01);
        Message message02 = new Message(2L, "Igazgatói dícséret", student01, teacher03);
        Message message03 = new Message(3L, "Kiváló tanuló", student01, teacher01);
        Message message04 = new Message(4L, "A WC-ben cigizett", student01, teacher02);
        Message message05 = new Message(5L, "Órán beszélgetett", student02, teacher01);
        Message message06 = new Message(6L, "Jó sportoló", student03, teacher01);

        messageService.save(message01);
        messageService.save(message02);
        messageService.save(message03);
        messageService.save(message04);
        messageService.save(message05);
        messageService.save(message06);


        //Test student service

        List<Student> students = studentService.findAll();
        for (Student student :
                students) {
            System.out.println("Student name: " + student.getFirstName() + " " + student.getLastName());
        }

        //Test messages service
        List<Message> messages = messageService.findAll();

        System.out.println("=========findAll()=========");
        for (Message message : messages) {
            System.out.println(message.getDescription());
        }


        messages = messageService.findAllByTeacher(teacher01);

        System.out.println("=========findAllByTeacher()=========");
        for (Message message : messages) {
            System.out.println(message.getDescription() + ", " + message.getTeacher().getFirstName() + " " + message.getTeacher().getLastName());
        }

        messages = messageService.findAllByStudent(student02);

        System.out.println("=========findAllByStudent()=========");
        for (Message message : messages) {
            System.out.println(message.getDescription() + ", " + message.getStudent().getFirstName() + " " + message.getStudent().getLastName());
        }


        //Test subject service
        Subject findedById = subjectService.findById(2L);
        System.out.println("Az id altal talat tantargy" + findedById.getDescription());

        subjectService.deleteById(5L);

        List<Subject> subjects = subjectService.findAll();

        System.out.println("A maradek:");
        for (Subject subject : subjects) {
            System.out.println(subject.getDescription());
        }


    }
}
