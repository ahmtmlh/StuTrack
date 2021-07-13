package com.yeet.StuTrack.controller;

import com.yeet.StuTrack.model.dto.LectureDTO;
import com.yeet.StuTrack.model.entity.Lecture;
import com.yeet.StuTrack.model.entity.Student;
import com.yeet.StuTrack.model.entity.Teacher;
import com.yeet.StuTrack.model.entity.User;
import com.yeet.StuTrack.response.Response;
import com.yeet.StuTrack.security.UserPrincipal;
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

import java.util.Optional;

@RestController("/lecture")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class LectureController {

    @Autowired
    private LectureService lectureService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addLecture(@RequestBody LectureDTO lectureInfo, BindingResult result,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal){
        if (result.hasErrors()){
            return Response.badValue("Wrong data received", "Binding error").build();
        }

        Teacher teacher = teacherService.getTeacherByUsername(userPrincipal.getUsername());

        Optional<Lecture> addedLecture = lectureService.addLecture(lectureInfo.getName(), lectureInfo.getDate(),
                lectureInfo.getSubject(), lectureInfo.getComment(), teacher);

        if (addedLecture.isEmpty()){
            return Response.badValue("Lecture add failed", "Reason unknown").build();
        }

        return Response.ok("Lecture has been added successfully").build();
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PostMapping("/{id}/new-student/{student-id}")
    public ResponseEntity<Response> addStudentToLecture(@PathVariable(name = "id") Long lectureId,
                                                        @PathVariable(name = "student-id") Long studentId){
        Optional<Lecture> lecture = lectureService.getLectureById(lectureId);

        if (lecture.isEmpty()){
            return Response.notFound("Lecture not found!").build();
        }

        Optional<Student> student = studentService.getStudentById(studentId);

        if (student.isEmpty()){
            return Response.notFound("Student not found!").build();
        }

        lecture.get().getStudents().add(student.get());
        boolean updateResult = lectureService.updateLecture(lecture.get()).isPresent();

        if (updateResult)
            return Response.badValue("Add student failed!", "Update failed").build();
        else
            return Response.ok("Student has been added successfully").build();
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @DeleteMapping("/{id}/new-student/{student-id}")
    public ResponseEntity<Response> removeStudentFromLecture(@PathVariable(name = "id") Long lectureId,
                                                             @PathVariable(name = "student-id") Long studentId,
                                                             @AuthenticationPrincipal UserPrincipal principal){

        Optional<Lecture> lecture = lectureService.getLectureById(lectureId);
        boolean delete = false;
        boolean lectureFound = lecture.isPresent();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + User.UserType.ADMIN))){
            delete = lectureFound;
        } else {
            if (lectureFound){
                Teacher teacher = teacherService.getTeacherByUsername(principal.getUsername());
                delete = teacher.getTeacherId().equals(lecture.get().getTeacher().getTeacherId());
            }
        }

        if (!lectureFound){
            return Response.notFound("Lecture not found").build();
        }

        if (!delete) {
            return Response.badValue("Logged in teacher can't change properties of this lecture",
                    "Invalid teacher").build();
        }

        Optional<Student> student = studentService.getStudentById(studentId);

        if (student.isEmpty()){
            return Response.notFound("Student not found").build();
        }

        lecture.get().getStudents().remove(student.get());
        boolean deleteSuccess = lectureService.updateLecture(lecture.get()).isPresent();

        if (deleteSuccess){
            return Response.ok("Student has been successfully removed from the lecture").build();
        } else {
            return Response.badValue("Student can't be deleted", "Reason unknown").build();
        }
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping("/{id}/students")
    public ResponseEntity<?> getStudentsOfLecture(@PathVariable(name = "id") Long lectureId){
        Optional<Lecture> lecture = lectureService.getLectureById(lectureId);
        // Lazy fetch will take care of loading students from the database
        if (lecture.isPresent()){
            return Response.ok("Lecture is valid").body(lecture.get().getStudents()).build();
        } else {
            return Response.notFound("Lecture is not found!").build();
        }
    }

}
