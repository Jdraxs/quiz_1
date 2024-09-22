package com.eam.quiz.controller;

import com.eam.quiz.model.Subject;
import com.eam.quiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.findAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        Optional<Subject> subject = subjectService.searchSubject(id);
        return subject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.insertSubject(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        Optional<Subject> subject = subjectService.searchSubject(id);
        if (subject.isPresent()) {
            Subject updatedSubject = subject.get();
            updatedSubject.setName(subjectDetails.getName());
            updatedSubject.setDescription(subjectDetails.getDescription());
            updatedSubject.setNumberClasses(subjectDetails.getNumberClasses());
            updatedSubject.setCode(subjectDetails.getCode());
            return ResponseEntity.ok(subjectService.updateSubject(updatedSubject));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable Long id) {
        if(subjectService.searchSubject(id).isPresent()) {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
