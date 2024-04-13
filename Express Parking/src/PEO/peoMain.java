package PEO;

import java.util.ArrayList;

import application.Database;
import application.Parking;
import application.ParkingDB;
import application.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class peoMain {
	double x, y;
	private String userEmail;
	private String userFirstLast;
	private String tempUUID;
	Database db = new Database();
	ParkingDB parkDB = new ParkingDB();

	@FXML
	private TextField peo_tb_spacevalue;
	@FXML
	private Label peo_lbl_space_status;

	@FXML
	private Label peo_lbl_name;

	@FXML
	private Button peo_btn_logout;

	@FXML
	void peo_mouse_dragged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);

	}

	@FXML
	void peo_mouse_pressed(MouseEvent event) throws Exception {
		x = event.getSceneX();
		y = event.getSceneY();
		System.out.println("mouse pressed");
		loadList();
	}

	@FXML
	void peo_mouse_close(MouseEvent event) {
		System.out.println("close");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			db.logOutUser(this.userEmail, false);
			stage.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	void peo_mouse_minus(MouseEvent event) {
		System.out.println("min");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void peo_btn_logout_pressed(MouseEvent event) {
		System.out.println("logout");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			db.logOutUser(this.userEmail, false);
			Stage primaryStage = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("../Auth/Login/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Auth/Login/application.css").toExternalForm());

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
	private ListView<String> peo_listview;

	public void initData(ArrayList<User> users, String currEmail) throws Exception {
		db.load();
		// TODO Auto-generated method stub
		this.userEmail = currEmail;

		for (User u : users) {
			if (u.getEmail().equals(currEmail)) {
				this.userFirstLast = u.getFirstname() + " " + u.getLastname();
			}
		}
		loadList();
		peo_lbl_name.setText("Welcome, " + userFirstLast);

	}

	private void loadList() throws Exception {
		// TODO Auto-generated method stub
		parkDB.parking.clear();
		parkDB.load();
		peo_listview.getItems().clear();
		for (User u : db.users) {
			this.tempUUID = u.getUUID();
			for (Parking p : parkDB.parking) {
				String newuserFirstLast = u.getFirstname() + " " + u.getLastname();
				if (p.getUuid().equals(tempUUID)) {
					if (p.getParkingOne() != "") {
						peo_listview.getItems()
								.add("Name: " + newuserFirstLast + " - UUID: " + p.getUuid() + " - Booking ID: "
										+ p.getParkingOne() + " - Time: " + p.getParkingOneTime() + " - Status: "
										+ p.getParking1PayStatus());
					}
					if (p.getParkingTwo() != "") {
						peo_listview.getItems()
								.add("Name: " + newuserFirstLast + " - UUID: " + p.getUuid() + " - Booking ID: "
										+ p.getParkingTwo() + " - Time: " + p.getParkingTwoTime() + " - Status: "
										+ p.getParking2PayStatus());
					}
					if (p.getParkingThree() != "") {
						peo_listview.getItems()
								.add("Name: " + newuserFirstLast + " - UUID: " + p.getUuid() + " - Booking ID: "
										+ p.getParkingThree() + " - Time: " + p.getParkingThreeTime() + " - Status: "
										+ p.getParking3PayStatus());
					}
				}
			}
		}
	}

	public void removeParking(ActionEvent event) throws Exception {
		db.loadSpaces();
		if (!parkDB.checkOccupation(peo_tb_spacevalue.getText().toString())) {
			db.setSpaces(Database.parkingSpace - Integer.valueOf(peo_tb_spacevalue.getText()));
			peo_lbl_space_status.setText("Space removed");
			peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
		} else {
			peo_lbl_space_status.setText("Space is Occupied");
			peo_lbl_space_status.setTextFill(Color.ORANGERED);
		}
	}

	public void addParking(ActionEvent event) throws Exception {
		db.loadSpaces();
		if (peo_tb_spacevalue.getText().isBlank()) {
			peo_lbl_space_status.setText("Invalid Space.");
			peo_lbl_space_status.setTextFill(Color.ORANGERED);
		}
		if (Database.parkingSpace > 7500
				|| (Database.parkingSpace + Integer.valueOf(peo_tb_spacevalue.getText()) > 7500)) {
			peo_lbl_space_status.setText("Parking space Exceeded.");
			peo_lbl_space_status.setTextFill(Color.ORANGERED);
		} else {
			peo_lbl_space_status.setText("Parking space Increased.");
			peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
			db.setSpaces(Database.parkingSpace + Integer.valueOf(peo_tb_spacevalue.getText()));
		}
	}

	public void cancelRequest(ActionEvent event) throws Exception {
		String[] uuidText = peo_listview.getSelectionModel().getSelectedItem().toString().replaceAll(" ", "")
				.split(":");
		String[] bookingText = peo_listview.getSelectionModel().getSelectedItem().toString().replaceAll(" ", "")
				.split(":");
		System.out.println("UUID =" + uuidText[2].toString().split("-")[0] + " BookingID="
				+ bookingText[3].toString().split("-")[0]);
		parkDB.CancelBooking(bookingText[2].toString().split("-")[0], bookingText[3].toString().split("-")[0]);
		peo_lbl_space_status.setText("Cancelled request.");
		peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
		loadList();
	}

	public void grantRequest(ActionEvent event) throws Exception {
		String[] uuidText = peo_listview.getSelectionModel().getSelectedItem().toString().replaceAll(" ", "")
				.split(":");
		String[] bookingText = peo_listview.getSelectionModel().getSelectedItem().toString().replaceAll(" ", "")
				.split(":");
		
		for (Parking p : parkDB.parking) {
			if(p.getUuid().equals(uuidText[2].toString().split("-")[0]) 
					&& p.getParkingOne().equals(bookingText[3].toString().split("-")[0])) {
				p.setParking1PayStatus("Pending Admin");
				peo_lbl_space_status.setText("Updated user.");
				peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
			}
			if(p.getUuid().equals(uuidText[2].toString().split("-")[0]) 
					&& p.getParkingTwo().equals(bookingText[3].toString().split("-")[0])) {
				p.setParking2PayStatus("Pending Admin");
				peo_lbl_space_status.setText("Updated user.");
				peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
			}
			if(p.getUuid().equals(uuidText[2].toString().split("-")[0]) 
					&& p.getParkingThree().equals(bookingText[3].toString().split("-")[0])) {
				p.setParking3PayStatus("Pending Admin");
				peo_lbl_space_status.setText("Updated user.");
				peo_lbl_space_status.setTextFill(Color.LIMEGREEN);
			}
		}
		parkDB.updateDB("parking.csv");
		loadList();
	}
}
