package model;

public class Product {
	    private String product_id;
	    private String product_name;
	    private double price;
	    private String color;
	    private String wired_or_wireless;
	    private int soluong;
	    private String description;
	    private String image_url;
	    private String category_id;
	    
		public Product(String product_id, String product_name, double price, String color, String wired_or_wireless,
				int soluong, String description, String image_url, String category_id) {
			super();
			this.product_id = product_id;
			this.product_name = product_name;
			this.price = price;
			this.color = color;
			this.wired_or_wireless = wired_or_wireless;
			this.soluong = soluong;
			this.description = description;
			this.image_url = image_url;
			this.category_id = category_id;
		}

		public Product() {
			
		}

		public String getProduct_id() {
			return product_id;
		}

		public void setProduct_id(String product_id) {
			this.product_id = product_id;
		}

		public String getProduct_name() {
			return product_name;
		}

		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getWired_or_wireless() {
			return wired_or_wireless;
		}

		public void setWired_or_wireless(String wired_or_wireless) {
			this.wired_or_wireless = wired_or_wireless;
		}

		public int getSoluong() {
			return soluong;
		}

		public void setSoluong(int soluong) {
			this.soluong = soluong;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getImage_url() {
			return  "/ImageView/"+image_url; 
		}

		public void setImage_url(String image_url) {
		    this.image_url = image_url; 
		}

		public String getCategory_id() {
			return category_id;
		}

		public void setCategory_id(String category_id) {
			this.category_id = category_id;
		}
		
		@Override
	    public String toString() {
	        return "Product{" +
	                "product_id='" + product_id + '\'' +
	                ", product_name='" + product_name + '\'' +
	                ", price=" + price +
	                ", color='" + color + '\'' +
	                ", wired_or_wireless='" + wired_or_wireless + '\'' +
	                ", soluong=" + soluong +
	                ", description='" + description + '\'' +
	                ", image_url='" + image_url + '\'' +
	                ", category_id='" + category_id + '\'' +
	                '}';
	    }

	    

	    
	}

