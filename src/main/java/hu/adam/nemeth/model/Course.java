package hu.adam.nemeth.model;


import hu.adam.nemeth.model.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    private Date startTime;
    private Date endTime;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @OneToOne
    private Subject subject;
    private String classroom;

}
