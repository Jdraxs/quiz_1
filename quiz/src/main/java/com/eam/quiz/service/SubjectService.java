package com.eam.quiz.service;

import com.eam.quiz.model.Subject;
import com.eam.quiz.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectService() {

    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    //Search
    public Optional<Subject> findById(long id) {
        return subjectRepository.findById(id);
    }

    //Insert
    public Subject insertSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    //Delete
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

    //Update
    public Subject updateSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}
