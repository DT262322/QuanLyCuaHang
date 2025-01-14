   package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Product;
import Bus.busProduct;

public class ProductListController implements Initializable {

    @FXML
    private Button btnAddsp;

    @FXML
    private Button btnDeletesp;

    @FXML
    private Button btnEditsp;

    @FXML
    private Button btnUpdatesp;

    @FXML
    private TableView<Product> tbSp;
    @FXML
    private TableColumn<Product, Integer> colBrandID;

    @FXML
    private TableColumn<Product, String> colCategoryID;

    @FXML
    private TableColumn<Product, String> colColor;

    @FXML
    private TableColumn<Product, String> colDescription;

    @FXML
    private TableColumn<Product, String> colIMG;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, String> colProductID;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    private TableColumn<Product, String> colWiredorWireless;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private TextField txtCategogyID;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtDes;

    @FXML
    private TextField txtPDid;

    @FXML
    private TextField txtPDname;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSoLuong;
    @FXML
    private TextField txtWiredorWireless;
    @FXML
    private TextField txtIMG;
    @FXML
    private Button btnChooseImage;
    @FXML
    private ImageView imageView;
	busProduct bus = new busProduct();
	Product p = null;
	 ObservableList<Product> productObservableList = FXCollections.observableArrayList();
	

	 //sự kiện chọn ảnh từ máy
	@FXML
	void btnChooseImage_click(ActionEvent event) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Chọn hình ảnh");
	    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	        String imagePath = selectedFile.toURI().toString();
	        txtIMG.setText(imagePath);
	        Image image = new Image(imagePath, true);
	        imageView.setImage(image);
	        imageView.setFitWidth(215); 
	        imageView.setFitHeight(187); 
	    }
	}
	
	//sự kiện ADD
	@FXML
	void btnAddsp_click(ActionEvent event) {
	    String product_id = txtPDid.getText();
	    
	    // Kiểm tra xem ID sản phẩm đã tồn tại chưa
	    boolean isDuplicate = productObservableList.stream().anyMatch(product -> product.getProduct_id().equals(product_id));
	    
	    if (isDuplicate) {
	        // Xử lý trường hợp ID sản phẩm đã tồn tại
	    	JOptionPane.showMessageDialog(null, "ID bị trùng,xin hãy nhập lại !");
    		return;
	    } else {
	        // Tiếp tục với việc thêm sản phẩm mới
	        String product_name = txtPDname.getText();
	        String category_id = txtCategogyID.getText();
	        int quantity = Integer.parseInt(txtSoLuong.getText());
	        String color = txtColor.getText();
	        double price = Double.parseDouble(txtPrice.getText());
	        String description = txtDes.getText();
	        String wireorwireless = txtWiredorWireless.getText();
	        String img = txtIMG.getText();
	        Product pd = new Product(product_id, product_name, price, color, wireorwireless, quantity, description, img, category_id);
	        bus.add(pd);
	        JOptionPane.showMessageDialog(null,"Thêm thành công");
	        productObservableList.add(pd);
	        clearInputFields();
	    }
	}

	//làm trống form
	private void clearInputFields() {
	    txtPDid.clear();
	    txtPDname.clear();
	    txtCategogyID.clear();
	    txtSoLuong.clear();
	    txtColor.clear();
	    txtPrice.clear();
	    txtDes.clear();
	    txtWiredorWireless.clear();
	    txtIMG.clear();
	}

	//Sự kiện delete
    @FXML
    void btnDeletesp_click(ActionEvent event) {
    	Product selectedProduct = tbSp.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            bus.delete(selectedProduct);
            productObservableList.remove(selectedProduct);
            JOptionPane.showMessageDialog(null,"xóa thành công");
            clearInputFields();
        }
    }
    
    //sự kiện update
    @FXML
    void btnUpdatesp_click(ActionEvent event) {
        String product_id = txtPDid.getText();
        String product_name = txtPDname.getText();
        String category_id = txtCategogyID.getText();
        int quantity = Integer.parseInt(txtSoLuong.getText());
        String color = txtColor.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String description = txtDes.getText();
        String wireorwireless = txtWiredorWireless.getText();
        String img = txtIMG.getText();
        Product updatedProduct = new Product(product_id, product_name, price, color, wireorwireless, quantity, description, img, category_id);
        boolean updated = bus.update(updatedProduct);
        if (updated) {
            int selectedIndex = tbSp.getSelectionModel().getSelectedIndex();
            productObservableList.set(selectedIndex, updatedProduct);
            JOptionPane.showMessageDialog(null,"cập nhật thành công");
            clearInputFields(); 
        } else {
            JOptionPane.showMessageDialog(null,"cập nhật không thành công");
        }
    }
    
    //sự kiện tìm kiếm
    @FXML
    void handleTimKiem(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
            String searchKeyword = txtTimKiem.getText();
            searchPrd(searchKeyword);
        }
    }
    private void searchPrd(String pd) {
    	ObservableList<Product> flteredList=FXCollections.observableArrayList();
    	for(Product product:productObservableList) {
    		if(product.getProduct_id().contains(pd) || product.getProduct_name().contains(pd) ||product.getCategory_id().contains(pd))
    		{
    			flteredList.add(product);
    		}
    	}
    	tbSp.setItems(flteredList);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<Product> productList = bus.findAll();
        System.out.println("Product List Size: " + productList.size());
        colProductID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("product_name"));	
        colCategoryID.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colWiredorWireless.setCellValueFactory(new PropertyValueFactory<>("wired_or_wireless"));
        colIMG.setCellValueFactory(new PropertyValueFactory<>("image_url"));
        colIMG.setCellFactory(tc -> new TableCell<Product, String>() {
            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty || imageUrl == null) {
                    setText(null);
                } else {
                    setText(imageUrl);
                }
            }
        });
        productObservableList.addAll(productList);
        tbSp.setItems(productObservableList);
        System.out.println("Observable List Size: " + productObservableList.size());
        tbSp.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbSp.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                Product selectedProduct = row.getItem();
                if (selectedProduct != null && selectedProduct.getImage_url() != null) {
                    String imagePath = selectedProduct.getImage_url();
                    Image imageFX = new Image(imagePath, true);
                    imageView.setImage(imageFX);
                    imageView.setFitWidth(215);
                    imageView.setFitHeight(187);
                }
            });
            row.setOnMouseExited(event -> {
                String defaultImagePath = "/ImageView/DNN.jpg";
                Image defaultImage = new Image(defaultImagePath, true);
                imageView.setImage(defaultImage);
            });
            return row;
        });
        tbSp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
        	
			@Override
			public void changed(ObservableValue<? extends Product> arg0, Product arg1, Product arg2) {
				// TODO Auto-generated method stub
				int index=tbSp.getSelectionModel().getSelectedIndex();
				if(index ==-1) {
					return;
				}
					p=tbSp.getItems().get(index);
					txtPDid.setText(p.getProduct_id());
					txtPDname.setText(p.getProduct_name());
					txtCategogyID.setText(p.getCategory_id());
					txtSoLuong.setText(p.getSoluong()+"");
					txtWiredorWireless.setText(p.getWired_or_wireless());
					txtColor.setText(p.getColor());
					txtPrice.setText(p.getPrice()+"");
					txtDes.setText(p.getDescription());
					txtIMG.setText(p.getImage_url());
					String imagePath = p.getImage_url();
			        Image image = new Image(imagePath, true);
			        imageView.setImage(image);
			        imageView.setFitWidth(170);
			        imageView.setFitHeight(149);
				}
		});

    }        
    }