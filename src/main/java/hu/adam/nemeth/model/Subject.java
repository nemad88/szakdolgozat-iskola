package hu.adam.nemeth.model;


import hu.adam.nemeth.model.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Column(name = "description")
    private String description;

    public Subject(Long id, String description) {
        super(id);
        this.description = description;
    }

}
