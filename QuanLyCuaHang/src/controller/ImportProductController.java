package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import Bus.busImportProduct;
import Bus.busProduct;
import Bus.busSuppliers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.CChiTietPhieuNhapKho;
import model.CPhieuNhapKho;
import model.CQuanLyNhanVien;
import model.Product;
import model.Suppliers;

public class ImportProductController implements Initializable {
	@FXML
	private Label nhapHangText;
	@FXML
	private Label lableGia;
	@FXML
	private Label lableIDPhieu;
	@FXML
	private Label lableSL;
	@FXML
	private Button btnLuuPhieuNhap;
	@FXML
	private Text textGiaNhap;
	@FXML
	private ComboBox<Suppliers> cmbNhaSX;
	@FXML
	private Hyperlink hyberReset;
	@FXML
	private DatePicker dateNhapHang;

	@FXML
	private TextField txtIDNhanVien;

	@FXML
	private TextField txtIDPhieuNhap;
	@FXML
	private TableColumn<Product, String> colTenSanSp;
	@FXML
	private TableColumn<Product, Integer> colTonKho;
	@FXML
	private TableView<Product> tbvSearchSP;

	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnAddTBV;
	@FXML
	private TextField txtGiaNhap;
	@FXML
	private Hyperlink hyberLichSu;
	@FXML
	private TextField txtSoLuong;

	@FXML
	private TableColumn<CChiTietPhieuNhapKho, Double> colGiaNhap;

	@FXML
	private TableColumn<CChiTietPhieuNhapKho, String> colMaSP;

	@FXML
	private TableColumn<CChiTietPhieuNhapKho, Integer> colSoLuong;

	@FXML
	private TableColumn<CChiTietPhieuNhapKho, String> colTenSP;
	@FXML
	private TableView<CChiTietPhieuNhapKho> tbvNhapSP;
	@FXML
	private Button btnEdit;
	@FXML
	private Button bthDelete;

	// hàm clear giá trị
	public void clearTxt() {
		txtSearch.clear();
		txtSoLuong.clear();
		txtGiaNhap.clear();
		txtSearch.requestFocus();
	}

	@FXML
	    void hyberReset_Click(ActionEvent event) {
		 clearTxt();
	    }

	@FXML
	void bthDelete_Click(ActionEvent event) {
		int selectedIndex = tbvNhapSP.getSelectionModel().getSelectedIndex();
		if (selectedIndex == -1) {
			showAlert(AlertType.WARNING, "Warning", null, "Please chooose product in table");
			return;
		}
		// Xóa sản phẩm khỏi danh sách
		danhsachchitiet.remove(selectedIndex);
		// Cập nhật TableView
		tbvNhapSP.setItems(FXCollections.observableArrayList(danhsachchitiet));
		tbvNhapSP.refresh();
		clearTxt();
	}

	@FXML
	void btnEdit_Click(ActionEvent event) {
		// Kiểm tra xem có sản phẩm nào được chọn chưa
		int selectedIndex = tbvNhapSP.getSelectionModel().getSelectedIndex();
		if (selectedIndex == -1) {
			showAlert(AlertType.WARNING, "Warning", null, "Please chooose product in table");
			return;
		}
		CChiTietPhieuNhapKho selectedProduct = tbvNhapSP.getSelectionModel().getSelectedItem();
		int newSoLuong = Integer.parseInt(txtSoLuong.getText());
		double newGiaNhap = Double.parseDouble(txtGiaNhap.getText());
		selectedProduct.setSoluong(newSoLuong);
		selectedProduct.setGiaNhap(newGiaNhap);
		tbvNhapSP.refresh();

	}

	// show sence lịch sử hóa đơn
	@FXML
	void hyberLichSu_Clickl(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ImportHistory.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		// Add the path to your CSS file
		String cssPath = getClass().getResource("/application/importHistory.css").toExternalForm();
		scene.getStylesheets().add(cssPath);

		Stage stage = new Stage();

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
	}

