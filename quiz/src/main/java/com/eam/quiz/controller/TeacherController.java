package com.eam.quiz.controller;

import com.eam.quiz.model.Teacher;
import com.eam.quiz.service.TeacherService;
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
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.searchTeacher(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public void createTeacher(@RequestBody Teacher teacher) {
        teacherService.insertTeacher(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Optional<Teacher> teacher = teacherService.searchTeacher(id);
        if (teacher.isPresent()) {
            Teacher updatedTeacher = teacher.get();
            updatedTeacher.setName(teacherDetails.getName());
            updatedTeacher.setEmail(teacherDetails.getEmail());
            updatedTeacher.setPhoneNumber(teacherDetails.getPhoneNumber());
            return ResponseEntity.ok(teacherService.updateTeacher(updatedTeacher));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable Long id) {
        if(teacherService.searchTeacher(id).isPresent()) {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
