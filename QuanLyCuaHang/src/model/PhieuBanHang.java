package model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class PhieuBanHang {
    private int phieubhId;
    private CQuanLyNhanVien nhanvienTaoDon;
    private Customers customerId;
    private Date ngayTaoDon;
    private List<ChiTietPhieuBanHang> chiTietPhieuBanHangList;

	public List<ChiTietPhieuBanHang> getChiTietPhieuBanHangList() {
		return chiTietPhieuBanHangList;
	}
	public void setChiTietPhieuBanHangList(List<ChiTietPhieuBanHang> chiTietPhieuBanHangList) {
		this.chiTietPhieuBanHangList = chiTietPhieuBanHangList;
	}
	@Override
	public int hashCode() {
		return Objects.hash(phieubhId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuBanHang other = (PhieuBanHang) obj;
		return phieubhId == other.phieubhId;
	}


	private ChiTietPhieuBanHang chitietphieubanhang;
	
	public ChiTietPhieuBanHang getChitietphieubanhang() {
		return chitietphieubanhang;
	}
	public void setChitietphieubanhang(ChiTietPhieuBanHang chitietphieubanhang) {
		this.chitietphieubanhang = chitietphieubanhang;
	}
	public PhieuBanHang() {
		super();
	}
	public PhieuBanHang(int phieubhId, CQuanLyNhanVien nhanvienTaoDon, Customers customerId, Date ngayTaoDon) {
		super();
		this.phieubhId = phieubhId;
		this.nhanvienTaoDon = nhanvienTaoDon;
		this.customerId = customerId;
		this.ngayTaoDon = ngayTaoDon;
	}



	public int getPhieubhId() {
		return phieubhId;
	}


	public void setPhieubhId(int phieubhId) {
		this.phieubhId = phieubhId;
	}


	public CQuanLyNhanVien getNhanvienTaoDon() {
		return nhanvienTaoDon;
	}


	public void setNhanvienTaoDon(CQuanLyNhanVien nhanvienTaoDon) {
		this.nhanvienTaoDon = nhanvienTaoDon;
	}


	public Customers getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Customers customerId) {
		this.customerId = customerId;
	}


	public Date getNgayTaoDon() {
		return ngayTaoDon;
	}


	public void setNgayTaoDon(Date ngayTaoDon) {
		this.ngayTaoDon = ngayTaoDon;
	}
	
	

    
}

