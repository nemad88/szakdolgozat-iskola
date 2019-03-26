package hu.adam.nemeth.bootstrap;

import hu.adam.nemeth.model.*;
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
    private final CourseService courseService;
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

    public int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public String randomName(String[] listOfNames) {
        return listOfNames[getRandomNumberInRange(0, listOfNames.length - 1)];
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

    public void generateStudents(int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student();
            String firstName = randomName(readLinesFromFile("sampledata/malefirstnames.txt"));
            if (getRandomNumberInRange(0, 1) == 0) {
                firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
            }
            String lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));
            student.setBirthDate(LocalDate.now().minusDays(getRandomNumberInRange(6 * 365, 14 * 365)));
            student.setBirthPlace("Budapest");
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setRole("ROLE_STUDENT");
            student.setTelephone("+36-" + getRandomNumberInRange(20000000, 30999999));
            student.setIdentityCard(getRandomNumberInRange(1111111, 9999999) + (char) getRandomNumberInRange(32, 45) + "");
            student.setEducationalId(getRandomNumberInRange(1111111, 9999999) + (char) getRandomNumberInRange(32, 45) + "");
            student.setAddress("1081 Budapest, József Attila utca 14. 3/2");
            student.setUserName("s" + i);
            student.setPassword("s" + i);
            for (int j = 0; j < 20; j++) {
                student.getCourses().add(courses.get(getRandomNumberInRange(0, courses.size() - 1)));
            }
            firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
            lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));
            student.setMothersName(lastName + " " + firstName);
            students.add(student);
            studentService.save(student);
        }
    }

    public void generateTeachers(int numberOfTeaches) {

        for (int i = 0; i < numberOfTeaches; i++) {
            Teacher teacher = new Teacher();
            String firstName = randomName(readLinesFromFile("sampledata/malefirstnames.txt"));
            if (getRandomNumberInRange(0, 1) == 0) {
                firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
            }
            String lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));
            teacher.setBirthDate(LocalDate.now().minusDays(getRandomNumberInRange(22 * 365, 65 * 365)));
            teacher.setBirthPlace("Budapest");
            teacher.setFirstName(firstName);
            teacher.setLastName(lastName);
            teacher.setRole("ROLE_TEACHER");
            teacher.setTelephone("+36-" + getRandomNumberInRange(20000000, 30999999));
            teacher.setIdentityCard(getRandomNumberInRange(1111111, 9999999) + (char) getRandomNumberInRange(32, 45) + "");
            teacher.setEducationalId(getRandomNumberInRange(1111111, 9999999) + (char) getRandomNumberInRange(32, 45) + "");
            teacher.setAddress("1081 Budapest, József Attila utca 14. 3/2");
            teacher.setUserName("t" + i);
            teacher.setPassword("t" + i);
            firstName = randomName(readLinesFromFile("sampledata/femalefirstnames.txt"));
            lastName = randomName(readLinesFromFile("sampledata/lastnames.txt"));
            teacher.setMothersName(lastName + " " + firstName);
            for (int j = 0; j < 7; j++) {
                teacher.getSubjects().add(subjects.get(getRandomNumberInRange(0, subjects.size() - 1)));
            }
            teachers.add(teacher);
            teacherService.save(teacher);
        }
    }

    public void generateSubjects() {
        String[] subjectDetails = readLinesFromFile("sampledata/subjects.txt");
        for (int i = 0; i < subjectDetails.length; i++) {
            Subject subject = new Subject();
            subject.setDescription(subjectDetails[i]);
            subjects.add(subject);
            subjectService.save(subject);
        }
    }

    public void generateMessages(int numberOfMessage) {
        String[] messageDetails = readLinesFromFile("sampledata/messages.txt");

        for (int i = 0; i < numberOfMessage; i++) {
            Message message = new Message();
            message.setDescription(messageDetails[getRandomNumberInRange(0, messageDetails.length - 1)]);
            message.setStudent(students.get(getRandomNumberInRange(0, students.size() - 1)));
            message.setTeacher(teachers.get(getRandomNumberInRange(0, teachers.size() - 1)));
            LocalDateTime date = LocalDateTime.now().minusMinutes(getRandomNumberInRange(0, 365 * 2 * 24 * 60));
            message.setDate(date);
            messages.add(message);
            messageService.save(message);
        }
    }

    public void generateCourses(int numberOfCourses) {
        for (int i = 0; i < numberOfCourses; i++) {
            Course course = new Course();
            course.setTeacher(teachers.get(getRandomNumberInRange(0, teachers.size() - 1)));
            course.setClassroom("A" + getRandomNumberInRange(1, 10));
            course.setSubject(subjects.get(getRandomNumberInRange(0, subjects.size() - 1)));
            LocalDateTime date = LocalDateTime.now().minusMinutes(getRandomNumberInRange(0, 365 / 2 * 1 * 24 * 60));
            course.setStartTime(date);
            course.setEndTime(date.plusMinutes(45));
            courses.add(course);
            courseService.save(course);
        }
    }

    public void generateMarks(int numberOfMarks) {

        for (int i = 0; i < numberOfMarks; i++) {
            Mark mark = new Mark();

            String[] markNames = new String[]{"Jeles", "Jó", "Közepes", "Elégséges", "Elégtelen"};
            Integer[] markValues = new Integer[]{5, 4, 3, 2, 1};
            int randomMarkAndValue = getRandomNumberInRange(0, markNames.length - 1);

            mark.setMarkValue(markValues[randomMarkAndValue]);
            mark.setMarkName(markNames[randomMarkAndValue]);
            mark.setStudent(students.get(getRandomNumberInRange(0, students.size() - 1)));
            mark.setTeacher(teachers.get(getRandomNumberInRange(0, teachers.size() - 1)));
            mark.setSubject(subjects.get(getRandomNumberInRange(0, subjects.size() - 1)));
            LocalDateTime date = LocalDateTime.now().minusMinutes(getRandomNumberInRange(0, 365 * 2 * 24 * 60));
            mark.setDate(date);
            marks.add(mark);
            markService.save(mark);
        }
    }

    private void loadData() {
        generateSubjects();
        generateTeachers(50);
        generateCourses(60);
        generateStudents(200);
        generateMessages(students.size() * 7);
        generateMarks(students.size() * 120);
    }
}
