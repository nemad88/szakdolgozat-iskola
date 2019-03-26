package hu.adam.nemeth.model;

import hu.adam.nemeth.model.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "marks")
public class Mark extends BaseEntity implements Comparable<Mark> {

    private String markName;
    private Integer markValue;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private LocalDateTime date;

    @Override
    public int compareTo(Mark o) {
        if (this.date.isBefore(o.getDate())) {
            System.out.println("before");
            return -1;

        } else if (this.date.isAfter(o.getDate())) {
            System.out.println("after");
            return 1;
        } else {
            System.out.println("equals");
            return 0;
        }
    }
}
