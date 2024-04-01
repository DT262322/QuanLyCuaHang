package Bus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ChiTietPhieuBanHang;
import model.Product;
import model.connectDB;

public class busChiTietBanHang {
	
	public boolean luuChiTietPhieuBanHang(ChiTietPhieuBanHang chiTietPhieuBanHang) {
		connectDB cnn = new connectDB();
	    Connection connection = cnn.getConnection();
        PreparedStatement stmt = null;
        boolean success = false;
        String query = "INSERT INTO `chitietphieubanhang`(`phieubh_id`, `product_id`, `soluong`, `price`, `trangthai`) VALUES (?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, chiTietPhieuBanHang.getPhieubh().getPhieubhId());
            stmt.setString(2, chiTietPhieuBanHang.getProduct_id().getProduct_id());
            stmt.setInt(3, chiTietPhieuBanHang.getSoluong());
            stmt.setDouble(4, chiTietPhieuBanHang.getPrice());
            stmt.setString(5, chiTietPhieuBanHang.getTrangthai().toString());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return success;
    }
    
}
