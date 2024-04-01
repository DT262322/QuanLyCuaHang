package model;

public class User {
private int id;
private String userName;
private String passWord;
private String hoTen;
private String roLe;
public User(int id, String userName, String passWord, String hoTen, String roLe) {
	super();
	this.id = id;
	this.userName = userName;
	this.passWord = passWord;
	this.hoTen = hoTen;
	this.roLe = roLe;
}



public User() {

}



public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
public String getHoTen() {
	return hoTen;
}
public void setHoTen(String hoTen) {
	this.hoTen = hoTen;
}
public String getRoLe() {
	return roLe;
}
public void setRoLe(String roLe) {
	this.roLe = roLe;
}
@Override
public String toString() {
    return "User{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", passWord='" + passWord + '\'' +
            ", hoTen='" + hoTen + '\'' +
            ", roLe='" + roLe + '\'' +
            '}';
}



public User(String userName, String passWord, String hoTen, String roLe) {
	super();
	this.userName = userName;
	this.passWord = passWord;
	this.hoTen = hoTen;
	this.roLe = roLe;
}



public User( String hoTen, String passWord,String roLe) {
	super();
	this.passWord = passWord;
	this.hoTen = hoTen;
	this.roLe = roLe;
}


}
