package dao;

import models.Student;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(Student student) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO students (name, gender, birth_date, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setString(3, student.getBirthDate()); // Pastikan ini dalam format YYYY-MM-DD
            ps.setString(4, student.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("birth_date"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updateStudent(Student student) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE students SET name = ?, gender = ?, birth_date = ?, phone = ? WHERE student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setString(3, student.getBirthDate());
            ps.setString(4, student.getPhone());
            ps.setInt(5, student.getStudentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getStudentIdByName(String name) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT student_id FROM students WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Jika tidak ditemukan
    }

}
