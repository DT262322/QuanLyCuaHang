package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class CPhieuNhapKho {
	private String phieuNhapID;
	private Date ngaynhap;
	private Suppliers supplierID;
	private CQuanLyNhanVien userID;
	private List<CChiTietPhieuNhapKho> chiTietPhieuNhapList; // Thêm trường này
	private Time time;
	// Các phương thức getter và setter
	
	public List<CChiTietPhieuNhapKho> getChiTietPhieuNhapList() {
		return chiTietPhieuNhapList;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setChiTietPhieuNhapList(List<CChiTietPhieuNhapKho> chiTietPhieuNhapList) {
		this.chiTietPhieuNhapList = chiTietPhieuNhapList;
	}

	public String getPhieuNhapID() {
		return phieuNhapID;
	}

	public void setPhieuNhapID(String phieuNhapID) {
		this.phieuNhapID = phieuNhapID;
	}

	public Date getNgaynhap() {
		return ngaynhap;
	}

	public void setNgaynhap(Date ngaynhap) {
		this.ngaynhap = ngaynhap;
	}

	public Suppliers getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Suppliers supplierID) {
		this.supplierID = supplierID;
	}

	public CQuanLyNhanVien getUserID() {
		return userID;
	}

	public void setUserID(CQuanLyNhanVien userID) {
		this.userID = userID;
	}

	public CPhieuNhapKho(String phieuNhapID, Date ngaynhap, Suppliers supplierID, CQuanLyNhanVien userID) {
		super();
		this.phieuNhapID = phieuNhapID;
		this.ngaynhap = ngaynhap;
		this.supplierID = supplierID;
		this.userID = userID;
	}

	public CPhieuNhapKho() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return phieuNhapID + " || " + ngaynhap + " || " + time ;
	}
}
