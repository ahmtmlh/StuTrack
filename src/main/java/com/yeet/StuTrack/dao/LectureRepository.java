package com.yeet.StuTrack.dao;

import com.yeet.StuTrack.model.entity.Lecture;
import com.yeet.StuTrack.model.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Long> {

    List<Lecture> findByTeacher(Teacher teacher);
}

