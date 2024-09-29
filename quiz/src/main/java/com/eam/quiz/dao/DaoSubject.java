package com.eam.quiz.dao;

import com.eam.quiz.model.Subject;
import com.eam.quiz.model.Teacher;
import com.eam.quiz.service.TeacherService;
import com.eam.quiz.singleton.DataBaseSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoSubject {
    private Connection connection;
    private TeacherService serviceTeacher;

    public DaoSubject() {
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
                long teacherId = rs.getLong("teacher_id");
                if (rs.wasNull()) {
                    subject.setTeacher(null);  // If teacher_id is null in the ResultSet
                } else {
                    Optional<Teacher> teacher = serviceTeacher.searchTeacher(teacherId);
                    subject.setTeacher(teacher.orElse(null));  // Safely sets to null if not found
                } // Set The Teacher To Null If There Is None
                subjects.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Error Has Occurred");
        } catch (NullPointerException e) {
            System.out.println("No Subject Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
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
                Subject subject = new Subject(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getInt("number_Classes"), rs.getString("code"));
                long teacherId = rs.getLong("teacher_id");
                if (rs.wasNull()) {
                    subject.setTeacher(null);  // If teacher_id is null in the ResultSet
                } else {
                    Optional<Teacher> teacher = serviceTeacher.searchTeacher(teacherId);
                    subject.setTeacher(teacher.orElse(null));  // Safely sets to null if not found
                } // Set The Teacher To Null If There Is None
                return Optional.of(subject);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And No Subject Was Found");
        } catch (NullPointerException e) {
            System.out.println("No Subject Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
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
        } catch (NullPointerException e) {
            System.out.println("No Subject Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
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
            System.out.println("Delete Successful");
        } catch (SQLException e) {
            System.out.println("An Error Ocurred");
        } catch (NullPointerException e) {
            System.out.println("No Subject Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
             }
    }

    //Update
    public Subject updateSubject(Subject subject) {
        // The Query Is Made In Sql
        String updateSQL = "UPDATE subject SET description = ?, name = ?, teacher_id = ?, code = ?, number_classes = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, subject.getDescription());
            pstmt.setString(2, subject.getName());
            if (subject.getTeacher() != null) {
                pstmt.setLong(3, subject.getTeacher().getId()); // Set Teacher ID
            } else {
                pstmt.setNull(3, Types.BIGINT); // Set NULL If There's No Teacher
            }
            pstmt.setString(4, subject.getCode());
            pstmt.setInt(5, subject.getNumberClasses());
            pstmt.setLong(6, subject.getId());
            //The Query Is Executed
            System.out.println(subject.getId());
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Update Successful");
            } else {
                System.out.println("No Subject Found With The Specified ID");
            }
        } catch (SQLException e) {
            System.out.println("An Error Occurred And The Subject Was Not Updated");
        } catch (NullPointerException e) {
            System.out.println("No Subject Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
        }
        return subject;
    }
}
