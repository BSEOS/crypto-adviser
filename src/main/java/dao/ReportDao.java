package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Report;
import controller.Utils;

public class ReportDao {
    public int creatReport(Report report) throws ClassNotFoundException {
		final String DB_URL = "jdbc:mysql://localhost:3306/bdd_crypto_adviser?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
		final String[] auth = Utils.getSQLAuth();
		final String USER = auth[0];
		final String PASS = auth[1];
        String INSERT_REPORT_SQL = "INSERT INTO Report" +
            "  (idviser_id, crypto_id, title, content, created_at) VALUES " +
            " (?, ?, ?, ?, NOW());";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection(DB_URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT_SQL)) {
            preparedStatement.setInt(1, report.getIdviser_id());
            preparedStatement.setInt(2, report.getCrypto_id());
            preparedStatement.setString(3, report.getTitle());
            preparedStatement.setString(4, report.getContent());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}