package com.eam.quiz.controller;

import com.eam.quiz.model.Subject;
import com.eam.quiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        try{
            Optional<Subject> subject = subjectService.findById(id);
            return subject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }   catch (NullPointerException e) {
            System.out.println("There Is Not A Subject With That ID");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.insertSubject(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        try{
            Optional<Subject> subject = subjectService.findById(id);
            Subject updatedSubject = subject.get();
            updatedSubject.setName(subjectDetails.getName());
            updatedSubject.setDescription(subjectDetails.getDescription());
            updatedSubject.setNumberClasses(subjectDetails.getNumberClasses());
            updatedSubject.setCode(subjectDetails.getCode());
            updatedSubject.setTeacher(subjectDetails.getTeacher());
            return ResponseEntity.ok(subjectService.updateSubject(updatedSubject));
        } catch (NullPointerException | NoSuchElementException e) {
            System.out.println("There Is Not A Subject With That ID");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable Long id) {
        try{
            subjectService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NullPointerException | NoSuchElementException e) {
            System.out.println("There Is Not A Subject With That ID");
        }
        return ResponseEntity.notFound().build();
    }
}
