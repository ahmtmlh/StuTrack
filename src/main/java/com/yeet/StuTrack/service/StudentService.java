package com.yeet.StuTrack.service;

import com.yeet.StuTrack.dao.StudentRepository;
import com.yeet.StuTrack.model.entity.Lecture;
import com.yeet.StuTrack.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Optional<Student> addStudent(String name, String surname, String number, int year){
        return Optional.of(studentRepository.save(new Student(name, surname, number, year)));
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public List<Student> getAllStudentsOfLecture(Long lectureId){
        return studentRepository.findAllByLecture(lectureId);
    }

    public List<Student> getAllStudentsOfLecture(Lecture lecture){
        return getAllStudentsOfLecture(lecture.getLectureId());
    }

    public Optional<Student> getStudentByNumber(String number){
        return Optional.of(studentRepository.findByNumber(number));
    }

    public Optional<Student> getStudentById(Long studentId){
        return studentRepository.findById(studentId);
    }

}

