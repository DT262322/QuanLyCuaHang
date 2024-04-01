package controller;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.OrderDetail;
import Bus.busProduct;

public class OrderHistoryController implements Initializable {

    @FXML
    private TableColumn<OrderDetail, Integer> colCus_ID;

    @FXML
    private TableColumn<OrderDetail, Date> colDate;

    @FXML
    private TableColumn<OrderDetail, String> colNV_ID;

    @FXML
    private TableColumn<OrderDetail, Integer> colPBH_ID;

    @FXML
    private TableColumn<OrderDetail, String> colPD_Name;

    @FXML
    private TableColumn<OrderDetail, Double> colPrice;

    @FXML
    private TableColumn<OrderDetail, Integer> colQuantity;

    @FXML
    private TableColumn<OrderDetail, String> colStatus;

    @FXML
    private TableView<OrderDetail> tbHistory;

    @FXML
    private TextField txtSearch;

    private busProduct bus;

    private ObservableList<OrderDetail> HisList;

    @FXML
    void checkSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = txtSearch.getText();
            searchHis(searchText);
        }
    }

    private void searchHis(String his) {
        ObservableList<OrderDetail> filteredList = FXCollections.observableArrayList();
        for (OrderDetail history : HisList) {
            String phieubhIdString = String.valueOf(history.getPhieuBh_ID());
            String cusPhoneString = String.valueOf(history.getCustomer_Phone());
            if (phieubhIdString.contains(his) || cusPhoneString.contains(his)) {
                filteredList.add(history);
            }
        }
        tbHistory.setItems(filteredList);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Khởi tạo busProduct
        bus = new busProduct();

        // Lấy danh sách order detail từ busProduct
        ArrayList<OrderDetail> orderDetail = bus.getOrderHistory();

        // Khởi tạo HisList từ danh sách order detail
        HisList = FXCollections.observableArrayList(orderDetail);

        // Gán các giá trị từ danh sách order detail vào các cột trong TableView
        colPBH_ID.setCellValueFactory(new PropertyValueFactory<>("phieuBh_ID"));
        colNV_ID.setCellValueFactory(new PropertyValueFactory<>("nhanVien_tao_don"));
        colCus_ID.setCellValueFactory(new PropertyValueFactory<>("customer_Phone"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("ngay_tao_don"));
        colPD_Name.setCellValueFactory(new PropertyValueFactory<>("product_Name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        // Gán dữ liệu từ HisList vào TableView
        tbHistory.setItems(HisList);
        tbHistory.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
