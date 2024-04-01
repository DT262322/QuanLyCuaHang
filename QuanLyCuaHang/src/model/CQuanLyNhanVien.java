package model;

import java.sql.Date;
import java.util.ArrayList;

public class CQuanLyNhanVien {
	private String userID;
    private String password;
    private String hoten;
    private Date ngayvaolam;
    private String gioitinh;
    private Date ngay_sinh;
    private String so_dien_thoai;
    private String cccd;
    private String email;
    private String urlImage; 
    private String status;
	CChucVu chucvu;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public Date getNgayvaolam() {
		return ngayvaolam;
	}
	public void setNgayvaolam(Date ngayvaolam) {
		this.ngayvaolam = ngayvaolam;
	}
	public String getGioitinh() {
		return gioitinh;
	}
	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}
	public Date getNgay_sinh() {
		return ngay_sinh;
	}
	public void setNgay_sinh(Date ngay_sinh) {
		this.ngay_sinh = ngay_sinh;
	}
	public String getSo_dien_thoai() {
		return so_dien_thoai;
	}
	public void setSo_dien_thoai(String so_dien_thoai) {
		this.so_dien_thoai = so_dien_thoai;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CChucVu getChucvu() {
		return chucvu;
	}
	public void setChucvu(CChucVu chucvu) {
		this.chucvu = chucvu;
	}
	public CQuanLyNhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
    @Override
    public String toString() {
    	return "CQuanLyNhanVien{" +
                "userID='" + userID + '\'' +
                ", hoten='" + hoten + '\'' +
                ", ngayvaolam=" + ngayvaolam +
                ", gioitinh='" + gioitinh + '\'' +
                ", ngay_sinh=" + ngay_sinh +
                ", so_dien_thoai='" + so_dien_thoai + '\'' +
                ", cccd='" + cccd + '\'' +
                ", email='" + email + '\'' +
                ", chuc_vu='" + chucvu+ '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
	}

	public CQuanLyNhanVien(String userID, String password, String hoten, Date ngayvaolam, String gioitinh,
			Date ngay_sinh, String so_dien_thoai, String cccd, String email, String urlImage, String status,
			CChucVu chucvu) {
		super();
		this.userID = userID;
		this.password = password;
		this.hoten = hoten;
		this.ngayvaolam = ngayvaolam;
		this.gioitinh = gioitinh;
		this.ngay_sinh = ngay_sinh;
		this.so_dien_thoai = so_dien_thoai;
		this.cccd = cccd;
		this.email = email;
		this.urlImage = urlImage;
		this.status = status;
		this.chucvu = chucvu;
	}
	
	
	
	
	
    
}
