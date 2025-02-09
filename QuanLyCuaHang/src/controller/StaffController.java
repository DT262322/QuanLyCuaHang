package controller;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.RandomStringUtils;

import Bus.BusQuanLyNhanVien;
import model.CChucVu;
import model.CQuanLyNhanVien;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javafx.beans.property.SimpleStringProperty;

public class StaffController implements Initializable {
	@FXML
	private Button btnBrowserImage;
	@FXML
	private ToggleGroup gioitinh;
	@FXML
	private RadioButton radioGioiTinhNam;
	@FXML
	private Button btnClear;
	@FXML
	private RadioButton radioGioiTinhNu;
	@FXML
	private Button btnChinhSuaTT;

	@FXML
	private Button btnThemNV;

	@FXML
	private Button btnXoaNV;

	@FXML
	private ComboBox<CChucVu> cmbChucVu;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColCanCuoc;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColChucVu;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColGioiTinh;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColHoTein;

	@FXML
	private TableColumn<CQuanLyNhanVien, Date> tbColNgaySinh;

	@FXML
	private TableColumn<CQuanLyNhanVien, Date> tbColNgayVaoLam;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColSoDienThoai;

	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColUserID;
	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColImaUrl;
	@FXML
	private TableView<CQuanLyNhanVien> tbvListNhanVien;
	@FXML
	private TableColumn<CQuanLyNhanVien, String> tbColEmail;
	@FXML
	private TextField txtCanCuoc;
	@FXML
	private TextField txtUrlImage;
	@FXML
	private TextField txtHoTen;
	@FXML
	private ImageView imgUser;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker pickerNgaySinh;

	@FXML
	private TextField txtSoDienThoai;
	private Window stage;

	@FXML
	void btnClear_clicked(ActionEvent event) {
		clearFields();
	}

