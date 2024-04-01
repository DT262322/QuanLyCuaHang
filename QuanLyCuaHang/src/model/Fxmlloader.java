package model;

import java.io.FileNotFoundException;
import java.net.URL;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Fxmlloader {
private Pane view;


public Pane getPage(String filename)
{
	try {
		URL fileURL=Main.class.getResource("/view/"+filename+".fxml");
		if(fileURL==null)
			throw new FileNotFoundException("file not found");
			view = new FXMLLoader().load(fileURL);
		}catch(Exception e){
			System.out.println("Not find"+filename+".fxml");
		}
		return view;
	}


}
