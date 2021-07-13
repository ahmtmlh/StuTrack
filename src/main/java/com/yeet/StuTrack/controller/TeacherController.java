package com.yeet.StuTrack.controller;

import javax.validation.Valid;

import com.yeet.StuTrack.model.dto.TeacherDTO;
import com.yeet.StuTrack.model.entity.Teacher;
import com.yeet.StuTrack.model.entity.User;
import com.yeet.StuTrack.response.Response;
import com.yeet.StuTrack.service.TeacherService;
import com.yeet.StuTrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> addTeacher(@RequestBody @Valid TeacherDTO teacher, BindingResult result){

        if (result.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        try{
            Optional<User> user = userService.userDetails(teacher.getUsername());

            if (user.isEmpty())
                throw new UsernameNotFoundException("");

            Optional<Teacher> teacherOptional = teacherService.addTeacher(
                    teacher.getName(), teacher.getSurname(), teacher.getUsername(), user.get());

            if (teacherOptional.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            return new ResponseEntity<>(teacherOptional.get(), HttpStatus.CREATED);
        } catch (UsernameNotFoundException ex){
            return Response.notFound("User not found!").build();
        }

    }

}
