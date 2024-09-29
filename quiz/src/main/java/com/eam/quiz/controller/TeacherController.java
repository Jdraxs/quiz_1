package com.eam.quiz.controller;

import com.eam.quiz.model.Teacher;
import com.eam.quiz.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        try{
            Optional<Teacher> teacher = teacherService.findById(id);
            return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }   catch (NullPointerException e) {
            System.out.println("There Is Not A Teacher With That ID");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        try{
            Optional<Teacher> teacher = teacherService.findById(id);
            Teacher updatedTeacher = teacher.get();
            updatedTeacher.setId(id);
            updatedTeacher.setName(teacherDetails.getName());
            updatedTeacher.setEmail(teacherDetails.getEmail());
            updatedTeacher.setPhoneNumber(teacherDetails.getPhoneNumber());
            return ResponseEntity.ok(teacherService.updateTeacher(updatedTeacher));
        } catch (NullPointerException e) {
            System.out.println("There Is Not A Teacher With That ID");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable Long id) {
        try{
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            System.out.println("There Is Not A Teacher With That ID");
        }
        return ResponseEntity.notFound().build();
    }
}
