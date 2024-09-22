package com.eam.quiz.service;

import com.eam.quiz.model.Subject;
import com.eam.quiz.model.Teacher;
import com.eam.quiz.repository.SubjectRepository;
import com.eam.quiz.singleton.DataBaseSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    private Connection connection;
    private TeacherService serviceTeacher;

    public SubjectService() {
        this.connection = DataBaseSingleton.getInstance().getConnection();
        this.serviceTeacher = new TeacherService();// Inject TeacherDAO
    }

    public List<Subject> findAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT * FROM subject";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getLong("id"));
                subject.setDescription(rs.getString("description"));
                subject.setName(rs.getString("name"));
                subject.setCode(rs.getString("code"));
                subject.setNumberClasses(rs.getInt("number_classes"));
                Optional<Teacher> teacher = serviceTeacher.searchTeacher(rs.getLong("teacher_id"));
                subject.setTeacher(teacher.orElse(null)); // Set The Teacher To Null If There Is None
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    //Search
    public Optional<Subject> searchSubject(long id) {
        // The Query Is Made In Sql
        String selectSQL = "SELECT * FROM subject Where id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setLong(1, id);

            // The Query Is Executed
            ResultSet rs = pstmt.executeQuery();

            // All The Results Are Iterated
            if (rs.next()) {
                Optional<Teacher> teacher = serviceTeacher.searchTeacher(rs.getLong("teacher_id"));
                Subject subject = new Subject(rs.getString("name"), rs.getString("description"), rs.getInt("number_Classes"), rs.getString("code"), teacher.orElse(null));
                return Optional.of(subject);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And No Subject Was Found");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //Insert
    public Subject insertSubject(Subject subject) {
        // The Query Is Made In Sql
        String insertSQL = "INSERT INTO subject (description, name, teacher_id, code, number_classes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, subject.getDescription());
            pstmt.setString(2, subject.getName());
            //Verifies If The Subject Has A Teacher And Assigns It If It Does
            if (subject.getTeacher() != null) {
                pstmt.setLong(3, subject.getTeacher().getId()); // Set Teacher ID
            } else {
                pstmt.setNull(3, Types.BIGINT); // Set NULL If There's No Teacher
            }
            pstmt.setString(4, subject.getCode());
            pstmt.setInt(5, subject.getNumberClasses());

            // The Query Is Executed In Int To Watch If There Are Rows Affected
            int rowsAffected = pstmt.executeUpdate();

            // The Query Is Verified
            if (rowsAffected > 0) {
                System.out.println("Insert Successful");
            } else {
                System.out.println("The Data Was Not Inserted");
            }
        } catch (SQLException e) {
            System.out.println("An Error Occurred And The Subject Was Not Inserted");
            e.printStackTrace();
        }
        return subject;
    }

    //Delete
    public void deleteSubject(Long id) {
        // The Query Is Made In Sql
        String deleteSQL = "DELETE FROM subject WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setLong(1, id);

            // The Query Is Executed
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An Error Ocurred");
            e.printStackTrace();
        }
    }

    //Update
    public Subject updateSubject(Subject subject) {
        // The Query Is Made In Sql
        String updateSQL = "UPDATE subject SET email = ?, name = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, subject.getDescription());
            pstmt.setString(2, subject.getName());
            pstmt.setLong(3, subject.getTeacher().getId());
            pstmt.setString(4, subject.getCode());
            pstmt.setInt(5, subject.getNumberClasses());
            if (subject.getTeacher() != null) {
                pstmt.setLong(3, subject.getTeacher().getId()); // Set Teacher ID
            } else {
                pstmt.setNull(3, Types.BIGINT); // Set NULL If There's No Teacher
            }

            //The Query Is Executed
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An Error Occurred And The Subject Was Not Updated");
            e.printStackTrace();
        }
        return subject;
    }
}
