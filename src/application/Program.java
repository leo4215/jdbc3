package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import db.DB;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DB.getConnection();

            st = conn.prepareStatement(""" 
                            INSERT INTO seller 
                            (Name, Email, BirthDate, BaseSalary, DepartmentId)
                            VALUES (?, ?, ?, ?, ?) """, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, "Carl Purple");
            st.setString(2, "carl@gmail.com");
            st.setDate(3, java.sql.Date.valueOf(LocalDate.of(2022, 1, 9)));
            st.setDouble(4, 3000.0);
            st.setInt(5, 4);

            int rowsAffected = st.executeUpdate(); // this method returns the number of rows affected

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys(); // may return one or more value of keys depending on how many insertions were made
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            } else {
                System.out.println("No row affected!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
