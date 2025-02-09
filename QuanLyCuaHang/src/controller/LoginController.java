package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Bus.BusQuanLyNhanVien;
import Bus.FxmlLoader;
import model.connectDB;
import application.Main;
import model.CChucVu;
import model.CQuanLyNhanVien;
import model.UtilClass;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private Hyperlink hyberForgotPass;
	@FXML
	private Button btnDangNhap;

	@FXML
	private ComboBox<CChucVu> cmbChucVu;

	@FXML
	private PasswordField txtMatKhau;

	@FXML
	private TextField txtTenDangNhap;
	@FXML
	private CheckBox checkShowMatKhau;
	@FXML
	private TextField txtShowMatKhau;

	@FXML
	void hyberForgotPass_click(ActionEvent event) throws IOException {
		 // Tải FXML mới từ ForgotPassword.fxml
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ForgotPassword.fxml"));
	    Parent root = loader.load();

	    // Lấy stage hiện tại và thiết lập scene mới với FXML đã tải
	    Stage stage = (Stage) hyberForgotPass.getScene().getWindow(); 
	    Scene scene = new Scene(root);

	    // Thêm CSS cho scene mới
	    String cssPath = getClass().getResource("/application/login.css").toExternalForm();
	    scene.getStylesheets().add(cssPath);

	    // Thiết lập scene mới cho stage và hiển thị stage
	    stage.setScene(scene);
	    stage.setTitle("Forgot Password");
	    stage.show();
	}

	@FXML
	void checkShowMatKhau_check(ActionEvent event) {

		if (checkShowMatKhau.isSelected()) {
			txtShowMatKhau.setText(txtMatKhau.getText());
			txtShowMatKhau.setVisible(true);
			txtMatKhau.setVisible(false);

		} else {
			txtMatKhau.setText(txtShowMatKhau.getText());
			txtMatKhau.setVisible(true);
			txtShowMatKhau.setVisible(false);

		}
		

	}

	

	// Kiểm tra dữ liệu
	public boolean CheckValueLogin() {
		
		if (txtTenDangNhap.getText().isEmpty() || txtTenDangNhap.getText().isBlank() || txtMatKhau.getText().isEmpty()
				|| txtMatKhau.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Please enter information");
			txtMatKhau.clear();
			txtTenDangNhap.requestFocus();
			return false;
		}
		return true;
	}

	// Chuyển Scene
	private void changeScene() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
			Parent root = loader.load();

			AdminController controller = loader.getController();
			// Truyền tên username vào ô xin chào
			controller.getUserName(txtTenDangNhap.getText());
			controller.getChucVu(cmbChucVu.getValue());
			Stage stage = (Stage) btnDangNhap.getScene().getWindow(); // Lấy stage hiện tại
			Scene scene = new Scene(root);
			String cssPath = getClass().getResource("/application/application.css").toExternalForm();
			scene.getStylesheets().add(cssPath);
			stage.setScene(scene);
			stage.setTitle("Admin Interface");
			stage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	void btnDangNhap_Click(ActionEvent event) {
		String username = txtTenDangNhap.getText();
		String password = txtMatKhau.getText();
		CChucVu selectedChucVu = cmbChucVu.getValue();

		if (!CheckValueLogin()) {
			return; // If the information is invalid, don't proceed further
		}

		// Check if the user has selected a position
		if (selectedChucVu == null) {
			JOptionPane.showMessageDialog(null, "Please select a position");
			return;
		}

		// Perform login check
		
		BusQuanLyNhanVien bus = new BusQuanLyNhanVien();
		boolean result = bus.kiemTraDangNhap(username, password, selectedChucVu.getChucVuID());
		
		// Display the result
		if (!result) {
			JOptionPane.showMessageDialog(null, "Incorrect username or password");
			txtMatKhau.clear();
			txtShowMatKhau.setText(password);
			txtTenDangNhap.requestFocus();
			
			return;
		} else {
			JOptionPane.showMessageDialog(null, "Login successful");
			CQuanLyNhanVien loggedInUser = bus.getUserByUserName(username); // Lấy thông tin người dùng từ database
	        UtilClass.setLoggedInUser(loggedInUser); // Lưu thông tin người dùng đã đăng nhập vào SessionUtils
	        UtilClass.setSelectedPosition(selectedChucVu); // Lưu thông tin vị trí đã chọn vào SessionUtils
			changeScene();
			if (selectedChucVu.getChucVuID().equals("EMP")) {
				System.out.println("Welcome Employee");
			} else {
				System.out.println("Welcome Manager");
			}
			
		}
	}

	@FXML
	void cmbChucVu_Click(ActionEvent event) {
		System.out.println(cmbChucVu.getValue());
	}

	// Khai báo mảng
	ArrayList<CChucVu> cv = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO Auto-generated method stub
		BusQuanLyNhanVien busNhanVien = new BusQuanLyNhanVien();
		cv = busNhanVien.showChucVu();
		cmbChucVu.getItems().addAll(cv);
		cmbChucVu.setValue(cv.get(0));

	}

}