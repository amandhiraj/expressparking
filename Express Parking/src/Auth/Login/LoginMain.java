package Auth.Login;

import java.io.IOException;

import Admin.AdminMain;
import Customer.CustomerMain;
import PEO.peoMain;
import application.Database;
import application.User;
import javafx.application.Platform;
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

public class LoginMain {
	@FXML
	private Label login_txt_lblStatus;
	@FXML
	private TextField login_txt_userEmail;
	@FXML
	private TextField login_txt_userPass;
	@FXML
	private Button login_btn_login;
	@FXML
	private Button login_btn_register;
	double x, y;


	Database db = new Database();

	@FXML
	private Label login_lbl_name;
	@FXML
	private Button login_btn_logout;

	@FXML
	void login_mouse_dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	void login_mouse_pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void login_mouse_close(MouseEvent event) {
		System.out.println("close");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void login_mouse_minus(MouseEvent event) {
		System.out.println("min");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	public void openRegisterGUI(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Stage thisStage = (Stage) login_btn_register.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("../Register/Register.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
		thisStage.close();
	}

	public void Login(ActionEvent event) throws Exception {

		Database db = new Database();

		db.load();
		for (User u : db.users) {
			System.out.println(u.toString());
		}

		if (db.checkLogin(login_txt_userEmail.getText().toLowerCase(), login_txt_userPass.getText())) {
			int perms = db.getUserPerms(login_txt_userEmail.getText().toLowerCase());
			FXMLLoader loader = null;

			Stage thisStage = (Stage) login_btn_login.getScene().getWindow();
			Stage stage = new Stage();

			switch (perms) {
			case 1:
				System.out.println(perms);
				loader = new FXMLLoader(getClass().getResource("../../Customer/customerUI.fxml"));
				stage.setScene(new Scene(loader.load()));
				CustomerMain cm = loader.getController();
				cm.initData(db.users, login_txt_userEmail.getText().toLowerCase());
				break;
			case 2:
				System.out.println(perms);
				loader = new FXMLLoader(getClass().getResource("../../PEO/peoUI.fxml"));
				stage.setScene(new Scene(loader.load()));
				peoMain peo = loader.getController();
				peo.initData(db.users, login_txt_userEmail.getText().toLowerCase());
				break;
			case 3:
				System.out.println(perms);
				loader = new FXMLLoader(getClass().getResource("../../Admin/adminUI.fxml"));
				stage.setScene(new Scene(loader.load()));
				AdminMain admin = loader.getController();
				admin.initData(db.users, login_txt_userEmail.getText().toLowerCase());
				break;
			}

			// update userStatus
			db.updateLoginStatus(login_txt_userEmail.getText().toLowerCase(), login_txt_userPass.getText(), true);

			stage.setOnCloseRequest(e -> {
				try {
					db.logOutUser(login_txt_userEmail.getText().toLowerCase(), false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Platform.exit();
			});
			stage.setResizable(false);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();
			thisStage.close();
			login_txt_lblStatus.setTextFill(Color.GREEN);
		} else {
			login_txt_lblStatus.setText("Login Faild user does not exist");
			login_txt_lblStatus.setTextFill(Color.valueOf("#ff7675"));
		}
	}

}
