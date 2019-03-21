package hu.adam.nemeth.model;

import hu.adam.nemeth.model.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    private String description;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Builder
    public Message(Long id, String description, Student student, Teacher teacher) {
        super(id);
        this.description = description;
        this.student = student;
        this.teacher = teacher;
    }

}
