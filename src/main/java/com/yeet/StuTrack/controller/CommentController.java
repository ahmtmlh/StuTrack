package com.yeet.StuTrack.controller;

import com.yeet.StuTrack.model.dto.CommentDTO;
import com.yeet.StuTrack.model.entity.*;
import com.yeet.StuTrack.response.Response;
import com.yeet.StuTrack.security.UserPrincipal;
import com.yeet.StuTrack.service.CommentService;
import com.yeet.StuTrack.service.LectureService;
import com.yeet.StuTrack.service.StudentService;
import com.yeet.StuTrack.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController("/comments")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LectureService lectureService;

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentDTO commentInfo, BindingResult result,
                                        @AuthenticationPrincipal UserPrincipal principal){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        if (principal == null || principal.getUsername().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        Teacher teacher = teacherService.getTeacherByUsername(principal.getUsername());

        Optional<Student> student = studentService.getStudentByNumber(commentInfo.getStudentNumber());

        if (student.isEmpty()){
            return Response.notFound("Student not found").build();
        }

        Optional<Lecture> lecture = lectureService.getLectureById(commentInfo.getLectureId());

        if (lecture.isEmpty()){
            return Response.notFound("Lecture not found").build();
        }

        Optional<Comment> addedComment =
                commentService.addComment(Comment.fromCommentDTO(commentInfo, lecture.get(), student.get(), teacher));

        if (addedComment.isEmpty()){
            return Response.badValue("Comment added failed!", "Reason not known").build();
        }

        return Response.ok("Comment added successfully").build();
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id){
        Optional<Comment> comment = commentService.getCommentById(id);

        if (comment.isEmpty()){
            return Response.notFound("Comment not found").build();
        }

        return Response.ok("Comment details retrieval successful")
                .body(comment.get().convertToCommentDTO()).build();

    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal){

        Optional<Comment> comment = commentService.getCommentById(id);

        boolean delete = false;
        boolean commentFound = comment.isPresent();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + User.UserType.ADMIN))){
            delete = commentFound;
        } else {
            if (commentFound){
                Teacher teacher = teacherService.getTeacherByUsername(principal.getUsername());
                delete = comment.get().getTeacher().getTeacherId().equals(teacher.getTeacherId());
            }
        }

        if (!commentFound){
            return Response.notFound("Comment not found").build();
        }

        if (!delete){
            return Response.badValue("Logged in teacher can't delete this comment, because its not the original" +
                    "author", "Invalid teacher").build();
        }

        commentService.deleteComment(comment.get().getCommentId());

        return Response.ok("Comment has been successfully deleted").build();
    }
}