	@FXML
	void btnAddTBV_Click(ActionEvent event) {
		String tenSp = txtSearch.getText();
		String sanPhamId = "";
		if (!checkValueAdd())
			return;
		// Thực hiện truy vấn SQL để lấy ID sản phẩm từ tên sản phẩm
		sanPhamId = busProduct.findProductIdByProductName(tenSp);

		if (!sanPhamId.isEmpty()) {
			int soLuong = Integer.parseInt(txtSoLuong.getText());
			double giaNhap = Double.parseDouble(txtGiaNhap.getText());
			boolean productAvailable = false;

			// Kiểm tra xem sản phẩm đã tồn tại trong danh sách chi tiết nhập kho hay chưa
			for (CChiTietPhieuNhapKho chitiet : danhsachchitiet) {
				if (chitiet.getProduct_id().equals(sanPhamId)) {
					// Nếu sản phẩm đã tồn tại, cập nhật số lượng và giá nhập mới
					chitiet.setSoluong(chitiet.getSoluong() + soLuong);
					tbvNhapSP.refresh();
					productAvailable = true;
					break;
				}
			}

			if (!productAvailable) {
				// Nếu sản phẩm chưa tồn tại, thêm sản phẩm mới vào danh sách chi tiết nhập kho
				CChiTietPhieuNhapKho chitiet = new CChiTietPhieuNhapKho();
				chitiet.setProduct_id(sanPhamId);
				chitiet.setTenSP(tenSp);
				chitiet.setSoluong(soLuong);
				chitiet.setGiaNhap(giaNhap);
				danhsachchitiet.add(chitiet);
				soSanhGiaNhap();
			}

			// Cập nhật TableView
			tbvNhapSP.setItems(FXCollections.observableArrayList(danhsachchitiet));
			colMaSP.setCellValueFactory(new PropertyValueFactory<CChiTietPhieuNhapKho, String>("product_id"));
			colTenSP.setCellValueFactory(new PropertyValueFactory<CChiTietPhieuNhapKho, String>("tenSP"));
			colSoLuong.setCellValueFactory(new PropertyValueFactory<CChiTietPhieuNhapKho, Integer>("soluong"));
			colGiaNhap.setCellValueFactory(new PropertyValueFactory<CChiTietPhieuNhapKho, Double>("giaNhap"));

			clearTxt();

		} else {
			showAlert(AlertType.ERROR, "Error", null, "Không tìm thấy ID sản phẩm cho tên sản phẩm đã nhập.");
		}

		return;

	}

