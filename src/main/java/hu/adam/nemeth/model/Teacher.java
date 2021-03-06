package hu.adam.nemeth.model;


import hu.adam.nemeth.model.common.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Message> messages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Message> marks = new HashSet<>();

}
