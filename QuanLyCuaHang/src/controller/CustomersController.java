package controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import Bus.busCustomers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customers;

public class CustomersController implements Initializable {

    @FXML
    private Button btnCusAdd;

    @FXML
    private Button btnCusDelete;

    @FXML
    private Button btnCusUpdate;

    @FXML
    private TableColumn<Customers, String> colAddress;

    @FXML
    private TableColumn<Customers, Date> colDate;

    @FXML
    private TableColumn<Customers, String> colEmail;

    @FXML
    private TableColumn<Customers, Integer> colID;

    @FXML
    private TableColumn<Customers, String> colName;

    @FXML
    private TableColumn<Customers, String> colPhone;

    @FXML
    private TableColumn<Customers, String> colSex;

    @FXML
    private CheckBox femaleBox;

    @FXML
    private CheckBox maleBox;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusEmail;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusPhone;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<Customers> tbCus;
    
    busCustomers bus=new busCustomers();
    Customers cus=null;
    ObservableList<Customers> customersObserableList=FXCollections.observableArrayList();
    @FXML
    void btnCusAdd_click(ActionEvent event) {
        // Lấy Customer_ID mới từ cơ sở dữ liệu
        int maxCustomerId = bus.getMaxCustomerId();
        // Tăng Customer_ID lên 1 để tạo Customer_ID mới
        int newCustomerId = maxCustomerId + 1;
        
        String customerName = txtCusName.getText();
        LocalDate dateOfBirth = txtDate.getValue();
        // Lấy giá trị của checkbox
        String gender = "";
        if (maleBox.isSelected()) {
            gender = "male";
        } else if (femaleBox.isSelected()) {
            gender = "female";
        }
        String address = txtCusAddress.getText();
        String phoneNumber = txtCusPhone.getText();
        String email = txtCusEmail.getText();
        
        Customers newCustomers = new Customers(newCustomerId, customerName, Date.valueOf(dateOfBirth), gender, address, phoneNumber, email);
        // Thêm khách hàng mới vào cơ sở dữ liệu
        boolean added = bus.add(newCustomers);
        if (added) {
            // Nếu thêm thành công, thêm vào danh sách và làm sạch các trường nhập liệu
            customersObserableList.add(newCustomers); 
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi thêm khách hàng!");
        }
    }

    private void clearFields() {
        txtCusID.clear();
        txtCusName.clear();
        txtDate.setValue(null);
        maleBox.setSelected(false);
        femaleBox.setSelected(false);
        txtCusAddress.clear();
        txtCusPhone.clear();
        txtCusEmail.clear();
    }
    @FXML
    void btnCusDelete_click(ActionEvent event) {
    	Customers selectedCus=tbCus.getSelectionModel().getSelectedItem();
    	if(selectedCus !=null) {
    		bus.delete(selectedCus);
    		customersObserableList.remove(selectedCus);
    		JOptionPane.showMessageDialog(null,"xóa thành công");
    		clearFields();
    	}
    }
    @FXML
    void btnCusUpdate_click(ActionEvent event) {
    	String customerId=txtCusID.getText();
    	String customerName=txtCusName.getText();
    	LocalDate dateOfBirth=txtDate.getValue();
    	//lấy giá trị của checkbox
    	String gender = "";
    	    if (maleBox.isSelected()) {
    	        gender = "male";
    	    } else if (femaleBox.isSelected()) {
    	        gender = "female";
    	    }
    	String address=txtCusAddress.getText();
    	String phoneNumber=txtCusPhone.getText();
    	String email=txtCusEmail.getText();
    	Customers updatedCus=new Customers(Integer.parseInt(customerId),customerName,Date.valueOf(dateOfBirth),gender,address,phoneNumber,email);
    	boolean updated =bus.update(updatedCus);
    	 if (updated) {
             JOptionPane.showMessageDialog(null,"cập nhật thành công");
             clearFields();
         } else {
             JOptionPane.showMessageDialog(null,"cập nhật không thành công");
         }
    }
    @FXML
    void handKey(KeyEvent event) { 
    	if (event.getCode() == KeyCode.ENTER) {
            String searchKeyword = txtSearch.getText();
            searchCus(searchKeyword);
        }
    }
    private void searchCus(String cus) {
    	ObservableList<Customers> flteredList=FXCollections.observableArrayList();
    	for(Customers customer:customersObserableList) {
    		if(customer.getPhoneNumber().contains(cus) || customer.getCustomerName().contains(cus))
    		{
    			flteredList.add(customer);
    		}
    	}
    	tbCus.setItems(flteredList);
    }
    @FXML
    void femaleBox_click(ActionEvent event) {
    	if(femaleBox.isSelected()) {
    		maleBox.setSelected(false);
    	}


    }
    @FXML
    void maleBox_click(ActionEvent event) {
    	if(maleBox.isSelected()) {
    		femaleBox.setSelected(false);
    	}


    	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<Customers> customerList=bus.findAll();
		colID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
	    colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	    colDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
	    colSex.setCellValueFactory(new PropertyValueFactory<>("customerSex"));
	    colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
	    colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
	    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	    customersObserableList.addAll(customerList);
	    tbCus.setItems(customersObserableList);
	    tbCus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customers>() {

			@Override
			public void changed(ObservableValue<? extends Customers> arg0, Customers arg1, Customers arg2) {
				// TODO Auto-generated method stub
				int index=tbCus.getSelectionModel().getSelectedIndex();
				if(index==-1) {
					return;
				}
				cus = tbCus.getItems().get(index);
	            txtCusID.setText(String.valueOf(cus.getCustomerId()));
	            txtCusName.setText(cus.getCustomerName());
	            txtCusAddress.setText(cus.getAddress());
	            txtCusEmail.setText(cus.getEmail());
	            txtCusPhone.setText(cus.getPhoneNumber());
	            txtDate.setValue(cus.getDateOfBirth().toLocalDate());
	            String gender = cus.getCustomerSex();
	            if ("male".equalsIgnoreCase(gender)) {
	                maleBox.setSelected(true);
	                femaleBox.setSelected(false);
	            } else if ("female".equalsIgnoreCase(gender)) {
	                maleBox.setSelected(false);
	                femaleBox.setSelected(true);
	            } else {
	                maleBox.setSelected(false);
	                femaleBox.setSelected(false);
	            }
	        
			}
	    	
	    });

	}

}
