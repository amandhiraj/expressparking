package Auth.Register;

import java.io.IOException;

import application.Database;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterMain {

	@FXML
	private Label register_txt_lblStatus;
	@FXML
	private TextField register_txt_firstname;
	@FXML
	private TextField register_txt_lastname;
	@FXML
	private TextField register_txt_userPass;
	@FXML
	private TextField register_txt_email;
	@FXML
	private Button register_btn_signup;

	double x, y;

	@FXML
	void register_mouse_dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	void register_mouse_pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void register_mouse_close(MouseEvent event) {
		System.out.println("close");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void register_btn_cancel_pressed(MouseEvent event) {

		System.out.println("logout");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Login/application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			stage.close();
			primaryStage.show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	void register_mouse_minus(MouseEvent event) {
		System.out.println("min");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	public void Register(ActionEvent event) throws Exception {

		Database db = new Database();

		db.load();
		for (User u : db.users) {
			System.out.println(u.toString());
		}

		if (register_txt_email.getText().length() < 3 || !register_txt_email.getText().contains("@")) {
			register_txt_lblStatus.setText("Email is Invalid");
			register_txt_lblStatus.setTextFill(Color.ORANGERED);
		} else if (register_txt_firstname.getText().isBlank()
				|| register_txt_lastname.getText().isBlank() || register_txt_email.getText().isBlank()
				|| register_txt_userPass.getText().isBlank()) {
			register_txt_lblStatus.setText("Please Fill in all the fields!");
			register_txt_lblStatus.setTextFill(Color.ORANGERED);
		} else {
			if (!db.checkRegister(register_txt_email.getText().toLowerCase())) {
				Stage primaryStage = new Stage();
				Stage thisStage = (Stage) register_btn_signup.getScene().getWindow();

				Parent root = FXMLLoader.load(getClass().getResource("../../Auth/Login/Login.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				thisStage.close();
				db.RegisterUser(register_txt_firstname.getText(),
						register_txt_lastname.getText(), register_txt_userPass.getText(), register_txt_email.getText().toLowerCase());
			} else {
				register_txt_lblStatus.setText("Email already exists");
				register_txt_lblStatus.setTextFill(Color.ORANGERED);
			}
		}
	}
}
