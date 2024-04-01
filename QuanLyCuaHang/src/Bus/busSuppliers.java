package Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import model.Suppliers;
import model.connectDB;

public class busSuppliers {
	connectDB cnn = new connectDB();
	Connection connection=cnn.getConnection();
	
	public ArrayList<Suppliers> findAll(){
		ArrayList<Suppliers> arr=new ArrayList<>();
		if(connection ==null)
		{
			return arr;
		}
		String sql="SELECT * FROM nhacungcap";
		try (Statement stm=connection.createStatement();
			ResultSet rs=stm.executeQuery(sql)) 
		{
			while(rs.next()) {
				try {
					Suppliers sp=new Suppliers(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
					arr.add(sp);
				}catch(NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			return arr;
	}
	public boolean Add(Suppliers sp) {
		String sql="INSERT INTO `nhacungcap`(`supplier_id`, `supplier_name`, `phone_number`, `address`, `email`) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pstm=connection.prepareStatement(sql);
			pstm.setString(1,sp.getSupplier_id());
			pstm.setString(2,sp.getSupplier_name());
			pstm.setString(3, sp.getPhone_number());
			pstm.setString(4,sp.getAddress());
			pstm.setString(5,sp.getEmail());
			int rs=pstm.executeUpdate();
			return rs !=0;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean Update(Suppliers sp) {
		String sql="UPDATE `nhacungcap` SET `supplier_id`=?,`supplier_name`=?,`phone_number`=?,`address`=?,`email`=? WHERE ?";
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);	
			pstm.setString(1,sp.getSupplier_id());
			pstm.setString(2,sp.getSupplier_name());
			pstm.setString(3, sp.getPhone_number());
			pstm.setString(4,sp.getAddress());
			pstm.setString(5,sp.getEmail());
			int rs=pstm.executeUpdate();
			return rs !=0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(Suppliers sp) {
		ArrayList<Suppliers> supplierList=findAll();
		int index=-1;
		for(int i=0;i<supplierList.size();i++) {
			if(supplierList.get(i).getSupplier_id().equals(sp.getSupplier_id())) {
			index=i;
			break;
		}
	}
	if(index!=-1)
	{
		supplierList.remove(index);
		return true;
	}
	return false;
}
}
