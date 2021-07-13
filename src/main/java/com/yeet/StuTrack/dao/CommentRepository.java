package com.yeet.StuTrack.dao;

import com.yeet.StuTrack.model.entity.Comment;
import com.yeet.StuTrack.model.entity.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{

    List<Comment> findAllByTeacher(Teacher teacher);

}


