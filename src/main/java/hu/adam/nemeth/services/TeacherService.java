package hu.adam.nemeth.services;

import hu.adam.nemeth.model.Teacher;
import hu.adam.nemeth.services.common.CrudService;

public interface TeacherService extends CrudService<Teacher, Long> {

    Teacher findByUserName(String userName);

}
