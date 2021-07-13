package com.yeet.StuTrack.service;

import com.yeet.StuTrack.dao.CommentRepository;
import com.yeet.StuTrack.model.entity.Comment;
import com.yeet.StuTrack.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> addComment(Comment comment){
        return Optional.of(commentRepository.save(comment));
    }

    public List<Comment> getAllCommentsByTeacher(Teacher teacher){
        return commentRepository.findAllByTeacher(teacher);
    }

    public Optional<Comment> getCommentById(Long commentId){
        return commentRepository.findById(commentId);
    }

    public Optional<Comment> editComment(Long commentId, String newCommentValue){
        Optional<Comment> comment = commentRepository.findById(commentId);

        return comment.map(value -> {
            value.setCommentValue(newCommentValue);
            value.setDate(new Date());
            return commentRepository.save(value);
        });
    }

    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }



}
