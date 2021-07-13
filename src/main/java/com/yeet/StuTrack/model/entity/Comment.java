package com.yeet.StuTrack.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeet.StuTrack.model.dto.CommentDTO;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false)
    private String commentValue;
    @Column(nullable = false)
    @JsonFormat(pattern =  "dd-MM-yyyy mm:HH")
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Lecture.class)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public CommentDTO convertToCommentDTO(){
        CommentDTO dto = new CommentDTO();

        dto.setCommentDate(date);
        dto.setCommentValue(commentValue);
        dto.setLectureId(lecture.getLectureId());
        dto.setStudentNumber(student.getNumber());
        dto.setTeacherId(teacher.getTeacherId());

        return dto;
    }

    public static Comment fromCommentDTO(CommentDTO commentDTO, Lecture lecture, Student student, Teacher teacher){
        Comment comment = new Comment();
        comment.setDate(commentDTO.getCommentDate());
        comment.setCommentValue(comment.getCommentValue());
        comment.setLecture(lecture);
        comment.setStudent(student);
        comment.setTeacher(teacher);
        return comment;
    }

}
