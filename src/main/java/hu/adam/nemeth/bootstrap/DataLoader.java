package hu.adam.nemeth.bootstrap;

import hu.adam.nemeth.model.*;
import hu.adam.nemeth.repositories.CourseRepository;
import hu.adam.nemeth.services.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final SubjectService subjectService;
    private final MessageService messageService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final MarkService markService;
    private final CourseRepository courseRepository;
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Mark> marks = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        int count = subjectService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    public String[] readLinesFromFile(String fileName) throws IOException {

        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        InputStream dbAsStream = resource.getInputStream(); // <-- this is the difference

        String content = IOUtils.toString(dbAsStream,"UTF-8");

        return content.split(";");
    }

    public Student makeAStudent(String[] studentDetails) {
        Student student = new Student();
        student.setFirstName(studentDetails[0]);
        student.setLastName(studentDetails[1]);
        student.setUserName(studentDetails[2]);
        student.setRole(studentDetails[3]);
        student.setPassword(studentDetails[4]);

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date date = format.parse(studentDetails[5]);
            student.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        student.setTelephone(studentDetails[6]);
        student.setAddress(studentDetails[7]);
        student.setIdentityCard(studentDetails[8]);
        student.setMothersName(studentDetails[9]);
        student.setEducationalId(studentDetails[10]);

        studentService.save(student);
        return student;
    }

    public Teacher makeATeacher(String[] teacherDetails) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDetails[0]);
        teacher.setLastName(teacherDetails[1]);
        teacher.setUserName(teacherDetails[2]);
        teacher.setRole(teacherDetails[3]);
        teacher.setPassword(teacherDetails[4]);

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date date = format.parse(teacherDetails[5]);
            teacher.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        teacher.setTelephone(teacherDetails[6]);
        teacher.setAddress(teacherDetails[7]);
        teacher.setIdentityCard(teacherDetails[8]);
        teacher.setMothersName(teacherDetails[9]);
        teacher.setEducationalId(teacherDetails[10]);

        Random rnd = new Random();


        for (int i = 0; i < 5; i++) {
            int randomNumber = rnd.nextInt((subjects.size() - 1) + 1) + 0;
            teacher.getSubjects().add(subjects.get(randomNumber));
        }

        teacherService.save(teacher);
        return teacher;
    }

    public Subject makeASubject(String[] subjectDetails) {
        Subject subject = new Subject();
        subject.setDescription(subjectDetails[0]);
        subjectService.save(subject);
        return subject;
    }

    public Message makeAMessage(String[] messageDetails) {
        Message message = new Message();
        message.setDescription(messageDetails[0]);
        Random rnd = new Random();

        message.setStudent(students.get(rnd.nextInt(students.size())));
        message.setTeacher(teachers.get(rnd.nextInt(teachers.size())));

        messageService.save(message);
        return message;
    }

    public Course makeACourse(String[] courseDetails) {
        Course course = new Course();
        //TODO implement
        return course;
    }

    public void makeAMarksFromMark(String detail) {

        Random rnd = new Random();
        int numbersOfMarks = rnd.nextInt(100) + 50;
        for (int i = 0; i < numbersOfMarks; i++) {
            Mark mark = new Mark();
            mark.setMark(detail);
            mark.setStudent(students.get(rnd.nextInt(students.size())));
            mark.setTeacher(teachers.get(rnd.nextInt(teachers.size())));
            mark.setSubject(subjects.get(rnd.nextInt(subjects.size())));

            DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            try {

                String years = (rnd.nextInt(2019 - 2011) + 2011) + "";
                String month = (rnd.nextInt(12 - 1) + 1) + "";
                String day = (rnd.nextInt(28 - 1) + 1) + "";
                System.out.println(month + "-" + day + "-" + years);

                Date date = format.parse(month + "-" + day + "-" + years);
                mark.setDate(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            markService.save(mark);
        }
    }

    private void loadData() {
        try {
            //MAKE STUDENTS
            String fileName = "sampledata/student.txt";
            String[] lines = readLinesFromFile(fileName);
            for (String line : lines) {
                Student student = makeAStudent(line.split(":"));
                students.add(student);
            }

            //MAKE SUBJECTS
            fileName = "sampledata/subject.txt";
            lines = readLinesFromFile(fileName);
            for (String line : lines) {
                Subject subject = makeASubject(line.split(":"));
                subjects.add(subject);
            }
            //MAKE TEACHERS
            fileName = "sampledata/teacher.txt";
            lines = readLinesFromFile(fileName);
            for (String line : lines) {
                Teacher teacher = makeATeacher(line.split(":"));
                teachers.add(teacher);
            }

            //MAKE MESSAGES
            fileName = "sampledata/message.txt";
            lines = readLinesFromFile(fileName);
            for (String line : lines) {
                Message message = makeAMessage(line.split(":"));
                messages.add(message);
            }

            //MAKE MARK

            String[] possibleMarks = new String[]{"Jeles", "Jó", "Közepes", "Elégséges", "Elégtelen"};
            for (String mark : possibleMarks) {
                makeAMarksFromMark(mark);
            }


            //MAKE COURSE
            fileName = "sampledata/course.txt";
            lines = readLinesFromFile(fileName);
            for (String line : lines) {
                Course course = makeACourse(line.split(":"));
                courses.add(course);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