	@FXML
	void btnLuuPhieuNhap_Click(ActionEvent event) {
		// Lấy các giá trị của phiếu nhập
		String idPhieuNhap = txtIDPhieuNhap.getText();
		String userID = txtIDNhanVien.getText();
		Suppliers suppliers = cmbNhaSX.getValue();
		Date ngaynhap = Date.valueOf(dateNhapHang.getValue());

		// kiêm tra các giá trị nhập vào
		if (!checkValueSavePhieu())
			return;

		// Tạo đối tượng user
		CQuanLyNhanVien user = new CQuanLyNhanVien();
		user.setUserID(userID);

		// Tạo đối tượng phiếu nhập
		CPhieuNhapKho phieunhapkho = new CPhieuNhapKho(idPhieuNhap, ngaynhap, suppliers, user);

		// Lưu phiếu nhập vào cơ sở dữ liệu
		boolean addPhieuNhap = busImport.savePhieuNhap(phieunhapkho);

		if (addPhieuNhap) {
			// Lưu các chi tiết phiếu nhập vào cơ sở dữ liệu
			for (CChiTietPhieuNhapKho chitiet : danhsachchitiet) {
				String idSanPham = chitiet.getProduct_id();
				int soLuong = Integer.parseInt(chitiet.getSoluong() + "");
				double giaNhap = Double.parseDouble(chitiet.getGiaNhap() + "");
				CChiTietPhieuNhapKho obj = new CChiTietPhieuNhapKho(idPhieuNhap, idSanPham, soLuong, giaNhap);

				// Thêm chi tiết phiếu nhập vào cơ sở dữ liệu và kiểm tra kết quả
				boolean addChiTiet = busImport.addChiTietPhieuNhap(obj);

				if (!addChiTiet) {

					showAlert(AlertType.ERROR, "Error", null, "Nhập kho không thành công.");

					return;
				} else {
					boolean updateSoLuong = busImport.updateSoLuongSP(soLuong, idSanPham);

					if (updateSoLuong) {

						System.out.println("Cập nhật thành công số lượng sản phẩm");
					} else {
						System.out.println("Cập nhật thất bại");
					}
				}
			}
			// Cập nhật lại số lượng trong tbvSerach
			for (CChiTietPhieuNhapKho strChiTiet : danhsachchitiet) {
				String id = strChiTiet.getProduct_id();
				int newSoLuong = strChiTiet.getSoluong();
				for (Product newProduct : listProduct) {
					if (newProduct.getProduct_id().equals(id)) {
						newProduct.setSoluong(newProduct.getSoluong() + newSoLuong);
						break;
					}
				}
			}
			tbvSearchSP.refresh();
			// Hiển thị thông báo thành công nếu mọi thứ đều ok

			clearTxt();
			generateAutoID();
			cmbNhaSX.setValue(suppliers);
			tbvNhapSP.getItems().clear();
			danhsachchitiet.clear();

			System.out.println(danhsachchitiet);
			showAlert(AlertType.CONFIRMATION, "Success", null, "Nhập kho thành công.");
		} else {
			// Hiển thị thông báo lỗi nếu lưu phiếu nhập thất bại
			showAlert(AlertType.ERROR, "Error", null, "Lưu phiếu nhập thất bại.");
		}
	}

	@FXML
	void txtSearch_Press(KeyEvent event) {
		String query = txtSearch.getText().trim();
		SearchProduct(query);
	}

	// Kiếm tra các giá trị
	public boolean checkValueAdd() {
		// Kiểm tra trường Số Lượng
		String soLuongText = txtSoLuong.getText().trim(); // Loại bỏ khoảng trắng ở đầu và cuối
		if (!soLuongText.matches("\\d+")) { // Kiểm tra xem có phải là số không
			lableSL.setVisible(true);
			return false;
		} else {
			lableSL.setVisible(false);
		}

		// Kiểm tra trường Giá Nhập
		String giaNhapText = txtGiaNhap.getText().trim(); // Loại bỏ khoảng trắng ở đầu và cuối
		if (!giaNhapText.matches("^\\d+(\\.\\d+)?$")) { // Kiểm tra xem có phải là số thập phân không
			lableGia.setVisible(true);
			return false;
		} else {
			lableGia.setVisible(false);
		}

		return true;

	}

	public boolean checkValueSavePhieu() {
		// Kiêm tra trương id Phiếu
		String idPhieuNhap = txtIDPhieuNhap.getText();
		if (busImport.checkIDPhieuNhap(idPhieuNhap)) {
			lableIDPhieu.setVisible(true);
			return false;
		} else {
			lableIDPhieu.setVisible(false);
		}
		// Kiểm tra nha cung cấp
		if (cmbNhaSX.getValue() == null) {
			showAlert(AlertType.ERROR, "Error", null, "Vui lòng chọn nhà cung cấp ");
			return false;
		}

		if (tbvNhapSP.getItems().isEmpty()) {
			showAlert(AlertType.ERROR, "Error", null, "Chưa có sản phẩm nào trong bảng");
			return false;
		}
		return true;

	}

	// Khai báo
	busSuppliers busSupplier = new busSuppliers();
	busImportProduct busImport = new busImportProduct();
	busProduct busProduct = new busProduct();
	ArrayList<Suppliers> listSupplier = new ArrayList<>();
	ArrayList<Product> listProduct = new ArrayList<>();
	ArrayList<CChiTietPhieuNhapKho> danhsachchitiet = new ArrayList<>();
	ObservableList<Product> productList = FXCollections.observableArrayList();

