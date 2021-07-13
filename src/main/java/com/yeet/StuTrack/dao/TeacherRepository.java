package com.yeet.StuTrack.dao;

import com.yeet.StuTrack.model.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findByNameAndSurname(String name, String surname);
    Teacher findByUsername(String username);

}
