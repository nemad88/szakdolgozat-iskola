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

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public int randomNumbeInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public String randomName(String[] listOfNames) {
        return listOfNames[randomNumbeInRange(0, listOfNames.length - 1)];
    }

    public String[] readLinesFromFile(String fileName) {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        InputStream dbAsStream = null;
        try {
            dbAsStream = resource.getInputStream();
            String content = IOUtils.toString(dbAsStream, "UTF-8");
            return content.split(";");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student makeAStudent(String[] studentDetails) {
        Student student = new Student();

        String firstName = randomName(readLinesFromFile("sampledata/malefirstnames.txt"));
        if (randomNumbeInRange(0, 1) == 0){
            firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
        }
        String lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));

        student.setBirthDate(LocalDate.now().minusDays(randomNumbeInRange(6 * 365, 14 * 365)));
        student.setFirstName(firstName);
        student.setLastName(lastName);

        student.setUserName(studentDetails[2]);
        student.setRole(studentDetails[3]);
        student.setPassword(studentDetails[4]);
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

        String firstName = randomName(readLinesFromFile("sampledata/malefirstnames.txt"));
        if (randomNumbeInRange(0, 1) == 0){
            firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
        }
        String lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));

        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);

        teacher.setUserName(teacherDetails[2]);
        teacher.setRole(teacherDetails[3]);
        teacher.setPassword(teacherDetails[4]);
        teacher.setBirthDate(LocalDate.now().minusDays(randomNumbeInRange(22 * 365, 65 * 365)));
        teacher.setTelephone(teacherDetails[6]);
        teacher.setAddress(teacherDetails[7]);
        teacher.setIdentityCard(teacherDetails[8]);
        teacher.setMothersName(teacherDetails[9]);
        teacher.setEducationalId(teacherDetails[10]);
        for (int i = 0; i < 5; i++) {
            teacher.getSubjects().add(subjects.get(randomNumbeInRange(0, subjects.size() - 1)));
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
        LocalDateTime date = LocalDateTime.now().minusMinutes(rnd.nextInt(365 * 2 * 24 * 60));
        message.setDate(date);
        messageService.save(message);
        return message;
    }

    public Course makeACourse(String[] courseDetails) {
        Course course = new Course();


        return course;
    }

    public void makeMarksFromMark(String markName, Integer markValue) {
        Random rnd = new Random();
        int numbersOfMarks = rnd.nextInt(200) + 150;
        for (int i = 0; i < numbersOfMarks; i++) {
            Mark mark = new Mark();

            mark.setMarkName(markName);
            mark.setMarkValue(markValue);
            mark.setStudent(students.get(rnd.nextInt(students.size())));
            mark.setTeacher(teachers.get(rnd.nextInt(teachers.size())));
            mark.setSubject(subjects.get(rnd.nextInt(subjects.size())));

            LocalDateTime date = LocalDateTime.now().minusMinutes(rnd.nextInt(365 * 2 * 24 * 60));
            mark.setDate(date);
            markService.save(mark);
        }
    }

    private void loadData() {

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

        String[] markNames = new String[]{"Jeles", "Jó", "Közepes", "Elégséges", "Elégtelen"};
        Integer[] markValues = new Integer[]{5, 4, 3, 2, 1};

        for (int i = 0; i < markNames.length; i++) {
            makeMarksFromMark(markNames[i], markValues[i]);
        }


        //MAKE COURSE
        fileName = "sampledata/course.txt";
        lines = readLinesFromFile(fileName);
        for (String line : lines) {
            Course course = makeACourse(line.split(":"));
            courses.add(course);
        }


    }
}
