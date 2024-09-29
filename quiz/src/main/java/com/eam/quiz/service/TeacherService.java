package com.eam.quiz.service;
import com.eam.quiz.model.Teacher;
import com.eam.quiz.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherService() {

    }

    //Search All Teachers
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    //Search An Specific Teacher
    public Optional<Teacher> findById(long id) {
        return teacherRepository.findById(id);
    }

    //Insert
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    //Delete
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }

    //Update
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
}