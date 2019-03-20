package hu.adam.nemeth.model;

import hu.adam.nemeth.model.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "marks")
public class Mark extends BaseEntity {

    private String mark;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private Date date;

}
