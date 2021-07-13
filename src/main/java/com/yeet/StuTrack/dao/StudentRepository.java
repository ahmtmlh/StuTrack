package com.yeet.StuTrack.dao;

import com.yeet.StuTrack.model.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findByNumber(String number);
    Student findByNumberStartingWith(String number);
    Student findByNameStartingWith(String name);
    List<Student> findAllByYear(Integer year);

    @Query(value = "from student s join s.lectures l where l.lectureId=:lectureId")
    List<Student> findAllByLecture(Long lectureId);
}
