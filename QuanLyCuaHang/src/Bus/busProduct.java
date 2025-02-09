package Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.OrderDetail;
import model.Product;
import model.connectDB;

public class busProduct {
	connectDB cnn = new connectDB();
	Connection connection = cnn.getConnection(); // Initialize connection

	// truy vấn tất cả
	public ArrayList<Product> findAll() {
		ArrayList<Product> arr = new ArrayList<>();

		if (connection == null) {
			return arr;
		}
		String sql = "SELECT * FROM sanpham";
		try (Statement stm = connection.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {
				try {
					Product pd = new Product(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4),
							rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
					arr.add(pd);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	// Truy vấn Update
	public boolean update(Product pd) {
		String sql = "UPDATE `sanpham` SET `product_name`=?, `price`=?, `color`=?, `wired_or_wireless`=?, `soluong`=?, `description`=?, `image_url`=?, `category_id`=? WHERE `product_id`=?";

		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, pd.getProduct_name());
			pstm.setDouble(2, pd.getPrice());
			pstm.setString(3, pd.getColor());
			pstm.setString(4, pd.getWired_or_wireless());
			pstm.setInt(5, pd.getSoluong());
			pstm.setString(6, pd.getDescription());
			pstm.setString(7, pd.getImage_url());
			pstm.setString(8, pd.getCategory_id());
			pstm.setString(9, pd.getProduct_id());

			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// truy vấn insert
	public boolean add(Product pd) {
		String sql = "INSERT INTO `sanpham`(`product_id`, `product_name`, `price`, `color`, `wired_or_wireless`, `soluong`, `description`, `image_url`, `category_id`) VALUES (?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, pd.getProduct_id());
			pstm.setString(2, pd.getProduct_name());
			pstm.setString(3, pd.getPrice() + "");
			pstm.setString(4, pd.getColor());
			pstm.setString(5, pd.getWired_or_wireless());
			pstm.setString(6, pd.getSoluong() + "");
			pstm.setString(7, pd.getDescription());
			pstm.setString(8, pd.getImage_url());
			int rs = pstm.executeUpdate();
			return rs != 0;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean delete(Product pd) {
		ArrayList<Product> productList = findAll();
		int index = -1;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getProduct_id().equals(pd.getProduct_id())) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			productList.remove(index);
			return true;
		}
		return false;
	}

	// Phương thức để tìm ID sản phẩm từ tên sản phẩm
	public String findProductIdByProductName(String productName) {
		String sql = "SELECT product_id FROM sanpham WHERE product_name = ?";
		String productId = "";

		try {
			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, productName);
			ResultSet rs = pstm.executeQuery();

			// Kiểm tra xem có kết quả trả về không
			if (rs.next()) {
				productId = rs.getString("product_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productId;
	}
	//Update số lượng sản phẩm sau khi nhập kho
	 public boolean updateQuantity(Product pd, int soLuong) {
	        String sql = "UPDATE `sanpham` SET `product_name`=?, `soluong`=?, `color`=? WHERE `product_id`=?";
	        
	        try {
	            PreparedStatement pstm = connection.prepareStatement(sql);
	            
	            // Giả sử `soluong` là tên cột chứa số lượng sản phẩm trong bảng `sanpham`
	            // Bạn cần cập nhật số lượng mới bằng cách trừ đi số lượng sản phẩm đã mua (soLuong)
	            int soLuongMoi = pd.getSoluong() - soLuong;
	            
	            // Đảm bảo số lượng mới không nhỏ hơn 0
	            if (soLuongMoi < 0) {
	                soLuongMoi = 0;
	            }
	            
	            // Thiết lập các tham số cho câu truy vấn
	            pstm.setString(1, pd.getProduct_name());
	            pstm.setInt(2, soLuongMoi);
	            pstm.setString(3, pd.getColor());
	            pstm.setString(4, pd.getProduct_id());

	            int ketQua = pstm.executeUpdate();
	            return ketQua != 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	 public ArrayList<OrderDetail> getOrderHistory() {
		    ArrayList<OrderDetail> orderDetails = new ArrayList<>();
		    String sql = "SELECT pbh.phieubh_id, pbh.nhanvien_tao_don, pbh.customer_id, pbh.ngay_tao_don, " +
		             "       ctpbh.product_id, ctpbh.soluong, ctpbh.price, ctpbh.trangthai, " +
		             "       sp.product_name, " +  
		             "       cus.phone_number " + 
		             "FROM phieubanhang pbh " +
		             "JOIN chitietphieubanhang ctpbh ON pbh.phieubh_id = ctpbh.phieubh_id " +
		             "JOIN sanpham sp ON ctpbh.product_id = sp.product_id " +
		             "JOIN khachhang cus ON pbh.customer_id = cus.customer_id";

		    try {
		        PreparedStatement pstm = connection.prepareStatement(sql);
		        ResultSet resultSet = pstm.executeQuery();
		        while (resultSet.next()) {
		            OrderDetail orderDetail = new OrderDetail();
		            // Đặt các giá trị của OrderDetail từ ResultSet
		            orderDetail.setPhieuBh_ID(resultSet.getInt("phieubh_id"));
		            orderDetail.setNhanVien_tao_don(resultSet.getString("nhanvien_tao_don"));
		            orderDetail.setCustomer_Phone(resultSet.getInt("phone_number"));
		            orderDetail.setNgay_tao_don(resultSet.getDate("ngay_tao_don"));	          
		            orderDetail.setProduct_ID(resultSet.getString("product_id"));
		            orderDetail.setProduct_Name(resultSet.getString("product_name")); // Lấy tên sản phẩm
		            orderDetail.setSoLuong(resultSet.getInt("soluong"));
		            orderDetail.setPrice(resultSet.getDouble("price"));
		            orderDetail.setTrangThai(resultSet.getString("trangthai"));
		            
		            // Thêm OrderDetail vào danh sách
		            orderDetails.add(orderDetail);
		        }
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
		    return orderDetails;
		}


}
