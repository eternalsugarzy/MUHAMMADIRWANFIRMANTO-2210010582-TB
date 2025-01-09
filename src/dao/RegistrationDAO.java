package dao;

import models.Registration;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    private String studentName; // Tambahan
    private String className;   // Tambahan

    // Tambahkan pendaftaran baru
    public void addRegistration(Registration registration) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO registrations (student_id, class_id, registration_date) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, registration.getStudentId());
            ps.setInt(2, registration.getClassId());
            ps.setString(3, registration.getRegistrationDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil semua data pendaftaran
    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT r.registration_id, r.student_id, r.class_id, r.registration_date, "
                + "s.name AS student_name, c.class_name AS class_name "
                + "FROM registrations r "
                + "JOIN students s ON r.student_id = s.student_id "
                + "JOIN classes c ON r.class_id = c.class_id";

        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Registration registration = new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("class_id"),
                        rs.getString("registration_date")
                );
                registration.setStudentName(rs.getString("student_name")); // Ambil nama siswa
                registration.setClassName(rs.getString("class_name"));     // Ambil nama kelas
                registrations.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public void updateRegistration(Registration registration) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE registrations SET student_id = ?, class_id = ?, registration_date = ? WHERE registration_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, registration.getStudentId());
            ps.setInt(2, registration.getClassId());
            ps.setString(3, registration.getRegistrationDate());
            ps.setInt(4, registration.getRegistrationId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus pendaftaran berdasarkan ID
    public void deleteRegistration(int registrationId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM registrations WHERE registration_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, registrationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
