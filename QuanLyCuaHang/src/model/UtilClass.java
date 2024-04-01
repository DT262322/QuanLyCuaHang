package model;

import model.CQuanLyNhanVien;
import model.CChucVu;

public class UtilClass {
    private static CQuanLyNhanVien loggedInUser;
    private static CChucVu selectedPosition;
    
    public static void setLoggedInUser(CQuanLyNhanVien user) {
        loggedInUser = user;
    }

    public static CQuanLyNhanVien getLoggedInUser() {
        return loggedInUser;
    }

    public static void setSelectedPosition(CChucVu position) {
        selectedPosition = position;
    }

    public static CChucVu getSelectedPosition() {
        return selectedPosition;
    }
}

