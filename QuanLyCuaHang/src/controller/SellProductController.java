package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Bus.BusQuanLyNhanVien;
import Bus.busCustomers;
import Bus.busProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CQuanLyNhanVien;
import model.ChiTietPhieuBanHang;
import model.Customers;
import model.PhieuBanHang;
import model.Product;
import model.UtilClass;
import model.connectDB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import Bus.busPhieuBanHang;
import Bus.busChiTietBanHang;

public class SellProductController implements Initializable {
	 	@FXML
	    private Button btnAddCus;

	    @FXML
	    private Button btnHistory;
	    
	    @FXML
	    private Button btnBuy;

	    @FXML
	    private Button btnRemove;
	    @FXML
	    private Button btnShowPD;

	    @FXML
	    private TableColumn<ChiTietPhieuBanHang, String> colColor;

	    @FXML
	    private TableColumn<ChiTietPhieuBanHang, String> colPDname;

	    @FXML
	    private TableColumn<ChiTietPhieuBanHang, Double> colPrice;
	    @FXML
	    private TableColumn<ChiTietPhieuBanHang, Integer> colSoLuong;
	    @FXML
	    private ListView<Customers> listViewCus;

	    @FXML
	    private GridPane menu_GridPD;
	    @FXML
	    private ScrollPane menu_scrollPane;

	    @FXML
	    private Label menu_total;

	    @FXML
	    private TableView<ChiTietPhieuBanHang> cartTableView;
	    @FXML
	    private Label phieuBH_id;
	    @FXML
	    private TextField txtSearchCus;
	    @FXML
	    private ProductController productController;
	    BusQuanLyNhanVien busNV;
	    PhieuBanHang pbh;
	    Customers cus=null;
	    
	    
    List<Customers> customers=new ArrayList<>();
    
    busCustomers busCus=new busCustomers();
    
    busProduct busPD=new busProduct();
    
    private ObservableList<Product> cardListData = FXCollections.observableArrayList();
    
    ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
    
    private ObservableList<ChiTietPhieuBanHang> cardCTPBH = FXCollections.observableArrayList();
    
    public ObservableList<ChiTietPhieuBanHang> getCardCTPBH() {
		return cardCTPBH;
	}


	public void setCardCTPBH(ObservableList<ChiTietPhieuBanHang> cardCTPBH) {
		this.cardCTPBH = cardCTPBH;
	}


