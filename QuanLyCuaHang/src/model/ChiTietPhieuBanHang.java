package model;

import java.util.Objects;

public class ChiTietPhieuBanHang {
	private PhieuBanHang phieubh;
	private Product product_id;
	private int soluong;
	private double price;
	private String trangthai;
	private Product product;


    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
	
	
	@Override
	public int hashCode() {
		return Objects.hash(phieubh, product_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietPhieuBanHang other = (ChiTietPhieuBanHang) obj;
		return Objects.equals(phieubh, other.phieubh) && Objects.equals(product_id, other.product_id);
	}
	public ChiTietPhieuBanHang(PhieuBanHang phieubh, Product product_id, int soluong, double price, String trangthai) {
		super();
		this.phieubh = phieubh;
		this.product_id = product_id;
		this.soluong = soluong;
		this.price = price;
		this.trangthai = trangthai;
	}
	public PhieuBanHang getPhieubh() {
		return phieubh;
	}
	public void setPhieubh(PhieuBanHang phieubh) {
		this.phieubh = phieubh;
	}
	public Product getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Product product_id) {
		this.product_id = product_id;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public ChiTietPhieuBanHang() {
		super();
	}
	private PhieuBanHang phieubanhang;

	public PhieuBanHang getPhieubanhang() {
		return phieubanhang;
	}
	public void setPhieubanhang(PhieuBanHang phieubanhang) {
		this.phieubanhang = phieubanhang;
	}
	
}
