package dao;

import models.Class;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    public void addClass(Class classData) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO classes (class_name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, classData.getClassName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM classes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                classes.add(new Class(
                        rs.getInt("class_id"),
                        rs.getString("class_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void deleteClass(int classId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM classes WHERE class_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, classId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClass(Class updatedClass) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE classes SET class_name = ? WHERE class_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, updatedClass.getClassName());
            ps.setInt(2, updatedClass.getClassId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getClassIdByName(String name) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT class_id FROM classes WHERE class_name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("class_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Jika tidak ditemukan
    }

}
