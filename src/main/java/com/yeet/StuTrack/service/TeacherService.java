package com.yeet.StuTrack.service;


import com.yeet.StuTrack.dao.TeacherRepository;
import com.yeet.StuTrack.model.entity.Teacher;
import com.yeet.StuTrack.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Optional<Teacher> addTeacher(String name, String surname, String username, User user){
        return Optional.of(teacherRepository.save(new Teacher(name, surname, username, user)));
    }

    public Teacher getTeacherByUsername(String username){
        return teacherRepository.findByUsername(username);
    }
}
