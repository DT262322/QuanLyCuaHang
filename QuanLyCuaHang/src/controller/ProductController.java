package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Bus.busProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ChiTietPhieuBanHang;
import model.PhieuBanHang;
import model.Product;

public class ProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private SpinnerValueFactory<Integer> spin;
    private Product pd;
    private Image image;
    private busProduct busPD;

    private PhieuBanHang phieuBHang;
    private ObservableList<ChiTietPhieuBanHang> cardCTPBH = FXCollections.observableArrayList();
    // Thêm trường sellProductController để tham chiếu đến SellProductController
    @FXML
    private SellProductController sellProductController;
    
    public void setSellProductController(SellProductController sellProductController) {
        this.sellProductController = sellProductController;
    }
    
    public void setGioHang(ObservableList<ChiTietPhieuBanHang> cardctbh )
    {
    	this.cardCTPBH=cardctbh;
    }
    
    public void setData(Product proData) {
        this.pd = proData;
        if (pd != null) {
            prod_name.setText(proData.getProduct_name());
            prod_price.setText(String.valueOf(proData.getPrice()));
            String imageUrl = proData.getImage_url(); 
            image = new Image(imageUrl, 150, 94, false, true); 
            prod_imageView.setImage(image);
        } else {
            // Xử lý khi pd là null
            System.out.println("pd is null");
        }
    }


    @FXML
    void addBtn(ActionEvent event) {
        try {
            if (pd != null) {
                if (sellProductController != null) {
                    int quantity = prod_spinner.getValue();
                        ChiTietPhieuBanHang newProduct = new ChiTietPhieuBanHang();
                        newProduct.setPhieubh(phieuBHang);
                        newProduct.setPrice(pd.getPrice());
                        newProduct.setProduct_id(pd);
                        newProduct.setSoluong(quantity);
                        newProduct.setTrangthai("0");
                        if(cardCTPBH.contains(newProduct)) 
                        {
                        	int i=cardCTPBH.indexOf(newProduct);
                        	ChiTietPhieuBanHang ct= cardCTPBH.get(i);               	
                        	ct.setSoluong(ct.getSoluong()+newProduct.getSoluong());
                        	System.out.println(ct.getSoluong()+" . "+newProduct.getSoluong());  	
                        }else {
                        	cardCTPBH.add(newProduct);
                        }
                        updatePriceForCart();
                       System.out.println(pd.getProduct_name());
                        System.out.println(phieuBHang.getPhieubhId()+ " co sl sanpham: "+cardCTPBH.size());
                } else {
                    System.out.println("Lỗi: sellProductController chưa được thiết lập"+pd.getProduct_id());
                }
            } else {
                System.out.println("Lỗi: pd chưa được chọn");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    public void setPhieuBanHang(PhieuBanHang phieuBanHang)
    {
    	this.phieuBHang=phieuBanHang;
    }

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }
    private void updatePriceForCart() {
        for (ChiTietPhieuBanHang item : cardCTPBH) {
            double newPrice = item.getProduct_id().getPrice() * item.getSoluong();
            item.setPrice(newPrice);
        }
        sellProductController.updateTotalPrice(); // Sau khi cập nhật giá cho mỗi mặt hàng trong giỏ hàng, gọi phương thức updateTotalPrice() của SellProductController để cập nhật tổng giá trị.
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        busPD = new busProduct();
        ArrayList<Product> productList = busPD.findAll();
        if (!productList.isEmpty()) {
            Product product = productList.get(0);
            setData(product);
        } else {
            System.out.println("Danh sách sản phẩm rỗng");
        }
      
        setQuantity();
        prod_spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            updatePriceForCart();
        });
    }

}
