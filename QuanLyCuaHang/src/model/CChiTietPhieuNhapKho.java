package model;

public class CChiTietPhieuNhapKho {
	private String phieunhap_id;
	private String product_id;
	private int soluong;
	private double giaNhap;
	private String tenSP;
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public String getPhieunhap_id() {
		return phieunhap_id;
	}
	public void setPhieunhap_id(String phieunhap_id) {
		this.phieunhap_id = phieunhap_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public double getGiaNhap() {
		return giaNhap;
	}
	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}
	public CChiTietPhieuNhapKho(String phieunhap_id, String product_id, int soluong, double giaNhap) {
		super();
		this.phieunhap_id = phieunhap_id;
		this.product_id = product_id;
		this.soluong = soluong;
		this.giaNhap = giaNhap;
	}
	

	public CChiTietPhieuNhapKho() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return product_id + tenSP +soluong +giaNhap;
	}
}
