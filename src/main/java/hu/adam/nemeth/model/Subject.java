package hu.adam.nemeth.model;


import hu.adam.nemeth.model.common.BaseEntity;
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
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Set<Mark> marks = new HashSet<>();

    public Subject(Long id, String description) {
        super(id);
        this.description = description;
    }

    public Subject(String desc) {
        this.description = desc;
    }

}
