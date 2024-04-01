package Bus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.CQuanLyNhanVien;
import model.PhieuBanHang;
import model.connectDB;
public class busPhieuBanHang {
	public boolean luuPhieuBanHang(PhieuBanHang phieuBanHang) {
	    if (phieuBanHang.getNhanvienTaoDon() == null) {
	        System.err.println("Lỗi: Người tạo đơn không khả dụng.");
	        return false;
	    }

	    connectDB cnn = new connectDB();
	    Connection connection = cnn.getConnection();
	    PreparedStatement stmt = null;
	    boolean success = false;
	    String query = "INSERT INTO `phieubanhang`(`phieubh_id`, `nhanvien_tao_don`, `customer_id`, `ngay_tao_don`) "
	            + "VALUES (?, ?, ?, ?)";
	    try {
	        stmt = connection.prepareStatement(query);
	        stmt.setInt(1, phieuBanHang.getPhieubhId());
	        stmt.setString(2, phieuBanHang.getNhanvienTaoDon().getUserID());
	        stmt.setInt(3, phieuBanHang.getCustomerId().getCustomerId());
	        stmt.setDate(4, phieuBanHang.getNgayTaoDon());
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


