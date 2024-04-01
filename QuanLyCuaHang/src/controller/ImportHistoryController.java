package controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.WindowConstants;

import Bus.busImportProduct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.CPhieuNhapKho;
import model.connectDB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ImportHistoryController implements Initializable {
	@FXML
	private Button btnSearchPN;
	@FXML
	private Hyperlink hyberReset;
	@FXML
	private ListView<CPhieuNhapKho> lsvHistory;
	@FXML
	private TextField txtSearchPN;
	// Khai báo
	busImportProduct busImport = new busImportProduct();
	ArrayList<CPhieuNhapKho> phieunhap = new ArrayList<>();
	// Lấy ra đường dần hiện tại
	File currentDirection = new File("");
	String linkDirection = currentDirection.getAbsolutePath();

	@FXML
	void btnSearchPN_Click(ActionEvent event) {
		 String textSearch = txtSearchPN.getText();
		    ArrayList<CPhieuNhapKho> phieuNhapOjb = new ArrayList<>();

		    try {
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        java.util.Date ngayNhapUtil = dateFormat.parse(textSearch);
		        java.sql.Date ngayNhap = new java.sql.Date(ngayNhapUtil.getTime());
		        phieuNhapOjb = busImport.timKiemPNByNgayNhap(ngayNhap);
		    } catch (IllegalArgumentException | ParseException e) {
		        
		        phieuNhapOjb = busImport.timKiemPNByMaHoaDon(textSearch);
		    }

		    lsvHistory.getItems().clear();
		    lsvHistory.getItems().addAll(phieuNhapOjb);

	}

	@FXML
	void hyberReset_click(ActionEvent event) {
		lsvHistory.getItems().clear();
		lsvHistory.getItems().addAll(phieunhap);
	}

	@FXML
	void listview_DobleClick(MouseEvent event) {
		if (event.getClickCount() == 2) {

			CPhieuNhapKho selectItem = lsvHistory.getSelectionModel().getSelectedItem();
			if (selectItem != null) {
				String getID = selectItem.getPhieuNhapID();

				connectDB conDB = new connectDB();
				Connection con = conDB.getConnection();
				try {

					System.out.println(this.getClass().getPackageName());
					JasperDesign jDesign = JRXmlLoader.load(linkDirection + "\\src\\report\\QuanLyCuaHang.jrxml");
					JasperReport jReport = JasperCompileManager.compileReport(jDesign);

					// Tạo một HashMap để lưu trữ tham số và giá trị của chúng

					HashMap<String, Object> parameters = new HashMap<>();
					parameters.put("phieunhap_id", getID); // Đặt giá trị cho tham số "maloai"

					JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters, con);
					JasperViewer viewer = new JasperViewer(jPrint);

					viewer.setTitle("Lịch sử");
					JasperViewer.viewReport(jPrint, false);
					;

				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// add danh sách hóa đơn vào listview
		phieunhap = busImport.getIDPhieuNhap();
		lsvHistory.getItems().addAll(phieunhap);
	}

}
