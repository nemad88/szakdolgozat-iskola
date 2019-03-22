package hu.adam.nemeth.model;

import hu.adam.nemeth.model.common.Person;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToMany
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Message> messages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Mark> marks = new HashSet<>();
}
