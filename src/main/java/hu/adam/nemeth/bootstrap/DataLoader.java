package hu.adam.nemeth.bootstrap;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import hu.adam.nemeth.model.*;
import hu.adam.nemeth.services.*;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    private static final int NUMBER_OF_TEACHERS = 10;
    private static final int NUMBER_OF_STUDENTS = 100;
    private static final int START_TIME_HOUR_OF_DAY = 8;
    private static final int TIME_ZONES_NUMBER_OF_DAY = 7;
    private LocalDate STARTS_OF_SCHOOL_YEAR = LocalDate.now();
    private static final int NUMBER_OF_STUDENT_MESSAGE = 30;
    private static final int NUMBER_OF_STUDENT_MARKS = 150;


    public DataLoader(SubjectService subjectService, MessageService messageService, StudentService studentService, TeacherService teacherService, MarkService markService, CourseService courseService, List<Student> students, List<Teacher> teachers, List<Subject> subjects, List<Mark> marks, List<Course> courses, List<Message> messages, ResourceLoader resourceLoader) {
        this.subjectService = subjectService;
        this.messageService = messageService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.markService = markService;
        this.courseService = courseService;
        this.students = students;
        this.teachers = teachers;
        this.subjects = subjects;
        this.marks = marks;
        this.courses = courses;
        this.messages = messages;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = subjectService.findAll().size();
        if (count == 0) {
            loadData();
            showManual();
        }
    }

    /*
    @param min inclusive
    @param max inclusive
     */

    public int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public LocalDate getRandomDateInRange(LocalDate start, LocalDate end) {
        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay());
        return LocalDate.ofEpochDay(randomDay);
//        LocalDateTime date = LocalDateTime.of(LocalDate.ofEpochDay(randomDay), LocalTime.of(0, 0).plusMinutes(getRandomNumberInRange(0, 24 * 60)));
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

            for (int j = 0; j < courses.size(); j = j + (teachers.size())) {
                student.getCourses().add(courses.get(getRandomNumberInRange(j, j + (teachers.size() - 1))));
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

    public void generateMessages(int messageOfStudent) {
        String[] messageDetails = readLinesFromFile("sampledata/messages.txt");
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < messageOfStudent; j++) {
                Message message = new Message();
                message.setMessageTitle(messageDetails[getRandomNumberInRange(0, messageDetails.length - 1)]);
                Lorem lorem = LoremIpsum.getInstance();
                message.setDescription(lorem.getWords(50, 150));
                message.setStudent(students.get(i));
                message.setTeacher(teachers.get(getRandomNumberInRange(0, teachers.size() - 1)));
                LocalDateTime date = LocalDateTime.of(getRandomDateInRange(STARTS_OF_SCHOOL_YEAR, LocalDate.now()), LocalTime.of(0, 0).plusMinutes(getRandomNumberInRange(0, 24 * 60)));
                message.setDate(date);
                messages.add(message);
                messageService.save(message);
            }
        }
    }

    public void generateCourses(int numberOfCourse) {
        if (LocalDate.now().getMonthValue() >= 10) {
            STARTS_OF_SCHOOL_YEAR = LocalDate.of(LocalDate.now().getYear(), 9, 1);
        } else {
            STARTS_OF_SCHOOL_YEAR = LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);
        }

        LocalDateTime actualDay = LocalDateTime.of(STARTS_OF_SCHOOL_YEAR, LocalTime.MIN);
        LocalDateTime lastDayOfSchoolYear = actualDay.plusMonths(9);


        for (LocalDateTime dt = actualDay; dt.isBefore(lastDayOfSchoolYear); ) {
            dt = dt.plusHours(START_TIME_HOUR_OF_DAY);

            if (!(dt.getDayOfWeek().equals(6) || dt.getDayOfWeek().equals(7))) {
                for (int i = 0; i < numberOfCourse; i++) {
                    for (int j = 0; j < teachers.size(); j++) {
                        Course course = new Course();
                        course.setTeacher(teachers.get(j));
                        course.setClassroom("A" + (j + 1));
                        List<Subject> subjectsOfTeacher = new ArrayList<>(teachers.get(j).getSubjects());
                        course.setSubject(subjectsOfTeacher.get(getRandomNumberInRange(0, subjectsOfTeacher.size() - 1)));
                        course.setStartTime(dt);
                        course.setEndTime(dt.plusMinutes(45));
                        courses.add(course);
                        courseService.save(course);
                    }
                    dt = dt.plusHours(1);
                }
                dt = dt.minusHours(numberOfCourse);
            }
            dt = dt.minusHours(START_TIME_HOUR_OF_DAY);
            dt = dt.plusDays(1);
        }
    }

    public void generateMarks(int numberOfMarks) {

        String[] markNames = new String[]{"Elégtelen", "Elégséges", "Közepes", "Jó", "Jeles"};

        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < numberOfMarks; j++) {
                Mark mark = new Mark();
                int randomValue = getRandomNumberInRange(0, markNames.length - 1);
                mark.setMarkValue(randomValue + 1);
                mark.setMarkName(markNames[randomValue]);
                mark.setStudent(students.get(i));
                mark.setTeacher(teachers.get(getRandomNumberInRange(0, teachers.size() - 1)));
                mark.setSubject(subjects.get(getRandomNumberInRange(0, subjects.size() - 1)));
                LocalDateTime date = LocalDateTime.of(getRandomDateInRange(STARTS_OF_SCHOOL_YEAR, LocalDate.now()), LocalTime.of(0, 0).plusMinutes(getRandomNumberInRange(0, 24 * 60)));
                mark.setDate(date);
                marks.add(mark);
                markService.save(mark);
            }
        }
    }

    private void loadData() {
        generateSubjects();
        generateTeachers(NUMBER_OF_TEACHERS);
        generateCourses(TIME_ZONES_NUMBER_OF_DAY);
        generateStudents(NUMBER_OF_STUDENTS);
        generateMessages(NUMBER_OF_STUDENT_MESSAGE);
        generateMarks(NUMBER_OF_STUDENT_MARKS);
        System.out.println("\n======================================================================");
        System.out.println("Adatok betöltése megtörtént, a program használatra kész".toUpperCase());
        System.out.println("======================================================================");
    }

    private void showManual() {
        System.out.println("\n\n=============HASZNÁLATI UTASÍTÁS=============\n\n");

        System.out.println("A program böngészőből való elérése a következő címen lehetséges: http://localhost:8080\n");

        System.out.println("A tanár oldal elérése: t0/t0 ... " + "t" + (teachers.size() - 1) + "/t" + (teachers.size() - 1) + " adatokkal lehetséges");
        System.out.println("A diák oldal elérése: s0/s0 ... " + "s" + (students.size() - 1) + "/s" + (students.size() - 1) + " adatokkal lehetséges\n");
        System.out.println("Az adatbázis elérése az alábbi címen érhető el: http://localhost:8080/db");
        System.out.println("A belépés az alapértelmezett beállításokkal lehetséges.");
    }
}
