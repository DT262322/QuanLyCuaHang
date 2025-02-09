package Bus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.CChiTietPhieuNhapKho;
import model.CPhieuNhapKho;
import model.connectDB;

public class busImportProduct {
	connectDB cnn = new connectDB();
	Connection connection = cnn.getConnection();
	PreparedStatement pstm;

	// Lấy giá nhập gần đây nhất
	public double getGiaNhap(String productId) {
		String sql = "SELECT gia_nhap FROM chitietphieunhapkho JOIN phieunhapkho ON chitietphieunhapkho.phieunhap_id = phieunhapkho.phieunhap_id WHERE product_id = ? ORDER BY phieunhapkho.time DESC LIMIT 1;";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, productId);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getDouble("gia_nhap");
			} else {
				return -1; // Hoặc một giá trị khác để biểu thị rằng không tìm thấy giá nhập.
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Hoặc một giá trị khác để biểu thị lỗi.
		} finally {
			// Đóng các tài nguyên, ví dụ: connection, pstm, rs nếu cần.
		}
	}

	// Lưu phiếu nhập kho
	public boolean savePhieuNhap(CPhieuNhapKho nhapkho) {
		String sql = "INSERT INTO `phieunhapkho`(`phieunhap_id`, `ngaynhap`, `time` ,`supplier_id`, "
				+ "`userID`) VALUES (?,?,NOW(),?,?)";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, nhapkho.getPhieuNhapID());
			// Chuyển đổi đối tượng Date thành chuỗi
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strNgayNhap = sdf.format(nhapkho.getNgaynhap());
			pstm.setString(2, strNgayNhap);
			pstm.setString(3, nhapkho.getSupplierID().getSupplier_id());
			pstm.setString(4, nhapkho.getUserID().getUserID());

			// Thực hiện thêm dữ liệu vào cơ sở dữ liệu
			int rs = pstm.executeUpdate();

			// Trả về true nếu có ít nhất một dòng được thêm vào cơ sở dữ liệu
			return rs > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

//	public boolean addChiTietPhieuNhap(String idPhieuNhap, String idSanPham, int soLuong, double giaNhap) {
//		String sql = "INSERT INTO `chitietphieunhapkho`(`phieunhap_id`, `product_id`, `so_luong`, `gia_nhap`) "
//				+ "VALUES (?,?,?,?)";
//		try {
//			pstm = connection.prepareStatement(sql);
//			pstm.setString(1, idPhieuNhap);
//			pstm.setString(2, idSanPham);
//			pstm.setInt(3, soLuong);
//			pstm.setDouble(4, giaNhap);
//			int rs = pstm.executeUpdate();
//			return rs != 0;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return false;
//		}
//	}
	public boolean addChiTietPhieuNhap(CChiTietPhieuNhapKho chitiet) {
		String sql = "INSERT INTO `chitietphieunhapkho`(`phieunhap_id`, `product_id`, `so_luong`, `gia_nhap`) "
				+ "VALUES (?,?,?,?)";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, chitiet.getPhieunhap_id());
			pstm.setString(2, chitiet.getProduct_id());
			pstm.setInt(3, chitiet.getSoluong());
			pstm.setDouble(4, chitiet.getGiaNhap());
			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// Cập nhật lại số lượng sản phẩm trên csdl
	public boolean updateSoLuongSP(int soLuong, String productID) {
		String sql = "UPDATE sanpham SET soluong = soluong + ? WHERE product_id = ?";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, soLuong);
			pstm.setString(2, productID);
			int rs = pstm.executeUpdate();
			return rs != 0;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// lấy ra tất cả ID phiếu nhập kho và ngày
	public ArrayList<CPhieuNhapKho> getIDPhieuNhap() {
		String sql = "SELECT phieunhap_id, ngaynhap,time FROM phieunhapkho ORDER BY time DESC ";
		ArrayList<CPhieuNhapKho> listPhieuKho = new ArrayList<>();
		try {
			pstm = connection.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String IdPhieuNhap = rs.getString("phieunhap_id");
				Date ngaynhap = rs.getDate("ngaynhap");
				Time time = rs.getTime("time");
				CPhieuNhapKho pn = new CPhieuNhapKho();
				pn.setPhieuNhapID(IdPhieuNhap);
				pn.setNgaynhap(ngaynhap);
				pn.setTime(time);
				listPhieuKho.add(pn);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listPhieuKho;
	}

	// Kiểm tra phiếu nhập có hay không
	public boolean checkIDPhieuNhap(String idphieunhap) {
		String sql = "SELECT COUNT(*) FROM phieunhapkho WHERE phieunhap_id = ?";
		try {
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, idphieunhap);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return false;
	}
	//Tìm kiếm hóa đơn theo mã hóa đơn hoặc ngày nhập
	 public ArrayList<CPhieuNhapKho> timKiemPNByNgayNhap(Date value) {
	        String sql = "SELECT phieunhap_id, ngaynhap, time FROM phieunhapkho WHERE ngaynhap = ?";
	        ArrayList<CPhieuNhapKho> listPhieuKho = new ArrayList<>();
	        try {
	            PreparedStatement pstm = connection.prepareStatement(sql);
	            // Thiết lập tham số cho mã hóa đơn và ngày nhập
	            pstm.setDate(1, value);
	            ResultSet rs = pstm.executeQuery();
	            while (rs.next()) {
	                String IdPhieuNhap = rs.getString("phieunhap_id");
	                Date ngayNhap = rs.getDate("ngaynhap");
	                Time thoiGian = rs.getTime("time");
	                CPhieuNhapKho pn = new CPhieuNhapKho();
	                pn.setPhieuNhapID(IdPhieuNhap);
	                pn.setNgaynhap(ngayNhap);
	                pn.setTime(thoiGian);
	                listPhieuKho.add(pn);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return listPhieuKho;
	    }
	 public ArrayList<CPhieuNhapKho> timKiemPNByMaHoaDon(String value) {
	        String sql = "SELECT phieunhap_id, ngaynhap, time FROM phieunhapkho WHERE phieunhap_id = ?";
	        ArrayList<CPhieuNhapKho> listPhieuKho = new ArrayList<>();
	        try {
	            PreparedStatement pstm = connection.prepareStatement(sql);
	            // Thiết lập tham số cho mã hóa đơn và ngày nhập
	            pstm.setString(1, value);
	            ResultSet rs = pstm.executeQuery();
	            while (rs.next()) {
	                String IdPhieuNhap = rs.getString("phieunhap_id");
	                Date ngayNhap = rs.getDate("ngaynhap");
	                Time thoiGian = rs.getTime("time");
	                CPhieuNhapKho pn = new CPhieuNhapKho();
	                pn.setPhieuNhapID(IdPhieuNhap);
	                pn.setNgaynhap(ngayNhap);
	                pn.setTime(thoiGian);
	                listPhieuKho.add(pn);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return listPhieuKho;
	    }
}
