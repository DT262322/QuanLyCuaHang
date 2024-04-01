package model;

public class Suppliers {
	    private String supplier_id;
	    private String supplier_name;
	    private String phone_number;
	    private String address;
	    private String email;

	    public Suppliers() {
			
		}

		public Suppliers(String supplier_id, String supplier_name, String phone_number, String address, String email) {
			super();
			this.supplier_id = supplier_id;
			this.supplier_name = supplier_name;
			this.phone_number = phone_number;
			this.address = address;
			this.email = email;
		}

		public String getSupplier_id() {
			return supplier_id;
		}

		public void setSupplier_id(String supplier_id) {
			this.supplier_id = supplier_id;
		}

		public String getSupplier_name() {
			return supplier_name;
		}

		public void setSupplier_name(String supplier_name) {
			this.supplier_name = supplier_name;
		}

		public String getPhone_number() {
			return phone_number;
		}

		public void setPhone_number(String phone_number) {
			this.phone_number = phone_number;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

//		@Override
////		public String toString() {
////			return "Suppliers [supplier_id=" + supplier_id + ", supplier_name=" + supplier_name + ", phone_number="
////					+ phone_number + ", address=" + address + ", email=" + email + "]";
////		}
		@Override
		 public String toString() {
		        return supplier_name; // Chỉ hiển thị tên nhà sản xuất trong ComboBox
		    }
		
	}