	// Nhập mã sản phẩm sẽ show ra tên sản phẩm
	private void SearchProduct(String query) {
		ObservableList<Product> filteredList = FXCollections.observableArrayList();
		for (Product product : productList) {
			if (product.getProduct_name().toLowerCase().contains(query.toLowerCase())
					|| product.getProduct_id().toLowerCase().contains(query.toLowerCase())) {
				filteredList.add(product);
			}
		}

		tbvSearchSP.setItems(filteredList);
	}

	// Thoogn báo
	public void showAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	public void setIDNhanVien(String IDNhanVien) {
		txtIDNhanVien.setText(IDNhanVien);
	}

	// Tạo phiếu nhập tự động
	// Tạo mã phiếu nhập tự động
	private void generateAutoID() {
		Random random = new Random();
		int randomNumber = random.nextInt(90000) + 10000;

		txtIDPhieuNhap.setText("PN" + randomNumber);
	}

	// So sánh giá nhập mới giá nhập cũ
	public void soSanhGiaNhap() {
		double giaNhapNew = Double.parseDouble(txtGiaNhap.getText());
		double giaNhapOld = Double.parseDouble(textGiaNhap.getText());
		double str = giaNhapOld * 0.1;

		if (giaNhapNew > giaNhapOld + str) {
			showAlert(AlertType.WARNING, "Warning", null, "Giá nhập mới đang lớn hơn giá nhập cũ");
			// Hiển thị cảnh báo hoặc thực hiện các hành động khác tại đây
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Chỉnh ngày nhập là ngày hiện tại

		LocalDate currentDate = LocalDate.now();
		dateNhapHang.setValue(currentDate);
		generateAutoID();
		// Ẩn lable thông báo
		lableGia.setVisible(false);
		lableSL.setVisible(false);
		lableIDPhieu.setVisible(false);
		// TODO Auto-generated method stub
		listSupplier = busSupplier.findAll();
		cmbNhaSX.getItems().addAll(listSupplier);
		txtIDNhanVien.setEditable(true);

		// Add danh sách sản phẩm và tồn kho vào tableview search
		listProduct = busProduct.findAll();
		productList.addAll(listProduct);
		tbvSearchSP.setItems(productList);
		colTenSanSp.setCellValueFactory(new PropertyValueFactory<Product, String>("product_name"));
		colTonKho.setCellValueFactory(new PropertyValueFactory<Product, Integer>("soluong"));

		// lấy giá trị từ bảng search đưa lên text field
		tbvSearchSP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
			@Override
			public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
				if (newValue != null) {
					// Lấy sản phẩm được chọn từ TableView
					Product selectedProduct = newValue;

					// Hiển thị thông tin sản phẩm lên các trường thông tin tương ứng
					txtSearch.setText(selectedProduct.getProduct_name());
					String getProductID = selectedProduct.getProduct_id();
					double giaNhap = busImport.getGiaNhap(getProductID);
					if (giaNhap != -1) {

						textGiaNhap.setText(giaNhap + "");
						;
					} else {
						textGiaNhap.setText(0 + "");
					}

				}
			}

		});
		// Thêm listener cho table nhapSP
		tbvNhapSP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CChiTietPhieuNhapKho>() {

			@Override
			public void changed(ObservableValue<? extends CChiTietPhieuNhapKho> arg0, CChiTietPhieuNhapKho oldValue,
					CChiTietPhieuNhapKho newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					CChiTietPhieuNhapKho selectedProduct = newValue;
					txtSearch.setText(selectedProduct.getTenSP());
					txtGiaNhap.setText(selectedProduct.getGiaNhap() + "");
					txtSoLuong.setText(selectedProduct.getSoluong() + "");
				}
			}
		});
	}

}
