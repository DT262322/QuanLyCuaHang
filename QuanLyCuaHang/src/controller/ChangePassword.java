package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Bus.BusQuanLyNhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangePassword implements Initializable {
	@FXML
	Hyperlink hyberLogin;
	@FXML
	private Button btnChangePass;

	@FXML
	private CheckBox checkShowPassword;

	@FXML
	private TextField txtConfirmNewPass;

	@FXML
	private PasswordField txtHideNewPassword;

	@FXML
	private PasswordField txtHideComfirmPassword;

	@FXML
	private TextField txtUserID;

	@FXML
	private TextField txtnewPass;

	// Kiểm tra id đã được nhập chưa
	public boolean checkValue() {
		// Kiểm tra xem trường UserID có rỗng không
		if (txtUserID.getText().isBlank()) {
			showAlert(AlertType.WARNING, "Error", "UserID is empty");
			return false;
		}

		// Kiểm tra xem trường New Password có rỗng không
		if (txtHideNewPassword.getText().isBlank()) {
			showAlert(AlertType.WARNING, "Error", "Please Enter New Password");
			return false;
		}

		// Kiểm tra xem trường Confirm Password có rỗng không
		if (txtHideComfirmPassword.getText().isBlank()) {
			showAlert(AlertType.WARNING, "Error", "Please Confirm Your Password");
			return false;
		}

		// Kiểm tra xem trường New Password có rỗng không
		if (txtnewPass.getText().isBlank()) {
			showAlert(AlertType.WARNING, "Error", "Please Enter New Password");
			return false;
		}

		// Kiểm tra xem trường Confirm Password có rỗng không
		if (txtConfirmNewPass.getText().isBlank()) {
			showAlert(AlertType.WARNING, "Error", "Please Confirm Your Password");
			return false;
		}

		// Kiểm tra xem mật khẩu mới và xác nhận mật khẩu mới có khớp nhau không
		if (!txtnewPass.getText().equals(txtConfirmNewPass.getText())) {
			showAlert(AlertType.WARNING, "Error", "New Password and Confirm Password do not match");
			return false;
		}

		return true;
	}

	public boolean checkPassword() {
		String newPassword = txtnewPass.getText();
		String confirmNewPassword = txtConfirmNewPass.getText();
		String hidePassword = txtHideNewPassword.getText();
		String hideComfirmPassword = txtHideComfirmPassword.getText();
		if (!newPassword.equals(confirmNewPassword) || !hidePassword.equals(hideComfirmPassword)) {
			showAlert(AlertType.ERROR, "Error", "Passwords do not match.");
			txtnewPass.clear();
			txtConfirmNewPass.clear();
			txtHideNewPassword.clear();
			txtHideComfirmPassword.clear();
			txtnewPass.requestFocus();
			return false;
		}

		return true;
	}

	@FXML
	void hyberLogin_Click(ActionEvent event) throws IOException {
		changeSenceToLogin();
	}

	@FXML
	void checkShowPassword_check(ActionEvent event) {
		if (checkShowPassword.isSelected()) {
			txtnewPass.setText(txtHideNewPassword.getText());
			txtConfirmNewPass.setText(txtHideComfirmPassword.getText());
			txtnewPass.setVisible(true);
			txtConfirmNewPass.setVisible(true);
			txtHideNewPassword.setVisible(false);
			txtHideComfirmPassword.setVisible(false);
		} else {
			txtHideNewPassword.setText(txtnewPass.getText());
			txtHideComfirmPassword.setText(txtConfirmNewPass.getText());
			txtHideNewPassword.setVisible(true);
			txtHideComfirmPassword.setVisible(true);
			txtnewPass.setVisible(false);
			txtConfirmNewPass.setVisible(false);
		}
	}

	@FXML
	void btnChangePass_clicked(ActionEvent event) throws IOException {
		String userID = txtUserID.getText();
		String passString = txtnewPass.getText();
		BusQuanLyNhanVien bus = new BusQuanLyNhanVien();
		if (checkValue()) {
			if (checkPassword()) {
				boolean result = bus.updatePassword(passString, userID);
				if (result) {
					showAlert(AlertType.INFORMATION, "Success", "Password updated successfully.");

				} else {

					showAlert(AlertType.ERROR, "Error", "Failed to update password. Please Enter Correct Your UserID.");
				}
			}
		}

	}

	public void changeSenceToLogin() throws IOException {
		// Tải FXML mới từ ForgotPassword.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		Parent root = loader.load();

		// Lấy stage hiện tại và thiết lập scene mới với FXML đã tải
		Stage stage = (Stage) btnChangePass.getScene().getWindow();
		Scene scene = new Scene(root);

		// Thêm CSS cho scene mới
		String cssPath = getClass().getResource("/application/login.css").toExternalForm();
		scene.getStylesheets().add(cssPath);

		// Thiết lập scene mới cho stage và hiển thị stage
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();

	}

	public void getUserID(String userid) {
		txtUserID.setText(userid);
		txtUserID.setEditable(false);
	}

	private void showAlert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
