package com.eam.quiz.service;
import com.eam.quiz.model.Teacher;
import com.eam.quiz.repository.TeacherRepository;
import com.eam.quiz.singleton.DataBaseSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    Connection connection;

    public TeacherService() {
        this.connection = DataBaseSingleton.getInstance().getConnection();
    }

    //Search All Teachers
    public List<Teacher> findAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        //Simple Sql Stuff
        String selectAllSql = "SELECT * FROM teacher";
        try{
            //Query Execution
            Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectAllSql);

            //Cicle To Save All The Teachers In The ArrayList
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getLong("id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPhoneNumber(rs.getInt("phone_number"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And No Teacher Was Found");
            e.printStackTrace();
        }
        return teachers;
    }

    //Search An Specific Teacher
    public Optional<Teacher> searchTeacher(long id) {
        // The Query Is Made In Sql
        String selectSQL = "SELECT * FROM teacher Where id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setLong(1, id);

            // The Query Is Executed
            ResultSet rs = pstmt.executeQuery();

            // All The Results Are Iterated
            if (rs.next()) {
                Teacher teacher = new Teacher(rs.getInt("phone_number"), rs.getString("name"), rs.getString("email"));
                return Optional.of(teacher);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And No Teacher Was Found");
            e.printStackTrace();
        }
        return null;
    }

    //Insert
    public Teacher insertTeacher(Teacher teacher) {
        // The Query Is Made In Sql
        String insertSQL = "INSERT INTO teacher (email, name, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, teacher.getEmail());
            pstmt.setString(2, teacher.getName());
            pstmt.setInt(3, teacher.getPhoneNumber());

            // The Query Is Executed In Int To Watch If There Are Rows Affected
            int rowsAffected = pstmt.executeUpdate();

            // The Query Is Verified
            if (rowsAffected > 0) {
                System.out.println("Insert Succesful");
            } else {
                System.out.println("The Data Was Not Inserted");
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And The Teacher Was Not Inserted");
            e.printStackTrace();
        }
        return teacher;
    }

    //Delete
    public void deleteTeacher(long id) {
        // The Query Is Made In Sql
        String deleteSQL = "DELETE FROM teacher WHERE id= ?";
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
    public Teacher updateTeacher(Teacher teacher) {
        // The Query Is Made In Sql
        String updateSQL = "UPDATE teacher SET email = ?, name = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, teacher.getEmail());
            pstmt.setString(2, teacher.getName());
            pstmt.setInt(3, teacher.getPhoneNumber());

            //The Query Is Executed
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And The Teacher Was Not Updated");
            e.printStackTrace();
        }
        return teacher;
    }
}