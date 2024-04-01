package Bus;

import java.io.FileNotFoundException;
import java.net.URL;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {
	private Pane view;
	public Pane getPage(String filename) {
		try {
			URL fileURL = Main.class.getResource("/View/"+filename+".fxml");
			if(fileURL == null)
				throw new FileNotFoundException("File not found");
			view = new FXMLLoader().load(fileURL);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Not found"+filename+".fxml");
		}
		return view;
	}
	}