	@FXML
	void btnAddCus_click(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customers.fxml"));
	        Parent root = loader.load();

	        Scene scene = new Scene(root);

	        // Add the path to your CSS file
	        String cssPath = getClass().getResource("/application/application.css").toExternalForm();
	        scene.getStylesheets().add(cssPath);

	        Stage stage = new Stage();

	        // Thêm sự kiện khi cửa sổ Customers được đóng lại
	        stage.setOnHidden(e -> {
	            // Sau khi cửa sổ Customers được đóng, cập nhật danh sách khách hàng
	            customers = busCus.findAll();
	            customersObservableList.clear();
	            customersObservableList.addAll(customers);
	            listViewCus.setItems(customersObservableList);
	        });

	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.showAndWait();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
   
    public void openProductController(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Product.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String cssPath = getClass().getResource("/application/application.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            // Lấy controller của Product.fxml
            ProductController productController = loader.getController();

            // Thiết lập tham chiếu cho ProductController
            productController.setSellProductController(this); // Đặt sellProductController ở đây

            // Truyền dữ liệu sản phẩm cho ProductController
            productController.setData(product);

            // Hiển thị giao diện Product
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(busPD.findAll());

        if (cardListData.isEmpty()) {
            System.out.println("No products found!");
            return;
        }

        int row = 0;
        int column = 0;
        menu_GridPD.getChildren().clear();
        menu_GridPD.getRowConstraints().clear();
        menu_GridPD.getColumnConstraints().clear();

        for (int i = 0; i < cardListData.size(); i++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/view/Product.fxml"));
                
                AnchorPane pane = load.load();
                pane.getStylesheets().add(getClass().getResource("/application/cardPD.css").toExternalForm());
                ProductController cardC = load.getController();
                cardC.setData(cardListData.get(i));

                // Lấy controller của Product.fxml
                productController = load.getController();

                // Thiết lập tham chiếu cho ProductController
                productController.setSellProductController(this); 
                productController.setPhieuBanHang(pbh);
                productController.setGioHang(cardCTPBH);
                if (column == 2) {
                    column = 0;
                    row += 1;
                }

                menu_GridPD.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int generateRandomPhieuBanHangID() {
        Random random = new Random();
        int phieubhID = random.nextInt(90000000) + 10000000;
        return phieubhID;
    }
    
 
    @FXML
    void btnBuy_click(ActionEvent event) {
        if (cardCTPBH.isEmpty()) {
        	JOptionPane.showMessageDialog(null,"Không có sản phẩm trong giỏ hàng để mua.");
            return;
        }
        // Lấy thông tin người dùng đã đăng nhập từ SessionUtils
        CQuanLyNhanVien nhanVienTaoDon = UtilClass.getLoggedInUser();
        if (nhanVienTaoDon == null) {
        	JOptionPane.showMessageDialog(null,"Không thể lấy thông tin người dùng đã đăng nhập.");
            return;
        }
        // Lấy ID của khách hàng từ ô tìm kiếm khách hàng
        Customers customerId = getCustomerIdFromSearchBox();
        // Tạo một phiếu bán hàng mới
        PhieuBanHang phieuBanHang = new PhieuBanHang();
        int phieubhID = generateRandomPhieuBanHangID();
        System.out.println(phieubhID);
        phieuBanHang.setPhieubhId(phieubhID);
        phieuBanHang.setNhanvienTaoDon(nhanVienTaoDon);
        phieuBanHang.setCustomerId(customerId);
        phieuBanHang.setNgayTaoDon(new java.sql.Date(System.currentTimeMillis()));

        // Lưu thông tin phiếu bán hàng vào cơ sở dữ liệu
        busPhieuBanHang busPBH = new busPhieuBanHang();
        boolean successPBH = busPBH.luuPhieuBanHang(phieuBanHang);
        if (successPBH) {
            System.out.println("Đã tạo phiếu bán hàng thành công.");
            // Lưu thông tin chi tiết phiếu bán hàng vào cơ sở dữ liệu
            busChiTietBanHang busCTPBH = new busChiTietBanHang();
            busProduct busPD = new busProduct(); // Khởi tạo đối tượng busProduct
            
            for (ChiTietPhieuBanHang chiTiet : cardCTPBH) {
                chiTiet.setPhieubh(phieuBanHang); // Thiết lập liên kết với phiếu bán hàng mới tạo
                boolean successCTPBH = busCTPBH.luuChiTietPhieuBanHang(chiTiet);
                    
                // Cập nhật số lượng sản phẩm trong cơ sở dữ liệu
                if (successCTPBH) {
                    System.out.println("Đã mua sản phẩm " + chiTiet.getProduct_id().getProduct_name());
                    busPD.updateQuantity(chiTiet.getProduct_id(), chiTiet.getSoluong()); // Cập nhật số lượng
                } else {
                    System.out.println("Lỗi: Không thể mua sản phẩm " + chiTiet.getProduct_id().getProduct_name());
                }
            }
            // Hiển thị thông báo "Pay suscess!"
            if (successPBH) {
                JOptionPane.showMessageDialog(null, "Pay suscess!");
            }
            
            showReport(phieubhID);      
            cardCTPBH.clear();
            updateTotalPrice();
        } else {
        	JOptionPane.showMessageDialog(null,"Lỗi: Không thể tạo phiếu bán hàng.");
        }
    }



    private void showReport(int phieubhID) {
        try {
            // Lấy đối tượng ClassLoader để truy cập tệp JRXML trong thư mục resources
            ClassLoader classLoader = getClass().getClassLoader();
            
            // Lấy đường dẫn tương đối của tệp JRXML
            URL reportUrl = getClass().getResource("/report/PhieuBanHang.jrxml");
            if (reportUrl != null) {
                String decodedPath = URLDecoder.decode(reportUrl.getPath(), "UTF-8");
                JasperDesign jDesign = JRXmlLoader.load(decodedPath);
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                
                // Tạo HashMap để lưu trữ các tham số
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("idphieubh", String.valueOf(phieubhID)); // Đặt giá trị cho tham số "idphieubh"
                
                // Lấy kết nối cơ sở dữ liệu
                connectDB conDB = new connectDB();
                Connection con = conDB.getConnection();
                
                // Tạo JasperPrint từ báo cáo và tham số
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters, con);
                
                // Hiển thị báo cáo trong JasperViewer
                JasperViewer viewer = new JasperViewer(jPrint, false); // 'false' để không đóng ứng dụng khi đóng JasperViewer
                viewer.setTitle("HÓA ĐƠN");
                viewer.setVisible(true); // Hiển thị JasperViewer
                
                // Đóng kết nối cơ sở dữ liệu
                con.close();
            } else {
                System.out.println("Không tìm thấy tệp JRXML.");
            }
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }





    
    @FXML
    void btnShowPD_click(ActionEvent event) {
    	try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductList.fxml"));
	        Parent root = loader.load();

	        Scene scene = new Scene(root);

	        // Add the path to your CSS file
	        String cssPath = getClass().getResource("/application/application.css").toExternalForm();
	        scene.getStylesheets().add(cssPath);

	        Stage stage = new Stage();

	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.showAndWait();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
}

    @FXML
    void btnRemove_click(ActionEvent event) {
    	try {
            ChiTietPhieuBanHang selectedProduct = cartTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                cardCTPBH.remove(selectedProduct);
                updateTotalPrice(); // Cập nhật giá trị tổng
                System.out.println("Removed product: " + selectedProduct.getProduct_id().getProduct_name());
                System.out.println("Updated total: " + menu_total.getText());
            } else {
            	JOptionPane.showMessageDialog(null,"Lỗi: Chưa chọn sản phẩm để xóa.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnHistory_click(ActionEvent event) {
    	try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderHistory.fxml"));
	        Parent root = loader.load();

	        Scene scene = new Scene(root);

	        // Add the path to your CSS file
	        String cssPath = getClass().getResource("/application/application.css").toExternalForm();
	        scene.getStylesheets().add(cssPath);

	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.showAndWait();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
    
    
    @FXML
    void keyCheckCus(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
            String searchKeyword = txtSearchCus.getText();
            searchCus(searchKeyword);
        }
    }
    private void searchCus(String cus) {
    	ObservableList<Customers> flteredList=FXCollections.observableArrayList();
    	for(Customers customer:customersObservableList) {
    		String customerIdString = String.valueOf(customer.getCustomerId());
    		if(customer.getPhoneNumber().contains(cus) || customer.getCustomerName().contains(cus) || customerIdString.contains(cus))
    		{
    			flteredList.add(customer);
    		}
    	}
    	listViewCus.setItems(flteredList);
    }
    
    private Customers getCustomerIdFromSearchBox() {
   	 String searchKeyword = txtSearchCus.getText();
   	    for (Customers customer : customersObservableList) {
   	        String customerIdString = String.valueOf(customer.getPhoneNumber());
   	        if (customer.getPhoneNumber().contains(searchKeyword) || customer.getCustomerName().contains(searchKeyword) || customerIdString.contains(searchKeyword)) {
   	        	return customer;
   	        }
   	    }
   	    return null;
   }

    public void updateTotalPrice() {
        double totalPrice = 0.0;
        for (ChiTietPhieuBanHang item : cardCTPBH) {
            totalPrice += item.getPrice();
        }
        menu_total.setText(String.format("%.2f", totalPrice));
        cartTableView.setItems(cardCTPBH);
        cartTableView.refresh();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		busNV = new BusQuanLyNhanVien();
		pbh= new PhieuBanHang();
        colPDname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct_id().getProduct_name()));
        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colColor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct_id().getColor()));
        colSoLuong.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSoluong()).asObject());
        phieuBH_id.setText(generateRandomPhieuBanHangID()+"");
        pbh.setPhieubhId(Integer.parseInt(phieuBH_id.getText())); 
        phieuBH_id.setVisible(false);
        updateTotalPrice();
        cartTableView.setItems(cardCTPBH);
		customers=busCus.findAll();
		customersObservableList.addAll(customers);
		listViewCus.setItems(customersObservableList);
		listViewCus.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		 menuDisplayCard();
		listViewCus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customers>() {
			@Override
			public void changed(ObservableValue<? extends Customers> arg0, Customers arg1, Customers arg2) {
				// TODO Auto-generated method stub
				int index=listViewCus.getSelectionModel().getSelectedIndex();
				if(index ==-1) {
					return;
				}
				cus=listViewCus.getItems().get(index);
					txtSearchCus.setText(cus.getPhoneNumber());
				}
		});
	}

}
