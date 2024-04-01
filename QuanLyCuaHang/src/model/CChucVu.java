package model;

import java.util.ArrayList;

public class CChucVu {
	 private String chucVuID; // Thêm trường chucVuID
		private String tenChucVu;

	    public String getChucVuID() {
	        return chucVuID;
	    }

	    public void setChucVuID(String cChucVu) {
	        this.chucVuID = cChucVu;
	    }

	    public String getTenChucVu() {
	        return tenChucVu;
	    }

	    public void setTenChucVu(String tenChucVu) {
	        this.tenChucVu = tenChucVu;
	    }

	    public CChucVu() {
	        super();
	    }

	    public CChucVu(String chucVuID, String tenChucVu) {
	        super();
	        this.chucVuID = chucVuID;
	        this.tenChucVu = tenChucVu;
	    }

	    public CChucVu(String tenChucVu) {
	        super();
	        this.tenChucVu = tenChucVu;
	    }

	    @Override
	    public String toString() {
	        return tenChucVu;
	    }
	
	
	
	
}