package Bus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import model.connectDB;
import model.CChucVu;
import model.CQuanLyNhanVien;
import org.apache.commons.lang3.RandomStringUtils;

public class BusQuanLyNhanVien {
	connectDB cnn = new connectDB();
	Connection connection;

	public BusQuanLyNhanVien() {
		connection = cnn.getConnection();
	}

	// Phương thức để chuyển đổi mật khẩu thành chuỗi MD5
	private String md5Hash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] byteData = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : byteData) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	// Hàm kiểm tra Tên đăng nhập và mật khẩu

	public boolean kiemTraDangNhap(String username, String password, String chucVuID) {
		// Kiểm tra xem các thông tin cần thiết có null hoặc rỗng không
		if (username == null || password == null || chucVuID == null || username.isEmpty() || password.isEmpty()
				|| chucVuID.isEmpty()) {
			System.out.println("Please enter complete login information.");
			return false;
		}
		// lấy mật khẩu đổi thành chuổi và truyên vào truy vấn
		String hashedPassword = md5Hash(password);
		String sql = "SELECT * FROM Employee WHERE userID = ? AND password = ? AND Position = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setString(2, hashedPassword);
			pstm.setString(3, chucVuID);

			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				updateStatus("active", username);
				return true;
			} else {
				System.out.println("The username or password is incorrect.");
				return false;
			}
		} catch (SQLException e) {
			// Bắt lỗi và in ra thông báo
			System.out.println("An error occurred while checking the login.");
			e.printStackTrace();
			return false;
		}
	}

	public void updateStatus(String status, String userID) {
		String sql = "UPDATE Employee SET status = ? WHERE userID = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, status);
			pstm.setString(2, userID);
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("An error occurred while updating user status.");
			e.printStackTrace();
		}
	}

	// Phương thức lấy danh sách chức vụ
	public ArrayList<CChucVu> showChucVu() {
		ArrayList<CChucVu> chucVuList = new ArrayList<>();
		String sql = "SELECT position_id, position_name FROM Position";

		try (PreparedStatement pstm = connection.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
			while (rs.next()) {
				CChucVu cv = new CChucVu(rs.getString("position_id"), rs.getString("position_name"));
				chucVuList.add(cv);
			}
		} catch (SQLException e) {
			// Xử lý ngoại lệ SQL

		} catch (Exception e) {
			// Xử lý các ngoại lệ khác

		}

		return chucVuList;
	}

	public ArrayList<CQuanLyNhanVien> findAllNhanVien() {
		String sql = "SELECT e.`userID`, e.`fullname`, e.`Date_of_employment`, e.`Gender`, e.`Date_of_birth`, e.`PhoneNumber`, e.`IdentityCard`, e.`email`, e.`url_img_user`, p.`position_name` AS `position`"
				+ " FROM `employee` e" + " LEFT JOIN `position` p ON e.`Position` = p.`position_id`";

		ArrayList<CQuanLyNhanVien> listNhanVien = new ArrayList<>();
		try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
			while (rs.next()) {
				CQuanLyNhanVien nhanVienOjb = new CQuanLyNhanVien();
				nhanVienOjb.setUserID(rs.getString("userID"));
				nhanVienOjb.setHoten(rs.getString("fullname"));
				nhanVienOjb.setNgayvaolam(rs.getDate("Date_of_employment"));
				nhanVienOjb.setGioitinh(rs.getString("Gender"));
				nhanVienOjb.setNgay_sinh(rs.getDate("Date_of_birth"));
				nhanVienOjb.setSo_dien_thoai(rs.getString("PhoneNumber"));
				nhanVienOjb.setCccd(rs.getString("IdentityCard"));
				nhanVienOjb.setEmail(rs.getString("email"));
				nhanVienOjb.setUrlImage(rs.getString("url_img_user"));
				// Set chức vụ
				CChucVu chucVu = new CChucVu();
				chucVu.setTenChucVu(rs.getString("position"));
				nhanVienOjb.setChucvu(chucVu);
				listNhanVien.add(nhanVienOjb);
			}
		} catch (SQLException e) {
			// Xử lý ngoại lệ SQL
			e.printStackTrace();
		} catch (Exception e) {
			// Xử lý các ngoại lệ khác
			e.printStackTrace();
		}

		return listNhanVien;
	}

	// Thêm nhân viên
	public boolean addNhanVien(CQuanLyNhanVien nv) {

		String sql = "INSERT INTO `Employee`(`userID`, `password`, `fullname`,Date_of_employment, "
				+ "`Gender`, `Date_of_birth`, `PhoneNumber`, `IdentityCard`,`email`, `Position`,`url_img_user`,`status`)"
				+ " VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?)";
		try {

			PreparedStatement ptsm = connection.prepareStatement(sql);
			ptsm.setString(1, nv.getUserID());
			ptsm.setString(2, nv.getPassword());
			ptsm.setString(3, nv.getHoten());
			ptsm.setString(4, nv.getGioitinh());
			ptsm.setDate(5, nv.getNgay_sinh());
			ptsm.setString(6, nv.getSo_dien_thoai());
			ptsm.setString(7, nv.getCccd());
			ptsm.setString(8, nv.getEmail());
			ptsm.setString(9, nv.getChucvu().getChucVuID());
			ptsm.setString(10, nv.getUrlImage());
			ptsm.setString(11, nv.getStatus());
			int rs = ptsm.executeUpdate();
			return rs != 0;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// Xóa nhân viên
	public boolean deleteNhanVien(CQuanLyNhanVien nv) {
		String sql = "DELETE FROM `employee` WHERE userID = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, nv.getUserID());
			int rs = pstm.executeUpdate();

			return rs != 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// Chỉnh sửa TT nhân viên
	public boolean editNhanVien(CQuanLyNhanVien nv) {
		String sql = "UPDATE Employee SET fullname = ?, Gender = ?, "
				+ "Date_of_birth = ?, PhoneNumber = ?, IdentityCard = ?,`email`=?, position = ?, url_img_user = ? WHERE userID = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, nv.getHoten());
			pstm.setString(2, nv.getGioitinh());
			pstm.setDate(3, nv.getNgay_sinh());
			pstm.setString(4, nv.getSo_dien_thoai());
			pstm.setString(5, nv.getCccd());
			pstm.setString(6, nv.getEmail());
			pstm.setString(7, nv.getChucvu().getChucVuID());
			pstm.setString(8, nv.getUrlImage());
			pstm.setString(9, nv.getUserID());

			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Update unsuccessful!!!" + e);
			return false;
		}
	}

	public boolean updatePassword(String password, String userid) {
		String sql = "UPDATE `employee` SET `password`=? WHERE `userID`=?";
		String passMD5 = md5Hash(password);
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, passMD5);
			pstm.setString(2, userid);

			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Update unsuccessful!!!" + e);
			return false;
		}

	}

	public boolean changeStatusUser(String status, String userid) {
		String sql = "UPDATE `employee` SET `status`=? WHERE `userID`=?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, status);
			pstm.setString(2, userid);

			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// lấy id user từ tên đăng nhập
	public CQuanLyNhanVien getUserByUserName(String username) {
		CQuanLyNhanVien user = null;
		String sql = "SELECT * FROM Employee WHERE userID = ?";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, username);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				user = new CQuanLyNhanVien();
				user.setUserID(rs.getString("userID"));
				user.setPassword(rs.getString("password"));
				user.setHoten(rs.getString("fullname"));
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
