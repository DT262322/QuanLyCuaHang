package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import model.CChucVu;
import model.CPhieuNhapKho;
import model.CQuanLyNhanVien;
import model.Fxmlloader;
import model.connectDB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import Bus.BusQuanLyNhanVien;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController implements Initializable {
	@FXML
	private MenuItem MenuLogout;
	@FXML
	private Button btnHome;
   
	@FXML
	private Button btnCustomers;

	@FXML
	private Button btnProduct;
	@FXML
	private SplitMenuButton MenuBtn;
	@FXML
	private MenuItem btnReset;

	@FXML
	private MenuItem btnShutDown;

	@FXML
	private MenuItem btnChangePassword;
	@FXML
	private Menu tbnManage;
	@FXML
	private MenuItem btnStaff;

	@FXML
	private Button btnSupplier;

	@FXML
	private Text lbName;

//    @FXML
//    private Text lblUsername;

	@FXML
	private BorderPane mainPane;

	@FXML
	private MenuBar tabMenu;

	// Phương thức lấy tên username
	public void getUserName(String username) {
//    	lblUsername.setText("Welcome, "+ username);
		lbName.setText(username);
//		System.out.println(username);
	}

	@FXML
	void btnHome_click(ActionEvent event) {
		Fxmlloader object = new Fxmlloader();
		Pane view = object.getPage("Home");
		mainPane.setCenter(view);
	}

	@FXML
	void btnCustomers_click(ActionEvent event) {
		Fxmlloader object = new Fxmlloader();
		Pane view = object.getPage("Customers");
		mainPane.setCenter(view);
	}

	@FXML
	void btnSupplier_click(ActionEvent event) {
		Fxmlloader object = new Fxmlloader();
		Pane view = object.getPage("Suppliers");
		mainPane.setCenter(view);
	}

	@FXML
	void btnProduct_click(ActionEvent event) {
		Fxmlloader object = new Fxmlloader();
		Pane view = object.getPage("SellProduct");
		mainPane.setCenter(view);
	}

	@FXML
	void btnReset_click(ActionEvent event) {

	}

	@FXML
	void btnShutDown_click(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Shutdown......");
		Platform.runLater(() -> {
			System.exit(0);
		});
	}

	// Chuyên scene nhập hàng
	@FXML
	void menuImportProduct_click(ActionEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ImportProduct.fxml"));
			Parent root = loader.load();
			ImportProductController importProductController = loader.getController();
			// truyền userid vào scene khác
			String username = lbName.getText();
			importProductController.setIDNhanVien(username);
			
			Scene scene = new Scene(root);

			// Add the path to your CSS file
			String cssPath = getClass().getResource("/application/importProduct.css").toExternalForm();
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
	void btnChangePassword_click(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangePassword.fxml"));
			Parent root = loader.load();
			ChangePassword changePassController = loader.getController();
			String user = lbName.getText();
			changePassController.getUserID(user);
			changePassController.hyberLogin.setVisible(false);
			
			Scene scene = new Scene(root);
			
			// Add the path to your CSS file
			String cssPath = getClass().getResource("/application/Login.css").toExternalForm();
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
	void btnStaff_click(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Staff.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);

			// Add the path to your CSS file
			String cssPath = getClass().getResource("/application/staff.css").toExternalForm();
			scene.getStylesheets().add(cssPath);

			Stage stage = new Stage();

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Phương thức ẩn chức năng quản lý nhân viên đối với role là nhân viên
	public void getChucVu(CChucVu chucVu) {
		if (chucVu.getChucVuID().equals("ST")) {
			btnStaff.setVisible(false);
		} else {
			btnStaff.setVisible(true);
		}
	}
	//Logout
	@FXML
    void MenuLogout_Click(ActionEvent event) {
		
		try {
			BusQuanLyNhanVien busNhanVien = new BusQuanLyNhanVien();
			String status = "inactive";
			String userid = lbName.getText();
			boolean result = busNhanVien.changeStatusUser(status, userid);
			if(result)
			{
				System.out.println("Đăng xuất thành công");
			}
			else {
				System.out.println("lỖi");
			}
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
	        Parent root = loader.load();

	       
	        Scene scene = new Scene(root);

	        
	        String cssPath = getClass().getResource("/application/Login.css").toExternalForm();
	        scene.getStylesheets().add(cssPath);

	        // Get the stage of the current event source
	        Stage stage = (Stage) MenuLogout.getParentPopup().getOwnerWindow();

	        
	        stage.setScene(scene);
	        stage.setTitle("Login");
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	
	}
}
