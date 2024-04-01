package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Bus.busSuppliers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customers;
import model.Suppliers;

public class SuppliersController implements Initializable {

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private TableView<Suppliers> tbSuppliers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableColumn<Suppliers, String> colAddress;

    @FXML
    private TableColumn<Suppliers, String> colEmail;

    @FXML
    private TableColumn<Suppliers, String> colID;

    @FXML
    private TableColumn<Suppliers, String> colName;

    @FXML
    private TableColumn<Suppliers, String> colPhone;
    
    Suppliers supp=null;
    ArrayList<Suppliers> sp=new ArrayList<>();
    busSuppliers bus=new busSuppliers();
    ObservableList<Suppliers> supplierObservableList=FXCollections.observableArrayList();
    
    
    

    @FXML
    void handKey(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
            String searchKeyword = txtSearch.getText();
            searchSup(searchKeyword);
        }
    }
    private void searchSup(String sup) {
    	ObservableList<Suppliers> flteredList=FXCollections.observableArrayList();
    	for(Suppliers suppliers:supplierObservableList) {
    		if(suppliers.getSupplier_name().contains(sup) || suppliers.getSupplier_id().contains(sup))
    		{
    			flteredList.add(suppliers);
    		}
    	}
    	tbSuppliers.setItems(flteredList);
    }
    
    @FXML
    void btnAddSupplier_click(ActionEvent event) {
    	String supplier_id=txtID.getText();
    	boolean isDuplicate=supplierObservableList.stream().anyMatch(suppliers->(suppliers.getSupplier_id().equals(supplier_id)));
    	if(isDuplicate)
    	{
    		JOptionPane.showMessageDialog(null, "ID bị trùng,xin hãy nhập lại !");
    		return;
    	}
    	String supplier_name=txtName.getText();
    	String phone_number=txtPhone.getText();
    	String address=txtAddress.getText();
    	String supplier_email=txtEmail.getText();
    	Suppliers sp=new Suppliers(supplier_id, supplier_name, phone_number, address, supplier_email);
    	bus.Add(sp);
    	supplierObservableList.add(sp);
    	clearTxtField();
    }
    
    public void clearTxtField() {
    	txtID.clear();
    	txtName.clear();	
    	txtPhone.clear();
    	txtAddress.clear();
    	txtEmail.clear();
    }
    
    
    @FXML
    void btnDeleteSupplier_click(ActionEvent event) {
    	Suppliers selectedSuppliers=tbSuppliers.getSelectionModel().getSelectedItem();
    	if(selectedSuppliers !=null)
    	{
    		bus.delete(selectedSuppliers);
    		supplierObservableList.remove(selectedSuppliers);
    		JOptionPane.showMessageDialog(null,"xóa thành công");
    		clearTxtField();
    	}
    }

    
    @FXML
    void btnUpdateSupplier_click(ActionEvent event) {
    	String supplier_id=txtID.getText();
    	String supplier_name=txtName.getText();
    	String phone_number=txtPhone.getText();
    	String address=txtAddress.getText();
    	String supplier_email=txtEmail.getText();
    	Suppliers sp=new Suppliers(supplier_id, supplier_name, phone_number, address, supplier_email);
    	boolean updated =bus.Update(sp);
    	if(updated) {
    		int selectedIndex=tbSuppliers.getSelectionModel().getSelectedIndex();
    		supplierObservableList.set(selectedIndex, sp);
    		  JOptionPane.showMessageDialog(null,"cập nhật thành công");
    		clearTxtField();
    	}JOptionPane.showMessageDialog(null,"cập nhật không thành công");
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<Suppliers> supplierList=bus.findAll();
		colID.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		supplierObservableList.addAll(supplierList);
		tbSuppliers.setItems(supplierObservableList);
		tbSuppliers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Suppliers>() {

			@Override
			public void changed(ObservableValue<? extends Suppliers> arg0, Suppliers arg1, Suppliers arg2) {
				// TODO Auto-generated method stub
				int index=tbSuppliers.getSelectionModel().getSelectedIndex();//(note)lấy chỉ mục của dòng được chọn
				if(index==-1) {
					return;//(note)nếu không có mục nào được chọn, thì thoát khỏi phương thức
				}
				//đặt các giá trị vào trường văn bản
				supp=tbSuppliers.getItems().get(index);
				txtName.setText(supp.getSupplier_name());
				txtID.setText(supp.getSupplier_id());
				txtPhone.setText(supp.getPhone_number());
				txtAddress.setText(supp.getAddress());
				txtEmail.setText(supp.getEmail());
			}
			
		});
	}

}
