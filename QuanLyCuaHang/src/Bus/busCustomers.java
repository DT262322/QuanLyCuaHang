package Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customers;
import model.Suppliers;
import model.connectDB;

public class busCustomers {
connectDB cnn=new connectDB();
Connection connection=cnn.getConnection();

public ArrayList<Customers> findAll(){
	ArrayList<Customers> arr=new ArrayList<>();
	if(connection==null) {
		return arr;
	}
	String sql="SELECT * FROM `khachhang`";
	try(Statement stm=connection.createStatement();
		ResultSet rs=stm.executeQuery(sql);
			) {
		while(rs.next()) {
			try {
				Customers cus=new Customers(rs.getInt(1),rs.getString(2),rs.getDate(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				arr.add(cus);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return arr;
}
	public int getMaxCustomerId() {
		int maxCustomerId = 0;
		String sql = "SELECT MAX(customer_id) FROM khachhang";
		try (PreparedStatement pstm = connection.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {
			if (rs.next()) {
				maxCustomerId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxCustomerId;
		}
	
	public boolean add(Customers cus) {
		int maxCustomerId = getMaxCustomerId();
        
        int newCustomerId = maxCustomerId + 1;
		String sql="INSERT INTO `khachhang`(`customer_id`, `customer_name`, `date_of_birth`, `customer_sex`, `address`, `phone_number`, `email`) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstm=connection.prepareStatement(sql);
			pstm.setInt(1, newCustomerId);
			pstm.setString(2,cus.getCustomerName());
			pstm.setDate(3,cus.getDateOfBirth());
			pstm.setString(4,cus.getCustomerSex());
			pstm.setString(5,cus.getAddress());
			pstm.setString(6,cus.getPhoneNumber());
			pstm.setString(7,cus.getEmail());
			int rs=pstm.executeUpdate();
			return rs !=0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;		
	}
	public boolean update(Customers cus) {
	    String sql = "UPDATE `khachhang` SET `customer_name`=?, `date_of_birth`=?, `customer_sex`=?, `address`=?, `phone_number`=?, `email`=? WHERE `customer_id`=?";
	    try {
	        PreparedStatement pstm = connection.prepareStatement(sql);
	        pstm.setString(1, cus.getCustomerName());
	        pstm.setDate(2, cus.getDateOfBirth());
	        pstm.setString(3, cus.getCustomerSex());
	        pstm.setString(4, cus.getAddress());
	        pstm.setString(5, cus.getPhoneNumber());
	        pstm.setString(6, cus.getEmail());
	        pstm.setInt(7, cus.getCustomerId());
	        int rs = pstm.executeUpdate();
	        return rs != 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean delete(Customers cus) {
	    String sql = "DELETE FROM `khachhang` WHERE `customer_id` = ?";
	    try {
	        PreparedStatement pstm = connection.prepareStatement(sql);
	        pstm.setInt(1, cus.getCustomerId());
	        int rs = pstm.executeUpdate();
	        return rs != 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
