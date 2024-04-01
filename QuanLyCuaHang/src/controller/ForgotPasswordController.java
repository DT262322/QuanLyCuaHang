package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable {

	@FXML
	private Button btnConfirm;

	@FXML
	private Hyperlink hyberBackSence;

	@FXML
	private Hyperlink hyberSendCode;

	@FXML
	private TextField txtCode;

	@FXML
	private TextField txtOTP;
	@FXML
	private TextField txtEmail;

	// kiểm tra dữ liệu
	public boolean checkValueTxT() {
		if (txtCode.getText().isBlank() || txtCode.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	// Kiểm tra OTP đã chính xác chưa
	public boolean checkOTP() {
		if (txtCode.getText().equals(txtOTP.getText())) {
			System.out.println("OTP is correct");
			return true;
		} else {
			System.out.println("OTP is incorrect");
			return false;
		}
	}

	@FXML
	void btnConfirm_Click(ActionEvent event) throws IOException {
		if (!txtCode.getText().isBlank()) {
			if (checkOTP()) {
				showAlert(AlertType.INFORMATION, "Success", null, "OTP is correct.");
				// Tải FXML mới từ ForgotPassword.fxml
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangePassword.fxml"));
				Parent root = loader.load();

				// Lấy stage hiện tại và thiết lập scene mới với FXML đã tải
				Stage stage = (Stage) btnConfirm.getScene().getWindow();
				Scene scene = new Scene(root);

				// Thêm CSS cho scene mới
				String cssPath = getClass().getResource("/application/login.css").toExternalForm();
				scene.getStylesheets().add(cssPath);

				// Thiết lập scene mới cho stage và hiển thị stage
				stage.setScene(scene);
				stage.setTitle("Change Password");
				stage.show();
			} else {
				showAlert(AlertType.ERROR, "Error", null, "OTP is incorrect.");
			}
		} else {
			showAlert(AlertType.WARNING, "Warning", null, "Please enter OTP.");
		}
	}

	@FXML
	void hyberBackSence_Clicked(ActionEvent event) throws IOException {

		// Tải FXML mới từ ForgotPassword.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		Parent root = loader.load();

		// Lấy stage hiện tại và thiết lập scene mới với FXML đã tải
		Stage stage = (Stage) hyberBackSence.getScene().getWindow();
		Scene scene = new Scene(root);

		// Thêm CSS cho scene mới
		String cssPath = getClass().getResource("/application/login.css").toExternalForm();
		scene.getStylesheets().add(cssPath);

		// Thiết lập scene mới cho stage và hiển thị stage
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();
	}

	// Tạo môt randomOTP
	public void randomOTP() {
		Random rd = new Random();
		txtOTP.setText("" + rd.nextInt(1000000 + 1));
	}

	@FXML
	void hyberSendCode_Click(ActionEvent event) {
		if (!txtEmail.getText().isBlank()) {
			randomOTP();
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", 465);
			props.put("mail.smtp.user", "autosoftdev84@gmail.com");
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.debug", true);
			props.put("mail.smtp.socketFactory.port", 465);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", false);

			try {
				Session session = Session.getDefaultInstance(props, null);
				session.setDebug(true);
				MimeMessage message = new MimeMessage(session);
				message.setText("Your OTP is " + txtOTP.getText());
				message.setSubject("OTP Reset Password");
				message.setFrom(new InternetAddress("autosoftdev84@gmail.com"));
				message.addRecipient(RecipientType.TO, new InternetAddress(txtEmail.getText().trim()));
				message.saveChanges();

				Transport transport = session.getTransport("smtp");
				transport.connect("smtp.gmail.com", "autosoftdev84@gmail.com", "acem xnyf nboc oaya");
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();

				JOptionPane.showMessageDialog(null, "OTP has been sent to your Email id");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Please check your internet connection");
			}
		} else {
			showAlert(AlertType.WARNING, "Warning", null, "Please enter your email address.");
			return;
		}
	}

	public static void showAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		txtOTP.setVisible(false);
	}

}
