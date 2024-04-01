package model;

import java.sql.Date;

public class OrderDetail {
   private int phieuBh_ID;
   private String nhanVien_tao_don;
   private int customer_ID;
   private int customer_Phone;
   private Date ngay_tao_don;
   private String product_ID;
   private String product_Name;
   private int soLuong;
   private Double price;
   private String trangThai;
   
public int getCustomer_Phone() {
	return customer_Phone;
}
public void setCustomer_Phone(int customer_Phone) {
	this.customer_Phone = customer_Phone;
}
public OrderDetail(int phieuBh_ID, String nhanVien_tao_don, int customer_ID, int customer_Phone, Date ngay_tao_don,
		String product_ID, String product_Name, int soLuong, Double price, String trangThai) {
	super();
	this.phieuBh_ID = phieuBh_ID;
	this.nhanVien_tao_don = nhanVien_tao_don;
	this.customer_ID = customer_ID;
	this.customer_Phone = customer_Phone;
	this.ngay_tao_don = ngay_tao_don;
	this.product_ID = product_ID;
	this.product_Name = product_Name;
	this.soLuong = soLuong;
	this.price = price;
	this.trangThai = trangThai;
}
public String getProduct_Name() {
	return product_Name;
}
public void setProduct_Name(String product_Name) {
	this.product_Name = product_Name;
}
public int getPhieuBh_ID() {
	return phieuBh_ID;
}
public void setPhieuBh_ID(int phieuBh_ID) {
	this.phieuBh_ID = phieuBh_ID;
}
public String getNhanVien_tao_don() {
	return nhanVien_tao_don;
}
public void setNhanVien_tao_don(String nhanVien_tao_don) {
	this.nhanVien_tao_don = nhanVien_tao_don;
}
public int getCustomer_ID() {
	return customer_ID;
}
public void setCustomer_ID(int customer_ID) {
	this.customer_ID = customer_ID;
}
public Date getNgay_tao_don() {
	return ngay_tao_don;
}
public void setNgay_tao_don(Date ngay_tao_don) {
	this.ngay_tao_don = ngay_tao_don;
}
public String getProduct_ID() {
	return product_ID;
}
public void setProduct_ID(String product_ID) {
	this.product_ID = product_ID;
}
public int getSoLuong() {
	return soLuong;
}
public void setSoLuong(int soLuong) {
	this.soLuong = soLuong;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public String getTrangThai() {
	return trangThai;
}
public void setTrangThai(String trangThai) {
	this.trangThai = trangThai;
}

public OrderDetail() {

}
   
   
}
