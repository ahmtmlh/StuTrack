package com.yeet.StuTrack.service;

import com.yeet.StuTrack.dao.LectureRepository;
import com.yeet.StuTrack.model.entity.Lecture;
import com.yeet.StuTrack.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    public Optional<Lecture> addLecture(String name, Date date, String subject, String comment, Teacher teacher){
        return Optional.of(lectureRepository.save(new Lecture(name, date, subject, comment, teacher)));
    }

    public Optional<Lecture> updateLecture(Lecture lecture){
        return Optional.of(lectureRepository.save(lecture));
    }

    public Optional<Lecture> getLectureById(Long lectureId){
        return lectureRepository.findById(lectureId);
    }
}
