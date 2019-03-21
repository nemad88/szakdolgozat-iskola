package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Mark;
import hu.adam.nemeth.model.Student;
import hu.adam.nemeth.services.common.CrudService;

import java.util.List;


public interface MarkService extends CrudService<Mark, Long> {

    List<Mark> findAllByStudent(Student student);

}
