package model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.AdminController;

public class connectDB {
    static final String URL = "jdbc:mysql://localhost:3306/quanlycuahang";
    static final String user = "root";
    static final String pw = "";
    private Connection connect;

    public connectDB() {
        connect = null;
    }

    public Connection getConnection() {
        if (connect == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(URL, user, pw);
                return connect;
            } catch (SQLException e) {
                showErrorAlert("Lỗi kết nối đến cơ sở dữ liệu: " + e.getMessage());
                return null;
            } catch (ClassNotFoundException e) {
     
                showErrorAlert("Lỗi tải driver JDBC: " + e.getMessage());
                return null;
            }
        } else {
            return connect;
        }
    }
    
    public String validateLogin(String username, String password) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("role");
                    } else {
                        showErrorAlert("Tên đăng nhập hoặc mật khẩu không đúng!");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