	@FXML
	void btnChinhSuaTT_Clicked(ActionEvent event) throws ParseException {
		// Lấy thông tin từ các trường nhập liệu
		String hoten = txtHoTen.getText();
		String gioitinh = getSelectedGender();
		String sdt = txtSoDienThoai.getText();
		String cccd = txtCanCuoc.getText();
		String urlImage = txtUrlImage.getText();
		LocalDate ngaySinh = pickerNgaySinh.getValue();

		// Kiểm tra xem đã chọn nhân viên trong TableView chưa
		CQuanLyNhanVien selectedNhanVien = tbvListNhanVien.getSelectionModel().getSelectedItem();
		if (selectedNhanVien == null) {
			showAlert(AlertType.WARNING, "Warning", "Please select the employee to edit.");
			return;
		}

		// Kiểm tra xem đã chọn chức vụ từ ComboBox hay chưa
		CChucVu chucVuID = cmbChucVu.getValue();
		if (chucVuID == null) {
			showAlert(AlertType.WARNING, "Warning", "Please select the position for the employee.");
			return;
		}

		// Cập nhật thông tin mới cho nhân viên đã chọn
		selectedNhanVien.setHoten(hoten);
		selectedNhanVien.setGioitinh(gioitinh);
		selectedNhanVien.setNgay_sinh(java.sql.Date.valueOf(ngaySinh)); // Chuyển đổi LocalDate thành java.sql.Date
		selectedNhanVien.setSo_dien_thoai(sdt);
		selectedNhanVien.setCccd(cccd);
		selectedNhanVien.setUrlImage(urlImage);
		selectedNhanVien.setChucvu(chucVuID);

		// Hiển thị hộp thoại comfirm chỉnh sửa
		Alert comfirmAlert = new Alert(AlertType.CONFIRMATION);
		comfirmAlert.setTitle("Confirm editing");
		comfirmAlert.setHeaderText(null);
		comfirmAlert.setContentText("Are you sure you want to adjust the information of employee "
				+ selectedNhanVien.getHoten() + " with userID: " + selectedNhanVien.getUserID() + "?");
		Optional<ButtonType> result = comfirmAlert.showAndWait();

		// Xác nhận nếu người dùng chấp nhận chỉnh sửa
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Gọi phương thức để cập nhật nhân viên
			boolean editNV = bus.editNhanVien(selectedNhanVien);
			if (editNV) {
				// Nếu cập nhật thành công, làm mới TableView
				tbvListNhanVien.refresh();
				showAlert(AlertType.INFORMATION, "Update successful", "Employee " + hoten + " has been updated.");
			} else {
				// Hiển thị thông báo lỗi nếu cập nhật không thành công
				showAlert(AlertType.ERROR, "Error", "An error occurred while updating.");
			}
		}
	}

	@FXML
	void btnBrowserImage_Click(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Select employee image");
	    fileChooser.getExtensionFilters()
	            .addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
	    File selectedFile = fileChooser.showOpenDialog(stage);
	    if (selectedFile != null) {
	    	String directLink = selectedFile.getAbsolutePath();
	    	System.out.println(directLink);
	        String fileName = selectedFile.getName(); // Lấy tên file từ đường dẫn
	        txtUrlImage.setText(fileName);
	        String image= "file:///" + directLink ;
	        Image imageStaff = new Image(image);
	        imgUser.setImage(imageStaff);
	    }
	}

	@FXML
	void btnCanCuoc(MouseEvent event) {
		String cccd = CCCDGenerator.generateRandomCCCD();
		txtCanCuoc.setText(cccd);
	}

	@FXML
	void btnThemNV_Clicked(ActionEvent event) throws ParseException {
		if (!checkValueTxt()) {
			return;
		} else {
			String password = PasswordGenerator.generateRandomPassword();
			String hoten = txtHoTen.getText();
			String gioitinh = getSelectedGender();
			String sdns = pickerNgaySinh.getValue() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date ngaysinhUtil = sdf.parse(sdns); // Parse to java.util.Date
			java.sql.Date ngaysinh = new java.sql.Date(ngaysinhUtil.getTime()); // Convert to java.sql.Date
			String soDT = txtSoDienThoai.getText();
			String cccd = txtCanCuoc.getText();
			CChucVu cv = cmbChucVu.getValue();
			String email = txtEmail.getText();
			String status = "inactive";
			// Tạo ngày vào làm
			java.sql.Date ngayvaolam = new java.sql.Date(System.currentTimeMillis()); // Lấy ngày hiện tại

			CQuanLyNhanVien nv = new CQuanLyNhanVien();
			// Add vào mảng
			nv.setUserID(CreateUserID(hoten, cccd, cmbChucVu.getValue()));
			nv.setPassword(password);
			nv.setHoten(hoten);
			nv.setNgayvaolam(ngayvaolam);
			nv.setGioitinh(gioitinh);
			nv.setNgay_sinh(ngaysinh);
			nv.setSo_dien_thoai(soDT);
			nv.setCccd(cccd);
			nv.setEmail(email);
			nv.setChucvu(cv);
			nv.setUrlImage(txtUrlImage.getText());
			nv.setStatus(status);
			// Tạo UserID mới
			String newUserID = CreateUserID(hoten, cccd, cmbChucVu.getValue());
			// Kiểm tra trùng lặp UserID
			if (checkDuplicateUserID(newUserID)) {
				showAlert(AlertType.ERROR, "Error", "UserID already exists in the system.");
				return; // Kết thúc phương thức nếu UserID đã tồn tại
			}

			boolean kq = bus.addNhanVien(nv);
			try {

				if (kq) {
					listNhanVien.add(nv);
					tbvListNhanVien.getItems().add(nv);
					tbvListNhanVien.getItems().clear();
					tbvListNhanVien.getItems().addAll(listNhanVien);
					clearFields();
					showAlert(AlertType.INFORMATION, "Add successful", "Employee has been added to the system.");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				showAlert(AlertType.ERROR, "Error", "An error occurred while adding the employee.");
			}
		}
	}

	@FXML
	void btnXoaNV_Click(ActionEvent event) {
		// Khai báo
		String hoten = txtHoTen.getText();
		String canCuoc = txtCanCuoc.getText();
		CChucVu chucVu = cmbChucVu.getValue();

		// Kiểm tra đã chọn nhân viên trong TableView chưa
		CQuanLyNhanVien selectedNhanVien = tbvListNhanVien.getSelectionModel().getSelectedItem();
		if (selectedNhanVien == null) {
			showAlert(AlertType.WARNING, "Warning", "Please select the employee to delete.");
			return;
		}

		// Hiển thị hộp thoại comfirm
		Alert comfirmAlert = new Alert(AlertType.CONFIRMATION);
		comfirmAlert.setTitle("Confirm deletion");
		comfirmAlert.setHeaderText(null);
		comfirmAlert.setContentText("Are you sure you want to delete employee " + selectedNhanVien.getHoten() + "?");
		Optional<ButtonType> result = comfirmAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			boolean xoaNv = bus.deleteNhanVien(selectedNhanVien);
			if (xoaNv) {
				listNhanVien.remove(selectedNhanVien);
				tbvListNhanVien.getItems().remove(selectedNhanVien);
				showAlert(AlertType.INFORMATION, "Delete successful",
						"Employee " + hoten + " has been removed from the database.");
			} else {
				showAlert(AlertType.ERROR, "Error", "An error occurred while deleting.");
			}
		}

	}
	// Các hàm để lấy giá trị

	// Chon giới tính
	private String getSelectedGender() {
		if (radioGioiTinhNam.isSelected()) {
			return "Male";
		} else if (radioGioiTinhNu.isSelected()) {
			return "Female";
		}
		return ""; // Trả về chuỗi rỗng nếu không có giới tính nào được chọn
	}

	public boolean checkValueTxt() {
		if (txtHoTen.getText().isBlank() || txtHoTen.getText().isEmpty()) {
			showAlert(AlertType.WARNING, "Error", "Full name is empty");
			return false;
		}

		if (pickerNgaySinh.getValue() == null) {
			showAlert(AlertType.WARNING, "Error", "Date of birth not selected");
			return false;
		}
		if (cmbChucVu.getValue() == null) {
			showAlert(AlertType.WARNING, "Error", "Please select a position");
			return false;
		}
		if (txtCanCuoc.getText().isBlank() || txtCanCuoc.getText().isEmpty()) {
			showAlert(AlertType.WARNING, "Error", "Identity card number is empty");
			return false;
		}
		if (!txtCanCuoc.getText().matches("\\d{12}")) {
			showAlert(AlertType.WARNING, "Error", "Invalid identity card number, please re-enter");
			return false;
		}
		// Check phone number
		String soDienThoai = txtSoDienThoai.getText();

		// Check that the phone number is not empty
		if (soDienThoai.isBlank() || soDienThoai.isEmpty()) {
			showAlert(AlertType.WARNING, "Error", "Phone number is empty");
			return false;
		}

		// Check that the phone number contains only numbers and is exactly 10
		// characters long
		if (!soDienThoai.matches("[0-9]+") || soDienThoai.length() != 10) {
			showAlert(AlertType.WARNING, "Error", "Phone number must contain only numbers and be 10 characters long");
			return false;
		}

		// Kiếm tra email đã được nhập và đúng định dạng chưa
		String email = txtEmail.getText();
		if (email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

			showAlert(AlertType.WARNING, "Error", "Invalid email address, please re-enter");
			return false;
		}

		return true;
	}

	// Kiếm tra trùng dữ liệu userID

	public boolean checkDuplicateUserID(String userID) {
		for (CQuanLyNhanVien nv : listNhanVien) {
			if (nv.getUserID().equals(userID)) {
				return true; // Nếu tìm thấy UserID trùng lặp, trả về true
			}
		}
		return false; // Nếu không tìm thấy UserID trùng lặp, trả về false
	}

	// Tạo căn cước công dân ngau nhiên
	public class CCCDGenerator {
		public static String generateRandomCCCD() {
			Random random = new Random();
			StringBuilder cccdBuilder = new StringBuilder();
			for (int i = 0; i < 12; i++) {
				int digit = random.nextInt(10); // Sinh ngẫu nhiên một chữ số từ 0 đến 9
				cccdBuilder.append(digit);
			}
			return cccdBuilder.toString();
		}
	}

	// Tạo mật khẩu ngẫu nhiên
	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public class PasswordGenerator {
		public static String generateRandomPassword() {
			String setPassord = "12345";
			String passMD5 = getMD5(setPassord);
			return passMD5;
		}
	}

	// Phương thức lấy tên userID tự động
	public String CreateUserID(String hoTen, String canCuoc, CChucVu chucVu) {
		// Phân tích họ tên thành các từ riêng biệt
		String[] words = hoTen.trim().split("\\s+");

		// Tạo chuỗi viết tắt từ chữ cái đầu của mỗi từ
		StringBuilder initials = new StringBuilder();
		for (String word : words) {
			initials.append(word.substring(0, 1));
		}

		// Lấy 4 số cuối của số căn cước công dân
		String lastFourDigits = canCuoc.substring(canCuoc.length() - 4);

		// Lấy chữ cái viết tắt của chức vụ (chỉ lấy 2 ký tự đầu tiên)
		String chucVuAbbreviation = chucVu.getChucVuID().substring(0, 2);
		// Ghép thành userID
		String userID = initials.toString().toUpperCase() + lastFourDigits + chucVuAbbreviation;

		// Kiểm tra và cắt bớt nếu userID quá dài
		if (userID.length() > 10) {
			userID = userID.substring(0, 10);
			System.out.println(userID);
		}

		return userID;
	}

	// Hàm hiển thị hộp thoại cảnh báo
	private void showAlert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void clearFields() {
		// Xóa nội dung của các trường nhập liệu
		txtHoTen.clear();
		pickerNgaySinh.setValue(null);
		txtSoDienThoai.clear();
		txtCanCuoc.clear();
		radioGioiTinhNam.setSelected(true);
		txtEmail.clear();
		txtUrlImage.clear();
	}

	// Khai báo (Lấy danh sách các chức vụ từ BusQuanLyNhanVien)
	BusQuanLyNhanVien bus = new BusQuanLyNhanVien();
	ArrayList<CChucVu> listChucVu = new ArrayList<>();
	ArrayList<CQuanLyNhanVien> listNhanVien = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File currentLink = new File("");
		String directionLink = currentLink.getAbsolutePath();
		String defaultImagePath = "file:///" + directionLink + "\\src\\ImageView\\imgStaff.jpg";
		System.out.println(defaultImagePath);
		Image image = new Image(defaultImagePath);
		imgUser.setImage(image);

		
		listChucVu = bus.showChucVu();
		cmbChucVu.getItems().addAll(listChucVu);
		listNhanVien = bus.findAllNhanVien();

		// Thêm danh sách nhân viên vào tableview
		tbColUserID.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("userID"));
		tbColHoTein.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("hoten"));
		tbColNgayVaoLam.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, Date>("ngayvaolam"));
		tbColGioiTinh.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("gioitinh"));
		tbColNgaySinh.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, Date>("ngay_sinh"));
		tbColSoDienThoai.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("so_dien_thoai"));
		tbColCanCuoc.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("cccd"));
		tbColEmail.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("email"));
		tbColChucVu.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("chucvu"));
		tbColImaUrl.setCellValueFactory(new PropertyValueFactory<CQuanLyNhanVien, String>("urlImage"));

		// ẩn cột url đi
		tbColImaUrl.setVisible(false);
		tbvListNhanVien.getItems().addAll(listNhanVien);

		// Add listener cho TableView tbvListNhanVien
		tbvListNhanVien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CQuanLyNhanVien>() {
			@Override
			public void changed(ObservableValue<? extends CQuanLyNhanVien> observable, CQuanLyNhanVien oldValue,
					CQuanLyNhanVien newValue) {

				CQuanLyNhanVien selectedNhanVien = newValue;
				if (selectedNhanVien != null) {
					txtHoTen.setText(selectedNhanVien.getHoten());
					pickerNgaySinh.setValue(selectedNhanVien.getNgay_sinh().toLocalDate());
					txtSoDienThoai.setText(selectedNhanVien.getSo_dien_thoai());
					txtCanCuoc.setText(selectedNhanVien.getCccd());
					txtEmail.setText(selectedNhanVien.getEmail());
					if (selectedNhanVien.getGioitinh().equals("Male")) {
						radioGioiTinhNam.setSelected(true);
					} else if (selectedNhanVien.getGioitinh().equals("Female")) {
						radioGioiTinhNu.setSelected(true);
					}
					cmbChucVu.setValue(selectedNhanVien.getChucvu());
//					
					txtUrlImage.setText(selectedNhanVien.getUrlImage());
					
					File currentLink = new File("");
					String directionLink = currentLink.getAbsolutePath();
					String image=  "file:///" + directionLink+"\\src\\ImageView\\"+ txtUrlImage.getText() ;
					Image imageStaff = new Image(image);
					imgUser.setImage(imageStaff);	
				}
			}
		});

	}
}