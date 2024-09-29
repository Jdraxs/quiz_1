package com.eam.quiz.dao;

import com.eam.quiz.exceptions.TeacherExceptions;
import com.eam.quiz.model.Subject;
import com.eam.quiz.model.Teacher;
import com.eam.quiz.singleton.DataBaseSingleton;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoTeacher {
    Connection connection;

    public DaoTeacher() {
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
                teacher.setSubject(findSubjectsTeacher(teacher.getId()));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And No Teacher Was Found");
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
                Teacher teacher = new Teacher(rs.getLong("id"), rs.getInt("phone_number"), rs.getString("name"), rs.getString("email"));
                teacher.setSubject(findSubjectsTeacher(id));
                return Optional.of(teacher);
            }
        } catch (SQLException e) {
            System.out.println("An Error Occurred And No Teacher Was Found");
        } catch (NullPointerException e) {
            System.out.println("No Teacher Found With The Specified ID");
        } catch (NumberFormatException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
        }
        return null;
    }

    //Method Used To Add The Subjects That Belong To A Specific Teacher
    private List<Subject> findSubjectsTeacher(long id){
        //List Of The Subjects
        List<Subject> subjects = new ArrayList<>();
        //Sql Query
        String query = "SELECT * FROM subject";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Data Of The Subject
                Subject subject = new Subject();
                subject.setId(rs.getLong("id"));
                subject.setDescription(rs.getString("description"));
                subject.setName(rs.getString("name"));
                subject.setCode(rs.getString("code"));
                subject.setNumberClasses(rs.getInt("number_classes"));
                long teacherId = rs.getLong("teacher_id");
                if (rs.wasNull()) {
                    subject.setTeacher(null);  // If teacher_id Is Null In The Result Set
                } else {
                    if(teacherId == id){
                        subjects.add(subject); // Adds The Subject To The Teacher If It Belongs To Him
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("An SQL Error Occurred And No Teacher Was Found");
        } catch (NullPointerException e) {
            System.out.println("No Teacher Found With The Specified ID");
        } catch (NumberFormatException | HttpMessageNotReadableException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
        }
        return subjects;
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
                throw new TeacherExceptions("The Data Was Not Inserted", null);
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And The Teacher Was Not Inserted");
        }
        return teacher;
    }

    //Delete
    public boolean deleteTeacher(long id) {
        // The Query Is Made In SQL
        String deleteSQL = "DELETE FROM teacher WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setLong(1, id);

            // The Query Is Executed In Int To Check If There Are Rows Affected
            int rowsAffected = pstmt.executeUpdate();

            // Verify if rows were affected
            if (rowsAffected > 0) {
                System.out.println("Delete Successful");
                return true; // Return true if deletion was successful
            } else {
                System.out.println("No Teacher Found With The Specified ID");
                return false; // Return false if no rows were affected
            }
        } catch (SQLException e) {
            System.out.println("An SQL Error Has Occurred: " + e.getMessage());
            return false; // Return false in case of SQL error
        } catch (NullPointerException e) {
            System.out.println("No Teacher Found With The Specified ID");
            return false; // Return false if there was a null pointer
        } catch (NumberFormatException | HttpMessageNotReadableException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted: " + e.getMessage());
            return false; // Return false for other input errors
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
            pstmt.setLong(4, teacher.getId());
            System.out.println(teacher.getId());
            //The Query Is Executed
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update Successful");
            } else {
                System.out.println("No teacher found with the specified ID");
            }
        } catch (SQLException e) {
            System.out.println("An Error Ocurred And The Teacher Was Not Updated");
        } catch (NullPointerException e) {
            System.out.println("No Teacher Found With The Specified ID");
        } catch (NumberFormatException  | HttpMessageNotReadableException e) {
            System.out.println("Some Of The Data Is Wrongly Inputted");
        }
        return teacher;
    }
}
